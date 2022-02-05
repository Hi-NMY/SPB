package com.example.spb.common;

import java.util.List;

/**
 * @author nmy
 * @title: RequestEntityJson
 * @date 2022-01-25 15:28
 */
public class RequestListJson<T> {

    private RequestCode resultCode;

    private List<T> dataList;

    public RequestCode getResultCode() {
        return resultCode;
    }

    public void setResultCode(RequestCode resultCode) {
        this.resultCode = resultCode;
    }

    public List<T> getDataList() {
        return dataList;
    }

    public void setDataList(List<T> dataList) {
        this.dataList = dataList;
    }
}
