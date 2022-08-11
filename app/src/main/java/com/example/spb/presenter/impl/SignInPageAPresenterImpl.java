package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.entity.Sign;
import com.example.spb.model.implA.SignModelImpl;
import com.example.spb.model.inter.SignModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISignInPageAPresenter;
import com.example.spb.presenter.utils.*;
import com.example.spb.view.Component.ResponseToast;
import com.example.spb.view.activity.SignInPage;
import com.example.spb.view.inter.ISignInPageAView;
import com.example.spb.xserver.ObtainServerDate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class SignInPageAPresenterImpl extends BasePresenter<ISignInPageAView> implements ISignInPageAPresenter {

    private final SignModel signModel;
    private Sign s;
    private String serverDate;
    private int old;
    private int now;
    private String account;

    public SignInPageAPresenterImpl() {
        signModel = new SignModelImpl();
    }

    public void obtainUserSignDate(String account, OnReturn onReturn) {
        this.account = account;
        Sign sign = new Sign();
        sign.setUser_account(account);
        signModel.queryUserSign(account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<Sign> requestEntityJson = new Gson().fromJson(value, new TypeToken<RequestEntityJson<Sign>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        s = requestEntityJson.getData();
                        obtainNowDay(onReturn);
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainNowDay(OnReturn onReturn) {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_server_date));
        String sharedDate = sharedPreferences.getString(InValues.send(R.string.server_date), null);
        ObtainServerDate.obtainDate(new ObtainServerDate.OnReturn() {
            @Override
            public void onReturn(String date) {
                RequestEntityJson<String> requestEntityJson = new Gson().fromJson(date, new TypeToken<RequestEntityJson<String>>() {
                }.getType());
                serverDate = requestEntityJson.getData();
                Calendar calendar = new GregorianCalendar();
                if (sharedDate != null && !"".equals(sharedDate)) {
                    calendar.setTime(MyDateClass.stringToDate(sharedDate));
                    old = calendar.get(Calendar.DAY_OF_MONTH);
                } else {
                    old = 0;
                }
                calendar.setTime(MyDateClass.stringToDate(serverDate));
                now = calendar.get(Calendar.DAY_OF_MONTH);
                if (old != now) {
                    initTask();
                   // noDay(onReturn);
                }
                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_RIGHT, s);
                onReturn.onReturn();
//                else {
//                    if (isAttachView()) {
//                        if (s.getSign_right() == 1) {
//                            if (s.getSign_day() == null || "".equals(s.getSign_day())) {
//                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_ERROR_RIGHT, s);
//                            } else {
//                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_NO_RIGHT, s);
//                            }
//                            //未签到广播1   signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
//                        } else {
//                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_RIGHT, s);
//                            //已签到广播2    signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
//                        }
//                        onReturn.onReturn();
//                    }
//                }
            }
        });
    }

    public void noDay(OnReturn onReturn) {
        if (s.getSign_right() == 1 || now - old > 1) {
            //修改数据库删除signday
            signModel.updateSignDay(account, new MyCallBack() {
                @Override
                public void onSuccess(@NotNull Response response) {
                    String value = DataVerificationTool.isEmpty(response);
                    if (value != null) {
                        RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                        if (ResponseToast.toToast(requestCode)) {
                            s.setSign_day("");
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_ERROR_RIGHT, s);
                            //断签广播3（签到初始化）  signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                            onReturn.onReturn();
                        }
                    }
                }

                @Override
                public void onError(int t) {

                }
            });
        } else {
            List<Integer> days = MyResolve.InSignDay(s.getSign_day());
            if (days.size() == 7) {
                signModel.updateSignDayAndRight(account, new MyCallBack() {
                    @Override
                    public void onSuccess(@NotNull Response response) {
                        String value = DataVerificationTool.isEmpty(response);
                        if (value != null) {
                            RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                            if (ResponseToast.toToast(requestCode)) {
                                s.setSign_right(1);
                                s.setSign_day("");
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_ERROR_RIGHT, s);
                                onReturn.onReturn();
                            }
                        }
                    }

                    @Override
                    public void onError(int t) {

                    }
                });
            } else {
                signModel.updateSignRight(account, new MyCallBack() {
                    @Override
                    public void onSuccess(@NotNull Response response) {
                        String value = DataVerificationTool.isEmpty(response);
                        if (value != null) {
                            RequestCode requestCode = new Gson().fromJson(value, RequestCode.class);
                            if (ResponseToast.toToast(requestCode)) {
                                s.setSign_right(1);
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), SignInPage.SIGN_NO_RIGHT, s);
                                //未签到广播1   signInPage -> 金币,sign ->  连续签到数据 ,badge ->  徽章数据   接收
                                onReturn.onReturn();
                            }
                        }
                    }

                    @Override
                    public void onError(int t) {

                    }
                });
            }
        }
    }

    public void initTask() {
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
        editor.putInt(InValues.send(R.string.sign_task_daysign), 0);
        editor.putInt(InValues.send(R.string.sign_task_bar), 0);
        editor.putInt(InValues.send(R.string.sign_task_like), 0);
        editor.putInt(InValues.send(R.string.sign_task_tolike), 0);
        editor.putInt(InValues.send(R.string.sign_task_video), 0);
        editor.commit();

        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_server_date));
        e.putString(InValues.send(R.string.server_date), serverDate);
        e.commit();

        Task.initTaskLike();
        Task.initTaskTopic();
    }

    public interface OnReturn {
        void onReturn();
    }
}
