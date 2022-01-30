package com.example.spb.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.util.Log;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SendHandler;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
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
                                User user1 = new Gson().fromJson(a.substring(3),User.class);
                                user.setUser_account(user1.getUser_account());
                                user.setUser_birth(user1.getUser_birth());
                                user.setUser_home(user1.getUser_home());
                                user.setUser_favorite(user1.getUser_favorite());
                                user.setUser_profile(user1.getUser_profile());
                                user.setUser_privacy(user1.getUser_privacy());
                                user.setUser_ip(user1.getUser_ip());
                                user.setUser_name(user1.getUser_name());
                                user.setUser_token(user1.getUser_token());
                                user.setStu_sex(user1.getStu_sex());
                                user.setStu_name(user1.getStu_name());
                                setUserMsg(user);
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

    public void setUserMsg(User user){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        editor.putString(InValues.send(R.string.user_account),user.getUser_account());
        editor.putString(InValues.send(R.string.user_birth),user.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite),user.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home),user.getUser_home());
        editor.putString(InValues.send(R.string.user_ip),user.getUser_ip());
        editor.putString(InValues.send(R.string.user_name),user.getUser_name());
        editor.putString(InValues.send(R.string.user_privacy),user.getUser_privacy());
        editor.putString(InValues.send(R.string.user_profile),user.getUser_profile());
        editor.putString(InValues.send(R.string.user_token),user.getUser_token());
        editor.putString(InValues.send(R.string.stu_sex),user.getStu_sex());
        editor.putString(InValues.send(R.string.stu_name),user.getStu_name());
        editor.apply();
    }

    public void verifyPassword(User user, Handler verifyAccountHanlder) {
        SharedPreferences sharedPreferences2 = MySharedPreferences.getShared(InValues.send(R.string.Shared_Push));
        String ip = sharedPreferences2.getString(InValues.send(R.string.push_id),"");
        user.setUser_ip(ip);
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
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_RongUser));
                editor.putString(InValues.send(R.string.RongUser_userId),uAccount);
                editor.putString(InValues.send(R.string.RongUser_token),token);
                editor.apply();
                SharedPreferences.Editor editor1 = MySharedPreferences.saveShared(InValues.send(R.string.Shared_notify_setup));
                editor1.putBoolean(InValues.send(R.string.notify_collect),true);
                editor1.putBoolean(InValues.send(R.string.notify_comment),true);
                editor1.putBoolean(InValues.send(R.string.notify_follow),true);
                editor1.putBoolean(InValues.send(R.string.notify_like),true);
                editor1.putBoolean(InValues.send(R.string.notify_system),true);
                editor1.putBoolean(InValues.send(R.string.notify_all),true);
                editor.apply();
                Log.d("rongLink","true");
            }

            @Override
            public void onError(RongIMClient.ConnectionErrorCode connectionErrorCode) {

            }

            @Override
            public void onDatabaseOpened(RongIMClient.DatabaseOpenStatus databaseOpenStatus) {
                getView().goIntent();
            }
        });
    }
}
