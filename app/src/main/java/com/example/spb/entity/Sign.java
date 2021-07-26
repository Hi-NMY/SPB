package com.example.spb.entity;

import java.io.Serializable;

public class Sign implements Serializable {

    public String user_account;
    public int sign_coin;
    public int sign_right;
    public String sign_day;
    public String sign_star_badge;
    public String sign_task_badge;
    public String sign_like_badge;
    public int sign_ct_badge;


    public int getSign_right() {
        return sign_right;
    }

    public void setSign_right(int sign_right) {
        this.sign_right = sign_right;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public int getSign_coin() {
        return sign_coin;
    }

    public void setSign_coin(int sign_coin) {
        this.sign_coin = sign_coin;
    }

    public String getSign_day() {
        return sign_day;
    }

    public void setSign_day(String sign_day) {
        this.sign_day = sign_day;
    }

    public String getSign_star_badge() {
        return sign_star_badge;
    }

    public void setSign_star_badge(String sign_star_badge) {
        this.sign_star_badge = sign_star_badge;
    }

    public String getSign_task_badge() {
        return sign_task_badge;
    }

    public void setSign_task_badge(String sign_task_badge) {
        this.sign_task_badge = sign_task_badge;
    }

    public String getSign_like_badge() {
        return sign_like_badge;
    }

    public void setSign_like_badge(String sign_like_badge) {
        this.sign_like_badge = sign_like_badge;
    }

    public int getSign_ct_badge() {
        return sign_ct_badge;
    }

    public void setSign_ct_badge(int sign_ct_badge) {
        this.sign_ct_badge = sign_ct_badge;
    }
}
