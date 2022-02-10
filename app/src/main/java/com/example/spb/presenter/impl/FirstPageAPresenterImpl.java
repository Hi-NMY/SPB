package com.example.spb.presenter.impl;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.entity.Dto.VerifyPasswordDto;
import com.example.spb.model.implA.AccountSecurityModelImpl;
import com.example.spb.model.inter.AccountSecurityModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SendHandler;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.FirstPage;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.UserInfo;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class FirstPageAPresenterImpl extends BasePresenter<IFirstPageAView> implements IFirstPageAPresenter {

    private final AccountSecurityModel accountSecurityModel;
    private String userAccount;

    public FirstPageAPresenterImpl() {
        accountSecurityModel = new AccountSecurityModelImpl();
    }

    public void setAccount(Intent data) {
        try {
            String account = data.getStringExtra(IUserRegisteredPageAView.STRINGEXTRA);
            if (isAttachView()) {
                getView().response(account, FirstPage.RESPONSE_ACC);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean setFirstLogIn() {
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_FirstLogIn));
        editor.putBoolean(InValues.send(R.string.FirstLogIn_login), false);
        return editor.commit();
    }

    public void verifyAccount(String userAccount, Handler handler) {
        this.userAccount = userAccount;
        accountSecurityModel.queryVerifyAndUserFull(userAccount, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<UserDto> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        UserDto userDto = requestEntityJson.getData();
                        setUserMsg(userDto);
                        handler.sendMessage(SendHandler.setMessage(FirstPage.RESPONSE_SUCCESS_ONE, userDto));
                    } else {
                        handler.sendMessage(SendHandler.setMessage(FirstPage.RESPONSE_ERROR, null));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setUserMsg(UserDto userDto) {
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        editor.putString(InValues.send(R.string.user_account), userDto.getUser_account());
        editor.putString(InValues.send(R.string.user_birth), userDto.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite), userDto.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home), userDto.getUser_home());
        editor.putString(InValues.send(R.string.user_ip), userDto.getUser_ip());
        editor.putString(InValues.send(R.string.user_name), userDto.getUser_name());
        editor.putString(InValues.send(R.string.user_privacy), userDto.getUser_privacy());
        editor.putString(InValues.send(R.string.user_profile), userDto.getUser_profile());
        editor.putString(InValues.send(R.string.user_token), userDto.getUser_token());
        editor.putString(InValues.send(R.string.stu_sex), userDto.getStu_sex());
        editor.putString(InValues.send(R.string.stu_name), userDto.getStu_name());
        editor.apply();
    }

    public void verifyPassword(String userPassword, Handler verifyAccountHanlder) {
        SharedPreferences sharedPreferences2 = MySharedPreferences.getShared(InValues.send(R.string.Shared_Push));
        VerifyPasswordDto v = new VerifyPasswordDto();
        v.setUser_account(userAccount);
        v.setUser_password(userPassword);
        v.setUser_ip(sharedPreferences2.getString(InValues.send(R.string.push_id), ""));
        accountSecurityModel.queryVerifyUserPassword(v, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        verifyAccountHanlder.sendMessage(SendHandler.setMessage(FirstPage.RESPONSE_SUCCESS_TWO, null));
                    } else {
                        verifyAccountHanlder.sendMessage(SendHandler.setMessage(FirstPage.RESPONSE_ERROR, null));
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }


    public void connectRong(String token, String uName, String uAccount) {
        RongIM.connect(token, new RongIMClient.ConnectCallback() {
            @Override
            public void onSuccess(String s) {
                UserInfo userInfo = new UserInfo(uAccount, uName, Uri.parse(InValues.send(R.string.prefix_img)
                        + uAccount + InValues.send(R.string.suffix_head_img)));
                RongIM.getInstance().setCurrentUserInfo(userInfo);
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_RongUser));
                editor.putString(InValues.send(R.string.RongUser_userId), uAccount);
                editor.putString(InValues.send(R.string.RongUser_token), token);
                editor.apply();
                SharedPreferences.Editor editor1 = MySharedPreferences.saveShared(InValues.send(R.string.Shared_notify_setup));
                editor1.putBoolean(InValues.send(R.string.notify_collect), true);
                editor1.putBoolean(InValues.send(R.string.notify_comment), true);
                editor1.putBoolean(InValues.send(R.string.notify_follow), true);
                editor1.putBoolean(InValues.send(R.string.notify_like), true);
                editor1.putBoolean(InValues.send(R.string.notify_system), true);
                editor1.putBoolean(InValues.send(R.string.notify_all), true);
                editor.apply();
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
