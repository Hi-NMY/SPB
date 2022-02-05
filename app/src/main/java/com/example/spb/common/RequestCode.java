package com.example.spb.common;

public class RequestCode {

    public static final int SUCCESS = 200;
    public static final int ERROR = 4004;

    private int code;
    private String messgae;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessgae() {
        return messgae;
    }

    public void setMessgae(String messgae) {
        this.messgae = messgae;
    }
}