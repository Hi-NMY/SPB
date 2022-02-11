package com.example.spb.presenter.otherimpl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.common.RequestEntityJson;
import com.example.spb.common.RequestListJson;
import com.example.spb.entity.SchoolTable;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.model.implA.AccountSecurityModelImpl;
import com.example.spb.model.implA.UserModelImpl;
import com.example.spb.model.inter.AccountSecurityModel;
import com.example.spb.model.inter.UserModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MySharedPreferences;
import com.example.spb.view.Component.ResponseToast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import org.litepal.LitePal;

import java.io.IOException;
import java.util.List;

public class DataUserMsgPresenter extends UserDto{

    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private String user_account;
    private String user_name;
    private String stu_name;
    private String user_birth;
    private String user_home;
    private String user_favorite;
    private String user_profile;
    private String user_badge;
    private int user_longDay;
    private String user_privacy;
    private String user_token;
    private String user_ip;
    private String stu_sex;

    public DataUserMsgPresenter(String user_account) {
        AccountSecurityModel accountSecurityModel = new AccountSecurityModelImpl();
        accountSecurityModel.queryVerifyAndUserFull(user_account, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestEntityJson<UserDto> requestEntity = new Gson().fromJson(value, new TypeToken<RequestEntityJson<UserDto>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestEntity.getResultCode())) {
                        setUserMsg(requestEntity.getData());
                        obtainUserSubject();
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public void setUserMsg(UserDto userDto) {
        sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_User));
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        user_account = userDto.getUser_account();
        user_name = userDto.getUser_name();
        stu_name = userDto.getStu_name();
        user_birth = userDto.getUser_birth();
        user_home = userDto.getUser_home();
        user_favorite = userDto.getUser_favorite();
        user_profile = userDto.getUser_profile();
        user_badge = userDto.getUser_badge();
        user_longDay = userDto.getUser_longday();
        user_privacy = userDto.getUser_privacy();
        user_token = userDto.getUser_token();
        user_ip = userDto.getUser_ip();
        stu_sex = userDto.getStu_sex();
        editor.putString(InValues.send(R.string.user_account), userDto.getUser_account());
        editor.putString(InValues.send(R.string.user_birth), userDto.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite), userDto.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home), userDto.getUser_home());
        editor.putString(InValues.send(R.string.user_ip), userDto.getUser_ip());
        editor.putString(InValues.send(R.string.user_name), userDto.getUser_name());
        editor.putString(InValues.send(R.string.user_privacy), userDto.getUser_privacy());
        editor.putString(InValues.send(R.string.user_badge), userDto.getUser_badge());
        editor.putInt(InValues.send(R.string.user_longday), userDto.getUser_longday());
        editor.putString(InValues.send(R.string.user_profile), userDto.getUser_profile());
        editor.putString(InValues.send(R.string.user_token), userDto.getUser_token());
        editor.putString(InValues.send(R.string.stu_sex), userDto.getStu_sex());
        editor.putString(InValues.send(R.string.stu_name), userDto.getStu_name());
        editor.apply();
    }

    public void setUpdateUserMsg(UserDto updateUserDtoMsg) {
        editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_User));
        user_name = updateUserDtoMsg.getUser_name();
        user_birth = updateUserDtoMsg.getUser_birth();
        user_home = updateUserDtoMsg.getUser_home();
        user_favorite = updateUserDtoMsg.getUser_favorite();
        user_profile = updateUserDtoMsg.getUser_profile();
        editor.putString(InValues.send(R.string.user_birth), updateUserDtoMsg.getUser_birth());
        editor.putString(InValues.send(R.string.user_favorite), updateUserDtoMsg.getUser_favorite());
        editor.putString(InValues.send(R.string.user_home), updateUserDtoMsg.getUser_home());
        editor.putString(InValues.send(R.string.user_name), updateUserDtoMsg.getUser_name());
        editor.putString(InValues.send(R.string.user_profile), updateUserDtoMsg.getUser_profile());
        editor.apply();
    }

    public String getUser_account() {
        user_account = sharedPreferences.getString(InValues.send(R.string.user_account), "");
        return user_account;
    }

    public void setUser_account(String user_account) {
        editor.putString(InValues.send(R.string.user_account), user_account);
        editor.apply();
        this.user_account = user_account;
    }

    public String getUser_name() {
        user_name = sharedPreferences.getString(InValues.send(R.string.user_name), "");
        return user_name;
    }

    public void setUser_name(String user_name) {
        editor.putString(InValues.send(R.string.user_name), user_name);
        editor.apply();
        this.user_name = user_name;
    }

    public String getStu_name() {
        stu_name = sharedPreferences.getString(InValues.send(R.string.stu_name), "");
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        editor.putString(InValues.send(R.string.stu_name), stu_name);
        editor.apply();
        this.stu_name = stu_name;
    }

    public String getUser_birth() {
        user_birth = sharedPreferences.getString(InValues.send(R.string.user_birth), "");
        return user_birth;
    }

    public void setUser_birth(String user_birth) {
        editor.putString(InValues.send(R.string.user_birth), user_birth);
        editor.apply();
        this.user_birth = user_birth;
    }

    public String getUser_home() {
        user_home = sharedPreferences.getString(InValues.send(R.string.user_home), "");
        return user_home;
    }

    public void setUser_home(String user_home) {
        editor.putString(InValues.send(R.string.user_home), user_home);
        editor.apply();
        this.user_home = user_home;
    }

    public String getUser_favorite() {
        user_favorite = sharedPreferences.getString(InValues.send(R.string.user_favorite), "");
        return user_favorite;
    }

    public void setUser_favorite(String user_favorite) {
        editor.putString(InValues.send(R.string.user_favorite), user_favorite);
        editor.apply();
        this.user_favorite = user_favorite;
    }

    public String getUser_profile() {
        user_profile = sharedPreferences.getString(InValues.send(R.string.user_profile), "");
        return user_profile;
    }

    public void setUser_profile(String user_profile) {
        editor.putString(InValues.send(R.string.user_profile), user_profile);
        editor.apply();
        this.user_profile = user_profile;
    }

    public String getUser_badge() {
        user_badge = sharedPreferences.getString(InValues.send(R.string.user_badge), "");
        return user_badge;
    }

    public void setUser_badge(String user_badge) {
        editor.putString(InValues.send(R.string.user_badge), user_badge);
        editor.apply();
        this.user_badge = user_badge;
    }

    public int getUser_longDay() {
        user_longDay = sharedPreferences.getInt(InValues.send(R.string.user_longday), 0);
        return user_longDay;
    }

    public void setUser_longDay(int user_longDay) {
        editor.putInt(InValues.send(R.string.user_longday), user_longDay);
        editor.apply();
        this.user_longDay = user_longDay;
    }

    public String getUser_privacy() {
        user_privacy = sharedPreferences.getString(InValues.send(R.string.user_privacy), "");
        return user_privacy;
    }

    public void setUser_privacy(String user_privacy) {
        editor.putString(InValues.send(R.string.user_privacy), user_privacy);
        editor.apply();
        this.user_privacy = user_privacy;
    }

    public String getUser_token() {
        user_token = sharedPreferences.getString(InValues.send(R.string.user_token), "");
        return user_token;
    }

    public void setUser_token(String user_token) {
        editor.putString(InValues.send(R.string.user_token), user_token);
        editor.apply();
        this.user_token = user_token;
    }

    public String getUser_ip() {
        user_ip = sharedPreferences.getString(InValues.send(R.string.user_ip), "");
        return user_ip;
    }

    public void setUser_ip(String user_ip) {
        editor.putString(InValues.send(R.string.user_ip), user_ip);
        editor.apply();
        this.user_ip = user_ip;
    }

    public String getStu_sex() {
        stu_sex = sharedPreferences.getString(InValues.send(R.string.stu_sex), "");
        return stu_sex;
    }

    public void setStu_sex(String stu_sex) {
        editor.putString(InValues.send(R.string.stu_sex), stu_sex);
        editor.apply();
        this.stu_sex = stu_sex;
    }

    public void obtainUserSubject() {
        UserModel userModel = new UserModelImpl();
        userModel.querySchoolTable(new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) throws IOException {
                String value = DataVerificationTool.isEmpty(response);
                if (value != null) {
                    RequestListJson<SchoolTable> requestList = new Gson().fromJson(value, new TypeToken< RequestListJson<SchoolTable>>() {
                    }.getType());
                    if (ResponseToast.toToast(requestList.getResultCode())) {
                        LitePal.deleteAll(SchoolTable.class);
                        for (SchoolTable s : requestList.getDataList()) {
                            s.save();
                        }
                    }
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }
}
