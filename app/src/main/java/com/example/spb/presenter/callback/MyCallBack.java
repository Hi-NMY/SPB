package com.example.spb.presenter.callback;

public interface MyCallBack<T> {
    //超时
    int ERROR_ONE = 1;
    //连接错误
    int ERROR_TWO = 2;

    void onSuccess(T response);

    void onError(int t);
}
