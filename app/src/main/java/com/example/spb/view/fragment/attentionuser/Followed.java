package com.example.spb.view.fragment.attentionuser;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.FollowFPresenterImpl;
import com.example.spb.presenter.impl.FollowedFPresenterImpl;
import com.example.spb.presenter.inter.IFollowedFPresenter;
import com.example.spb.view.inter.IFollowFView;
import com.example.spb.view.inter.IFollowedFView;

public class Followed extends BaseMVPFragment<IFollowFView, FollowFPresenterImpl> implements IFollowedFView {

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
    protected FollowFPresenterImpl createPresenter() {
        return new FollowFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
