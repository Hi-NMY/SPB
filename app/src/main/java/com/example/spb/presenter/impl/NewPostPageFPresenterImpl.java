package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.INewPostPageFPresenter;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;

public class NewPostPageFPresenterImpl extends BasePresenter<INewPostPageFView> implements INewPostPageFPresenter {

    private final HomePage homePage;

    public NewPostPageFPresenterImpl(HomePage activity) {
        this.homePage = activity;
    }

    public void obtainNewBar(boolean loadFun) {
        homePage.getDataPostBarPresenter().obtainNewBar(loadFun);
    }
}
