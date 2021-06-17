package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.TopicBarAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.inter.INewTopicBarFPresenter;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.INewTopicBarFView;

import java.util.List;

public class NewTopicBarFPresenterImpl extends BasePresenter<INewTopicBarFView> implements INewTopicBarFPresenter {

    private TopicBarPage topicBarPage;
    private TopicBarAdapter topicBarAdapter;

    public NewTopicBarFPresenterImpl(TopicBarPage t) {
        this.topicBarPage = t;
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (topicBarAdapter == null && fun){
            topicBarAdapter = new TopicBarAdapter(topicBarPage,b);
            recyclerView.setAdapter(topicBarAdapter);
            recyclerView.startLayoutAnimation();
        }else {
            //获取更多
        }
    }
}
