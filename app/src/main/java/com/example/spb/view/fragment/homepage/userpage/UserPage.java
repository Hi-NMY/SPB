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
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.UserPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
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
    private RefreshDataNum refreshDataNum;
    private TextView mUserPageAttentionnum;
    private RefreshHeadImg refreshHeadImg;
    private TextView mUserPagePostbarnum;
    private TextView mUserPageFollow;
    private TextView mUserPageFollowed;
    private RefreshFollowNum refreshFollowNum;
    private TextView mUserPageCollectnum;
    private TextView mUserPageUseronlineday;
    private ImageView mUserPageUserbadge;
    private RefreshLongDay refreshLongDay;
    private AssistCard assistCard;
    private RelativeLayout mUserPageSubject;
    private RelativeLayout mUserPageDayactive;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        refreshMsg = new RefreshMsg();
        refreshDataNum = new RefreshDataNum();
        refreshHeadImg = new RefreshHeadImg();
        refreshFollowNum = new RefreshFollowNum();
        refreshLongDay = new RefreshLongDay();
        assistCard = new AssistCard();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_assist_card), assistCard);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow), refreshFollowNum);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_userMsg), refreshMsg);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_reUserPage_Datanum), refreshDataNum);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_headimg), refreshHeadImg);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), refreshLongDay);
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
        homePage = (HomePage) getActivity();
        mUserPageUserR = (RelativeLayout) view.findViewById(R.id.user_page_userR);
        mUserPageUseronlinetip = (ImageView) view.findViewById(R.id.user_page_useronlinetip);
        mUserPageCollectnumR = (RelativeLayout) view.findViewById(R.id.user_page_collectnum_r);
        mUserPageCollectnum = (TextView) view.findViewById(R.id.user_page_collectnum);
        mUserPageAttentionnumR = (RelativeLayout) view.findViewById(R.id.user_page_attentionnum_r);
        mUserPageUserHeadimg = (RoundedImageView) view.findViewById(R.id.user_page_user_headimg);
        mUserPageUsername = (TextView) view.findViewById(R.id.user_page_username);
        mUserPageUsersex = (ImageView) view.findViewById(R.id.user_page_usersex);
        mUserPageAttentionnum = (TextView) view.findViewById(R.id.user_page_attentionnum);
        mUserPagePostbarnum = (TextView) view.findViewById(R.id.user_page_postbarnum);
        mUserPageFollow = (TextView) view.findViewById(R.id.user_page_follow);
        mUserPageFollowed = (TextView) view.findViewById(R.id.user_page_followed);
        mUserPageUseronlineday = (TextView) view.findViewById(R.id.user_page_useronlineday);
        mUserPageUserbadge = (ImageView) view.findViewById(R.id.user_page_userbadge);
        mR1 = (RelativeLayout) view.findViewById(R.id.r1);
        mR2 = (RelativeLayout) view.findViewById(R.id.r2);
        mR3 = (RelativeLayout) view.findViewById(R.id.r3);
        mR4 = (RelativeLayout) view.findViewById(R.id.r4);
        mUserPageSubject = (RelativeLayout) view.findViewById(R.id.user_page_subject);
        mUserPageDayactive = (RelativeLayout) view.findViewById(R.id.user_page_dayactive);
        createDialog();
        setMyListener();
    }

    @Override
    protected void initData() {
        mUserPageUsername.setText(homePage.getDataUserMsgPresenter().getUser_name());
        mUserPageAttentionnum.setText(String.valueOf(homePage.getDataAttentionTopicPresenter().attentionNum.size()));
        mUserPageFollow.setText("关注 " + MyDateClass.sendMath(homePage.getDataFollowPresenter().obtainFollowNum()));
        mUserPageFollowed.setText("被关注 " + MyDateClass.sendMath(homePage.getDataFollowedPresenter().obtainFollowedNum()));
        mUserPageUseronlineday.setText(homePage.getDataUserMsgPresenter().getUser_longDay() + "天");
        viewMyCard();
        if (homePage.getDataUserMsgPresenter().getStu_sex().equals("男")) {
            mUserPageUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            mUserPageUsersex.setImageResource(R.drawable.icon_girl);
        }
        if (homePage.getDataUserMsgPresenter().getUser_badge() == null || homePage.getDataUserMsgPresenter().getUser_badge().equals("")) {
            mUserPageUserbadge.setVisibility(View.INVISIBLE);
        } else {
            Glide.with(homePage)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + homePage.getDataUserMsgPresenter().getUser_badge())
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(mUserPageUserbadge);
        }
        Glide.with(this)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + homePage.getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                .centerCrop()
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .into(mUserPageUserHeadimg);
        mUserPageFollow.postInvalidate();
        mUserPageFollowed.postInvalidate();
        mUserPageUsername.postInvalidate();
        mUserPageAttentionnum.postInvalidate();
        mUserPageUseronlineday.postInvalidate();
    }

    private void viewMyCard(){
        mPresenter.obtainMyCard(new UserPageFPresenterImpl.OnCardReturn() {
            @Override
            public void onReturn(boolean classKey, boolean activeKey) {
                if (!classKey){
                    mUserPageSubject.setVisibility(View.GONE);
                }else {
                    mUserPageSubject.setVisibility(View.VISIBLE);
                }
                if (!activeKey){
                    mUserPageDayactive.setVisibility(View.GONE);
                }else {
                    mUserPageDayactive.setVisibility(View.VISIBLE);
                }
            }
        });
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
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), homePage.getDataUserMsgPresenter().getUser_account());
                    }
                });
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
        SpbBroadcast.destroyBrc(refreshDataNum);
        SpbBroadcast.destroyBrc(refreshHeadImg);
        SpbBroadcast.destroyBrc(refreshLongDay);
        SpbBroadcast.destroyBrc(assistCard);
    }

    class RefreshMsg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUserPageUsername.setText(homePage.getDataUserMsgPresenter().getUser_name());
            mUserPageUsername.postInvalidate();
        }
    }

    class RefreshDataNum extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //获取关注话题数量
            mUserPageAttentionnum.setText(String.valueOf(homePage.getDataAttentionTopicPresenter().obtainAttentionTopicNum()));
            mUserPageAttentionnum.postInvalidate();
            //获取关注话题数量
            mUserPageCollectnum.setText(String.valueOf(homePage.getDataCollectBarPresenter().obtainCollectNum()));
            mUserPageCollectnum.postInvalidate();
            //获取帖子数量
            mPresenter.obtainBarNum(new UserPageFPresenterImpl.OnReturn() {
                @Override
                public void onReturn(int num) {
                    mUserPagePostbarnum.setText(String.valueOf(num));
                    mUserPagePostbarnum.postInvalidate();
                }
            });
            //获取收藏数量
        }
    }

    class RefreshHeadImg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            if (a == 1) {
                String badge = intent.getStringExtra("key_two");
                mUserPageUserbadge.setVisibility(View.VISIBLE);
                Glide.with(homePage)
                        .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + badge)
                        .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                        .centerCrop()
                        .into(mUserPageUserbadge);
            } else {
                Glide.with(MyApplication.getContext())
                        .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + homePage.getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                        .centerCrop()
                        .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                        .into(mUserPageUserHeadimg);
            }
        }
    }

    class RefreshFollowNum extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUserPageFollow.setText("关注 " + MyDateClass.sendMath(homePage.getDataFollowPresenter().obtainFollowNum()));
            mUserPageFollowed.setText("被关注 " + MyDateClass.sendMath(homePage.getDataFollowedPresenter().obtainFollowedNum()));
            mUserPageFollow.postInvalidate();
            mUserPageFollowed.postInvalidate();
        }
    }

    class RefreshLongDay extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mUserPageUseronlineday.setText(homePage.getDataUserMsgPresenter().getUser_longDay() + "天");
            mUserPageUseronlineday.postInvalidate();
        }
    }

    class AssistCard extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            viewMyCard();
        }
    }
}
