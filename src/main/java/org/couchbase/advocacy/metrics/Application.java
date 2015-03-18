package org.couchbase.advocacy.metrics;

import com.couchbase.client.CouchbaseClient;
import net.spy.memcached.PersistTo;
import net.spy.memcached.ReplicateTo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.couchbase.advocacy.metrics.twitter.TwitterUpdate;
import org.couchbase.advocacy.metrics.twitter.TwitterUpdateRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;
import org.springframework.data.couchbase.repository.config.EnableCouchbaseRepositories;

import java.util.Arrays;
import java.util.List;

@Configuration
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

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        ctx.close();
    }

    @Bean
    CommandLineRunner commandLineRunner(TwitterUpdateRepository repo) {
        return args -> {
            repo.deleteAll();
            TwitterUpdate tu = new TwitterUpdate("key", 0, "ldoguin", 1, 1, 1, 1);
            repo.save(tu);
            TwitterUpdate tuFromCouchbase = repo.findOne(tu.getKey());
            log.info(tuFromCouchbase);

            TwitterUpdate tu2 = new TwitterUpdate("key2", 0, "ldoguin", 1, 1, 1, 1);
            TwitterUpdate tu3 = new TwitterUpdate("key3", 0, "ldoguin", 1, 1, 1, 1);
            List<TwitterUpdate> updateList = Arrays.asList(tu2, tu3);
            repo.save(updateList);
            Iterable<TwitterUpdate> tUpdates = repo.findAll();
            tUpdates.forEach((update) -> log.info(update));
        };
    }

}



