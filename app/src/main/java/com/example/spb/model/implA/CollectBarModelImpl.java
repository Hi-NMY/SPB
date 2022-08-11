package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Dto.CollectBarDto;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.CollectBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: CollectBarModelImpl
 * @date 2022-01-30 14:48
 */
public class CollectBarModelImpl extends SpbModelAbstrate implements CollectBarModel {
    @Override
    public void queryCollectBarList(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryCollectBarList) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void queryCollectBarFullList(String userAccount, MyCallBack callBack) {
        StringBuffer stringBuffer = new StringBuffer("?");
        stringBuffer.append("&").append("user_account").append("=").append(userAccount);
        sendHttp(InValues.send(R.string.queryCollectBarFullList) + stringBuffer, GET, requestBody, callBack);
    }

    @Override
    public void addCollectBar(CollectBarDto collectBarDto, String cacheAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", collectBarDto.getUser_account())
                .add("cache_account", cacheAccount)
                .add("pb_one_id", collectBarDto.getPb_one_id())
                .build();
        sendHttp(InValues.send(R.string.addCollectBar), POST, requestBody, callBack);
    }

    @Override
    public void deleteCollectBar(CollectBarDto collectBarDto, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("pb_one_id", collectBarDto.getPb_one_id())
                .add("user_account", collectBarDto.getUser_account())
                .build();
        sendHttp(InValues.send(R.string.deleteCollectBar), POST, requestBody, callBack);
    }
}
