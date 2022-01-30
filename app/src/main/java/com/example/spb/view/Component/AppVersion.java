package com.example.spb.view.Component;

import android.app.Activity;
import android.content.Context;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.xserver.APPDownload;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class AppVersion {

    private Context context;
    private Activity activity;
    private DialogInter versionDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mAppDetailed;
    private TextView mCodeNum;

    public AppVersion(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void startVersion(String version,boolean tipKey){
        chechingVersion(version, new OnReturn() {
            @Override
            public void onReturn(int code, String app_detailed) {
                Looper.prepare();
                if (code == 201){
                    if (tipKey){
                        MyToastClass.ShowToast(MyApplication.getContext(),"已是最新版本");
                    }
                }else if (code == 200){
                    List<String> strings = MyResolve.InBadge(app_detailed);
                    versionDialog = new ComponentDialog(activity, R.layout.dialog_downloadapp_view, new ComponentDialog.InitDialog() {
                        @Override
                        public void initView(View view) {
                            mButtonRight = (Button) view.findViewById(R.id.button_right);
                            mButtonClose = (Button) view.findViewById(R.id.button_close);
                            mAppDetailed = (TextView) view.findViewById(R.id.app_detailed);
                            mCodeNum = (TextView) view.findViewById(R.id.code_num);
                        }

                        @Override
                        public void initData() {
                            StringBuffer stringBuffer = new StringBuffer();
                            if (strings != null || strings.size() != 0){
                                for (int i = 0 ; i < strings.size() ;i++){
                                    if (i == 0){
                                        mCodeNum.setText(strings.get(0));
                                    }else {
                                        stringBuffer.append(strings.get(i) + "\n");
                                    }
                                }
                                mAppDetailed.setText(stringBuffer.toString());
                            }
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
                                    MyToastClass.ShowToast(context,"请在通知栏查看更新进度");
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            APPDownload appDownload = new APPDownload(context,activity);
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

    private void chechingVersion(String version,OnReturn onReturn){
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20,TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(InValues.send(R.string.AppVersion))
                .post(new FormBody.Builder()
                        .add("versionCode",version)
                        .build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String a = response.body().string();
                if (Integer.valueOf(a.substring(0,3)) == 201){
                    onReturn.onReturn(201,null);
                }else if(Integer.valueOf(a.substring(0,3)) == 200){
                    onReturn.onReturn(200,a.substring(3));
                }
            }
        });
    }

    public interface OnReturn{
        void onReturn(int code,String app_detailed);
    }
}
