package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.AttentionUserPageAModelImpl;
import com.example.spb.model.inter.IAttentionUserPageAModel;
import com.example.spb.presenter.inter.IAttentionUserPageAPresenter;
import com.example.spb.view.inter.IAttentionUserPageAView;

public class AttentionUserPageAPresenterImpl extends BasePresenter<IAttentionUserPageAView> implements IAttentionUserPageAPresenter {

    private IAttentionUserPageAModel mIAttentionUserPageAModel;

    public AttentionUserPageAPresenterImpl() {
        mIAttentionUserPageAModel = new AttentionUserPageAModelImpl();
    }
}
