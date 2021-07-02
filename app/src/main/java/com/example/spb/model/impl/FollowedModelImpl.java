package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Diary;
import com.example.spb.entity.Followed;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class FollowedModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Followed> {

    @Override
    public void addData(int fun, Followed data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Followed data, MyCallBack callBack) {
        switch (fun){
            case DATAFOLLOWED_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("id", String.valueOf(data.getId()))
                        .add("user_account", data.getUser_account())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Followed),requestBody,callBack);
                break;
            case DATAFOLLOWED_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.Followed),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Followed data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Followed data, MyCallBack callBack) {

    }
}
