package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.model.implA.SignModelImpl;
import com.example.spb.model.inter.SignModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInTaskFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ISignInTaskFView;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class SignInTaskFPresenterImpl extends BasePresenter<ISignInTaskFView> implements ISignInTaskFPresenter {

    private int inSignKey;
    private int inBarKey;
    private int inlikeKey;
    private int intolikeKey;
    private int invideoKey;
    private final SignModel signModel;

    public int getInSignKey() {
        return inSignKey;
    }

    public void setInSignKey(int inSignKey) {
        this.inSignKey = inSignKey;
    }

    public int getInBarKey() {
        return inBarKey;
    }

    public void setInBarKey(int inBarKey) {
        this.inBarKey = inBarKey;
    }

    public int getInlikeKey() {
        return inlikeKey;
    }

    public void setInlikeKey(int inlikeKey) {
        this.inlikeKey = inlikeKey;
    }

    public int getIntolikeKey() {
        return intolikeKey;
    }

    public void setIntolikeKey(int intolikeKey) {
        this.intolikeKey = intolikeKey;
    }

    public int getInvideoKey() {
        return invideoKey;
    }

    public void setInvideoKey(int invideoKey) {
        this.invideoKey = invideoKey;
    }

    public SignInTaskFPresenterImpl() {
        signModel = new SignModelImpl();
    }

    public void alreadyTask(String account, OnReturn onReturn) {
        signModel.updateSignCoin(account, String.valueOf(10), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), 10, null);
                        onReturn.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn {
        void onReturn();
    }
}
