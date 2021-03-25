package com.example.spb.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.presenter.littlefun.SendHandler;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
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
                        handler.sendMessage(SendHandler.setMessage(Integer.valueOf(a),null));
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
                        verifyAccountHanlder.sendMessage(SendHandler.setMessage(Integer.valueOf(a),null));
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
}
