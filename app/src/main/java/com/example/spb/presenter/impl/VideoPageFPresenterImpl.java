package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.VideoPageFModelImpl;
import com.example.spb.presenter.inter.IVideoPageFPresenter;
import com.example.spb.view.inter.IVideoPageFView;

public class VideoPageFPresenterImpl extends BasePresenter<IVideoPageFView> implements IVideoPageFPresenter {

    private SpbModelBasicInter mIVideoPageFModel;

    public VideoPageFPresenterImpl() {
        mIVideoPageFModel = new VideoPageFModelImpl();
    }
}
