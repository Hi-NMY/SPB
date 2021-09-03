package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.UserFollowAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Follow;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFollowFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FollowFPresenterImpl extends BasePresenter<IFollowFView> implements IFollowFPresenter {

    private UserFollowAdapter userFollowAdapter;
    private AttentionUserPage attentionUserPage;
    private SpbModelBasicInter followModel;

    public FollowFPresenterImpl(AttentionUserPage a) {
        this.attentionUserPage = a;
        followModel = new FollowModelImpl();
    }

    public void addList(List<User> u, RecyclerView recyclerView){
        if (userFollowAdapter == null){
            userFollowAdapter = new UserFollowAdapter(u,attentionUserPage);
            recyclerView.setAdapter(userFollowAdapter);
        }else {
            userFollowAdapter.setNewList(u);
        }
    }

    public void addFollowList(String account){
        Follow follow = new Follow();
        follow.setUser_account(account);
        followModel.selectData(followModel.DATAFOLLOW_SELECT_TWO, follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<User> users = new Gson().fromJson(a.substring(3),new TypeToken<List<User>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow),0,(Serializable) users);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {
            }
        });
    }

}
