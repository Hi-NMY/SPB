package com.example.spb.presenter.otherimpl;

import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Diary;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DataFollowPresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter followModel;
    public List<Follow> followList;
    private String user_account;
    private String a;
    private Gson gson;

    public DataFollowPresenter(String user_account) {
        this.user_account = user_account;
        followModel = new FollowModelImpl();
        followList = new ArrayList<>();
        gson = new Gson();
        initDate();
    }

    public void initDate() {
        Follow follow = new Follow();
        follow.setUser_account(user_account);
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

    public void addFollow(String cacheAccount){
        Follow follow = new Follow();
        follow.setUser_account(cacheAccount);
        follow.setCache_account(user_account);
        followModel.addData(followModel.DATAFOLLOW_ADD_ONE,follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == SUCCESS) {
                        followList.add(0,follow);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow),0,(Serializable)follow);
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

    public void removeFollow(String cacheAccount){
        Follow follow = new Follow();
        follow.setUser_account(cacheAccount);
        follow.setCache_account(user_account);
        followModel.deleteData(followModel.DATAFOLLOW_DELETE_ONE,follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a) == SUCCESS) {
                        followList.removeIf(followList -> followList.getUser_account().equals(follow.getUser_account()));
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow),1,(Serializable)follow);
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

    public boolean determineFollow(String account){
        if (followList != null && followList.stream().anyMatch(followList -> followList.getUser_account().equals(account))){
            return true;
        }else {
            return false;
        }
    }

    public int obtainFollowNum(){
        if (followList != null){
            return followList.size();
        }else {
            return 0;
        }
    }

    public interface OnReturn{
        void onReturn();
    }
}
