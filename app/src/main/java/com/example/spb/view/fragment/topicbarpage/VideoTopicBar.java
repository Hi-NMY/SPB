package com.example.spb.view.fragment.topicbarpage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.VideoTopicBarFPresenterImpl;
import com.example.spb.presenter.inter.IVideoTopicBarFPresenter;
import com.example.spb.view.inter.IVideoTopicBarFView;

public class VideoTopicBar extends BaseMVPFragment<IVideoTopicBarFView,VideoTopicBarFPresenterImpl> implements IVideoTopicBarFView {

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
    protected VideoTopicBarFPresenterImpl createPresenter() {
        return new VideoTopicBarFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_topicbar_video_topic_bar;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
