package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Sign;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.SignInModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInTaskFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.inter.ISignInTaskFView;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignInTaskFPresenterImpl extends BasePresenter<ISignInTaskFView> implements ISignInTaskFPresenter {

    private int inSign_Key;
    private int inBar_key;
    private int inlike_key;
    private int intolike_key;
    private int invideo_key;
    private SpbModelBasicInter signModel;

    public int getInSign_Key() {
        return inSign_Key;
    }

    public void setInSign_Key(int inSign_Key) {
        this.inSign_Key = inSign_Key;
    }

    public int getInBar_key() {
        return inBar_key;
    }

    public void setInBar_key(int inBar_key) {
        this.inBar_key = inBar_key;
    }

    public int getInlike_key() {
        return inlike_key;
    }

    public void setInlike_key(int inlike_key) {
        this.inlike_key = inlike_key;
    }

    public int getIntolike_key() {
        return intolike_key;
    }

    public void setIntolike_key(int intolike_key) {
        this.intolike_key = intolike_key;
    }

    public int getInvideo_key() {
        return invideo_key;
    }

    public void setInvideo_key(int invideo_key) {
        this.invideo_key = invideo_key;
    }

    public SignInTaskFPresenterImpl() {
        signModel = new SignInModelImpl();
    }

    public void alreadyTask(String account,OnReturn onReturn){
        Sign sign = new Sign();
        sign.setUser_account(account);
        sign.setSign_coin(10);
        signModel.updateData(signModel.DATASIGN_UPDATE_FIVE, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), 10,null);
                        onReturn.onReturn();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn{
        void onReturn();
    }
}
