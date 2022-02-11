package com.example.spb.xserver;

import android.util.Log;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.concurrent.TimeUnit;

public class PushClient {

    // The user agent
    protected final String USER_AGENT = "Mozilla/5.0";

    // The host
    protected static final String host = "http://msg.umeng.com";

    // The upload path
    protected static final String uploadPath = "/upload";

    // The post path
    protected static final String postPath = "/api/send";

    public boolean send(AndroidUnicast msg) throws Exception {
        String url = host + postPath;
        String postBody = msg.getPostBody();
        String sign = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            BigInteger bigInt = new BigInteger(1, md.digest(("POST" + url + postBody + msg.getAppMasterSecret()).getBytes("utf8")));
            sign = String.format("%032x", bigInt);
        } catch (Exception e) {
            e.printStackTrace();
        }
        url = url + "?sign=" + sign;
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .header("User-Agent", USER_AGENT)
                .header("Content-Type", "UTF-8")
                .post(RequestBody.Companion.create(postBody, MediaType.Companion.parse("application/json;charset=utf-8")))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String a = response.body().string();
                Log.d("aaaaaaaaaaaaaaaaaaaaa", a);
            }
        });

        return true;
    }
}
