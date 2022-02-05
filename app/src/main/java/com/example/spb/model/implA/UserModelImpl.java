package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Dto.UserInformationDto;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import java.io.File;

/**
 * @author nmy
 * @title: UserModelImpl
 * @date 2022-01-30 15:11
 */
public class UserModelImpl extends SpbModelAbstrate implements UserModel {
    @Override
    public void querySchoolTable(MyCallBack callBack) {
        sendHttp(InValues.send(R.string.querySchoolTable), null, callBack);
    }

    @Override
    public void querySearchUser(String search, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("search", search)
                .build();
        sendHttp(InValues.send(R.string.querySearchUser), requestBody, callBack);
    }

    @Override
    public void updateUserPersonalInformation(UserInformationDto info, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", info.getUser_account())
                .add("user_name", info.getUser_name())
                .add("user_birth", info.getUser_birth())
                .add("user_favorite", info.getUser_favorite())
                .add("user_home", info.getUser_home())
                .add("user_profile", info.getUser_profile())
                .build();
        sendHttp(InValues.send(R.string.updateUserPersonalInformation), requestBody, callBack);
    }

    @Override
    public void updateUserIp(String userAccount, String ip, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("ip", ip)
                .build();
        sendHttp(InValues.send(R.string.updateUserIp), requestBody, callBack);
    }

    @Override
    public void updateUserToken(String userAccount, String token, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("token", token)
                .build();
        sendHttp(InValues.send(R.string.updateUserToken), requestBody, callBack);
    }

    @Override
    public void updateUserHeadImage(File file, String userAccount, MyCallBack callBack) {
        builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_account", userAccount)
                .addFormDataPart("file", file.getName(), RequestBody.Companion.create(file
                        , MediaType.Companion.parse("image/png")));
        requestBody = builder.build();
        sendHttp(InValues.send(R.string.updateUserHeadImage), requestBody, callBack);
    }

    @Override
    public void updateUserBgImage(File file, String userAccount, MyCallBack callBack) {
        builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_account", userAccount)
                .addFormDataPart("file", file.getName(), RequestBody.Companion.create(file
                        , MediaType.Companion.parse("image/png")));
        requestBody = builder.build();
        sendHttp(InValues.send(R.string.updateUserBgImage), requestBody, callBack);
    }

    @Override
    public void updateUserBadgeImage(String userBadge, String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("user_badge", userBadge)
                .build();
        sendHttp(InValues.send(R.string.updateUserBadgeImage), requestBody, callBack);
    }

    @Override
    public void updateUserPrivacy(String userPrivacy, String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .add("user_privacy", userPrivacy)
                .build();
        sendHttp(InValues.send(R.string.updateUserPrivacy), requestBody, callBack);
    }

    @Override
    public void deleteUserIp(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.deleteUserIp), requestBody, callBack);
    }
}
