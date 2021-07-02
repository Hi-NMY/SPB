package com.example.spb.presenter.impl;

import android.content.SharedPreferences;
import com.example.spb.R;
import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.inter.IUserPageFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.view.inter.IUserPageFView;

public class UserPageFPresenterImpl extends BasePresenter<IUserPageFView> implements IUserPageFPresenter {

    private SpbModelBasicInter barModel;

    public UserPageFPresenterImpl() {
        barModel = new BarModelImpl();
    }

    public void obtainBarNum(OnReturn onReturn){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
        int a = sharedPreferences.getInt(InValues.send(R.string.userBar_num),0);
        onReturn.onReturn(a);
    }

    public interface OnReturn{
        void onReturn(int num);
    }
}
