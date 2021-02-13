package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserHomePageAModelImpl;
import com.example.spb.model.inter.IUserHomePageAModel;
import com.example.spb.presenter.inter.IUserHomePageAPresenter;
import com.example.spb.view.inter.IUserHomePageAView;

public class UserHomePageAPresenterImpl extends BasePresenter<IUserHomePageAView> implements IUserHomePageAPresenter {

    private IUserHomePageAModel mIUserHomePageAModel;

    public UserHomePageAPresenterImpl() {
        mIUserHomePageAModel = new UserHomePageAModelImpl();
    }
}
