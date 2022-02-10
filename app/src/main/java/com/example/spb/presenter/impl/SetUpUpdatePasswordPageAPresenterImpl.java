package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.entity.Dto.UpdatePasswordDto;
import com.example.spb.model.implA.AccountSecurityModelImpl;
import com.example.spb.model.inter.AccountSecurityModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISetUpUpdatePasswordPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.ISetUpUpdatePasswordPageAView;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class SetUpUpdatePasswordPageAPresenterImpl extends BasePresenter<ISetUpUpdatePasswordPageAView> implements ISetUpUpdatePasswordPageAPresenter {

    private final AccountSecurityModel accountSecurityModel;

    public SetUpUpdatePasswordPageAPresenterImpl() {
        accountSecurityModel = new AccountSecurityModelImpl();
    }

    public void updatePassword(String account, String password, String passwordOld, OnReturn onReturn) {
        UpdatePasswordDto updatePasswordDto = new UpdatePasswordDto();
        updatePasswordDto.setUser_account(account);
        updatePasswordDto.setUser_password(password);
        updatePasswordDto.setUser_password_old(passwordOld);
        accountSecurityModel.updateUserPassword(updatePasswordDto, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    ResponseToast.toToast(requestCode);
                    onReturn.onReturn();
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
