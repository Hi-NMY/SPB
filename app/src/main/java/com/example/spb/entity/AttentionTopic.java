package com.example.spb.entity;

public class AttentionTopic {

    public int id;
    public int topic_id;
    public String topic_name;
    public String topic_date;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_date() {
        return topic_date;
    }

    public void setTopic_date(String topic_date) {
        this.topic_date = topic_date;
    }
}
