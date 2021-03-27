package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class FollowModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Follow> {

    @Override
    public void addData(int fun, Follow data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Follow data, MyCallBack callBack) {
        switch (fun){
            case DATAFOLLOW_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("id", String.valueOf(data.getId()))
                        .add("user_account", data.getUser_account())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Follow),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Follow data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Follow data, MyCallBack callBack) {

    }
}
