package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: VerifyPasswordDto
 * @date 2022-01-30 14:39
 */
public class VerifyPasswordDto {

    private String user_account;
    private String user_ip;
    private String user_password;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_ip() {
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        this.user_ip = user_ip;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
