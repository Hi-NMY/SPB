package com.example.spb.presenter.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Sign;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.SignInModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInPageAPresenter;
import com.example.spb.presenter.littlefun.*;
import com.example.spb.view.inter.ISignInPageAView;
import com.example.spb.xserver.ObtainServerDate;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SignInPageAPresenterImpl extends BasePresenter<ISignInPageAView> implements ISignInPageAPresenter {

    private BaseMVPActivity baseMVPActivity;
    private SpbModelBasicInter signModel;
    private Sign s;
    private String serverDate;
    private int old;
    private int now;
    private String account;

    public SignInPageAPresenterImpl(Activity aISignInPageAView) {
        baseMVPActivity = (BaseMVPActivity) aISignInPageAView;
        signModel = new SignInModelImpl();
    }

    public void obtainUserSignDate(String account,OnReturn onReturn){
        this.account = account;
        Sign sign = new Sign();
        sign.setUser_account(account);
        signModel.selectData(signModel.DATASIGN_SELECT_ONE, sign, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    if (Integer.valueOf(a.substring(0,3)) == 200){
                        s = new Gson().fromJson(a.substring(3),Sign.class);
                        obtainNowDay(onReturn);
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

    public void obtainNowDay(OnReturn onReturn){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_server_date));
        String sharedDate = sharedPreferences.getString(InValues.send(R.string.server_date),null);
        ObtainServerDate.obtainDate(new ObtainServerDate.OnReturn() {
            @Override
            public void onReturn(String date) {
                serverDate = date;
                Calendar calendar = new GregorianCalendar();
                if (sharedDate != null && !sharedDate.equals("")){
                    calendar.setTime(MyDateClass.stringToDate(sharedDate));
                    old = calendar.get(calendar.DAY_OF_MONTH);
                }else {
                    old = 0;
                }
                calendar.setTime(MyDateClass.stringToDate(date));
                now = calendar.get(calendar.DAY_OF_MONTH);
                if (old != now){
                    initTask();
                    noDay(onReturn);
                }else {
                    if (isAttachView()){
                        if (s.getSign_right() == 1){
                            if (s.getSign_day() == null || s.getSign_day().equals("")){
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_ERROR_RIGHT,(Serializable)s);
                            }else {
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_NO_RIGHT,(Serializable)s);
                            }
                            //未签到广播1   signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                        }else {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_RIGHT,(Serializable)s);
                            //已签到广播2    signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                        }
                        onReturn.onReturn();
                    }
                }
            }
        });
    }

    public void noDay(OnReturn onReturn){
        if (s.getSign_right() == 1 || now - old > 1){
            //修改数据库删除signday
            Sign sign = new Sign();
            sign.setUser_account(account);
            signModel.updateData(signModel.DATASIGN_UPDATE_ONE, sign, new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    try {
                        String a = response.body().string();
                        if (Integer.valueOf(a) == 200){
                            s.setSign_day("");
                            SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_ERROR_RIGHT,(Serializable)s);
                            //断签广播3（签到初始化）  signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                            onReturn.onReturn();
                        }
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        }else {
            List<Integer> days = MyResolve.InSignDay(s.getSign_day());
            Sign sign = new Sign();
            sign.setUser_account(account);
            if (days.size() == 7){
                signModel.updateData(signModel.DATASIGN_UPDATE_THREE, sign, new MyCallBack() {
                    @Override
                    public void onSuccess(@NotNull Response response) {
                        try {
                            String a = response.body().string();
                            if (Integer.valueOf(a) == 200){
                                s.setSign_right(1);
                                s.setSign_day("");
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_ERROR_RIGHT,(Serializable)s);
                                onReturn.onReturn();
                            }
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(int t) {

                    }
                });
            }else {
                signModel.updateData(signModel.DATASIGN_UPDATE_TWO, sign, new MyCallBack() {
                    @Override
                    public void onSuccess(@NotNull Response response) {
                        try {
                            String a = response.body().string();
                            if (Integer.valueOf(a) == 200){
                                s.setSign_right(1);
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_sign_data),getView().SIGN_NO_RIGHT,(Serializable)s);
                                //未签到广播1   signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                                onReturn.onReturn();
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
    }

    public void initTask(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
        editor.putInt(InValues.send(R.string.sign_task_daysign),0);
        editor.putInt(InValues.send(R.string.sign_task_bar),0);
        editor.putInt(InValues.send(R.string.sign_task_like),0);
        editor.putInt(InValues.send(R.string.sign_task_tolike),0);
        editor.putInt(InValues.send(R.string.sign_task_video),0);
        editor.commit();

        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_server_date));
        e.putString(InValues.send(R.string.server_date),serverDate);
        e.commit();

        Task.initTaskLike();
        Task.initTaskTopic();
    }

    public interface OnReturn{
        void onReturn();
    }
}
