package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.AttentionTopic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class AttentionTopicModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<AttentionTopic> {

    @Override
    public void addData(int fun, AttentionTopic data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, AttentionTopic data, MyCallBack callBack) {
        switch (fun){
            case DATEATTENTIONTOPIC_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("topic_date", data.getTopic_date())
                        .add("user_account", data.getTopic_name())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.AttentionTopic),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, AttentionTopic data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, AttentionTopic data, MyCallBack callBack) {

    }
}
