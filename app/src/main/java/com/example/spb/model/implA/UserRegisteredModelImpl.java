package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Dto.UserRegisteredDto;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.UserRegisteredModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * @author nmy
 * @title: UserRegisteredModelImpl
 * @date 2022-01-30 15:13
 */
public class UserRegisteredModelImpl extends SpbModelAbstrate implements UserRegisteredModel {
    @Override
    public void userRegistered(UserRegisteredDto userRegisteredDto, File file, MyCallBack callBack) {
        builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_account", userRegisteredDto.getUser_account())
                .addFormDataPart("user_password", userRegisteredDto.getUser_password())
                .addFormDataPart("user_name", userRegisteredDto.getUser_name())
                .addFormDataPart("user_token", userRegisteredDto.getUser_token())
                .addFormDataPart("file", file.getName(), RequestBody.Companion.create(file
                        , MediaType.Companion.parse("image/png")));
        requestBody = builder.build();
        sendHttp(InValues.send(R.string.userRegistered), requestBody, callBack);
    }
}
