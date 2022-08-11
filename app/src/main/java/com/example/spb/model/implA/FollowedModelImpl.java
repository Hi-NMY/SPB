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
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryFollowedList) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryFollowedCount(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryFollowedCount) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryFollowedUserList(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryFollowedUserList) + stringBuffer, GET, requestBody, callBack);
    }
}
