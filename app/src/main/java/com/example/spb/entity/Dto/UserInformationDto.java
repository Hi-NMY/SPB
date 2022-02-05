package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: UserInformationDto
 * @date 2022-01-30 15:11
 */
public class UserInformationDto extends UserDto{

    private String user_account;
    private String user_profile;
    private String user_name;
    private String user_birth;
    private String user_home;
    private String user_favorite;

    public String getUser_account() {
        return user_account;
    }

    public void setUser_account(String user_account) {
        this.user_account = user_account;
    }

    public String getUser_profile() {
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        this.user_profile = user_profile;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_birth() {
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        this.user_birth = user_birth;
    }

    public String getUser_home() {
        return user_home;
    }

    public void setUser_home(String user_home) {
        this.user_home = user_home;
    }

    public String getUser_favorite() {
        return user_favorite;
    }

    public void setUser_favorite(String user_favorite) {
        this.user_favorite = user_favorite;
    }
}
