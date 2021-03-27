package com.example.spb.presenter.otherimpl;

import com.example.spb.entity.Diary;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Like;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.LikeModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class DataLikePresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter likeModel;
    public List<Like> likeList;
    private String account;
    private String a;
    private Gson gson;

    public DataLikePresenter(String user_account) {
        account = user_account;
        likeModel = new LikeModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        Like like = new Like();
        like.setPb_one_id(account);
        likeModel.selectData(SpbModelBasicInter.DATALIKE_SELECT_ONE, like, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == SUCCESS){
                        likeList = gson.fromJson(a.substring(3),new TypeToken<List<Like>>()
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
