package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.TopicPageFPresenterImpl;
import com.example.spb.view.inter.ITopicPageFView;

public class TopicPage extends BaseMVPFragment<ITopicPageFView,TopicPageFPresenterImpl> implements ITopicPageFView {

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

    }

    @Override
    protected void initData() {

    }
}
