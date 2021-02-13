package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.MessagePageFModelImpl;
import com.example.spb.model.inter.IMessagePageFModel;
import com.example.spb.presenter.inter.IMessagePageFPresenter;
import com.example.spb.view.inter.IMessagePageFView;

public class MessagePageFPresenterImpl extends BasePresenter<IMessagePageFView> implements IMessagePageFPresenter {
    private IMessagePageFModel mIMessagePageFModel;

    public MessagePageFPresenterImpl() {
        mIMessagePageFModel = new MessagePageFModelImpl();
    }
}
