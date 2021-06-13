package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.TopicPageFPresenterImpl;
import com.example.spb.presenter.otherimpl.DataAttentionTopicPresenter;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.ITopicPageFView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class TopicPage extends BaseMVPFragment<ITopicPageFView, TopicPageFPresenterImpl> implements ITopicPageFView {

    public SmartRefreshLayout mTopicpageRefresh;
    private TextView mTopicpageGuessNext;
    private GifImageView mTopicpageRefreshGif;
    private MySmartRefresh mySmartRefresh;
    private HomePage homePage;
    private RecyclerView mTopicpageGuessList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

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
        mTopicpageGuessNext = (TextView) view.findViewById(R.id.topicpage_guess_next);
        mTopicpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.topicpage_refresh);
        mTopicpageRefreshGif = (GifImageView) view.findViewById(R.id.topicpage_refresh_gif);
        mTopicpageGuessList = (RecyclerView) view.findViewById(R.id.topicpage_guess_list);
        mySmartRefresh = new MySmartRefresh(mTopicpageRefresh, mTopicpageRefreshGif, null);
        setMyListener();
        createDialog();
        createRefresh();
        initData();
    }

    @Override
    protected void initData() {
        mPresenter.setHotTopic(homePage,homePage.getDataAttentionTopicPresenter().topics,new GridLayoutManager(homePage,2),mTopicpageGuessList);
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
                mPresenter.obtainHotTopic(homePage,null);
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
                    public void A() {
                        finishRRefresh(0);
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
}
