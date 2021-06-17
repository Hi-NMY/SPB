package com.example.spb.view.fragment.homepage.userpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.UserPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.activity.*;
import com.example.spb.view.inter.IUserPageFView;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

public class UserPage extends BaseMVPFragment<IUserPageFView, UserPageFPresenterImpl> implements IUserPageFView, View.OnClickListener {

    private RelativeLayout mUserPageUserR;
    private ImageView mUserPageUseronlinetip;
    private ComponentDialog tipDialog;
    private RelativeLayout mUserPageCollectnumR;
    private RelativeLayout mUserPageAttentionnumR;
    private RelativeLayout mR1;
    private RelativeLayout mR2;
    private RelativeLayout mR3;
    private RelativeLayout mR4;
    private HomePage homePage;

    private ImageView tipImage;
    private RoundedImageView mUserPageUserHeadimg;
    private TextView mUserPageUsername;
    private ImageView mUserPageUsersex;
    private RefreshMsg refreshMsg;
    private RefreshAttTopicNum refreshAttTopicNum;
    private TextView mUserPageAttentionnum;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected UserPageFPresenterImpl createPresenter() {
        refreshMsg = new RefreshMsg();
        refreshAttTopicNum = new RefreshAttTopicNum();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_userMsg), refreshMsg);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_reUserPage_topicnum), refreshAttTopicNum);
        return new UserPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_user_page;
    }

    @Override
    protected void initFragView(View view) {
        homePage = (HomePage) getActivity();
        mUserPageUserR = (RelativeLayout) view.findViewById(R.id.user_page_userR);
        mUserPageUseronlinetip = (ImageView) view.findViewById(R.id.user_page_useronlinetip);
        mUserPageCollectnumR = (RelativeLayout) view.findViewById(R.id.user_page_collectnum_r);
        mUserPageAttentionnumR = (RelativeLayout) view.findViewById(R.id.user_page_attentionnum_r);
        mUserPageUserHeadimg = (RoundedImageView) view.findViewById(R.id.user_page_user_headimg);
        mUserPageUsername = (TextView) view.findViewById(R.id.user_page_username);
        mUserPageUsersex = (ImageView) view.findViewById(R.id.user_page_usersex);
        mUserPageAttentionnum = (TextView)view.findViewById(R.id.user_page_attentionnum);
        mR1 = (RelativeLayout) view.findViewById(R.id.r1);
        mR2 = (RelativeLayout) view.findViewById(R.id.r2);
        mR3 = (RelativeLayout) view.findViewById(R.id.r3);
        mR4 = (RelativeLayout) view.findViewById(R.id.r4);
        createDialog();
        setMyListener();
    }

    @Override
    protected void initData() {
        mUserPageUsername.setText(homePage.getDataUserMsgPresenter().getUser_name());
        mUserPageAttentionnum.setText(String.valueOf(homePage.getDataAttentionTopicPresenter().attentionNum.size()));
        if (homePage.getDataUserMsgPresenter().getStu_sex().equals("男")) {
            mUserPageUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            mUserPageUsersex.setImageResource(R.drawable.icon_girl);
        }
        Glide.with(this)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + homePage.getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                .centerCrop()
                .into(mUserPageUserHeadimg);
        mUserPageUsername.postInvalidate();
        mUserPageAttentionnum.postInvalidate();
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
        mR1.setOnClickListener(this);
        mR2.setOnClickListener(this);
        mR3.setOnClickListener(this);
        mR4.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void createRefresh() {

    }

    @Override
    public void finishRRefresh(int num) {

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
            case R.id.r1:

                break;
            case R.id.r2:
                JumpIntent.startMyIntent(UserCoursePage.class);
                break;
            case R.id.r3:
                JumpIntent.startMyIntent(UserDiaryPage.class);
                break;
            case R.id.r4:
                JumpIntent.startMyIntent(SecondHandStorePage.class);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshMsg);
        SpbBroadcast.destroyBrc(refreshAttTopicNum);
    }

    class RefreshMsg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUserPageUsername.setText(homePage.getDataUserMsgPresenter().getUser_name());
            mUserPageUsername.postInvalidate();
        }
    }

    class RefreshAttTopicNum extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUserPageAttentionnum.setText(String.valueOf(homePage.getDataAttentionTopicPresenter().attentionTopicList.size()));
            mUserPageAttentionnum.postInvalidate();
        }
    }
}
