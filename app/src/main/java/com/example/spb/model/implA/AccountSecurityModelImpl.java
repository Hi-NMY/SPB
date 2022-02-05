package com.example.spb.model.implA;

import com.example.spb.R;
import com.example.spb.entity.Dto.UpdatePasswordDto;
import com.example.spb.entity.Dto.VerifyPasswordDto;
import com.example.spb.model.SpbAbstract.SpbModelAbstrate;
import com.example.spb.model.inter.AccountSecurityModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.InValues;
import okhttp3.FormBody;

/**
 * @author nmy
 * @title: AccountSecurityModelImpl
 * @date 2022-01-30 14:41
 */
public class AccountSecurityModelImpl extends SpbModelAbstrate implements AccountSecurityModel {

    @Override
    public void updateUserPassword(UpdatePasswordDto updatePasswordDto, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", updatePasswordDto.getUser_account())
                .add("user_password", updatePasswordDto.getUser_password())
                .add("user_password_old", updatePasswordDto.getUser_password_old())
                .build();
        sendHttp(InValues.send(R.string.updateUserPassword), requestBody, callBack);
    }

    @Override
    public void queryVerifyAndUserFull(String userAccount, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", userAccount)
                .build();
        sendHttp(InValues.send(R.string.queryVerifyAndUserFull), requestBody, callBack);
    }

    @Override
    public void queryVerifyUserPassword(VerifyPasswordDto verifyPasswordDto, MyCallBack callBack) {
        requestBody = new FormBody.Builder()
                .add("user_account", verifyPasswordDto.getUser_account())
                .add("user_password", verifyPasswordDto.getUser_password())
                .add("user_ip", verifyPasswordDto.getUser_ip())
                .build();
        sendHttp(InValues.send(R.string.queryVerifyUserPassword), requestBody, callBack);
    }
}
