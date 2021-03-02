package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.SetUpPageAModelImpl;
import com.example.spb.model.inter.ISetUpPageAModel;
import com.example.spb.presenter.inter.ISetUpPageAPresenter;
import com.example.spb.view.inter.ISetUpPageAView;

public class SetUpPageAPresenterImpl extends BasePresenter<ISetUpPageAView> implements ISetUpPageAPresenter {
    private ISetUpPageAModel mISetUpPageAModel;

    public SetUpPageAPresenterImpl() {
        mISetUpPageAModel = new SetUpPageAModelImpl();
    }
}
