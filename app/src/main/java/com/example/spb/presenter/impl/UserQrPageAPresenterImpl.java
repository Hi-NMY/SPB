package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserQrPageAModelImpl;
import com.example.spb.model.inter.IUserQrPageAModel;
import com.example.spb.presenter.inter.IUserQrPageAPresenter;
import com.example.spb.view.inter.IUserQrPageAView;

public class UserQrPageAPresenterImpl extends BasePresenter<IUserQrPageAView> implements IUserQrPageAPresenter {
    private IUserQrPageAModel mIUserQrPageAModel;

    public UserQrPageAPresenterImpl() {
        mIUserQrPageAModel = new UserQrPageAModelImpl();
    }
}
