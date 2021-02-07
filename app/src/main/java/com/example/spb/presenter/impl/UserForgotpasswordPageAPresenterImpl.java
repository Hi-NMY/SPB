package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserForgotpasswordPageAModelImpl;
import com.example.spb.model.inter.IUserForgotpasswordPageAModel;
import com.example.spb.presenter.inter.IUserForgotpasswordPageAPresenter;
import com.example.spb.view.inter.IUserForgotpasswordPageAView;

public class UserForgotpasswordPageAPresenterImpl extends BasePresenter<IUserForgotpasswordPageAView> implements IUserForgotpasswordPageAPresenter {

    private IUserForgotpasswordPageAView mIUserForgotpasswordPageAView;
    private IUserForgotpasswordPageAModel mIUserForgotpasswordPageAModel;

    public UserForgotpasswordPageAPresenterImpl(IUserForgotpasswordPageAView aIUserForgotpasswordPageAView) {
        mIUserForgotpasswordPageAView = aIUserForgotpasswordPageAView;
        mIUserForgotpasswordPageAModel = new UserForgotpasswordPageAModelImpl();
    }

}
