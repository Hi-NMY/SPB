package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.HotTopicBarFModelImpl;
import com.example.spb.model.inter.IHotTopicBarFModel;
import com.example.spb.presenter.inter.IHotTopicBarFPresenter;
import com.example.spb.view.inter.IHotTopicBarFView;

public class HotTopicBarFPresenterImpl extends BasePresenter<IHotTopicBarFView> implements IHotTopicBarFPresenter {
    private IHotTopicBarFModel mIHotTopicBarFModel;

    public HotTopicBarFPresenterImpl() {
        mIHotTopicBarFModel = new HotTopicBarFModelImpl();
    }
}
