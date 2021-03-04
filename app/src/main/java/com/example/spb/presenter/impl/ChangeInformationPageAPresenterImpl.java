package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.ChangeInformationAModelImpl;
import com.example.spb.model.inter.IChangeInformationAModel;
import com.example.spb.presenter.inter.IChangeInformationPageAPresenter;
import com.example.spb.view.inter.IChangeInformationPageAView;

public class ChangeInformationPageAPresenterImpl extends BasePresenter<IChangeInformationPageAView> implements IChangeInformationPageAPresenter {

    private IChangeInformationAModel mIChangeInformationAModel;

    public ChangeInformationPageAPresenterImpl() {
        mIChangeInformationAModel = new ChangeInformationAModelImpl();
    }
}
