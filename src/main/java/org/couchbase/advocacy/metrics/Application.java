package org.couchbase.advocacy.metrics;

import com.couchbase.client.protocol.views.Query;
import com.couchbase.client.protocol.views.Stale;
import com.couchbase.client.protocol.views.ViewResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.couchbase.advocacy.metrics.twitter.TwitterService;
import org.couchbase.advocacy.metrics.twitter.TwitterUpdate;
import org.couchbase.advocacy.metrics.twitter.TwitterUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.impl.TwitterTemplate;

import java.util.Arrays;
import java.util.List;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@EnableCouchbaseRepositories
public class Application extends AbstractCouchbaseConfiguration {

    private final static Log log = LogFactory.getLog(Application.class);

    @Value("${couchbase.cluster.bucket:default}")
    private String bucketName;

    @Value("${couchbase.cluster.password:}")
    private String password;

    @Value("${couchbase.cluster.ip:127.0.0.1}")
    private String ip;

    @Value("${couchbase.social.twitter.twitterConsumerKey}")
    private String twitterConsumerKey;

    @Value("${couchbase.social.twitter.twitterConsumerSecret}")
    private String twitterConsumerSecret;

    @Value("${couchbase.social.twitter.twitterAccessToken}")
    private String twitterAccessToken;

    @Value("${couchbase.social.twitter.twitterAccessTokenSecret}")
    private String twitterAccessTokenSecret;

    @Override
    protected List<String> bootstrapHosts() {
        return Arrays.asList(ip);
    }

    @Override
    protected String getBucketName() {
        return bucketName;
    }

    @Override
    protected String getBucketPassword() {
        return password;
    }

    @Bean
    Twitter twitter() {
        return new TwitterTemplate(twitterConsumerKey, twitterConsumerSecret,
                twitterAccessToken, twitterAccessTokenSecret);
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.close();
    }

    @Bean
    CommandLineRunner commandLineRunner(TwitterUpdateRepository repo, TwitterService service) {
        return args -> {
            repo.deleteAll();

            TwitterUpdate tu = new TwitterUpdate("key", 0, "ldoguin", 1, 1, 1, 1);
            repo.save(tu);
            TwitterUpdate tuFromCouchbase = repo.findOne(tu.getKey());

            TwitterUpdate tu2 = new TwitterUpdate("key2", 0, "ldoguin", 1, 1, 1, 1);
            TwitterUpdate tu3 = new TwitterUpdate("key3", 0, "ldoguin", 1, 1, 1, 1);
            List<TwitterUpdate> updateList = Arrays.asList(tu2, tu3);
            repo.save(updateList);

            service.update("ldoguin");

            Query query = new Query();
            query.setIncludeDocs(true);
            query.setStale(Stale.FALSE);
            ViewResponse vr = couchbaseTemplate().queryView("twitterupdate", "all", query);
            vr.iterator().forEachRemaining((update) -> log.info(update.getDocument()));

        };
    }

}



