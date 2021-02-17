package com.example.spb.view.fragment.ui.postbarpage;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.AttentionPageFPresenterImpl;
import com.example.spb.presenter.inter.IAttentionPageFPresenter;
import com.example.spb.view.inter.IAttentionPageFView;

public class AttentionPage extends BaseMVPFragment<IAttentionPageFView,AttentionPageFPresenterImpl> implements IAttentionPageFView {

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
    protected AttentionPageFPresenterImpl createPresenter() {
        return new AttentionPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_attention_page;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
