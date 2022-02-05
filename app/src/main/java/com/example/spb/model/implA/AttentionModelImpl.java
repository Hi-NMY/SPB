package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.AttentionModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: AttentionModelImpl
 * @date 2022-01-30 14:45
 */
public class AttentionModelImpl extends SpbModelAbstrate implements AttentionModel {
    @Override
    public void addAttentionTopic(String userAccount, String topicId, String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("topic_id", topicId)
                .add("topic_name", topicName)
                .build();
        sendHttp(InValues.send(R.string.addAttentionTopic), requestBody, callBack);
    }

    @Override
    public void queryAttentionTopic(String account, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", account)
                .build();
        sendHttp(InValues.send(R.string.queryAttentionTopic), requestBody, callBack);
    }

    @Override
    public void deleteAttentionTopicById(String id, String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("topic_id", id)
                .build();
        sendHttp(InValues.send(R.string.deleteAttentionTopicById), requestBody, callBack);
    }
}
