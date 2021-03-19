package com.example.spb.view.fragment.topicbarpage;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.NewTopicBarFPresenterImpl;
import com.example.spb.presenter.inter.INewTopicBarFPresenter;
import com.example.spb.view.inter.INewTopicBarFView;

public class NewTopicBar extends BaseMVPFragment<INewTopicBarFView,NewTopicBarFPresenterImpl> implements INewTopicBarFView {

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
    protected NewTopicBarFPresenterImpl createPresenter() {
        return new NewTopicBarFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_topicbar_new_topic_bar;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
