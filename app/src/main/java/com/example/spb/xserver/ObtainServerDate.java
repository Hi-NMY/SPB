package com.example.spb.xserver;

import com.example.spb.R;
import com.example.spb.presenter.utils.InValues;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class ObtainServerDate {

    public static void obtainDate(OnReturn onReturn){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(InValues.send(R.string.dateTime))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String a = response.body().string();
                onReturn.onReturn(a);
            }
        });
    }

    public interface OnReturn{
        void onReturn(String date);
    }

}
