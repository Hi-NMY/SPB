package com.example.spb.xserver;

import android.app.Notification;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.example.spb.R;
import com.example.spb.entity.Notice;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.taobao.accs.ACCSClient;
import com.taobao.accs.AccsClientConfig;
import com.taobao.agoo.TaobaoRegister;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.*;
import com.umeng.message.entity.UMessage;

import java.util.HashMap;
import java.util.Map;

/**
 * PushSDK集成帮助类
 */
public class PushHelper {

    private static final String TAG = PushHelper.class.getSimpleName();

    /**
     * 预初始化，已添加子进程中初始化sdk。
     * 使用场景：用户未同意隐私政策协议授权时，延迟初始化
     *
     * @param context 应用上下文
     */
    public static void preInit(Context context) {
        try {
            //解决推送消息显示乱码的问题
            AccsClientConfig.Builder builder = new AccsClientConfig.Builder();
            builder.setAppKey("umeng:" + PushConstants.APP_KEY);
            builder.setAppSecret(PushConstants.MESSAGE_SECRET);
            builder.setTag(AccsClientConfig.DEFAULT_CONFIGTAG);
            ACCSClient.init(context, builder.build());
            TaobaoRegister.setAccsConfigTag(context, AccsClientConfig.DEFAULT_CONFIGTAG);
        } catch (Exception e) {
            e.printStackTrace();
        }
        UMConfigure.preInit(context, PushConstants.APP_KEY, PushConstants.CHANNEL);
        init(context);
    }

    /**
     * 初始化。
     * 场景：用户已同意隐私政策协议授权时
     *
     * @param context 应用上下文
     */
    public static void init(Context context) {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在 应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息
        UMConfigure.init(
                context,
                PushConstants.APP_KEY,
                PushConstants.CHANNEL,
                UMConfigure.DEVICE_TYPE_PHONE,
                PushConstants.MESSAGE_SECRET
        );

        //获取消息推送实例
        final PushAgent pushAgent = PushAgent.getInstance(context);

        pushAdvancedFunction(context);

        //注册推送服务，每次调用register方法都会回调该接口
        pushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "deviceToken --> " + deviceToken);
                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_Push));
                editor.putString(InValues.send(R.string.push_id), deviceToken);
                editor.apply();
            }

            @Override
            public void onFailure(String errCode, String errDesc) {
                Log.e(TAG, "register failure：--> " + "code:" + errCode + ",desc:" + errDesc);
            }
        });
    }

    //推送高级功能集成说明
    private static void pushAdvancedFunction(Context context) {
        PushAgent pushAgent = PushAgent.getInstance(context);

        //设置通知栏显示通知的最大个数（0～10），0：不限制个数
        pushAgent.setDisplayNotificationNumber(0);
        pushAgent.setMuteDurationSeconds(0);
        try {
            SharedPreferences sharedPreferences = context.getSharedPreferences(InValues.send(R.string.Shared_notify_setup), Context.MODE_PRIVATE);
            boolean notifyAll = sharedPreferences.getBoolean(InValues.send(R.string.notify_all), true);
            if (notifyAll) {
                pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_ENABLE);
            } else {
                pushAgent.setNotificationPlaySound(MsgConstant.NOTIFICATION_PLAY_SDK_DISABLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //推送消息处理
        UmengMessageHandler msgHandler = new UmengMessageHandler() {
            @Override
            public Notification getNotification(Context context, UMessage uMessage) {
                String nowDate = MyDateClass.showNowDate();
                Map<String, String> mapData = new HashMap<>();
                for (Map.Entry entry : uMessage.extra.entrySet()) {
                    Object key = entry.getKey();
                    Object value = entry.getValue();
                    mapData.put(String.valueOf(key), String.valueOf(value));
                }
                if (Integer.parseInt(mapData.get(InValues.send(R.string.Push_fun))) == AndroidUnicast.PUSHSYSTEMKEY) {
                    Notice notice1 = new Notice();
                    notice1.setNotice_date(nowDate);
                    notice1.setPush_fun(Integer.parseInt(mapData.get(InValues.send(R.string.Push_fun))));
                    notice1.setPb_id(mapData.get(InValues.send(R.string.Push_system_key)));
                    notice1.setSee(1);
                    notice1.save();
                } else {
                    Notice notice = new Notice();
                    notice.setNotice_date(nowDate);
                    notice.setPush_fun(Integer.parseInt(mapData.get(InValues.send(R.string.Push_fun))));
                    notice.setUser_account(mapData.get(InValues.send(R.string.Push_useraccount_key)));
                    notice.setUser_name(mapData.get(InValues.send(R.string.Push_username_key)));
                    notice.setPb_id(mapData.getOrDefault(InValues.send(R.string.Push_pbid_key), ""));
                    notice.setComment_id(mapData.containsKey(InValues.send(R.string.Push_commentid_key)) ? Integer.parseInt(mapData.get(InValues.send(R.string.Push_commentid_key))) : 0);
                    notice.setSee(1);
                    notice.save();
                }
                SpbBroadcast.sendReceiver(context, InValues.send(R.string.Bcr_new_messasge), 0, null);
                return super.getNotification(context, uMessage);
            }

            //处理通知栏消息
            @Override
            public void dealWithNotificationMessage(Context context, UMessage msg) {
                super.dealWithNotificationMessage(context, msg);
                Log.i(TAG, "notification receiver:" + msg.getRaw().toString());
            }

            //处理透传消息
            @Override
            public void dealWithCustomMessage(Context context, UMessage msg) {
                super.dealWithCustomMessage(context, msg);
                Log.i(TAG, "custom receiver:" + msg.getRaw().toString());
            }
        };
        pushAgent.setMessageHandler(msgHandler);

        //推送消息点击处理
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void openActivity(Context context, UMessage msg) {
                super.openActivity(context, msg);
                Log.i(TAG, "click openActivity: " + msg.getRaw().toString());
            }

            @Override
            public void launchApp(Context context, UMessage msg) {
                super.launchApp(context, msg);
                Log.i(TAG, "click launchApp: " + msg.getRaw().toString());
            }

            @Override
            public void dismissNotification(Context context, UMessage msg) {
                super.dismissNotification(context, msg);
                Log.i(TAG, "click dismissNotification: " + msg.getRaw().toString());
            }
        };
        pushAgent.setNotificationClickHandler(notificationClickHandler);
    }

}
