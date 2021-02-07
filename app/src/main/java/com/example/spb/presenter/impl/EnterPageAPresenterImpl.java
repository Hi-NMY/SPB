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
    private IEnterPageAView mIEnterPageAView;

    public EnterPageAPresenterImpl(IEnterPageAView iEnterPageAView) {
        mIEnterPageAView = iEnterPageAView;
        enterPageAModel = new EnterPageAModelImpl();
        User user = new User();
        user.setName("name");
        enterPageAModel.addData(1,user, new MyCallBack() {
            @Override
            public void onSuccess(Object response) {

            }

            @Override
            public void onError(int t) {

            }
        });
    }

    @Override
    public boolean getFirstLogIn() {
        SharedPreferences sharedPreferences = MySharedPreferences.getShared("firstLogIn");
        return sharedPreferences.getBoolean("login",true);
    }
}
