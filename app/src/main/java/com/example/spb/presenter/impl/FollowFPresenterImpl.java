package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.UserFollowAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.FollowModelImpl;
import com.example.spb.model.inter.FollowModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFollowFPresenter;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class FollowFPresenterImpl extends BasePresenter<IFollowFView> implements IFollowFPresenter {

    private UserFollowAdapter userFollowAdapter;
    private final AttentionUserPage attentionUserPage;
    private final FollowModel followModel;

    public FollowFPresenterImpl(AttentionUserPage a) {
        this.attentionUserPage = a;
        followModel = new FollowModelImpl();
    }

    public void addList(List<UserDto> u, RecyclerView recyclerView) {
        if (userFollowAdapter == null) {
            userFollowAdapter = new UserFollowAdapter(u, attentionUserPage);
            recyclerView.setAdapter(userFollowAdapter);
        } else {
            userFollowAdapter.setNewList(u);
        }
    }

    public void addFollowList(String account) {
        followModel.queryFollowUserList(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<UserDto> requestListJson = new Gson().fromJson(value, new TypeToken<RequestListJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow),
                                0, (Serializable) requestListJson.getDataList());
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
