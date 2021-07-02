package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.AttentionTopicAdapter;
import com.example.spb.adapter.CollectBarAdapter;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.IUserCollectPageAPresenter;
import com.example.spb.view.inter.IUserCollectPageAView;

import java.util.List;

public class UserCollectPageAPresenterImpl extends BasePresenter<IUserCollectPageAView> implements IUserCollectPageAPresenter {

    private CollectBarAdapter collectBarAdapter;
    private BaseMVPActivity baseMVPActivity;

    public UserCollectPageAPresenterImpl(Activity activity) {
        baseMVPActivity = (BaseMVPActivity)activity;
    }

    public void addCollectData(List<Bar> cb, RecyclerView recyclerView){
        collectBarAdapter = new CollectBarAdapter(baseMVPActivity,cb);
        recyclerView.setAdapter(collectBarAdapter);
    }

    public void removeCollect(String id){
        if (collectBarAdapter != null){
            collectBarAdapter.removeData(id);
        }
    }
}
