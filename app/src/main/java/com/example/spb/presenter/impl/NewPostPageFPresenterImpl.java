package com.example.spb.presenter.impl;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.INewPostPageFPresenter;
import com.example.spb.presenter.otherimpl.DataPostBarPresenter;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;

import java.util.List;

public class NewPostPageFPresenterImpl extends BasePresenter<INewPostPageFView> implements INewPostPageFPresenter {

    private HomePage homePage;

    public NewPostPageFPresenterImpl(HomePage activity) {
        this.homePage = activity;
    }

    public void obtainNewBar(boolean loadFun) {
        homePage.getDataPostBarPresenter().obtainNewBar(loadFun);
    }
}
