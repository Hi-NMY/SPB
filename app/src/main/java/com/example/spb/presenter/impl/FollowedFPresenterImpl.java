package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.FollowedFModelImpl;
import com.example.spb.model.inter.IFollowedFModel;
import com.example.spb.presenter.inter.IFollowedFPresenter;
import com.example.spb.view.inter.IFollowedFView;

public class FollowedFPresenterImpl extends BasePresenter<IFollowedFView> implements IFollowedFPresenter {

    private IFollowedFModel mIFollowedFModel;

    public FollowedFPresenterImpl() {
        mIFollowedFModel = new FollowedFModelImpl();
    }
}
