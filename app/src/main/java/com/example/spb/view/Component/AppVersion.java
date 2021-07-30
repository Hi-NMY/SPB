package com.example.spb.view.Component;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class AppVersion {

    private Context context;
    private Activity activity;

    public AppVersion(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void startVersion(String version,boolean tipKey){
        chechingVersion(version, new OnReturn() {
            @Override
            public void onReturn(int code, String app_detailed) {
                Looper.prepare();
                if (code == 201){
                    if (tipKey){
                        MyToastClass.ShowToast(MyApplication.getContext(),"已是最新版本");
                    }
                }else if (code == 200){
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //创建dialog进行更新
                        }
                    });
                }
                Looper.loop();
            }
        });
    }

    private void chechingVersion(String version,OnReturn onReturn){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(InValues.send(R.string.AppVersion))
                .post(new FormBody.Builder()
                        .add("versionCode",version)
                        .build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String a = response.body().string();
                if (Integer.valueOf(a.substring(0,3)) == 201){
                    onReturn.onReturn(201,null);
                }else if(Integer.valueOf(a.substring(0,3)) == 200){
                    onReturn.onReturn(200,a.substring(3));
                }
            }
        });
    }

    public interface OnReturn{
        void onReturn(int code,String app_detailed);
    }
}
