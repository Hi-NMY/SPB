package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.TopicModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;

/**
 * @author nmy
 * @title: TopicModelImpl
 * @date 2022-01-30 15:09
 */
public class TopicModelImpl extends SpbModelAbstrate implements TopicModel {
    @Override
    public void queryTopicNameList(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.queryTopicNameList), GET, null, callBack);
    }

    @Override
    public void querySearchTopicNameList(String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        sendHttp(InValues.send(R.string.querySearchTopicNameList) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryRundomTopicFullList(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.queryRundomTopicFullList), GET, null, callBack);
    }

    @Override
    public void queryTopicFull(String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        sendHttp(InValues.send(R.string.queryTopicFull) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void querySearchTopicFullList(String topicName, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("topic_name").append("=").append(topicName);
        sendHttp(InValues.send(R.string.querySearchTopicFullList) + stringBuffer, GET, requestBody, callBack);
    }
}
