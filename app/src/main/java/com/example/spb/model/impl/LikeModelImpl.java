package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Like;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class LikeModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Like> {

    @Override
    public void addData(int fun, Like data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Like data, MyCallBack callBack) {
        switch (fun){
            case DATALIKE_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getPb_one_id())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Like),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Like data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Like data, MyCallBack callBack) {

    }
}
