package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.FollowedModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: FollowedModelImpl
 * @date 2022-01-30 14:59
 */
public class FollowedModelImpl extends SpbModelAbstrate implements FollowedModel {
    @Override
    public void queryFollowedList(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryFollowedList), requestBody, callBack);
    }

    @Override
    public void queryFollowedUserList(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryFollowedUserList), requestBody, callBack);
    }
}
