package com.example.spb.view.fragment.ui.postbarpage;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.presenter.inter.INewPostPageFPresenter;
import com.example.spb.view.inter.INewPostPageFView;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView,NewPostPageFPresenterImpl> implements INewPostPageFView {

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
    protected NewPostPageFPresenterImpl createPresenter() {
        return new NewPostPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_new_post_page;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
