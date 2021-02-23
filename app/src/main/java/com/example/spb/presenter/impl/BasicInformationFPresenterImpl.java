package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.BasicInformationFModelImpl;
import com.example.spb.model.inter.IBasicInformationFModel;
import com.example.spb.presenter.inter.IBasicInformationFPresenter;
import com.example.spb.view.inter.IBasicInformationFView;

public class BasicInformationFPresenterImpl extends BasePresenter<IBasicInformationFView> implements IBasicInformationFPresenter {

    private IBasicInformationFModel mIBasicInformationFModel;

    public BasicInformationFPresenterImpl() {
        mIBasicInformationFModel = new BasicInformationFModelImpl();
    }
}
