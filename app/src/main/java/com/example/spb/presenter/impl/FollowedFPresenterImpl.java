package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.UserFollowAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Followed;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IFollowedFPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowedFView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class FollowedFPresenterImpl extends BasePresenter<IFollowedFView> implements IFollowedFPresenter {

    private UserFollowAdapter userFollowAdapter;
    private AttentionUserPage attentionUserPage;
    private SpbModelBasicInter followedModel;

    public FollowedFPresenterImpl(AttentionUserPage a) {
        this.attentionUserPage = a;
        followedModel = new FollowedModelImpl();
    }

    public void addList(List<User> u, RecyclerView recyclerView){
        if (userFollowAdapter == null){
            userFollowAdapter = new UserFollowAdapter(u,attentionUserPage);
            recyclerView.setAdapter(userFollowAdapter);
        }else {
            userFollowAdapter.setNewList(u);
        }
    }

    public void addFollowedList(String account){
        Followed followed = new Followed();
        followed.setUser_account(account);
        followedModel.selectData(followedModel.DATAFOLLOWED_SELECT_TWO, followed, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<User> users = new Gson().fromJson(a.substring(3),new TypeToken<List<User>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Followed),0,(Serializable) users);
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
