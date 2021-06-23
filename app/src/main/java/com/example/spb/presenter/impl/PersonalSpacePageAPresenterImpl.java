package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Follow;
import com.example.spb.entity.Followed;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.FollowedModelImpl;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IPersonalSpacePageAPresenter;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.luck.picture.lib.entity.LocalMedia;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class PersonalSpacePageAPresenterImpl extends BasePresenter<IPersonalSpacePageAView> implements IPersonalSpacePageAPresenter {

    private String headImgPath;
    private SpbModelBasicInter userModel;
    private SpbModelBasicInter followedModel;
    private SpbModelBasicInter followModel;
    private User user;

    public PersonalSpacePageAPresenterImpl() {
        userModel = new UserModelImpl();
        followedModel = new FollowedModelImpl();
        followModel = new FollowModelImpl();
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

    public interface OnReturn{
        void onReturn(User user);
    }

    public interface OnReturnNum{
        void onReturn(int num);
    }
}
