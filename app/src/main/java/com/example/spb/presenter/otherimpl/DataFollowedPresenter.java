package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.Diary;
import com.example.spb.entity.Followed;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.DiaryModelImpl;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataFollowedPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter followedModel;
    public List<Followed> followedList;
    private String account;
    private String a;
    private Gson gson;

    public DataFollowedPresenter(String user_account) {
        account = user_account;
        followedModel = new FollowedModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        Followed followed = new Followed();
        followed.setUser_account(account);
        followedModel.selectData(SpbModelBasicInter.DATAFOLLOWED_SELECT_ONE, followed, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        followedList = gson.fromJson(a.substring(3),new TypeToken<List<Followed>>()
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
