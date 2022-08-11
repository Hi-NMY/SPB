package com.example.spb.model.SpbAbstract;

import com.example.spb.R;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class SpbModelAbstrate {

    public RequestBody requestBody = null;
    public MultipartBody.Builder builder = null;
    public static String POST = "POST";
    public static String GET = "GET";

    public void sendHttp(String path,String method, RequestBody requestBody, MyCallBack callBack) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = null;
        if (requestBody == null) {
            request = new Request.Builder()
                    .url(path)
                    .addHeader("token",MySharedPreferences.getShared(InValues.send(R.string.Shared_user_token)).getString(InValues.send(R.string.token), ""))
                    .build();
        } else {
            request = new Request.Builder()
                    .url(path)
                    .method(method,requestBody)
                    .addHeader("token",MySharedPreferences.getShared(InValues.send(R.string.Shared_user_token)).getString(InValues.send(R.string.token), ""))
                    .build();
        }
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                if (e instanceof SocketTimeoutException) {
                    callBack.onError(MyCallBack.ERROR_LONGTIME);
                }
                if (e instanceof ConnectException) {
                    callBack.onError(MyCallBack.ERROR_CONNECTION);
                } else {
                    callBack.onError(-1);
                }
                clearBody();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (callBack != null) {
                    callBack.onSuccess(response);
                }
                clearBody();
            }
        });
    }

    public String setString(Object o) {
        return String.valueOf(o);
    }

    private void clearBody() {
        requestBody = null;
    }
}
