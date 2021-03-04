package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserCoursePageAModelImpl;
import com.example.spb.model.inter.IUserCoursePageAModel;
import com.example.spb.presenter.inter.IUserCoursePageAPresenter;
import com.example.spb.view.inter.IUserCoursePageAView;

public class UserCoursePageAPresenterImpl extends BasePresenter<IUserCoursePageAView> implements IUserCoursePageAPresenter {

    private IUserCoursePageAModel mIUserCoursePageAModel;

    public UserCoursePageAPresenterImpl() {
        mIUserCoursePageAModel = new UserCoursePageAModelImpl();
    }
}
