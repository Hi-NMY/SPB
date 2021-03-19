package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.NewTopicBarFModelImpl;
import com.example.spb.model.inter.INewTopicBarFModel;
import com.example.spb.presenter.inter.INewTopicBarFPresenter;
import com.example.spb.view.inter.INewTopicBarFView;

public class NewTopicBarFPresenterImpl extends BasePresenter<INewTopicBarFView> implements INewTopicBarFPresenter {

    private INewTopicBarFModel mINewTopicBarFModel;

    public NewTopicBarFPresenterImpl() {
        mINewTopicBarFModel = new NewTopicBarFModelImpl();
    }
}
