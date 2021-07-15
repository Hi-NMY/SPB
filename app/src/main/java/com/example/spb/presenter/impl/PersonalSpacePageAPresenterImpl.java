package com.example.spb.presenter.impl;

import android.net.Uri;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.*;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalSpacePageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class PersonalSpacePageAPresenterImpl extends BasePresenter<IPersonalSpacePageAView> implements IPersonalSpacePageAPresenter {

    private String headImgPath;
    private String bgImgPath;
    private SpbModelBasicInter userModel;
    private SpbModelBasicInter followedModel;
    private SpbModelBasicInter followModel;
    private SpbModelBasicInter barModel;
    private SpbModelBasicInter videoModel;
    private User user;
    private boolean userFollowKey = false;

    public PersonalSpacePageAPresenterImpl() {
        barModel = new BarModelImpl();
        userModel = new UserModelImpl();
        followedModel = new FollowedModelImpl();
        followModel = new FollowModelImpl();
        videoModel = new VideoModelImpl();
    }

    public boolean isUserFollowKey() {
        return userFollowKey;
    }

    public void setUserFollowKey(boolean userFollowKey) {
        this.userFollowKey = userFollowKey;
    }

    public void getHeadImage(String account,List<LocalMedia> result){
        for (LocalMedia media : result) {
            if (media.isCompressed()){
                headImgPath = media.getCompressPath();
            }else {
                headImgPath = media.getCutPath();
            }
        }
        User user = new User();
        user.setUser_account(account);
        user.setUser_head_image(headImgPath);
        userModel.updateData(userModel.DATAUSER_UPDATE_TWO, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (isAttachView() && Integer.valueOf(a) == 200){
                        getView().response(headImgPath, getView().RESPONSE_SUCCESS);
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

    public void getBgImage(String account,List<LocalMedia> result){
        for (LocalMedia media : result) {
            if (media.isCompressed()){
                bgImgPath = media.getCompressPath();
            }else {
                bgImgPath = media.getCutPath();
            }
        }
        User user = new User();
        user.setUser_account(account);
        user.setUser_bg_image(bgImgPath);
        userModel.updateData(userModel.DATAUSER_UPDATE_THREE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (isAttachView() && Integer.valueOf(a) == 200){
                        getView().response(bgImgPath, getView().RESPONSE_SUCCESS);
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

    public void getUser(String account,OnReturn onReturn){
        user = new User();
        user.setUser_account(account);
        userModel.selectData(userModel.FIRSTPAGE_ONE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        user = new Gson().fromJson(a.substring(3),User.class);
                        onReturn.onReturn(user);
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

    public void getFollowNum(String account,OnReturnNum onReturn){
        Follow follow = new Follow();
        follow.setUser_account(account);
        followModel.selectData(followModel.DATAFOLLOW_SELECT_ONE, follow, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Follow> followList = new Gson().fromJson(a.substring(3),new TypeToken<List<Follow>>()
                        {}.getType());
                        onReturn.onReturn(followList.size());
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

    public void getFollowedNum(String account,OnReturnNum onReturn){
        Followed followed = new Followed();
        followed.setUser_account(account);
        followedModel.selectData(followedModel.DATAFOLLOWED_SELECT_ONE, followed, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Followed> followedList = new Gson().fromJson(a.substring(3),new TypeToken<List<Followed>>()
                        {}.getType());
                        onReturn.onReturn(followedList.size());
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

    public void obtainPersonalBar(String account,OnReturnBar onReturnBar){
        Bar bar = new Bar();
        bar.setUser_account(account);
        bar.setPb_date("1");
        barModel.selectData(barModel.DATABAR_SELECT_FOUR, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_bar)
                                ,0,account,(Serializable)bars);
                        onReturnBar.onReturn();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });

        videoModel.selectData(videoModel.DATAVIDEO_SELECT_FOUR, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_videobar)
                                ,0,account,(Serializable)bars);
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

    public void updateRong(String account,String name){
        UserInfo userInfo = new UserInfo(account, name,
                Uri.parse(InValues.send(R.string.httpHeader) + "/UserImageServer/" + account + "/HeadImage/myHeadImage.png"));
        RongIM.getInstance().refreshUserInfoCache(userInfo);
    }

    public interface OnReturn{
        void onReturn(User user);
    }

    public interface OnReturnNum{
        void onReturn(int num);
    }

    public interface OnReturnBar{
        void onReturn();
    }
}
