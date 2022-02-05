package com.example.spb.model.inter;

import com.example.spb.entity.Bar;
import com.example.spb.presenter.callback.MyCallBack;

import java.io.File;
import java.util.List;

/**
 * @author nmy
 * @title: PostBarModel
 * @date 2022-01-30 15:01
 */
public interface PostBarModel {

    void queryNoVideoBarListForDate(String pbDate, MyCallBack callBack);

    void queryNoVideoTopicBarListForThumbNum(String thumbNum, String topicName, MyCallBack callBack);

    void queryNoVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack);

    void queryNoVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack);

    void queryUserBarCount(String userAccount, MyCallBack callBack);

    void queryNoVideoFollowBarListForDate(String userAccount, String pbDate, MyCallBack callBack);

    void queryBarDetatilForPbid(String pbid, MyCallBack callBack);

    void queryUserBarLikeCount(String userAccount, MyCallBack callBack);

    void queryNoVideoSearchBarListForDate(String searchArt, MyCallBack callBack);

    void queryVideoBarListForDate(String pbDate, MyCallBack callBack);

    void queryVideoTopicBarListForDate(String pbDate, String topicName, MyCallBack callBack);

    void queryVideoUserBarListForDate(String userAccount, String pbDate, MyCallBack callBack);

    void deleteBar(String pbid, MyCallBack callBack);

    void addBar(Bar bar, List<File> image, File voice, File video, MyCallBack callBack);

}
