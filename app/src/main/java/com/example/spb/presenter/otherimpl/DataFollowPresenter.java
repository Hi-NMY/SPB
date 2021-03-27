package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.Diary;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataFollowPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter followModel;
    public List<Follow> followList;
    private String account;
    private String a;
    private Gson gson;

    public DataFollowPresenter(String user_account) {
        account = user_account;
        followModel = new FollowModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        Follow follow = new Follow();
        follow.setUser_account(account);
        followModel.selectData(SpbModelBasicInter.DATAFOLLOW_SELECT_ONE, follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        followList = gson.fromJson(a.substring(3),new TypeToken<List<Follow>>()
                        {}.getType());
                    }else {

                    }
                } catch (Exception e) {

                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
