package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserRegisteredPageAModelImpl;
import com.example.spb.presenter.inter.IUserRegisteredPageAPresenter;
import com.example.spb.view.inter.IUserRegisteredPageAView;

public class UserRegisteredPageAPresenterImpl extends BasePresenter<IUserRegisteredPageAView> implements IUserRegisteredPageAPresenter {

    private SpbModelBasicInter mIUserRegisteredPageAModel;
    private IUserRegisteredPageAView mIUserRegisteredPageAView;

    public UserRegisteredPageAPresenterImpl(IUserRegisteredPageAView iUserRegisteredPageAView) {
        mIUserRegisteredPageAView = iUserRegisteredPageAView;
        mIUserRegisteredPageAModel = new UserRegisteredPageAModelImpl();
    }
}
