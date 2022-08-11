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
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        stringBuffer.append("&").append("topic_id").append("=").append(topicId);
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        sendHttp(InValues.send(R.string.addAttentionTopic) + stringBuffer, POST, requestBody, callBack);
    }

    @Override
    public void queryAttentionTopic(String account, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(account);
        sendHttp(InValues.send(R.string.queryAttentionTopic) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void deleteAttentionTopicById(String id, String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("topic_id", id)
                .build();
        sendHttp(InValues.send(R.string.deleteAttentionTopicById), POST, requestBody, callBack);
    }
}
