package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.FollowModelImpl;
import com.example.spb.model.implA.FollowedModelImpl;
import com.example.spb.model.inter.FollowModel;
import com.example.spb.model.inter.FollowedModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IAttentionUserPageAPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.inter.IAttentionUserPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;

public class AttentionUserPageAPresenterImpl extends BasePresenter<IAttentionUserPageAView> implements IAttentionUserPageAPresenter {

    private final FollowModel followModel;
    private final FollowedModel followedModel;

    public AttentionUserPageAPresenterImpl() {
        followModel = new FollowModelImpl();
        followedModel = new FollowedModelImpl();
    }

    public void addFollowList(String account, OnReturn onReturn) {
        followModel.queryFollowUserList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<UserDto> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow)
                                , 0, (Serializable) requestListJson.getDataList());
                        onReturn.onReturn();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void addFollowedList(String account, OnReturn onReturn) {
        followedModel.queryFollowedUserList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<UserDto> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow)
                                , 0, (Serializable) requestListJson.getDataList());
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
