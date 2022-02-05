package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: UpdatePasswordDto
 * @date 2022-01-30 14:40
 */
public class UpdatePasswordDto {

    private String user_account;
    private String user_password_old;
    private String user_password;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_password_old() {
        return user_password_old;
    }

    public void setUser_password_old(String user_password_old) {
        this.user_password_old = user_password_old;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
}
