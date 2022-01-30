package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Topic;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

public class AttentionTopicModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Topic> {

    @Override
    public void addData(int fun, Topic data, MyCallBack callBack) {
        switch (fun){
            case DATAATTENTIONTOPIC_ADD_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account",data.getTopic_slogan())
                        .add("topic_id", String.valueOf(data.getId()))
                        .add("topic_name", data.getTopic_name())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.AttentionTopic),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, Topic data, MyCallBack callBack) {
        try {
            switch (fun){
                case DATAATTENTIONTOPIC_SELECT_ONE:
                    requestBody = new FormBody.Builder()
                            .add("user_account", data.getTopic_name())
                            .add("fun", String.valueOf(fun))
                            .build();
                    sendHttp(InValues.send(R.string.AttentionTopic),requestBody,callBack);
                    break;
            }
        }catch (Exception o){

        }
    }

    @Override
    public void updateData(int fun, Topic data, MyCallBack callBack) {

    }

    @Override
    public void deleteData(int fun, Topic data, MyCallBack callBack) {
        switch (fun){
            case DATAATTENTIONTOPIC_DELETE_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account",data.getTopic_slogan())
                        .add("topic_id", String.valueOf(data.getId()))
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.AttentionTopic),requestBody,callBack);
                break;
        }
    }
}
