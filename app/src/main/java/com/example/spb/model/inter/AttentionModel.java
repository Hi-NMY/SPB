package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: AttentionModel
 * @date 2022-01-30 14:42
 */
public interface AttentionModel {

    void addAttentionTopic(String userAccount, String topicId, String topicName, MyCallBack callBack);

    void queryAttentionTopic(String account, MyCallBack callBack);

    void deleteAttentionTopicById(String id, String userAccount, MyCallBack callBack);


}
