package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.entity.User;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.SetUpUpdatePasswordPageAModelImpl;
import com.example.spb.model.impl.UserModelImpl;
import com.example.spb.model.inter.ISetUpUpdatePasswordPageAModel;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.inter.ISetUpUpdatePasswordPageAPresenter;
import com.example.spb.view.inter.ISetUpUpdatePasswordPageAView;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class SetUpUpdatePasswordPageAPresenterImpl extends BasePresenter<ISetUpUpdatePasswordPageAView> implements ISetUpUpdatePasswordPageAPresenter {

    private SpbModelBasicInter userModel;

    public SetUpUpdatePasswordPageAPresenterImpl() {
        userModel = new UserModelImpl();
    }

    public void updatePassword(String account,String password,String passwordOld,OnReturn onReturn){
        User user = new User();
        user.setUser_account(account);
        user.setUser_password(password);
        user.setUser_token(passwordOld);
        userModel.updateData(userModel.DATAUSER_UPDATE_SEVEN, user, new MyCallBack() {
            @Override
            public void onSuccess(@NotNull Response response) throws IOException {
                String a = response.body().string();
                if (Integer.valueOf(a) == 200){
                    onReturn.onReturn();
                }
            }

            @Override
            public void onError(int t) {

            }
        });
    }

    public interface OnReturn{
        void onReturn();
    }
}
