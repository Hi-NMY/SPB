package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.AttentionTopicPageAModelImpl;
import com.example.spb.model.inter.IAttentionTopicPageAModel;
import com.example.spb.presenter.inter.IAttentionTopicPageAPresenter;
import com.example.spb.view.inter.IAttentionTopicPageAView;

public class AttentionTopicPageAPresenterImpl extends BasePresenter<IAttentionTopicPageAView> implements IAttentionTopicPageAPresenter {

    private IAttentionTopicPageAModel mIAttentionTopicPageAModel;

    public AttentionTopicPageAPresenterImpl() {
        mIAttentionTopicPageAModel = new AttentionTopicPageAModelImpl();
    }
}
