package com.example.spb.view.fragment.homepage.userpage;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.UserPageFPresenterImpl;
import com.example.spb.view.activity.PersonalSpace;
import com.example.spb.view.inter.IUserPageFView;
import com.example.spb.view.littlefun.JumpIntent;

public class UserPage extends BaseMVPFragment<IUserPageFView, UserPageFPresenterImpl> implements IUserPageFView, View.OnClickListener {

    private RelativeLayout mUserPageUserR;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected UserPageFPresenterImpl createPresenter() {
        return new UserPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_user_page;
    }

    @Override
    protected void initFragView(View view) {
        mUserPageUserR = (RelativeLayout)view.findViewById(R.id.user_page_userR);
        setMyListener();
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
        mUserPageUserR.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void startRefresh() {

    }

    @Override
    public void obtainMoreRefresh() {

    }

    @Override
    public void stopRefresh() {

    }

    @Override
    public void stopMoreRefresh() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_page_userR:
                JumpIntent.startMyIntent(PersonalSpace.class);
                break;
        }
    }
}
