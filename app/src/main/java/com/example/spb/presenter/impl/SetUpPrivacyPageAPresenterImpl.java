package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISetUpPrivacyPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.SetUpPrivacyPage;
import com.example.spb.view.inter.ISetUpPrivacyPageAView;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SetUpPrivacyPageAPresenterImpl extends BasePresenter<ISetUpPrivacyPageAView> implements ISetUpPrivacyPageAPresenter {

    private final List<Integer> keys;
    private final UserModel userModel;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setKeys(int position, boolean a) {
        if (a) {
            getKeys().remove(position);
            getKeys().add(position, 1);
        } else {
            getKeys().remove(position);
            getKeys().add(position, 2);
        }
    }

    public String getStringPrivacy() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < getKeys().size(); i++) {
            stringBuilder.append(getKeys().get(i));
        }
        return stringBuilder.toString();
    }

    public SetUpPrivacyPageAPresenterImpl() {
        keys = new ArrayList<>();
        userModel = new UserModelImpl();
    }

    public void setMyPrivacy(String s) {
        for (int i = 0; i < s.length(); i++) {
            keys.add(Integer.valueOf(s.substring(i, i + 1)));
        }
    }

    public void updateUserPrivacy(String account, int p) {
        UserDto userDto = new UserDto();
        userDto.setUser_account(account);
        userDto.setUser_privacy(getStringPrivacy());
        userModel.updateUserPrivacy(getStringPrivacy(), account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                    if (ResponseToast.toToast(requestCode)) {
                        getView().response(p, SetUpPrivacyPage.ON_SUCCEED);
                    } else {
                        getView().response(p, SetUpPrivacyPage.ON_ERROR);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
