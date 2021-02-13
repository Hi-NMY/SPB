package com.example.spb.view.fragment.ui.messagepage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.MessagePageFPresenterImpl;
import com.example.spb.presenter.inter.IMessagePageFPresenter;
import com.example.spb.view.inter.IMessagePageFView;

public class MessagePage extends BaseMVPFragment<IMessagePageFView,MessagePageFPresenterImpl> implements IMessagePageFView {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MessagePageFPresenterImpl createPresenter() {
        return new MessagePageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_message_page;
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
