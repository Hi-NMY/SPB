package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.LikeModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: LikeModelImpl
 * @date 2022-01-30 15:01
 */
public class LikeModelImpl extends SpbModelAbstrate implements LikeModel {
    @Override
    public void queryLike(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryLike), requestBody, callBack);
    }

    @Override
    public void addLike(String pbId, String userAccount, String cacheAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbId)
                .add("user_account", userAccount)
                .add("cache_account", cacheAccount == null ? "" : cacheAccount)
                .build();
        sendHttp(InValues.send(R.string.addLike), requestBody, callBack);
    }

    @Override
    public void deleteLike(String pbId, String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", pbId)
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.deleteLike), requestBody, callBack);
    }
}
