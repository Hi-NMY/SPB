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
                        .build();
                sendHttp(InValues.send(R.string.VerifyPassword),requestBody,callBack);
                break;
        }
    }

    @Override
    public void updateData(int fun, User data, MyCallBack callBack) {
        switch (fun){
            case DATEUSER_UPDATE_ONE:
                requestBody = new FormBody.Builder()
                        .add("user_account", data.getUser_account())
                        .add("user_name", data.getUser_name())
                        .add("user_birth", data.getUser_birth())
                        .add("user_favorite", data.getUser_favorite())
                        .add("user_home", data.getUser_home())
                        .add("user_profile", data.getUser_profile())
                        .build();
                sendHttp(InValues.send(R.string.User),requestBody,callBack);
                break;
        }
    }

    @Override
    public void deleteData(int fun, User data, MyCallBack callBack) {

    }
}
