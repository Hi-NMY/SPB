package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.SpbAvtivityBarFModelImpl;
import com.example.spb.presenter.inter.ISpbAvtivityBarFPresenter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;

public class SpbAvtivityBarFPresenterImpl extends BasePresenter<ISpbAvtivityBarFView> implements ISpbAvtivityBarFPresenter {

    private SpbModelBasicInter mISpbAvtivityBarFModel;

    public SpbAvtivityBarFPresenterImpl() {
        mISpbAvtivityBarFModel = new SpbAvtivityBarFModelImpl();
    }
}
