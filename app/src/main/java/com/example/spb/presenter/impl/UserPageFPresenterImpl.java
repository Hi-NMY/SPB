package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.UserPageFModelImpl;
import com.example.spb.presenter.inter.IUserPageFPresenter;
import com.example.spb.view.inter.IUserPageFView;

public class UserPageFPresenterImpl extends BasePresenter<IUserPageFView> implements IUserPageFPresenter {

    private SpbModelBasicInter mIUserPageFModel;

    public UserPageFPresenterImpl() {
        mIUserPageFModel = new UserPageFModelImpl();
    }
}
