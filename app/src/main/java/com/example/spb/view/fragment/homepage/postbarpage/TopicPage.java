package com.example.spb.view.fragment.homepage.postbarpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.TopicPageFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.ITopicPageFView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class TopicPage extends BaseMVPFragment<ITopicPageFView, TopicPageFPresenterImpl> implements ITopicPageFView {

    private SmartRefreshLayout mTopicpageRefresh;
    private TextView mTopicpageGuessNext;
    private GifImageView mTopicpageRefreshGif;
    private MySmartRefresh mySmartRefresh;
    private HomePage homePage;
    private RecyclerView mTopicpageGuessList;
    private TextView mTopicpageTipOne;
    private RecyclerView mTopicpageUseratList;
    private RefreshUserTopic refreshUserTopic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshUserTopic = new RefreshUserTopic();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_topic),refreshUserTopic);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        homePage.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (responseFlag){
                    case USERATTTOPIC_TRUE:
                        mTopicpageTipOne.setVisibility(View.VISIBLE);
                        mTopicpageUseratList.setVisibility(View.GONE);
                        break;
                    case USERATTTOPIC_FALSE:
                        mTopicpageTipOne.setVisibility(View.GONE);
                        mTopicpageUseratList.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }

    @Override
    protected TopicPageFPresenterImpl createPresenter() {
        return new TopicPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_topic_page;
    }

    @Override
    protected void initFragView(View view) {
        homePage = (HomePage) getActivity();
        mTopicpageTipOne = (TextView)view.findViewById(R.id.topicpage_tip_one);
        mTopicpageUseratList = (RecyclerView)view.findViewById(R.id.topicpage_userat_list);
        mTopicpageGuessNext = (TextView) view.findViewById(R.id.topicpage_guess_next);
        mTopicpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.topicpage_refresh);
        mTopicpageRefreshGif = (GifImageView) view.findViewById(R.id.topicpage_refresh_gif);
        mTopicpageGuessList = (RecyclerView) view.findViewById(R.id.topicpage_guess_list);
        mySmartRefresh = new MySmartRefresh(mTopicpageRefresh, mTopicpageRefreshGif, null);
        setMyListener();
        createDialog();
        createRefresh();
    }

    @Override
    protected void initData() {
        mPresenter.setHotTopic(homePage, homePage.getDataAttentionTopicPresenter().topics, new GridLayoutManager(homePage, 2), mTopicpageGuessList);
        mPresenter.setUserAttTopic(homePage, homePage.getDataAttentionTopicPresenter().attentionTopicList
                , new GridLayoutManager(homePage, 2), mTopicpageUseratList, new TopicPageFPresenterImpl.I() {
                    @Override
                    public void a() {

                    }

                    @Override
                    public void onReturn(boolean a) {
                        if (a){
                            response(null,USERATTTOPIC_TRUE);
                        }else {
                            response(null,USERATTTOPIC_FALSE);
                        }
                    }
                });
        mTopicpageGuessList.setVisibility(View.VISIBLE);
    }

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {
        mTopicpageGuessNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySmartRefresh.finishMyRefresh();
                mPresenter.obtainHotTopic(homePage, null);
            }
        });
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void createRefresh() {
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.obtainHotTopic(homePage, new TopicPageFPresenterImpl.I() {
                    @Override
                    public void a() {
                        finishRRefresh(0);
                    }

                    @Override
                    public void onReturn(boolean a) {

                    }
                });
                mPresenter.obtainUserAttTopic(homePage, new TopicPageFPresenterImpl.I() {
                    @Override
                    public void a() {

                    }

                    @Override
                    public void onReturn(boolean a) {
                        if (a){
                            response(null,USERATTTOPIC_TRUE);
                        }else {
                            response(null,USERATTTOPIC_FALSE);
                        }
                    }
                });
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshUserTopic);
    }

    class RefreshUserTopic extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.refreshUserTopic();
        }
    }
}
