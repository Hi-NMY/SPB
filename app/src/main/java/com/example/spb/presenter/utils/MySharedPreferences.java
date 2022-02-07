package com.example.spb.presenter.utils;

import android.content.Context;
import android.content.SharedPreferences;
import com.example.spb.app.MyApplication;

public class MySharedPreferences {

    private static SharedPreferences.Editor editor;

    public static SharedPreferences.Editor saveShared(String sharedName) {
        SharedPreferences sharedPreferences = MyApplication.getContext().getSharedPreferences(sharedName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        return editor;
    }

    public static SharedPreferences getShared(String sharedName) {
        return MyApplication.getContext().getSharedPreferences(sharedName, Context.MODE_PRIVATE);
    }

    public static void startSave() {
        editor.apply();
    }

    public static boolean startSave2() {
        return editor.commit();
    }

}
