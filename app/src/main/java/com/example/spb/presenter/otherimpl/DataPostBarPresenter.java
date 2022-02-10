package com.example.spb.presenter.otherimpl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.Bar;
import com.example.spb.model.implA.PostBarModelImpl;
import com.example.spb.model.inter.PostBarModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ResponseToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class DataPostBarPresenter {

    private final PostBarModel barModel;
    private final String user_account;
    private final Gson gson;
    public List<Bar> bars;
    public List<Bar> followbars;
    public List<Bar> barVideos;
    private static String cacheDate = "";
    private static String cacheDate2 = "";
    private static String cacheDate3 = "";

    public DataPostBarPresenter(String userAccount) {
        this.user_account = userAccount;
        barModel = new PostBarModelImpl();
        gson = new Gson();
        obtainNewBar(true);
        obtainFollowUserBar(true);
        obtainUserVideo(true);
    }

    public void obtainNewBar(boolean loadingFun) {
        if (loadingFun) {
            cacheDate = "";
        }
        barModel.queryNoVideoBarListForDate(cacheDate, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        bars = requestListJson.getDataList();
                        cacheDate = bars.get(bars.size() - 1).getPb_date();
                        if (loadingFun) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar), 0, (Serializable) bars);
                        } else {
                            bars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar), 1, (Serializable) bars);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
        obtainBarNum();
    }

    public void obtainBarNum() {
        barModel.queryUserBarCount(user_account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<String> requestEntityJson = gson.fromJson(value, new TypeToken<RequestEntityJson<String>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntityJson.getResultCode())) {
                        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
                        editor.putInt(InValues.send(R.string.userBar_num), Integer.parseInt(requestEntityJson.getData()));
                        editor.apply();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainFollowUserBar(boolean loadingFun) {
        if (loadingFun) {
            cacheDate2 = "";
        }
        barModel.queryNoVideoFollowBarListForDate(user_account, cacheDate2, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        followbars = requestListJson.getDataList();
                        if (followbars != null && followbars.size() > 0){
                            cacheDate2 = followbars.get(followbars.size() - 1).getPb_date();
                        }
                        if (loadingFun) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_FollowUserBar), 0, (Serializable) followbars);
                        } else {
                            assert followbars != null;
                            followbars.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_FollowUserBar), 1, (Serializable) followbars);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void obtainUserVideo(boolean loadingFun) {
        if (loadingFun) {
            cacheDate3 = "";
        }
        barModel.queryVideoBarListForDate(cacheDate3, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<Bar> requestListJson = gson.fromJson(value, new TypeToken<RequestListJson<Bar>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestListJson.getResultCode())) {
                        barVideos = requestListJson.getDataList();
                        if (barVideos != null && barVideos.size() > 0) {
                            cacheDate3 = barVideos.get(barVideos.size() - 1).getPb_date();
                        }
                        if (loadingFun) {
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_video), 0, (Serializable) barVideos);
                        } else {
                            assert barVideos != null;
                            barVideos.remove(0);
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_video), 1, (Serializable) barVideos);
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
