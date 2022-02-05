package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: TopicModel
 * @date 2022-01-30 15:08
 */
public interface TopicModel {

    void queryTopicNameList(MyCallBack callBack);

    void querySearchTopicNameList(String topicName, MyCallBack callBack);

    void queryRundomTopicFullList(MyCallBack callBack);

    void queryTopicFull(String topicName, MyCallBack callBack);

    void querySearchTopicFullList(String topicName, MyCallBack callBack);

}
