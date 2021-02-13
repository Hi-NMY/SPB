package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserPageFModelImpl;
import com.example.spb.model.inter.IUserPageFModel;
import com.example.spb.presenter.inter.IUserPageFPresenter;
import com.example.spb.view.inter.IUserPageFView;

public class UserPageFPresenterImpl extends BasePresenter<IUserPageFView> implements IUserPageFPresenter {

    private IUserPageFModel mIUserPageFModel;

    public UserPageFPresenterImpl() {
        mIUserPageFModel = new UserPageFModelImpl();
    }
}
