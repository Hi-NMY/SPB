package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.NewPostPageFModelImpl;
import com.example.spb.model.inter.INewPostPageFModel;
import com.example.spb.presenter.inter.INewPostPageFPresenter;
import com.example.spb.view.inter.INewPostPageFView;

public class NewPostPageFPresenterImpl extends BasePresenter<INewPostPageFView> implements INewPostPageFPresenter {

    private INewPostPageFModel mINewPostPageFModel;

    public NewPostPageFPresenterImpl() {
        mINewPostPageFModel = new NewPostPageFModelImpl();
    }
}
