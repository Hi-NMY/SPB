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
    private RandomTopicAdapter userAttTopicAdapter;
    private RecyclerView userTopicRec;
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

    public void setUserAttTopic(Activity activity, List<Topic> userAttTopics, GridLayoutManager gridLayoutManager, RecyclerView recyclerView,I i){
        this.activity = activity;
        this.userTopicRec = recyclerView;
        userTopicRec.setLayoutManager(gridLayoutManager);
        if (userAttTopics != null && userAttTopics.size() != 0){
            userAttTopicAdapter = new RandomTopicAdapter(activity, userAttTopics);
            userTopicRec.setAdapter(userAttTopicAdapter);
            i.onReturn(false);
        }else {
            i.onReturn(true);
        }
    }

    public void obtainUserAttTopic(HomePage homePage,I i){
        homePage.getDataAttentionTopicPresenter().initDate(new DataAttentionTopicPresenter.ReturnTopic() {
            @Override
            public void onReturn() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (homePage.getDataAttentionTopicPresenter().attentionTopicList != null
                                && homePage.getDataAttentionTopicPresenter().attentionTopicList.size() != 0){
                            if (userAttTopicAdapter == null){
                                userAttTopicAdapter = new RandomTopicAdapter(activity, homePage.getDataAttentionTopicPresenter().attentionTopicList);
                                userTopicRec.setAdapter(userAttTopicAdapter);
                            }else {
                                userAttTopicAdapter.refreshTopic(homePage.getDataAttentionTopicPresenter().attentionTopicList);
                            }
                            i.onReturn(false);
                        }else {
                            i.onReturn(true);
                        }
                    }
                });
            }
        });
    }

    public void refreshUserTopic(){
        if (userAttTopicAdapter != null){
            userAttTopicAdapter.notifyData();
        }
    }

    public interface I{
        void A();
        void onReturn(boolean a);
    }
}
