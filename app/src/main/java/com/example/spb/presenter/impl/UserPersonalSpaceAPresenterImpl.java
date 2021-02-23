package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserHomeSpaceAModelImpl;
import com.example.spb.model.inter.IUserHomeSpaceAModel;
import com.example.spb.presenter.inter.IUserPersonalSpaceAPresenter;
import com.example.spb.view.inter.IUserPersonalSpaceAView;

public class UserPersonalSpaceAPresenterImpl extends BasePresenter<IUserPersonalSpaceAView> implements IUserPersonalSpaceAPresenter {

    private IUserHomeSpaceAModel mIUserHomeSpaceAModel;

    public UserPersonalSpaceAPresenterImpl() {
        mIUserHomeSpaceAModel = new UserHomeSpaceAModelImpl();
    }
}
