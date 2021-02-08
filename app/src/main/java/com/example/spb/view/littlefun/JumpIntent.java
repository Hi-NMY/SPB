package com.example.spb.view.littlefun;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import com.example.spb.app.MyApplication;

public class JumpIntent {

    public static void startMyIntent(Class toActivity){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        MyApplication.getContext().startActivity(intent);
    }

    public static void startMsgIntent(Class toActivity,SetMsg setMsg){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        setMsg.setMessage(intent);
        MyApplication.getContext().startActivity(intent);
    }

    public static void startNewIntent(Class toActivity){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(intent);
    }

    public static void startNewMsgIntent(Class toActivity,SetMsg setMsg){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        setMsg.setMessage(intent);
        MyApplication.getContext().startActivity(intent);
    }

    public static void startForResultIntent(Activity myAct,Class toActivity,int result){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        myAct.startActivityForResult(intent,result);
    }

    public static void startMsgForResultIntent(Activity myAct,Class toActivity,int result,SetMsg setMsg){
        Intent intent = new Intent(MyApplication.getContext(),toActivity);
        setMsg.setMessage(intent);
        myAct.startActivityForResult(intent,result);
    }

    public static void startSetResultIntent(Activity myAct,int result,SetMsg setMsg){
        Intent intent = new Intent();
        setMsg.setMessage(intent);
        myAct.setResult(result,intent);
    }

    public static void finishAct(Activity myAct){
        myAct.finish();
    }

    public interface SetMsg{
        void setMessage(Intent intent);
    }

}
