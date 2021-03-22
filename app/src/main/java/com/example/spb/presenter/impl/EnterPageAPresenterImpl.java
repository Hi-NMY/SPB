package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.EnterPageAModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IEnterPageAPresenter;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.view.inter.IEnterPageAView;

public class EnterPageAPresenterImpl extends BasePresenter<IEnterPageAView> implements IEnterPageAPresenter {

    private SpbModelBasicInter enterPageAModel;

    public EnterPageAPresenterImpl() {
        enterPageAModel = new EnterPageAModelImpl();
    }

    @Override
    public boolean getFirstLogIn() {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared("firstLogIn");
        return sharedPreferences.getBoolean("login",true);
    }

    public boolean getInitialize(){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared("firstInitialize");
        return sharedPreferences.getBoolean("initialize",false);
    }

    public void setInitialize(){
//        SharedPreferences.Editor editor = MySharedPreferences.saveShared("settingMessage");
//        editor.putBoolean("thumb",true);
//        editor.putBoolean("comment",true);
//        editor.putBoolean("follow",true);
//        editor.putBoolean("share",true);
//        editor.putBoolean("share",true);
//        editor.commit();

//        SharedPreferences.Editor editor1 = MySharedPreferences.saveShared("privacySetting");
//        editor1.putBoolean("key",true);
//        editor1.putBoolean("mYuanxiSwitch",true);
//        editor1.putBoolean("mZhuanyeSwitch",true);
//        editor1.putBoolean("mNianjiSwitch",true);
//        editor1.commit();

//        SharedPreferences.Editor editor2 = MySharedPreferences.saveShared("todayDate");
//        editor2.putBoolean("key",true);
//        editor2.putString("Date","2020-01-01");
//        editor2.commit();

//        SharedPreferences.Editor editor3 = MySharedPreferences.saveShared("taskNum");
//        editor3.putBoolean("key",true);
//        editor3.putInt("sign",0);
//        editor3.putInt("oneDynamic",0);
//        editor3.putInt("thumbDynamic",0);
//        editor3.putInt("goodVoice",0);
//        editor3.putInt("goToSpace",0);
//        editor3.commit();
    }
}
