package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.entity.Sign;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;

public class SignInModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<Sign> {
    @Override
    public void addData(int fun, Sign data, MyCallBack callBack) {

    }

    @Override
    public void selectData(int fun, Sign data, MyCallBack callBack) {
        switch (fun){
            case DATASIGN_SELECT_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_SELECT_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, Sign data, MyCallBack callBack) {
        switch (fun){
            case DATASIGN_UPDATE_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .add("fun", String.valueOf(fun))
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_TWO:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_THREE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_FOUR:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("sign_coin", String.valueOf(data.getSign_coin()))
                        .add("sign_day", data.getSign_day())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_FIVE:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("sign_coin", String.valueOf(data.getSign_coin()))
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_SIX:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("sign_star_badge", data.getSign_star_badge())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
            case DATASIGN_UPDATE_SEVEN:
                requestBody = new FormBody.Builder()
                        .add("fun", String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("sign_like_badge", data.getSign_like_badge())
                        .build();
                sendHttp(InValues.send(R.string.Sign),requestBody,callBack);
                break;
        }
    }

    @Override
    public void deleteData(int fun, Sign data, MyCallBack callBack) {

    }
}
