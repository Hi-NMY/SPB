package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserHomeSpaceAModelImpl;
import com.example.spb.model.inter.IUserHomeSpaceAModel;
import com.example.spb.presenter.inter.IPersonalSpacePageAPresenter;
import com.example.spb.view.inter.IPersonalSpacePageAView;

public class PersonalSpacePagePageAPresenterImpl extends BasePresenter<IPersonalSpacePageAView> implements IPersonalSpacePageAPresenter {

    private IUserHomeSpaceAModel mIUserHomeSpaceAModel;

    public PersonalSpacePagePageAPresenterImpl() {
        mIUserHomeSpaceAModel = new UserHomeSpaceAModelImpl();
    }
}
