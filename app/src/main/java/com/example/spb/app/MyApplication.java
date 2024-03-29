package com.example.spb.app;

import android.app.Application;
import android.content.Context;
import com.example.spb.R;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.xserver.PushHelper;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import org.litepal.LitePal;

public class MyApplication extends Application implements RongIMClient.ConnectionStatusListener {

    private static Context context;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //预初始化
        PushHelper.preInit(this);
        //push初始化
        new Thread(new Runnable() {
            @Override
            public void run() {
                PushHelper.init(getApplicationContext());
            }
        }).start();

        LitePal.initialize(this);
        RongIM.init(context, InValues.send(R.string.rong_app_key));
        RongIM.setConnectionStatusListener(this);
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                SpbBroadcast.sendReceiver(getContext(), InValues.send(R.string.Bcr_new_messasge), 0, null);
                return false;
            }
        });
    }

    public static Context getContext() {
        return context;
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        if (connectionStatus.equals(ConnectionStatus.KICKED_OFFLINE_BY_OTHER_CLIENT)) {
            //当前用户账号在其他端登录，请提示用户并做出对应处理
        }
    }
}
