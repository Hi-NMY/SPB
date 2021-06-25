package com.example.spb.presenter.impl;

import androidx.recyclerview.widget.DefaultItemAnimator;
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
    private String tName;

    public void settName(String tName) {
        this.tName = tName;
    }

    public String gettName() {
        return tName;
    }

    public NewTopicBarFPresenterImpl(TopicBarPage t) {
        this.topicBarPage = t;
    }

    public void addNewTopicList(List<Bar> b, RecyclerView recyclerView, boolean fun){
        if (topicBarAdapter == null || fun){
            topicBarAdapter = new TopicBarAdapter(topicBarPage,b);
            topicBarAdapter.setNowTopicId(gettName());
            recyclerView.setAdapter(topicBarAdapter);
            ((DefaultItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
            recyclerView.startLayoutAnimation();
        }else {
            //获取更多
        }
    }

    public void refreshThumb(int num,String pbId){
        if (topicBarAdapter != null){
            topicBarAdapter.refreshLikeItem(num,pbId);
        }
    }
}
