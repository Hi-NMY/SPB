package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IUserQrPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.view.inter.IUserQrPageAView;
import com.example.spb.xserver.ObtainServerDate;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;

public class UserQrPageAPresenterImpl extends BasePresenter<IUserQrPageAView> implements IUserQrPageAPresenter {

    private String date;

    public UserQrPageAPresenterImpl() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void obtainDate(OnReturn onReturn) {
        ObtainServerDate.obtainDate(new ObtainServerDate.OnReturn() {
            @Override
            public void onReturn(String date) {
                setDate(date);
                onReturn.onReturn();
            }
        });
    }

    public interface OnReturn{
        void onReturn();
    }
}
