package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: TopicModelImpl
 * @date 2022-01-30 15:09
 */
public class TopicModelImpl extends SpbModelAbstrate implements TopicModel {
    @Override
    public void queryTopicNameList(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.queryTopicNameList), null, callBack);
    }

    @Override
    public void querySearchTopicNameList(String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("topic_name", topicName)
                .build();
        sendHttp(InValues.send(R.string.querySearchTopicNameList), requestBody, callBack);
    }

    @Override
    public void queryRundomTopicFullList(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.queryRundomTopicFullList), null, callBack);
    }

    @Override
    public void queryTopicFull(String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("topic_name", topicName)
                .build();
        sendHttp(InValues.send(R.string.queryTopicFull), requestBody, callBack);
    }

    @Override
    public void querySearchTopicFullList(String topicName, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("topic_name", topicName)
                .build();
        sendHttp(InValues.send(R.string.querySearchTopicFullList), requestBody, callBack);
    }
}
