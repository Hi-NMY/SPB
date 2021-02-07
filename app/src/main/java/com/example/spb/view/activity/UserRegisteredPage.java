package com.example.spb.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserRegisteredPageAPresenterImpl;
import com.example.spb.presenter.inter.IUserRegisteredPageAPresenter;
import com.example.spb.view.inter.IUserRegisteredPageAView;

public class UserRegisteredPage extends BaseMVPActivity<IUserRegisteredPageAView,UserRegisteredPageAPresenterImpl> implements IUserRegisteredPageAView {

    private IUserRegisteredPageAPresenter mIUserRegisteredPageAPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registered_page);
    }

    @Override
    protected UserRegisteredPageAPresenterImpl createPresenter() {
        return new UserRegisteredPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {

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
