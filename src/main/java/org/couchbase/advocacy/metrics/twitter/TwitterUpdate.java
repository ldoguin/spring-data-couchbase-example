package org.couchbase.advocacy.metrics.twitter;

import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;
import org.springframework.data.couchbase.core.mapping.Field;

@Document(expiry = 0)
public class TwitterUpdate {

    @Id
    @Field("id")
    private String key;

    @Field
    private long createdAt;

    @Field
    private final String type = "twitterUpdate";

    @Field
    private String account;

    @Field
    private int followers;

    @Field
    private int favorites;

    @Field
    private int friends;

    @Field
    private int statuses;

    public TwitterUpdate(String key, long createdAt, String account, int followers, int favorites, int friends, int statuses) {
        this.key = key;
        this.createdAt = createdAt;
        this.account = account;
        this.followers = followers;
        this.favorites = favorites;
        this.friends = friends;
        this.statuses = statuses;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getType() {
        return type;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public int getFavorites() {
        return favorites;
    }

    public void setFavorites(int favorites) {
        this.favorites = favorites;
    }

    public int getFriends() {
        return friends;
    }

    public void setFriends(int friends) {
        this.friends = friends;
    }

    public int getStatuses() {
        return statuses;
    }

    public void setStatuses(int statuses) {
        this.statuses = statuses;
    }

    @Override
    public String toString() {
        return "TwitterUpdate{" +
                "key='" + key + '\'' +
                ", createdAt=" + createdAt +
                ", type='" + type + '\'' +
                ", account='" + account + '\'' +
                ", followers=" + followers +
                ", favorites=" + favorites +
                ", friends=" + friends +
                ", statuses=" + statuses +
                '}';
    }
}