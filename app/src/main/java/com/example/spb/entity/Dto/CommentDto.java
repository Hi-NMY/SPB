package com.example.spb.entity.Dto;

import com.example.spb.entity.Comment;

/**
 * @author nmy
 * @title: CommentDto
 * @date 2022-01-30 14:52
 */
public class CommentDto extends Comment {

    public String pb_one_id;
    public String comment_art;
    public String comment_date;
    public String comment_user;
    public String user_name;
    public String comment_touser;
    public String user_toname;
    public int comment_id;
    public String cache_account;
    public String user_ip;

    public String getPb_one_id() {
        return pb_one_id == null ? "" : pb_one_id;
    }

    public String getComment_art() {
        return comment_art == null ? "" : comment_art;
    }

    public String getComment_date() {
        return comment_date == null ? "" : comment_date;
    }

    public String getComment_user() {
        return comment_user == null ? "" : comment_user;
    }

    public String getUser_name() {
        return user_name == null ? "" : user_name;
    }

    public String getComment_touser() {
        return comment_touser == null ? "" : comment_touser;
    }

    public String getUser_toname() {
        return user_toname == null ? "" : user_toname;
    }

    public String getCache_account() {
        return cache_account == null ? "" : cache_account;
    }

    public String getUser_ip() {
        return user_ip == null ? "" : user_ip;
    }

    public void setPb_one_id(String pb_one_id) {
        this.pb_one_id = pb_one_id;
    }

    public void setComment_art(String comment_art) {
        this.comment_art = comment_art;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public void setComment_user(String comment_user) {
        this.comment_user = comment_user;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setComment_touser(String comment_touser) {
        this.comment_touser = comment_touser;
    }

    public void setUser_toname(String user_toname) {
        this.user_toname = user_toname;
    }

    public void setComment_id(int comment_id) {
        this.comment_id = comment_id;
    }

    public void setCache_account(String cache_account) {
        this.cache_account = cache_account;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }
}
