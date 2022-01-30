package com.example.spb.presenter.impl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IAttentionUserPageAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.inter.IAttentionUserPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class AttentionUserPageAPresenterImpl extends BasePresenter<IAttentionUserPageAView> implements IAttentionUserPageAPresenter {

    private SpbModelBasicInter followModel;
    private SpbModelBasicInter followedModel;
    private Follow follow;
    private Followed followed;

    public AttentionUserPageAPresenterImpl() {
        followModel = new FollowModelImpl();
        followedModel = new FollowedModelImpl();
        follow = new Follow();
        followed = new Followed();
    }

    public void addFollowList(String account,OnReturn onReturn){
        follow.setUser_account(account);
        followModel.selectData(followModel.DATAFOLLOW_SELECT_TWO, follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<User> users = new Gson().fromJson(a.substring(3),new TypeToken<List<User>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow),0,(Serializable) users);
                        onReturn.onReturn();
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

    public void addFollowedList(String account,OnReturn onReturn){
        followed.setUser_account(account);
        followedModel.selectData(followedModel.DATAFOLLOWED_SELECT_TWO, followed, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<User> users = new Gson().fromJson(a.substring(3),new TypeToken<List<User>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Followed),0,(Serializable) users);
                        onReturn.onReturn();
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

    public interface OnReturn{
        void onReturn();
    }
}
