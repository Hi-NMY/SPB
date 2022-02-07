package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: UserBadgeDto
 * @date 2022-02-06 10:40
 */
public class UserBadgeDto {

    private String sign_star_badge;
    private String sign_task_badge;
    private String sign_like_badge;
    private int sign_ct_badge;

    public String getSign_star_badge() {
        return sign_star_badge;
    }

    public void setSign_star_badge(String sign_star_badge) {
        this.sign_star_badge = sign_star_badge;
    }

    public String getSign_task_badge() {
        return sign_task_badge;
    }

    public void setSign_task_badge(String sign_task_badge) {
        this.sign_task_badge = sign_task_badge;
    }

    public String getSign_like_badge() {
        return sign_like_badge;
    }

    public void setSign_like_badge(String sign_like_badge) {
        this.sign_like_badge = sign_like_badge;
    }

    public int getSign_ct_badge() {
        return sign_ct_badge;
    }

    public void setSign_ct_badge(int sign_ct_badge) {
        this.sign_ct_badge = sign_ct_badge;
    }
}
