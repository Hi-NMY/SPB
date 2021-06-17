package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.TopicBarAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.IHotTopicBarFPresenter;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IHotTopicBarFView;

import java.util.List;

public class HotTopicBarFPresenterImpl extends BasePresenter<IHotTopicBarFView> implements IHotTopicBarFPresenter {

    private TopicBarPage topicBarPage;
    private TopicBarAdapter topicBarAdapter;

    public HotTopicBarFPresenterImpl(TopicBarPage t) {
        this.topicBarPage = t;
    }

    public void addHotTopicList(List<Bar> b, RecyclerView recyclerView,boolean fun){
        if (topicBarAdapter == null && fun){
            topicBarAdapter = new TopicBarAdapter(topicBarPage,b);
            recyclerView.setAdapter(topicBarAdapter);
            recyclerView.startLayoutAnimation();
        }else {
            //获取更多
        }
    }
}
