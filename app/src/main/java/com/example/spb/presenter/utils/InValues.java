package com.example.spb.presenter.utils;


import com.example.spb.app.MyApplication;

public class InValues {

    public static String send(int i) {
        return MyApplication.getContext().getResources().getString(i);
    }
}
