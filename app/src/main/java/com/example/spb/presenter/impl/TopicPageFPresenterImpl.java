package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.TopicPageFModelImpl;
import com.example.spb.model.inter.ITopicPageFModel;
import com.example.spb.presenter.inter.ITopicPageFPresenter;
import com.example.spb.view.inter.ITopicPageFView;

public class TopicPageFPresenterImpl extends BasePresenter<ITopicPageFView> implements ITopicPageFPresenter {

    private ITopicPageFModel mITopicPageFModel;

    public TopicPageFPresenterImpl() {
        mITopicPageFModel = new TopicPageFModelImpl();
    }
}
