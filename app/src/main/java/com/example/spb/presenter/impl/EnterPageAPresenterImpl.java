package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.IEnterPageAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.view.activity.EnterPage;
import com.example.spb.view.inter.IEnterPageAView;
import com.example.spb.xserver.ObtainServerDate;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

public class EnterPageAPresenterImpl extends BasePresenter<IEnterPageAView> implements IEnterPageAPresenter {

    private final UserModel userModel;

    public EnterPageAPresenterImpl() {
        userModel = new UserModelImpl();
    }

    public void setUserIp() {
        SharedPreferences sharedPreferences1 = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
        String account = sharedPreferences1.getString(InValues.send(R.string.user_account), "");
        SharedPreferences sharedPreferences2 = MySharedPreferences.getShared(InValues.send(R.string.Shared_Push));
        String ip = sharedPreferences2.getString(InValues.send(R.string.push_id), "");
        userModel.updateUserIp(account, ip, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {

            }

            @Override
            public void onError(int t) {

            }
        });
    }

    @Override
    public boolean getFirstLogIn() {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_FirstLogIn));
        //  setInitialize();
        return sharedPreferences.getBoolean(InValues.send(R.string.FirstLogIn_login), true);
    }

    public void initDate(EnterPage enterPage, Jump jump) {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
        enterPage.initUserData(sharedPreferences.getString(InValues.send(R.string.user_account), ""));
        jump.toJump();
    }

    public interface Jump {
        void toJump();
    }

    public void setInitialize() {
        ObtainServerDate.obtainDate(new ObtainServerDate.OnReturn() {
            @Override
            public void onReturn(String date) {
                SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_server_date));
                e.putString(InValues.send(R.string.server_date), "2021-07-23 12:23:22");
                e.commit();
            }
        });

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
