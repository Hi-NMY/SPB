package com.example.spb.common;

/**
 * @author nmy
 * @title: RequestEntityJson
 * @date 2022-01-25 15:28
 */
public class RequestEntityJson<T> {

    private RequestCode resultCode;

    private T data;

    public RequestCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(RequestCode resultCode) {
        this.resultCode = resultCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
