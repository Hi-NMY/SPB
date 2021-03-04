package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.SecondHandStorePageAModelImpl;
import com.example.spb.model.inter.ISecondHandStorePageAModel;
import com.example.spb.presenter.inter.ISecondHandStorePageAPresenter;
import com.example.spb.view.inter.ISecondHandStorePageAView;

public class SecondHandStorePageAPresenterImpl extends BasePresenter<ISecondHandStorePageAView> implements ISecondHandStorePageAPresenter {

    private ISecondHandStorePageAModel mISecondHandStorePageAModel;

    public SecondHandStorePageAPresenterImpl() {
        mISecondHandStorePageAModel = new SecondHandStorePageAModelImpl();
    }
}
