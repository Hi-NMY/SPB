package com.example.spb.view.fragment.personalspace;

import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.PersonalPostBarFPresenterImpl;
import com.example.spb.presenter.inter.IPersonalPostBarFPresenter;
import com.example.spb.view.inter.IPersonalPostBarFView;

public class PersonalPostBar extends BaseMVPFragment<IPersonalPostBarFView,PersonalPostBarFPresenterImpl> implements IPersonalPostBarFView {

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
    protected PersonalPostBarFPresenterImpl createPresenter() {
        return new PersonalPostBarFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_personalspace_personal_post_bar;
    }

    @Override
    protected void initFragView(View view) {

    }

    @Override
    protected void initData() {

    }
}
