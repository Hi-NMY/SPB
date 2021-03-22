package com.example.spb.app;

import android.app.Application;
import android.content.Context;
import io.rong.imkit.RongIM;
import org.litepal.LitePal;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        String appKey = "x18ywvqfx4z8c";
        LitePal.initialize(this);
        context = getApplicationContext();
        RongIM.init(context, appKey);
    }

    public static Context getContext() {
        return context;
    }
}
