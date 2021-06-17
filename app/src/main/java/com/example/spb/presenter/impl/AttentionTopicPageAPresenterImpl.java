package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.AttentionTopicAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.inter.IAttentionTopicPageAPresenter;
import com.example.spb.view.inter.IAttentionTopicPageAView;

import java.util.List;

public class AttentionTopicPageAPresenterImpl extends BasePresenter<IAttentionTopicPageAView> implements IAttentionTopicPageAPresenter {

    private AttentionTopicAdapter attentionTopicAdapter;
    private Activity activity;

    public AttentionTopicPageAPresenterImpl(Activity a) {
        this.activity = a;
    }

    public void addAttentionTopic(List<Topic> t, RecyclerView recyclerView){
        if (attentionTopicAdapter == null){
            attentionTopicAdapter = new AttentionTopicAdapter(activity,t);
            recyclerView.setAdapter(attentionTopicAdapter);
        }
    }
}
