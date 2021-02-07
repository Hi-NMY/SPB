package com.example.spb.app;

import android.app.Application;
import android.content.Context;
import com.tamsiree.rxkit.RxTool;
import org.litepal.LitePal;

public class MyApplication extends Application {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        LitePal.initialize(this);
        context = getApplicationContext();
        RxTool.init(this);

    }

    public static Context getContext() {
        return context;
    }
}
