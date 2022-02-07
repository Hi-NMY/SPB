package com.example.spb.presenter.utils;

import android.os.Message;

public class SendHandler {

    public static Message setMessage(int what, Object obj) {
        Message message = new Message();
        message.what = what;
        message.obj = obj;
        return message;
    }

}
