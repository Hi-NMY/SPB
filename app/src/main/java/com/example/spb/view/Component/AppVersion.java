package com.example.spb.view.Component;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.common.RequestCode;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.entity.Dto.AppVersionDto;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.xserver.APPDownload;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppVersion {

    private final Context context;
    private final Activity activity;
    private DialogInter versionDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mAppDetailed;
    private TextView mCodeNum;

    public AppVersion(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void startVersion(String version, boolean tipKey) {
        chechingVersion(version, new OnReturn() {
            @Override
            public void onReturn(RequestEntityJson<AppVersionDto> requestEntityJson) {
                Looper.prepare();
                if (requestEntityJson.getResultCode().getCode() == RequestCode.ERROR) {
                    if (tipKey) {
                        MyToastClass.ShowToast(MyApplication.getContext(), "已是最新版本");
                    }
                } else if (requestEntityJson.getResultCode().getCode() == RequestCode.SUCCESS) {
                    List<String> strings = MyResolve.InBadge(requestEntityJson.getData().getDetailed());
                    versionDialog = new ComponentDialog(activity, R.layout.dialog_downloadapp_view, new ComponentDialog.InitDialog() {
                        @Override
                        public void initView(View view) {
                            mButtonRight = view.findViewById(R.id.button_right);
                            mButtonClose = view.findViewById(R.id.button_close);
                            mAppDetailed = view.findViewById(R.id.app_detailed);
                            mCodeNum = view.findViewById(R.id.code_num);
                        }

                        @Override
                        public void initData() {
                            StringBuilder stringBuilder = new StringBuilder();
                            for (int i = 0; i < strings.size(); i++) {
                                if (i == 0) {
                                    mCodeNum.setText(strings.get(0));
                                } else {
                                    stringBuilder.append(strings.get(i)).append("\n");
                                }
                            }
                            mAppDetailed.setText(stringBuilder.toString());
                        }

                        @Override
                        public void initListener() {
                            mButtonClose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    versionDialog.closeMyDialog();
                                }
                            });
                            mButtonRight.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    versionDialog.closeMyDialog();
                                    MyToastClass.ShowToast(context, "请在通知栏查看更新进度");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            APPDownload appDownload = new APPDownload(context, activity);
                                            appDownload.startDownload();
                                        }
                                    }).start();
                                }
                            });
                        }
                    });
                    versionDialog.showMyDialog();
                }
                Looper.loop();
            }
        });
    }

    private void chechingVersion(String version, OnReturn onReturn) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(InValues.send(R.string.isVerison))
                .post(new FormBody.Builder()
                        .add("versionCode", version)
                        .build())
                .addHeader("token", MySharedPreferences.getShared(InValues.send(R.string.Shared_user_token)).getString(InValues.send(R.string.token), ""))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    onReturn.onReturn(new Gson().fromJson(value, new TypeToken<RequestEntityJson<AppVersionDto>>() {
                    }.getType()));
                }
            }
        });
    }

    public interface OnReturn {
        void onReturn(RequestEntityJson<AppVersionDto> requestEntityJson);
    }
}
