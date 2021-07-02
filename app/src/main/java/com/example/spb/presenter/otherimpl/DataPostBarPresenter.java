package com.example.spb.presenter.otherimpl;

import android.content.SharedPreferences;
import android.os.Handler;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.impl.UserPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.presenter.littlefun.SendHandler;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class DataPostBarPresenter {

    private SpbModelBasicInter barModel;
    private String user_account;
    private Gson gson;
    public List<Bar> bars;
    public List<Bar> followbars;
    private static String cacheDate = "1";
    private static String cacheDate2 = "1";

    public DataPostBarPresenter(String user_account) {
        this.user_account = user_account;
        barModel = new BarModelImpl();
        gson = new Gson();
        obtainNewBar(true);
        obtainFollowUserBar(true);
    }

    public void obtainNewBar(boolean loadingFun){
        Bar bar = new Bar();
        if (loadingFun){
            bar.setPb_date("1");
        }else {
            bar.setPb_date(cacheDate);
        }
        barModel.selectData(barModel.DATABAR_SELECT_ONE, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (a.substring(0,3).equals("200")){
                        bars = gson.fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        cacheDate = bars.get(bars.size() - 1).getPb_date();
                        if (loadingFun){
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar),0, (Serializable)bars);
                        }else {
                            bars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar),1, (Serializable)bars);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        obtainBarNum();
    }

    public void obtainBarNum(){
        Bar bar = new Bar();
        bar.setUser_account(user_account);
        barModel.selectData(barModel.DATABAR_SELECT_FIVE, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        List<Bar> bars = new Gson().fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        if (bars != null){
                            SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
                            editor.putInt(InValues.send(R.string.userBar_num),bars.size());
                            editor.apply();
                        }
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

    public void obtainFollowUserBar(boolean loadingFun){
        Bar bar = new Bar();
        bar.setUser_account(user_account);
        if (loadingFun){
            bar.setPb_date("1");
        }else {
            bar.setPb_date(cacheDate2);
        }
        barModel.selectData(barModel.DATABAR_SELECT_SIX, bar, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (a.substring(0,3).equals("200")){
                        followbars = gson.fromJson(a.substring(3),new TypeToken<List<Bar>>(){}.getType());
                        cacheDate2 = followbars.get(followbars.size() - 1).getPb_date();
                        if (loadingFun){
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_FollowUserBar),0, (Serializable)followbars);
                        }else {
                            followbars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_FollowUserBar),1, (Serializable)followbars);
                        }
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
