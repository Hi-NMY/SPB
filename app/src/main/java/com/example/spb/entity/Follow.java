package com.example.spb.entity;

import java.io.Serializable;

public class Follow implements Serializable {

    public int id;
    public String follow_account;
    public String followed_account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFollow_account() {
        return follow_account;
    }

    public void setFollow_account(String follow_account) {
        this.follow_account = follow_account;
    }

    public String getFollowed_account() {
        return followed_account;
    }

    public void setFollowed_account(String followed_account) {
        this.followed_account = followed_account;
    }
}
