package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.MessagePageFModelImpl;
import com.example.spb.presenter.inter.IMessagePageFPresenter;
import com.example.spb.view.inter.IMessagePageFView;

public class MessagePageFPresenterImpl extends BasePresenter<IMessagePageFView> implements IMessagePageFPresenter {
    private SpbModelBasicInter mIMessagePageFModel;

    public MessagePageFPresenterImpl() {
        mIMessagePageFModel = new MessagePageFModelImpl();
    }
}
