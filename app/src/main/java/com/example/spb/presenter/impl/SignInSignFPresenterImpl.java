package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.model.implA.SignModelImpl;
import com.example.spb.model.inter.SignModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInSignFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ISignInSignFView;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class SignInSignFPresenterImpl extends BasePresenter<ISignInSignFView> implements ISignInSignFPresenter {

    private final SignModel signModel;
    private String asignDay;

    public SignInSignFPresenterImpl() {
        signModel = new SignModelImpl();
    }

    public void addNewSign(String account, int signDay) {
        asignDay = String.valueOf(MyResolve.OutSignDay(signDay));
        signModel.updateSignDayAndRightAndCoin(account, asignDay, String.valueOf(signDay), new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        asignDay = asignDay.replace("\\", "");
                        SpbBroadcast.sendReceiver(MyApplication.getContext()
                                , InValues.send(R.string.Bcr_add_sign), signDay, String.valueOf(asignDay));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
