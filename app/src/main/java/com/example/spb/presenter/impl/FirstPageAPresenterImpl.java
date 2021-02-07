package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.FirstPageAModelImpl;
import com.example.spb.presenter.inter.IFirstPageAPresenter;
import com.example.spb.view.inter.IFirstPageAView;

public class FirstPageAPresenterImpl extends BasePresenter<IFirstPageAView> implements IFirstPageAPresenter {

    private SpbModelBasicInter mIFirstPageAModel;
    private IFirstPageAView mIFirstPageAView;

    public FirstPageAPresenterImpl(IFirstPageAView iFirstPageAView) {
        mIFirstPageAView = iFirstPageAView;
        mIFirstPageAModel = new FirstPageAModelImpl();
    }
}
