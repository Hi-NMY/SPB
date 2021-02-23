package com.example.spb.view.fragment.homepage.videopage;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.VideoPageFPresenterImpl;
import com.example.spb.view.inter.IVideoPageFView;

public class VideoPage extends BaseMVPFragment<IVideoPageFView,VideoPageFPresenterImpl> implements IVideoPageFView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    protected VideoPageFPresenterImpl createPresenter() {
        return new VideoPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_video_page;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }
}
