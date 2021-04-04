package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.SendNewBarPageAModelImpl;
import com.example.spb.model.inter.ISendNewBarPageAModel;
import com.example.spb.presenter.inter.ISendNewBarPageAPresenter;
import com.example.spb.view.inter.ISendNewBarPageAView;

public class SendNewBarPageAPresenterImpl extends BasePresenter<ISendNewBarPageAView> implements ISendNewBarPageAPresenter {

    private ISendNewBarPageAModel mISendNewBarPageAModel;

    public SendNewBarPageAPresenterImpl() {
        mISendNewBarPageAModel = new SendNewBarPageAModelImpl();
    }
}
