package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.TopicPageFPresenterImpl;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.inter.ITopicPageFView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;

public class TopicPage extends BaseMVPFragment<ITopicPageFView, TopicPageFPresenterImpl> implements ITopicPageFView {

    private SmartRefreshLayout mTopicpageRefresh;
    private TextView mTopicpageRefreshTip;
    private TextView mTopicpageGuessNext;

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
        mTopicpageGuessNext = (TextView) view.findViewById(R.id.topicpage_guess_next);
        mTopicpageRefreshTip = (TextView) view.findViewById(R.id.topicpage_refresh_tip);
        mTopicpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.topicpage_refresh);
        mTopicpageGuessNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTopicpageRefresh.finishRefresh();
                RefreshTipAnima.tipAnimation(mTopicpageRefreshTip,12);
            }
        });
        initData();
        createDialog();
        setMyListener();
        createRefresh();
    }

    @Override
    protected void initData() {

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

    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void createRefresh() {
        mTopicpageRefresh.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void finishRefresh(int num) {

    }
}
