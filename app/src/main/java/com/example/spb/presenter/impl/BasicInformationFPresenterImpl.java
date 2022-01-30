package com.example.spb.presenter.impl;

import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.inter.IBasicInformationFPresenter;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.view.inter.IBasicInformationFView;

import java.util.ArrayList;
import java.util.List;

public class BasicInformationFPresenterImpl extends BasePresenter<IBasicInformationFView> implements IBasicInformationFPresenter {

    public List<String> strings;
    private List<Integer> keys;

    public List<Integer> getKeys() {
        return keys;
    }

    public void setMyPrivacy(String s){
        for (int i = 0 ; i < s.length() ; i++){
            keys.add(Integer.valueOf(s.substring(i,i + 1)));
        }
    }

    public BasicInformationFPresenterImpl() {
        strings = new ArrayList<>();
        keys = new ArrayList<>();
    }

    public List<String> setFavorite(String s){
        return strings = MyResolve.InFaTag(String.valueOf(s));
    }
}
