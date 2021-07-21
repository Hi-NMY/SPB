package com.example.spb.entity;

import org.litepal.crud.LitePalSupport;

public class Notice extends LitePalSupport {

    private String user_name;
    private String user_account;
    private String pb_id;
    private String notice_date;
    private int comment_id;
    private int push_fun;
    private int see;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getPb_id() {
        return pb_id;
    }

    public void setPb_id(String pb_id) {
        this.pb_id = pb_id;
    }

    public int getComment_id() {
        return comment_id;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public int getPush_fun() {
        return push_fun;
    }

    public void setPush_fun(int push_fun) {
        this.push_fun = push_fun;
    }

    public String getNotice_date() {
        return notice_date;
    }

    public void setNotice_date(String notice_date) {
        this.notice_date = notice_date;
    }

    public int getSee() {
        return see;
    }

    public void setSee(int see) {
        this.see = see;
    }
}
