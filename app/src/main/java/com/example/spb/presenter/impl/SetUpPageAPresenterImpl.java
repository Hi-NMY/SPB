package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.inter.ISetUpPageAPresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.presenter.utils.Task;
import com.example.spb.view.inter.ISetUpPageAView;
import io.rong.imkit.RongIM;

public class SetUpPageAPresenterImpl extends BasePresenter<ISetUpPageAView> implements ISetUpPageAPresenter {

    private SpbModelBasicInter userModel;

    public SetUpPageAPresenterImpl() {
        userModel = new UserModelImpl();
    }

    public void loginOut(String account,OnReturn onReturn){
        initBarNum();
        initRongUser();
        initServerDate();
        initShareSign();
        initShareTask();
        deleteIp(account);
        initShareLogIn();
        outRong();
        onReturn.onReturn();
    }

    private void outRong(){
        RongIM.getInstance().logout();
    }

    private void deleteIp(String account){
        //退出登录时加入逻辑：删除user_ip。使用户无法收到其他用户推送的消息
        User user = new User();
        user.setUser_account(account);
        userModel.deleteData(userModel.DATAUSER_DELETE_ONE,user,null);
    }

    private void initShareLogIn(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_FirstLogIn));
        editor.putBoolean(InValues.send(R.string.FirstLogIn_login), true);
        editor.apply();
    }

    private void initShareSign(){
        SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_sign_task));
        editor.putInt(InValues.send(R.string.sign_task_daysign),0);
        editor.putInt(InValues.send(R.string.sign_task_bar),0);
        editor.putInt(InValues.send(R.string.sign_task_like),0);
        editor.putInt(InValues.send(R.string.sign_task_tolike),0);
        editor.putInt(InValues.send(R.string.sign_task_video),0);
        editor.apply();
    }

    private void initShareTask(){
        Task.initTaskLike();
        Task.initTaskTopic();
    }

    private void initServerDate(){
        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_server_date));
        e.putString(InValues.send(R.string.server_date),"");
        e.commit();
    }

    private void initBarNum(){
        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
        e.putInt(InValues.send(R.string.userBar_num),0);
        e.commit();
    }

    private void initRongUser(){
        SharedPreferences.Editor e = MySharedPreferences.saveShared(InValues.send(R.string.Shared_RongUser));
        e.putString(InValues.send(R.string.RongUser_userId),"");
        e.putString(InValues.send(R.string.RongUser_token),"");
        e.commit();
    }

    public interface OnReturn{
        void onReturn();
    }

}
