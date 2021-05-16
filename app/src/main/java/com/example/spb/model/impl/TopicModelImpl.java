package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.CollectBar;
import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class TopicModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Topic> {

    @Override
    public void addData(int fun, Topic data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Topic data, MyCallBack callBack) {
        switch (fun){
            case DATATOPIC_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Topic),requestBody,callBack);
                break;
            case DATATOPIC_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("topic_name",String.valueOf(data.getTopic_name()))
                        .build();
                sendHttp(InValues.send(R.string.Topic),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Topic data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Topic data, MyCallBack callBack) {

    }
}
