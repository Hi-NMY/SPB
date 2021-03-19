package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.PersonalVideoFModelImpl;
import com.example.spb.model.inter.IPersonalVideoFModel;
import com.example.spb.presenter.inter.IPersonalVideoFPresenter;
import com.example.spb.view.inter.IPersonalVideoFView;

public class PersonalVideoFPresenterImpl extends BasePresenter<IPersonalVideoFView> implements IPersonalVideoFPresenter {

    private IPersonalVideoFModel mIPersonalVideoFModel;

    public PersonalVideoFPresenterImpl() {
        mIPersonalVideoFModel = new PersonalVideoFModelImpl();
    }
}
