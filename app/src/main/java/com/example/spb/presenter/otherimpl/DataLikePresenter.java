package com.example.spb.presenter.otherimpl;

import android.util.Log;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.*;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.FollowModelImpl;
import com.example.spb.model.impl.LikeModelImpl;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.xserver.AndroidNotification;
import com.example.spb.xserver.AndroidUnicast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

public class DataLikePresenter {

    private static int SUCCESS = 200;

    private SpbModelBasicInter likeModel;
    private SpbModelBasicInter barModel;
    public List<Like> likeList;
    private String account;
    private String a;
    private Gson gson;

    public DataLikePresenter(String user_account) {
        account = user_account;
        likeModel = new LikeModelImpl();
        barModel = new BarModelImpl();
        gson = new Gson();
        initDate();
    }

    private void initDate() {
        Like like = new Like();
        like.setCacheAccount(account);
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

    public void updateLikeData(String barId,String account,String oaccount,OnReturn onReturn){
        Like like = new Like();
        like.setPb_one_id(barId);
        like.setCacheAccount(account);
        Bar bar = new Bar();
        bar.setUser_account(oaccount);
        bar.setPb_one_id(barId);
        if (determineLike(barId)){
            onReturn.removeLike();
            likeList.removeIf(likeList -> likeList.getPb_one_id().equals(barId));
            likeModel.deleteData(likeModel.DATALIKE_DELETE_ONE, like, null);
            barModel.updateData(barModel.DATABAR_UPDATE_ONE,bar,null);
        }else {
            onReturn.addLike();
            likeList.add(like);
            likeModel.addData(likeModel.DATALIKE_ADD_ONE, like,null);
            barModel.updateData(barModel.DATABAR_UPDATE_TWO, bar, new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    //获取用户ip发送通知
                    try {
                        String a = response.body().string();
                        if (!account.equals(oaccount)){
                            AndroidUnicast unicast = null;
                            try {
                                unicast = new AndroidUnicast();
                                unicast.setDeviceToken(a);
                                unicast.setTicker( "Android unicast ticker");
                                unicast.setTitle(InValues.send(R.string.Push_Title));
                                unicast.setText(InValues.send(R.string.Push_Like_txt));
                                unicast.setExtraField(InValues.send(R.string.Push_fun),String.valueOf(unicast.PUSHLIKEKEY));
                                unicast.setExtraField(InValues.send(R.string.Push_pbid_key),barId);
                                unicast.clientSend(unicast);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        }
    }

    public boolean determineLike(String id){
        if (likeList != null && likeList.stream().anyMatch(likeList -> likeList.getPb_one_id().equals(id))){
            return true;
        }else {
            return false;
        }
    }

    public interface OnReturn{
        void removeLike();
        void addLike();
    }
}
