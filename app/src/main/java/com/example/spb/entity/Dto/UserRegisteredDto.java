package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: UserRegisteredDto
 * @date 2022-01-30 15:12
 */
public class UserRegisteredDto {

    private String user_account;
    private String user_password;
    private String user_name;
    private String user_token;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }
}
