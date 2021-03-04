package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.model.impl.UserDiaryPageAModelImpl;
import com.example.spb.model.inter.IUserDiaryPageAModel;
import com.example.spb.presenter.inter.IUserDiaryPageAPresenter;
import com.example.spb.view.inter.IUserDiaryPageAView;

public class UserDiaryPageAPresenterImpl extends BasePresenter<IUserDiaryPageAView> implements IUserDiaryPageAPresenter {

    private IUserDiaryPageAModel mIUserDiaryPageAModel;

    public UserDiaryPageAPresenterImpl() {
        mIUserDiaryPageAModel = new UserDiaryPageAModelImpl();
    }
}
