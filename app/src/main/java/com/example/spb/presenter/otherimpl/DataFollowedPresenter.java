package com.example.spb.presenter.otherimpl;

import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Followed;
import com.example.spb.model.implA.FollowedModelImpl;
import com.example.spb.model.inter.FollowedModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.view.Component.ResponseToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class DataFollowedPresenter {

    private final FollowedModel followedModel;
    public List<String> followedList;
    private final String account;
    private final Gson gson;

    public DataFollowedPresenter(String userAccount) {
        account = userAccount;
        followedModel = new FollowedModelImpl();
        gson = new Gson();
        initDate();
    }

    public void initDate() {
        Followed followed = new Followed();
        followed.setUser_account(account);
        followedModel.queryFollowedList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<String> requestList = gson.fromJson(value, new TypeToken<RequestListJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestList.getResultCode())) {
                        followedList = requestList.getDataList();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public boolean determineFollowed(String account) {
        if (followedList != null && followedList.stream().anyMatch(followedList -> followedList.equals(account))) {
            return true;
        } else {
            return false;
        }
    }

    public int obtainFollowedNum() {
        if (followedList != null) {
            return followedList.size();
        } else {
            return 0;
        }
    }
}
