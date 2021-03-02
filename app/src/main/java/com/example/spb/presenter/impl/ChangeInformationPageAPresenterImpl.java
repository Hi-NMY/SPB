package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.ChangeInformationAModelImpl;
import com.example.spb.model.inter.IChangeInformationAModel;
import com.example.spb.presenter.inter.IChangeInformationAPresenter;
import com.example.spb.view.inter.IChangeInformationPageAView;

public class ChangeInformationPageAPresenterImpl extends BasePresenter<IChangeInformationPageAView> implements IChangeInformationAPresenter {

    private IChangeInformationAModel mIChangeInformationAModel;

    public ChangeInformationPageAPresenterImpl() {
        mIChangeInformationAModel = new ChangeInformationAModelImpl();
    }
}
