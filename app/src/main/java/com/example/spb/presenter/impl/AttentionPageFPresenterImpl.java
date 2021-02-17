package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.AttentionPageFModelImpl;
import com.example.spb.model.inter.IAttentionPageFModel;
import com.example.spb.presenter.inter.IAttentionPageFPresenter;
import com.example.spb.view.inter.IAttentionPageFView;

public class AttentionPageFPresenterImpl extends BasePresenter<IAttentionPageFView> implements IAttentionPageFPresenter {

    private IAttentionPageFModel mIAttentionPageFModel;

    public AttentionPageFPresenterImpl() {
        mIAttentionPageFModel = new AttentionPageFModelImpl();
    }
}
