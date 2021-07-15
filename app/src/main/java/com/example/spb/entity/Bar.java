package com.example.spb.entity;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Bar implements Serializable {

    private String user_name;
    private String user_account;
    private String pb_one_id;
    private String pb_date;
    private String pb_article;
    private String pb_image_url;
    private String pb_voice;
    private String pb_video;
    private String pb_topic;
    private String pb_location;
    private int pb_thumb_num;
    private int pb_comment_num;

    private Bitmap videoBitmap;

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

    public String getPb_one_id() {
        return pb_one_id;
    }

    public void setPb_one_id(String pb_one_id) {
        this.pb_one_id = pb_one_id;
    }

    public String getPb_date() {
        return pb_date;
    }

    public void setPb_date(String pb_date) {
        this.pb_date = pb_date;
    }

    public String getPb_article() {
        return pb_article;
    }

    public void setPb_article(String pb_article) {
        this.pb_article = pb_article;
    }

    public String getPb_image_url() {
        return pb_image_url;
    }

    public void setPb_image_url(String pb_image_url) {
        this.pb_image_url = pb_image_url;
    }

    public String getPb_voice() {
        return pb_voice;
    }

    public void setPb_voice(String pb_voice) {
        this.pb_voice = pb_voice;
    }

    public String getPb_topic() {
        return pb_topic;
    }

    public void setPb_topic(String pb_topic) {
        this.pb_topic = pb_topic;
    }

    public String getPb_location() {
        return pb_location;
    }

    public void setPb_location(String pb_location) {
        this.pb_location = pb_location;
    }

    public int getPb_thumb_num() {
        return pb_thumb_num;
    }

    public void setPb_thumb_num(int pb_thumb_num) {
        this.pb_thumb_num = pb_thumb_num;
    }

    public int getPb_comment_num() {
        return pb_comment_num;
    }

    public void setPb_comment_num(int pb_comment_num) {
        this.pb_comment_num = pb_comment_num;
    }

    public String getPb_video() {
        return pb_video;
    }

    public void setPb_video(String pb_video) {
        this.pb_video = pb_video;
    }

    public Bitmap getVideoBitmap() {
        return videoBitmap;
    }

    public void setVideoBitmap(Bitmap videoBitmap) {
        this.videoBitmap = videoBitmap;
    }
}
