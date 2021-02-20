package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.PostBarDetailPageAModelImpl;
import com.example.spb.model.inter.IPostBarDetailPageAModel;
import com.example.spb.presenter.inter.IPostBarDetailPageAPresenter;
import com.example.spb.view.inter.IPostBarDetailPageAView;

public class PostBarDetailPageAPresenterImpl extends BasePresenter<IPostBarDetailPageAView> implements IPostBarDetailPageAPresenter {

    private IPostBarDetailPageAModel mIPostBarDetailPageAModel;

    public PostBarDetailPageAPresenterImpl() {
        mIPostBarDetailPageAModel = new PostBarDetailPageAModelImpl();
    }
}
