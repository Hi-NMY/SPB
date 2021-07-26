package com.example.spb.presenter.otherimpl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.google.gson.Gson;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class DataUserMsgPresenter {

    private SpbModelBasicInter userModel;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    public String user_account;
    public String user_name;
    public String stu_name;
    public String user_birth;
    public String user_home;
    public String user_favorite;
    public String user_profile;
    public String user_badge;
    public int user_longDay;
    public String user_privacy;
    public String user_token;
    public String user_ip;
    public String stu_sex;

    public DataUserMsgPresenter(String user_account) {
        userModel = new UserModelImpl();
        User user = new User();
        user.setUser_account(user_account);
        userModel.selectData(userModel.FIRSTPAGE_ONE, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                try {
                    String a = response.body().string();
                    switch (Integer.valueOf(a.substring(0,3))) {
                        case 200:
                            User user1 = new Gson().fromJson(a.substring(3),User.class);
                            user.setUser_account(user1.getUser_account());
                            user.setUser_birth(user1.getUser_birth());
                            user.setUser_home(user1.getUser_home());
                            user.setUser_favorite(user1.getUser_favorite());
                            user.setUser_profile(user1.getUser_profile());
                            user.setUser_badge(user1.getUser_badge());
                            user.setUser_longday(user1.getUser_longday());
                            user.setUser_privacy(user1.getUser_privacy());
                            user.setUser_ip(user1.getUser_ip());
                            user.setUser_name(user1.getUser_name());
                            user.setUser_token(user1.getUser_token());
                            user.setStu_sex(user1.getStu_sex());
                            user.setStu_name(user1.getStu_name());
                            setUserMsg(user);
                            break;
                        default:
                            break;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(int t) {
            }
        });
    }

    public void setUserMsg(User user){
        sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        user_account = user.getUser_account();
        user_name = user.getUser_name();
        stu_name = user.getStu_name();
        user_birth = user.getUser_birth();
        user_home = user.getUser_home();
        user_favorite = user.getUser_favorite();
        user_profile = user.getUser_profile();
        user_badge = user.getUser_badge();
        user_longDay = user.getUser_longday();
        user_privacy = user.getUser_privacy();
        user_token = user.getUser_token();
        user_ip = user.getUser_ip();
        stu_sex = user.getStu_sex();
        editor.putString(InValues.send(R.string.user_account),user.getUser_account());
        editor.putString(InValues.send(R.string.user_birth),user.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite),user.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home),user.getUser_home());
        editor.putString(InValues.send(R.string.user_ip),user.getUser_ip());
        editor.putString(InValues.send(R.string.user_name),user.getUser_name());
        editor.putString(InValues.send(R.string.user_privacy),user.getUser_privacy());
        editor.putString(InValues.send(R.string.user_badge),user.getUser_badge());
        editor.putInt(InValues.send(R.string.user_longday),user.getUser_longday());
        editor.putString(InValues.send(R.string.user_profile),user.getUser_profile());
        editor.putString(InValues.send(R.string.user_token),user.getUser_token());
        editor.putString(InValues.send(R.string.stu_sex),user.getStu_sex());
        editor.putString(InValues.send(R.string.stu_name),user.getStu_name());
        editor.apply();
    }

    public void setUpdateUserMsg(User updateUserMsg){
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        user_name = updateUserMsg.getUser_name();
        user_birth = updateUserMsg.getUser_birth();
        user_home = updateUserMsg.getUser_home();
        user_favorite = updateUserMsg.getUser_favorite();
        user_profile = updateUserMsg.getUser_profile();
        editor.putString(InValues.send(R.string.user_birth),updateUserMsg.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite),updateUserMsg.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home),updateUserMsg.getUser_home());
        editor.putString(InValues.send(R.string.user_name),updateUserMsg.getUser_name());
        editor.putString(InValues.send(R.string.user_profile),updateUserMsg.getUser_profile());
    }

    public String getUser_account() {
        user_account = sharedPreferences.getString(InValues.send(R.string.user_account),"");
        return user_account;
    }

    public void setUser_account(String user_account) {
        editor.putString(InValues.send(R.string.user_account),user_account);
        editor.apply();
        this.user_account = user_account;
    }

    public String getUser_name() {
        user_name = sharedPreferences.getString(InValues.send(R.string.user_name),"");
        return user_name;
    }

    public void setUser_name(String user_name) {
        editor.putString(InValues.send(R.string.user_name),user_name);
        editor.apply();
        this.user_name = user_name;
    }

    public String getStu_name() {
        stu_name = sharedPreferences.getString(InValues.send(R.string.stu_name),"");
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        editor.putString(InValues.send(R.string.stu_name),stu_name);
        editor.apply();
        this.stu_name = stu_name;
    }

    public String getUser_birth() {
        user_birth = sharedPreferences.getString(InValues.send(R.string.user_birth),"");
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        editor.putString(InValues.send(R.string.user_birth),user_birth);
        editor.apply();
        this.user_birth = user_birth;
    }

    public String getUser_home() {
        user_home = sharedPreferences.getString(InValues.send(R.string.user_home),"");
        return user_home;
    }

    public void setUser_home(String user_home) {
        editor.putString(InValues.send(R.string.user_home),user_home);
        editor.apply();
        this.user_home = user_home;
    }

    public String getUser_favorite() {
        user_favorite = sharedPreferences.getString(InValues.send(R.string.user_favorite),"");
        return user_favorite;
    }

    public void setUser_favorite(String user_favorite) {
        editor.putString(InValues.send(R.string.user_favorite),user_favorite);
        editor.apply();
        this.user_favorite = user_favorite;
    }

    public String getUser_profile() {
        user_profile = sharedPreferences.getString(InValues.send(R.string.user_profile),"");
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        editor.putString(InValues.send(R.string.user_profile),user_profile);
        editor.apply();
        this.user_profile = user_profile;
    }

    public String getUser_badge() {
        user_badge = sharedPreferences.getString(InValues.send(R.string.user_badge),"");
        return user_badge;
    }

    public void setUser_badge(String user_badge) {
        editor.putString(InValues.send(R.string.user_badge),user_badge);
        editor.apply();
        this.user_badge = user_badge;
    }

    public int getUser_longDay() {
        user_longDay = sharedPreferences.getInt(InValues.send(R.string.user_longday),0);
        return user_longDay;
    }

    public void setUser_longDay(int user_longDay) {
        editor.putInt(InValues.send(R.string.user_longday),user_longDay);
        editor.apply();
        this.user_longDay = user_longDay;
    }

    public String getUser_privacy() {
        user_privacy = sharedPreferences.getString(InValues.send(R.string.user_privacy),"");
        return user_privacy;
    }

    public void setUser_privacy(String user_privacy) {
        editor.putString(InValues.send(R.string.user_privacy),user_privacy);
        editor.apply();
        this.user_privacy = user_privacy;
    }

    public String getUser_token() {
        user_token = sharedPreferences.getString(InValues.send(R.string.user_token),"");
        return user_token;
    }

    public void setUser_token(String user_token) {
        editor.putString(InValues.send(R.string.user_token),user_token);
        editor.apply();
        this.user_token = user_token;
    }

    public String getUser_ip() {
        user_ip = sharedPreferences.getString(InValues.send(R.string.user_ip),"");
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        editor.putString(InValues.send(R.string.user_ip),user_ip);
        editor.apply();
        this.user_ip = user_ip;
    }

    public String getStu_sex() {
        stu_sex = sharedPreferences.getString(InValues.send(R.string.stu_sex),"");
        return stu_sex;
    }

    public void setStu_sex(String stu_sex) {
        editor.putString(InValues.send(R.string.stu_sex),stu_sex);
        editor.apply();
        this.stu_sex = stu_sex;
    }

    interface OnReturn{
        void success();
        void error();
    }

}
