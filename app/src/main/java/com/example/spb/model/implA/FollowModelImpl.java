package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.FollowModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: FollowModelImpl
 * @date 2022-01-30 14:57
 */
public class FollowModelImpl extends SpbModelAbstrate implements FollowModel {
    @Override
    public void queryFollowList(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryFollowList), requestBody, callBack);
    }

    @Override
    public void queryFollowCount(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryFollowCount), requestBody, callBack);
    }

    @Override
    public void queryFollowUserList(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryFollowUserList), requestBody, callBack);
    }

    @Override
    public void addFollow(String followAccount, String followedAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("follow_account", followAccount)
                .add("followed_account", followedAccount)
                .build();
        sendHttp(InValues.send(R.string.addFollow), requestBody, callBack);
    }

    @Override
    public void deleteFollow(String followAccount, String followedAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("follow_account", followAccount)
                .add("followed_account", followedAccount)
                .build();
        sendHttp(InValues.send(R.string.deleteFollow), requestBody, callBack);
    }
}
