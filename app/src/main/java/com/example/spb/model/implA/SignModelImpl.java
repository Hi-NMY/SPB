package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.SignModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: SignModelImpl
 * @date 2022-01-30 15:07
 */
public class SignModelImpl extends SpbModelAbstrate implements SignModel {
    @Override
    public void queryUserSign(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryUserSign), requestBody, callBack);
    }

    @Override
    public void queryUserBadge(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryUserBadge), requestBody, callBack);
    }

    @Override
    public void updateSignDay(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.updateSignDay), requestBody, callBack);
    }

    @Override
    public void updateSignRight(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.updateSignRight), requestBody, callBack);
    }

    @Override
    public void updateSignDayAndRight(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.updateSignDayAndRight), requestBody, callBack);
    }

    @Override
    public void updateSignDayAndRightAndCoin(String userAccount, String signDay, String coin, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("coin", coin)
                .add("sign_day", signDay)
                .build();
        sendHttp(InValues.send(R.string.updateSignDayAndRightAndCoin), requestBody, callBack);
    }

    @Override
    public void updateSignCoin(String userAccount, String coin, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("coin", coin)
                .build();
        sendHttp(InValues.send(R.string.updateSignCoin), requestBody, callBack);
    }

    @Override
    public void updateSignStarBadge(String userAccount, String starBadge, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("sign_star_badge", starBadge)
                .build();
        sendHttp(InValues.send(R.string.updateSignStarBadge), requestBody, callBack);
    }

    @Override
    public void updateSignLikeBadge(String userAccount, String likeBadge, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("sign_like_badge", likeBadge)
                .build();
        sendHttp(InValues.send(R.string.updateSignLikeBadge), requestBody, callBack);
    }

    @Override
    public void updateSignTaskBadge(String userAccount, String taskBadge, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("sign_task_badge", taskBadge)
                .build();
        sendHttp(InValues.send(R.string.updateSignTaskBadge), requestBody, callBack);
    }
}