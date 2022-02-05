package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.UserFollowAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.entity.Followed;
import com.example.spb.model.implA.FollowedModelImpl;
import com.example.spb.model.inter.FollowedModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFollowedFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowedFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class FollowedFPresenterImpl extends BasePresenter<IFollowedFView> implements IFollowedFPresenter {

    private UserFollowAdapter userFollowAdapter;
    private final AttentionUserPage attentionUserPage;
    private final FollowedModel followedModel;

    public FollowedFPresenterImpl(AttentionUserPage a) {
        this.attentionUserPage = a;
        followedModel = new FollowedModelImpl();
    }

    public void addList(List<UserDto> u, RecyclerView recyclerView) {
        if (userFollowAdapter == null) {
            userFollowAdapter = new UserFollowAdapter(u, attentionUserPage);
            recyclerView.setAdapter(userFollowAdapter);
        } else {
            userFollowAdapter.setNewList(u);
        }
    }

    public void addFollowedList(String account) {
        Followed followed = new Followed();
        followed.setUser_account(account);
        followedModel.queryFollowedUserList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<UserDto> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Followed),
                                0, requestEntityJson.getData());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

}
