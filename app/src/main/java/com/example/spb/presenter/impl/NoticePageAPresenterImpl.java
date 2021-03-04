package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.NoticePageAModelImpl;
import com.example.spb.model.inter.INoticePageAModel;
import com.example.spb.presenter.inter.INoticePageAPresenter;
import com.example.spb.view.inter.INoticePageAView;

public class NoticePageAPresenterImpl extends BasePresenter<INoticePageAView> implements INoticePageAPresenter {

    private INoticePageAModel mINoticePageAModel;

    public NoticePageAPresenterImpl() {
        mINoticePageAModel = new NoticePageAModelImpl();
    }
}
