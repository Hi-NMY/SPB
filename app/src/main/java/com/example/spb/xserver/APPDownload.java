package com.example.spb.xserver;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.example.spb.R;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.Component.MyToastClass;

import java.io.File;

public class APPDownload {

    private final NotificationCompat.Builder b;
    private NotificationManager m;
    private static final String NOTIFICATIONID = "1";
    private static final int ID = 1;
    private final Activity activity;
    private final Context context;

    public APPDownload(Context c, Activity a) {
        this.context = c;
        this.activity = a;
        b = new NotificationCompat.Builder(c, NOTIFICATIONID);
        b.setSmallIcon(R.mipmap.ic_launcher);
        b.setContentTitle("下载更新");
        b.setContentText("更新");
    }

    public void startDownload() {
        m = (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
        m.notify(ID, b.build());
        b.setProgress(100, 0, false);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            //只在Android O之上需要渠道，这里的第一个参数要和下面的channelId一样
            NotificationChannel notificationChannel = new NotificationChannel(String.valueOf(NOTIFICATIONID), "name", NotificationManager.IMPORTANCE_HIGH);
            //如果这里用IMPORTANCE_NOENE就需要在系统的设置里面开启渠道，通知才能正常弹出
            m.createNotificationChannel(notificationChannel);
        }
        DownLoadNewApp.get().downLoad(InValues.send(R.string.SPBAPK), "download", new DownLoadNewApp.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                m.cancel(ID);//设置关闭通知栏
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    i.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    Uri uri = FileProvider.getUriForFile(context, context.getPackageName() + ".fileprovider", file);
                    i.setDataAndType(uri, "application/vnd.android.package-archive");
                } else {
                    i.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }
                activity.startActivity(i);
            }

            @Override
            public void onDownloading(int progress) {
                b.setProgress(100, progress, false);
                m.notify(ID, b.build());
                //下载进度提示
                b.setContentText("下载" + progress + "%");
            }

            @Override
            public void onDownloadError() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        MyToastClass.ShowToast(context, "下载错误,请重新下载");
                    }
                });
            }
        });
    }
}
