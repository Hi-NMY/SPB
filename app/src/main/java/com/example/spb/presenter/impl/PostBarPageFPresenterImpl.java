package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.PostBarPageFModelImpl;
import com.example.spb.presenter.inter.IPostBarPageFPresenter;
import com.example.spb.view.inter.IPostBarPageFView;

public class PostBarPageFPresenterImpl extends BasePresenter<IPostBarPageFView> implements IPostBarPageFPresenter {
    private SpbModelBasicInter mIPostBarPageFModel;

    public PostBarPageFPresenterImpl() {
        mIPostBarPageFModel = new PostBarPageFModelImpl();
    }
}
