package com.example.spb.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.RongUser;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.presenter.littlefun.SendHandler;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.google.gson.Gson;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class FirstPageAPresenterImpl extends BasePresenter<IFirstPageAView> implements IFirstPageAPresenter {

    private SpbModelBasicInter userModel;
    private String account;

    public FirstPageAPresenterImpl() {
        userModel = new UserModelImpl();
    }

    public void setAccount(Intent data) {
        try {
            account = data.getStringExtra(IUserRegisteredPageAView.STRINGEXTRA);
            if (isAttachView()){
                getView().response(account,getView().RESPONSE_ACC);
            }
        }catch (Exception e){

        }
    }

    public boolean setFirstLogIn() {
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_FirstLogIn));
        editor.putBoolean(InValues.send(R.string.FirstLogIn_login),false);
        return editor.commit();
    }

    public void verifyAccount(User user, Handler handler){
        userModel.selectData(userModel.FIRSTPAGE_ONE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (isAttachView()){
                        switch (Integer.valueOf(a.substring(0,3))) {
                            case 200:
                                RongUser rongUser = new Gson().fromJson(a.substring(3),RongUser.class);
                                user.setUser_name(rongUser.getUserId());
                                user.setUser_token(rongUser.getToken());
                                handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_SUCCESS_ONE,user));
                                break;
                            default:
                                handler.sendMessage(SendHandler.setMessage(Integer.valueOf(a),null));
                                break;
                        }
                    }
                } catch (IOException e) {
                    if (isAttachView()){
                        handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {
                if (isAttachView()){
                    handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
                }
            }
        });
    }

    public void verifyPassword(User user, Handler verifyAccountHanlder) {
        userModel.selectData(userModel.FIRSTPAGE_TWO, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (isAttachView()){
                        switch (Integer.valueOf(a)) {
                            case 201:
                                verifyAccountHanlder.sendMessage(SendHandler.setMessage(getView().RESPONSE_SUCCESS_TWO,null));
                                break;
                            default:
                                verifyAccountHanlder.sendMessage(SendHandler.setMessage(Integer.valueOf(a),null));
                                break;
                        }
                    }
                } catch (IOException e) {
                    if (isAttachView()){
                        verifyAccountHanlder.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
                    }
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {
                if (isAttachView()){
                    verifyAccountHanlder.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
                }
            }
        });
    }

    public void connectRong(String token,String uName,String uAccount){
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                UserInfo userInfo = new UserInfo(uAccount,uName, Uri.parse(InValues
                        .send(R.string.httpHeader) +"/UserImageServer/"+uAccount+"/HeadImage/myHeadImage.png"));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                getView().goIntent();
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {

            }
        });
    }
}
