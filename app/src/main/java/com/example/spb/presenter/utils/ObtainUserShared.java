package com.example.spb.presenter.utils;

import android.content.SharedPreferences;
import com.example.spb.R;

public class ObtainUserShared {

    private static final SharedPreferences sharedPreferences;

    static {
        sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
    }

    public static String getUserName() {
        return sharedPreferences.getString(InValues.send(R.string.user_name), "");
    }

    public static String getUserAccount() {
        return sharedPreferences.getString(InValues.send(R.string.user_account), "");
    }

    public static String getUserFavority() {
        return sharedPreferences.getString(InValues.send(R.string.user_favorite), "");
    }

    public static String getUserPassword() {
        return sharedPreferences.getString(InValues.send(R.string.user_password), "");
    }

    public static String getUserBirth() {
        return sharedPreferences.getString(InValues.send(R.string.user_birth), "");
    }

    public static String getUserhome() {
        return sharedPreferences.getString(InValues.send(R.string.user_home), "");
    }

    public static String getUserProfile() {
        return sharedPreferences.getString(InValues.send(R.string.user_profile), "");
    }

    public static String getUserPrivacy() {
        return sharedPreferences.getString(InValues.send(R.string.user_privacy), "");
    }

    public static String getUserIp() {
        return sharedPreferences.getString(InValues.send(R.string.user_ip), "");
    }

    public static String getUserToken() {
        return sharedPreferences.getString(InValues.send(R.string.user_token), "");
    }
}
