package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.FollowFModelImpl;
import com.example.spb.model.inter.IFollowFModel;
import com.example.spb.presenter.inter.IFollowFPresenter;
import com.example.spb.view.inter.IFollowFView;

public class FollowFPresenterImpl extends BasePresenter<IFollowFView> implements IFollowFPresenter {

    private IFollowFModel mIFollowFModel;

    public FollowFPresenterImpl() {
        mIFollowFModel = new FollowFModelImpl();
    }
}
