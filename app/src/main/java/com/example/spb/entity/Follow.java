package com.example.spb.entity;

import java.io.Serializable;

public class Follow implements Serializable {

    public int id;
    public String user_account;
    public String cache_account;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_accountl) {
        this.user_account = user_accountl;
    }

    public String getCache_account() {
        return cache_account;
    }

    public void setCache_account(String cache_account) {
        this.cache_account = cache_account;
    }
}
