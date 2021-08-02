package com.example.spb.model.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

public class UserModelImpl extends SpbModelAbstrate implements SpbModelBasicInter<User> {

    @Override
    public void addData(int fun, User data, MyCallBack callBack) {
        switch (fun){
            case REGISTEREDPAGE:
                File file = new File(data.getUser_head_image());
                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("user_account",data.getUser_account())
                        .addFormDataPart("user_password",data.getUser_password())
                        .addFormDataPart("user_name",data.getUser_name())
                        .addFormDataPart("user_head_image",file.getName(),RequestBody.Companion.create(file
                                ,MediaType.Companion.parse("image/png")));
                requestBody = builder.build();
                sendHttp(InValues.send(R.string.VerifyMessage),requestBody,callBack);
                break;
        }
    }

    @Override
    public void selectData(int fun, User data, MyCallBack callBack) {
        switch (fun){
            case FIRSTPAGE_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.VerifyAccount),requestBody,callBack);
                break;
            case FIRSTPAGE_TWO:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .add("user_password", data.getUser_password())
                        .add("user_ip", data.getUser_ip())
                        .build();
                sendHttp(InValues.send(R.string.VerifyPassword),requestBody,callBack);
                break;
            case FIRSTPAGE_THREE:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("search_key", data.getUser_name())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_SELECT_FOUR:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_name", data.getUser_name())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, User data, MyCallBack callBack) {
        switch (fun){
            case DATAUSER_UPDATE_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("user_name", data.getUser_name())
                        .add("user_birth", data.getUser_birth())
                        .add("user_favorite", data.getUser_favorite())
                        .add("user_home", data.getUser_home())
                        .add("user_profile", data.getUser_profile())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_TWO:
                File file = new File(data.getUser_head_image());
                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fun",String.valueOf(fun))
                        .addFormDataPart("user_account",data.getUser_account())
                        .addFormDataPart("user_head_image",file.getName(),RequestBody.Companion.create(file
                                ,MediaType.Companion.parse("image/png")));
                requestBody = builder.build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_THREE:
                File file1 = new File(data.getUser_bg_image());
                builder = new MultipartBody.Builder()
                        .setType(MultipartBody.FORM)
                        .addFormDataPart("fun",String.valueOf(fun))
                        .addFormDataPart("user_account",data.getUser_account())
                        .addFormDataPart("user_bg_image",file1.getName(),RequestBody.Companion.create(file1
                                ,MediaType.Companion.parse("image/png")));
                requestBody = builder.build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_FOUR:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("user_ip", data.getUser_ip())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_FIVE:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("user_badge", data.getUser_badge())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_SIX:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .add("user_privacy", data.getUser_privacy())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
            case DATAUSER_UPDATE_SEVEN:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .add("user_password", data.getUser_password())
                        .add("user_password_old",data.getUser_token())
                        .build();
                sendHttp(InValues.send(R.string.UpdatePassword),requestBody,callBack);
                break;
        }
    }

    @Override
    public void deleteData(int fun, User data, MyCallBack callBack) {
        switch (fun){
            case DATAUSER_DELETE_ONE:
                requestBody = new FormBody.Builder()
                        .add("fun",String.valueOf(fun))
                        .add("user_account", data.getUser_account())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
        }
    }
}
