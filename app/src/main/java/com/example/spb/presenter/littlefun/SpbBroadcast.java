package com.example.spb.presenter.littlefun;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.Serializable;
import java.util.List;

public class SpbBroadcast {

    public static BroadcastReceiver broadcastReceiver;
    public static LocalBroadcastManager localBroadcastManager;

    public static void sendReceiver(Context c, String intentName,int textone, String texttwo){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(c);
        Intent intent = new Intent(intentName);
        intent.putExtra("key_one",textone);
        intent.putExtra("key_two",texttwo);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void sendReceiver(Context c, String intentName,int textone, int texttwo){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(c);
        Intent intent = new Intent(intentName);
        intent.putExtra("key_one",textone);
        intent.putExtra("key_two",texttwo);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void sendReceiver(Context c, String intentName,int textone, Serializable texttwo){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(c);
        Intent intent = new Intent(intentName);
        intent.putExtra("key_one",textone);
        intent.putExtra("key_two",texttwo);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void sendReceiver(Context c, String intentName,int textone,String texttwo, Serializable textthree){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(c);
        Intent intent = new Intent(intentName);
        intent.putExtra("key_one",textone);
        intent.putExtra("key_two",texttwo);
        intent.putExtra("key_three",textthree);
        localBroadcastManager.sendBroadcast(intent);
    }

    public static void obtainRecriver(Context c, String actionName, BroadcastReceiver receiver){
        broadcastReceiver = receiver;
        localBroadcastManager = LocalBroadcastManager.getInstance(c);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(actionName);
        localBroadcastManager.registerReceiver(receiver,intentFilter);
    }

    public static void destroyBrc(BroadcastReceiver b){
        localBroadcastManager.unregisterReceiver(b);
    }

    public static void destroyBrc(){
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
    }
}
