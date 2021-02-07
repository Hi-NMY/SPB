package com.example.spb.model.impl;

import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import okhttp3.FormBody;

public class EnterPageAModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<User> {

    @Override
    public void addData(int fun, User data, MyCallBack callBack) {
//        requestBody = new FormBody.Builder()
//                .add("id",setString(data.getName()))
//                .build();
//        sendHttp("a",requestBody,callBack);
    }

    @Override
    public void selectData(int fun, User data, MyCallBack callBack) {

    }

    @Override
    public void updateData(int fun, User data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, User data, MyCallBack callBack) {

    }
}
