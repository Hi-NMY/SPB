package com.example.spb.view.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserForgotpasswordPageAPresenterImpl;
import com.example.spb.presenter.inter.IUserForgotpasswordPageAPresenter;
import com.example.spb.view.inter.IUserForgotpasswordPageAView;

public class UserForgotpasswordPage extends BaseMVPActivity<IUserForgotpasswordPageAView,UserForgotpasswordPageAPresenterImpl> implements IUserForgotpasswordPageAView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_forgotpassword_page);
    }

    @Override
    protected UserForgotpasswordPageAPresenterImpl createPresenter() {
        return new UserForgotpasswordPageAPresenterImpl(this);
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

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {

    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }
}
