package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.PersonalPostBarFModelImpl;
import com.example.spb.model.inter.IPersonalPostBarFModel;
import com.example.spb.presenter.inter.IPersonalPostBarFPresenter;
import com.example.spb.view.inter.IPersonalPostBarFView;

public class PersonalPostBarFPresenterImpl extends BasePresenter<IPersonalPostBarFView> implements IPersonalPostBarFPresenter {

    private IPersonalPostBarFModel mIPersonalPostBarFModel;

    public PersonalPostBarFPresenterImpl() {
        mIPersonalPostBarFModel = new PersonalPostBarFModelImpl();
    }
}
