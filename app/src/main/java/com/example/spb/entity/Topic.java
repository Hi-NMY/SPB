package com.example.spb.entity;

public class Topic {

    private int id;
    private String topic_name;
    private int topic_barnum;
    private int topic_attentionnum;
    private String topic_slogan;
    private String topiv_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public int getTopic_barnum() {
        return topic_barnum;
    }

    public void setTopic_barnum(int topic_barnum) {
        this.topic_barnum = topic_barnum;
    }

    public int getTopic_attentionnum() {
        return topic_attentionnum;
    }

    public void setTopic_attentionnum(int topic_attentionnum) {
        this.topic_attentionnum = topic_attentionnum;
    }

    public String getTopic_slogan() {
        return topic_slogan;
    }

    public void setTopic_slogan(String topic_slogan) {
        this.topic_slogan = topic_slogan;
    }

    public String getTopiv_image() {
        return topiv_image;
    }

    public void setTopiv_image(String topiv_image) {
        this.topiv_image = topiv_image;
    }
}
