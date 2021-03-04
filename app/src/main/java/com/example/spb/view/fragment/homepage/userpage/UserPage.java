package com.example.spb.view.fragment.homepage.userpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.Nullable;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.UserPageFPresenterImpl;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.activity.AttentionTopicPage;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.UserCollectPage;
import com.example.spb.view.inter.IUserPageFView;
import com.example.spb.view.littlefun.JumpIntent;

public class UserPage extends BaseMVPFragment<IUserPageFView, UserPageFPresenterImpl> implements IUserPageFView, View.OnClickListener {

    private RelativeLayout mUserPageUserR;
    private ImageView mUserPageUseronlinetip;
    private ComponentDialog tipDialog;
    private RelativeLayout mUserPageCollectnumR;
    private RelativeLayout mUserPageAttentionnumR;

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
        mUserPageUserR = (RelativeLayout) view.findViewById(R.id.user_page_userR);
        mUserPageUseronlinetip = (ImageView) view.findViewById(R.id.user_page_useronlinetip);
        mUserPageCollectnumR = (RelativeLayout)view.findViewById(R.id.user_page_collectnum_r);
        mUserPageAttentionnumR = (RelativeLayout)view.findViewById(R.id.user_page_attentionnum_r);
        createDialog();
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

    private ImageView tipImage;

    @Override
    public void createDialog() {
        tipDialog = new ComponentDialog(getActivity(), R.layout.dialog_day_tip, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                tipImage = (ImageView) view.findViewById(R.id.img);
            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {
                tipImage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(0);
                    }
                });
            }
        });
    }

    @Override
    public void showDialogS(int i) {
        tipDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        tipDialog.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        mUserPageUserR.setOnClickListener(this);
        mUserPageUseronlinetip.setOnClickListener(this);
        mUserPageAttentionnumR.setOnClickListener(this);
        mUserPageCollectnumR.setOnClickListener(this);
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
        switch (v.getId()) {
            case R.id.user_page_userR:
                JumpIntent.startMyIntent(PersonalSpacePage.class);
                break;
            case R.id.user_page_useronlinetip:
                showDialogS(0);
                break;
            case R.id.user_page_attentionnum_r:
                JumpIntent.startMyIntent(AttentionTopicPage.class);
                break;
            case R.id.user_page_collectnum_r:
                JumpIntent.startMyIntent(UserCollectPage.class);
                break;
        }
    }
}
