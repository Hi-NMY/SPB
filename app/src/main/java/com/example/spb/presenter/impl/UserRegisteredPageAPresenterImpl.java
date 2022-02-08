package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.entity.Dto.UserRegisteredDto;
import com.example.spb.entity.RongUser;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.implA.UserRegisteredModelImpl;
import com.example.spb.model.inter.UserModel;
import com.example.spb.model.inter.UserRegisteredModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SendHandler;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.UserRegisteredPage;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class UserRegisteredPageAPresenterImpl extends BasePresenter<IUserRegisteredPageAView> {

    private final UserRegisteredModel userRegisteredModel;
    private final UserModel userModel;
    private String headImgPath;
    private Handler handler;
    private UserRegisteredDto userRegisteredDto;
    private RongUser user;

    public UserRegisteredPageAPresenterImpl() {
        userRegisteredModel = new UserRegisteredModelImpl();
        userModel = new UserModelImpl();
    }

    public void getHeadImage(List<LocalMedia> result) {
        for (LocalMedia media : result) {
            if (media.isCut() && media.isCompressed()) {
                headImgPath = media.getCompressPath();
            } else {
                headImgPath = media.getCutPath();
            }
        }

        if (isAttachView()) {
            getView().response(headImgPath, UserRegisteredPage.IMAGE_SUCCESS);
        }
    }

    public void registerUser(UserRegisteredDto userRegisteredDto, Handler handler) {
        this.handler = handler;
        this.userRegisteredDto = userRegisteredDto;
        userRegisteredModel.userRegistered(userRegisteredDto, new File(headImgPath), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        obtainRongUser();
                    }else {
                        handler.sendMessage(SendHandler.setMessage(UserRegisteredPage.RESPONSE_ERROR,null));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    private void obtainRongUser() {
        long date = getTimestamp();
        int num = getNonce();
        RequestBody requestBody = new FormBody.Builder()
                .add("userId", userRegisteredDto.getUser_account())
                .add("name", userRegisteredDto.getUser_name())
                .add("portraitUri", InValues.send(R.string.prefix_img) + userRegisteredDto.getUser_account() + InValues.send(R.string.suffix_head_img))
                .build();
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(InValues.send(R.string.rong_api))
                .header("App-Key", InValues.send(R.string.rong_app_key))
                .header("Nonce", String.valueOf(num))
                .header("Timestamp", String.valueOf(date))
                .header("Signature", getSignature(InValues.send(R.string.rong_app_secret) + num + date))
                .header("Content-Type", "application/x-www-form-urlencoded")
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                user = new Gson().fromJson(response.body().string(), RongUser.class);
                updateToken(user.getToken());
            }
        });
    }

    private void updateToken(String token) {
        userModel.updateUserToken(userRegisteredDto.getUser_account(), token, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (requestCode.getCode() == RequestCode.SUCCESS) {
                        setRongShared();
                        handler.sendMessage(SendHandler.setMessage(UserRegisteredPage.RESPONSE_SUCCESS, null));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    private int getNonce() {
        return new Random().nextInt(100000);
    }

    private long getTimestamp() {
        return System.currentTimeMillis();
    }

    private String getSignature(String val) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(val.getBytes("UTF-8"));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return "";
        }
    }

    public void setRongShared() {
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_RongUser));
        editor.putString(InValues.send(R.string.RongUser_userId), user.getUserId());
        editor.putString(InValues.send(R.string.RongUser_token), user.getToken());
        editor.apply();
    }
}
