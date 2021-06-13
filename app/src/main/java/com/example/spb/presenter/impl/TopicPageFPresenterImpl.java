package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.NewBarImageAdapter;
import com.example.spb.adapter.RandomTopicAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.inter.ITopicPageFPresenter;
import com.example.spb.presenter.otherimpl.DataAttentionTopicPresenter;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.fragment.homepage.postbarpage.TopicPage;
import com.example.spb.view.inter.ITopicPageFView;

import java.util.List;

public class TopicPageFPresenterImpl extends BasePresenter<ITopicPageFView> implements ITopicPageFPresenter {

    private RandomTopicAdapter randomTopicAdapter;
    private Activity activity;

    public TopicPageFPresenterImpl() {

    }

    public void setHotTopic(Activity activity, List<Topic> topics, GridLayoutManager gridLayoutManager, RecyclerView recyclerView){
        this.activity = activity;
        randomTopicAdapter = new RandomTopicAdapter(activity, topics);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(randomTopicAdapter);
    }

    public void obtainHotTopic(HomePage homePage,I i){
        homePage.getDataAttentionTopicPresenter().obtainRandomTopic(new DataAttentionTopicPresenter.HotTopic() {
            @Override
            public void onReturn() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        randomTopicAdapter.refreshTopic(homePage.getDataAttentionTopicPresenter().topics);
                        if (i!= null){
                            i.A();
                        }
                    }
                });
            }
        });
    }

    public interface I{
        void A();
    }
}
