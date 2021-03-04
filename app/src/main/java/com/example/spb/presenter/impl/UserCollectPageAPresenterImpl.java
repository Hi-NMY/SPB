package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserCollectPageAModelImpl;
import com.example.spb.model.inter.IUserCollectPageAModel;
import com.example.spb.presenter.inter.IUserCollectPageAPresenter;
import com.example.spb.view.inter.IUserCollectPageAView;

public class UserCollectPageAPresenterImpl extends BasePresenter<IUserCollectPageAView> implements IUserCollectPageAPresenter {

    private IUserCollectPageAModel mIUserCollectPageAModel;

    public UserCollectPageAPresenterImpl() {
        mIUserCollectPageAModel = new UserCollectPageAModelImpl();
    }
}
