package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Sign;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.SignInModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInSignFPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.inter.ISignInSignFView;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SignInSignFPresenterImpl extends BasePresenter<ISignInSignFView> implements ISignInSignFPresenter {

    private SpbModelBasicInter signModel;
    private String AsignDay;

    public SignInSignFPresenterImpl() {
        signModel = new SignInModelImpl();
    }

    public void addNewSign(String account,int signDay){
        AsignDay = String.valueOf(MyResolve.OutSignDay(signDay));
        Sign sign = new Sign();
        sign.setUser_account(account);
        sign.setSign_coin(signDay);
        sign.setSign_day(AsignDay);
        signModel.updateData(signModel.DATASIGN_UPDATE_FOUR, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        AsignDay = AsignDay.replace("\\","");
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign),signDay,String.valueOf(AsignDay));
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
}
