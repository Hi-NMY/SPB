package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.RongUser;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IUserRegisteredPageAPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.presenter.littlefun.SendHandler;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.google.gson.Gson;
import com.luck.picture.lib.entity.LocalMedia;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class UserRegisteredPageAPresenterImpl extends BasePresenter<IUserRegisteredPageAView>{

    private SpbModelBasicInter userModel;
    private String headImgPath;
    private RongUser r;

    public UserRegisteredPageAPresenterImpl() {
        userModel = new UserModelImpl();
    }

    public void getHeadImage(List<LocalMedia> result){
        for (LocalMedia media : result) {
            if (media.isCut() && media.isCompressed()){
                headImgPath = media.getCompressPath();
            }else {
                headImgPath = media.getCutPath();
            }
        }

        if (isAttachView()){
            getView().response(headImgPath,getView().IMAGE_SUCCESS);
        }
    }

    public void registerUser(User user, Handler handler){
        userModel.addData(UserModelImpl.REGISTEREDPAGE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                if (isAttachView()){
                    try {
                        String a = response.body().string();
                        switch (Integer.valueOf(a.substring(0,3))){
                            case 200:
                                String data = a.substring(3);
                                r = new Gson().fromJson(data,RongUser.class);
                                setRongShared();
                                handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_SUCCESS,null));
                                break;
                            default:
                                handler.sendMessage(SendHandler.setMessage(Integer.valueOf(a.substring(0,3)),null));
                                break;
                        }
                    } catch (IOException e) {
                        handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onError(int t) {
                handler.sendMessage(SendHandler.setMessage(getView().RESPONSE_ZERO,null));
            }
        });
    }

    public void setRongShared(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_RongUser));
        editor.putString(InValues.send(R.string.RongUser_userId),r.getUserId());
        editor.putString(InValues.send(R.string.RongUser_token),r.getToken());
        editor.apply();
    }
}
