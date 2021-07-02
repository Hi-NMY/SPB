package com.example.spb.presenter.callback;

import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public interface MyCallBack{
    //超时
    int ERROR_LONGTIME = 1;
    //连接错误
    int ERROR_CONNECTION = 2;

    void onSuccess(@NotNull Response response);

    void onError(int t);
}
