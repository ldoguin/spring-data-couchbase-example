package org.couchbase.advocacy.metrics.twitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.function.Consumer;

@Service
public class TwitterService {

    private final Twitter twitter;

    private final TwitterUpdateRepository twitterUpdateRepository;

    private String keyReg = "tw-%s-%d";

    @Autowired
    TwitterService(Twitter twitter, TwitterUpdateRepository twitterUpdateRepository) {
        this.twitter = twitter;
        this.twitterUpdateRepository = twitterUpdateRepository;
    }

    public void update(String twitterAccount){
        TwitterProfile twitterProfile = twitter.userOperations().getUserProfile(twitterAccount);
        int followers = twitterProfile.getFollowersCount();
        int favorites = twitterProfile.getFavoritesCount();
        int friends = twitterProfile.getFriendsCount();
        int statuses = twitterProfile.getStatusesCount();
        long createdAt = new Date().getTime();
        String key = String.format(keyReg, twitterAccount, createdAt);
        TwitterUpdate tu = new TwitterUpdate(key, createdAt, twitterAccount, followers, favorites, friends, statuses);
        twitterUpdateRepository.save(tu);
    }
}