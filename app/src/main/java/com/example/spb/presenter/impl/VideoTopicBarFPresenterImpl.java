package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.VideoTopicBarFModelImpl;
import com.example.spb.model.inter.IVideoTopicBarFModel;
import com.example.spb.presenter.inter.IVideoTopicBarFPresenter;
import com.example.spb.view.inter.IVideoTopicBarFView;

public class VideoTopicBarFPresenterImpl extends BasePresenter<IVideoTopicBarFView> implements IVideoTopicBarFPresenter {

    private IVideoTopicBarFModel mIVideoTopicBarFModel;

    public VideoTopicBarFPresenterImpl() {
        mIVideoTopicBarFModel = new VideoTopicBarFModelImpl();
    }
}
