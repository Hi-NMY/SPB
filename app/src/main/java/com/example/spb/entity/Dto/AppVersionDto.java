package com.example.spb.entity.Dto;

/**
 * @author nmy
 * @title: AppVersionDto
 * @date 2022-02-07 17:27
 */
public class AppVersionDto {

    private int versionCode;
    private String versionName;
    private String detailed;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getDetailed() {
        return detailed;
    }

    public void setDetailed(String detailed) {
        this.detailed = detailed;
    }
}
