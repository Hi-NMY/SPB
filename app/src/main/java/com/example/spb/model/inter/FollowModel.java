package com.example.spb.model.inter;

import com.example.spb.presenter.callback.MyCallBack;

/**
 * @author nmy
 * @title: FollowModel
 * @date 2022-01-30 14:56
 */
public interface FollowModel {

    void queryFollowList(String userAccount, MyCallBack callBack);

    void queryFollowUserList(String userAccount, MyCallBack callBack);

    void addFollow(String followAccount, String followedAccount, MyCallBack callBack);

    void deleteFollow(String followAccount, String followedAccount, MyCallBack callBack);
}
