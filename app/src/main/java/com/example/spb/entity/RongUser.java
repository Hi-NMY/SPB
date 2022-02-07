package com.example.spb.entity;

import com.google.gson.annotations.SerializedName;

public class RongUser {

    @SerializedName("code")
    private int code;

    @SerializedName("userId")
    private String userId;

    @SerializedName("token")
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
