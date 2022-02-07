package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: FollowedModel
 * @date 2022-01-30 14:59
 */
public interface FollowedModel {

    void queryFollowedList(String userAccount, MyCallBack callBack);

    void queryFollowedCount(String userAccount, MyCallBack callBack);

    void queryFollowedUserList(String userAccount, MyCallBack callBack);

}
