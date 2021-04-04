package com.example.spb.entity;

import com.google.gson.annotations.SerializedName;

public class RongUser {

    @SerializedName("id")
    private int code;

    @SerializedName("user_name")
    private String userId;

    @SerializedName("user_token")
    private String token;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
