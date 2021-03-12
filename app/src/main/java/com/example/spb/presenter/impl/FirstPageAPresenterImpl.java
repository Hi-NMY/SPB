package com.example.spb.presenter.impl;

import android.content.Intent;
import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FirstPageAModelImpl;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.inter.IUserRegisteredPageAView;

public class FirstPageAPresenterImpl extends BasePresenter<IFirstPageAView> implements IFirstPageAPresenter {

    private SpbModelBasicInter mIFirstPageAModel;
    private String account;

    public FirstPageAPresenterImpl() {
        mIFirstPageAModel = new FirstPageAModelImpl();
    }

    public void setAccount(Intent data) {
        try {
            account = data.getStringExtra(IUserRegisteredPageAView.STRINGEXTRA);
            if (isAttachView()){
                getView().response(account,IFirstPageAView.RESPONSE_SUCCESS);
            }
        }catch (Exception e){

        }
    }
}
