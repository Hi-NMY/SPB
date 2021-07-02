package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.CollectBar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class CollectBarModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<CollectBar> {

    @Override
    public void addData(int fun, CollectBar data, MyCallBack callBack) {
        switch (fun){
            case DATACOLLECTBAR_ADD_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account",data.getUser_account())
                        .add("cache_account",data.getCache_account())
                        .add("pb_one_id",data.getPb_one_id())
                        .build();
                sendHttp(InValues.send(R.string.CollectBar),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, CollectBar data, MyCallBack callBack) {
        switch (fun){
            case DATACOLLECTBAR_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account",data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.CollectBar),requestBody,callBack);
                break;
            case DATACOLLECTBAR_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("user_account",data.getUser_account())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.CollectBar),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, CollectBar data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, CollectBar data, MyCallBack callBack) {
        switch (fun){
            case DATACOLLECTBAR_DELETE_ONE:
                requestBody = new FormBody.Builder()
                        .add("pb_one_id",data.getPb_one_id())
                        .add("fun", String.valueOf(fun))
                        .add("user_account",data.getUser_account())
                        .add("cache_account",data.getCache_account())
                        .build();
                sendHttp(InValues.send(R.string.CollectBar),requestBody,callBack);
                break;
        }
    }
}
