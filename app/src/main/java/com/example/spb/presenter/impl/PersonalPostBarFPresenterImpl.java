package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.PersonalSpaceBarAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.IPersonalPostBarFPresenter;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IPersonalPostBarFView;

import java.util.List;

public class PersonalPostBarFPresenterImpl extends BasePresenter<IPersonalPostBarFView> implements IPersonalPostBarFPresenter {

    private PersonalSpacePage personalSpacePage;
    private PersonalSpaceBarAdapter personalSpaceBarAdapter;

    public PersonalPostBarFPresenterImpl(PersonalSpacePage p) {
        this.personalSpacePage = p;
    }

    public void addPersonalBarList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (personalSpaceBarAdapter == null || fun){
            personalSpaceBarAdapter = new PersonalSpaceBarAdapter(personalSpacePage,b);
            recyclerView.setAdapter(personalSpaceBarAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {

        }
    }

    public void refreshThumb(int num,String pbId){
        if (personalSpaceBarAdapter != null){
            personalSpaceBarAdapter.refreshLikeItem(num,pbId);
        }
    }
}
