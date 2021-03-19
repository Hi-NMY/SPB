package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.TopicBarPageAModelImpl;
import com.example.spb.model.inter.ITopicBarPageAModel;
import com.example.spb.presenter.inter.ITopicBarPageAPresenter;
import com.example.spb.view.inter.ITopicBarPageAView;

public class TopicBarPageAPresenterImpl extends BasePresenter<ITopicBarPageAView> implements ITopicBarPageAPresenter {

    private ITopicBarPageAModel mITopicBarPageAModel;

    public TopicBarPageAPresenterImpl() {
        mITopicBarPageAModel = new TopicBarPageAModelImpl();
    }
}
