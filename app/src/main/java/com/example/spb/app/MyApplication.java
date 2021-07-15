package com.example.spb.app;

import android.app.Application;
import android.content.Context;
import com.example.spb.R;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.InterTotal.SpbInterOne;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Message;
import org.litepal.LitePal;

public class MyApplication extends Application implements RongIMClient.ConnectionStatusListener {

    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        String appKey = "x18ywvqfx4z8c";
        LitePal.initialize(this);
        context = getApplicationContext();
        RongIM.init(context, appKey);
        RongIM.setConnectionStatusListener(this);
        RongIM.setOnReceiveMessageListener(new RongIMClient.OnReceiveMessageWrapperListener() {
            @Override
            public boolean onReceived(Message message, int i, boolean b, boolean b1) {
                SpbBroadcast.sendReceiver(getContext(), InValues.send(R.string.Bcr_new_messasge),0,null);
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
