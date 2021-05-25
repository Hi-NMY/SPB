package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.IBasicInformationFPresenter;
import com.example.spb.presenter.littlefun.MyResolve;
import com.example.spb.view.inter.IBasicInformationFView;

import java.util.ArrayList;
import java.util.List;

public class BasicInformationFPresenterImpl extends BasePresenter<IBasicInformationFView> implements IBasicInformationFPresenter {

    public List<String> strings;

    public BasicInformationFPresenterImpl() {
        strings = new ArrayList<>();
    }

    public List<String> setFavorite(String s){
        return strings = MyResolve.InFaTag(String.valueOf(s));
    }
}
