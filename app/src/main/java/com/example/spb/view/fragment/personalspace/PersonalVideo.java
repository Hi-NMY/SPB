package com.example.spb.view.fragment.personalspace;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.PersonalPostBarFPresenterImpl;
import com.example.spb.presenter.impl.PersonalVideoFPresenterImpl;
import com.example.spb.presenter.inter.IPersonalVideoFPresenter;
import com.example.spb.view.inter.IPersonalPostBarFView;
import com.example.spb.view.inter.IPersonalVideoFView;

public class PersonalVideo extends BaseMVPFragment<IPersonalVideoFView, PersonalVideoFPresenterImpl> implements IPersonalVideoFView {

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
    protected PersonalVideoFPresenterImpl createPresenter() {
        return new PersonalVideoFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_personalspace_video;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
