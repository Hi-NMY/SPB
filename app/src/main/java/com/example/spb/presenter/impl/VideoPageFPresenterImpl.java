package com.example.spb.presenter.impl;

import android.app.Activity;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.IVideoPageFPresenter;
import com.example.spb.view.inter.IVideoPageFView;

public class VideoPageFPresenterImpl extends BasePresenter<IVideoPageFView> implements IVideoPageFPresenter {

    private BaseMVPActivity baseMVPActivity;

    public VideoPageFPresenterImpl(Activity activity) {
        baseMVPActivity = (BaseMVPActivity)activity;
    }
}
