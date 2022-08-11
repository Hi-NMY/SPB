package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.impl.PersonalSpacePageAPresenterImpl;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.fragment.personalspace.BasicInformation;
import com.example.spb.view.fragment.personalspace.PersonalPostBar;
import com.example.spb.view.fragment.personalspace.PersonalVideo;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.example.spb.view.utils.JumpIntent;
import com.example.spb.view.utils.MyListAnimation;
import com.example.spb.view.utils.SetCommonNavigator;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import net.lucode.hackware.magicindicator.MagicIndicator;
import pl.droidsonroids.gif.GifImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalSpacePage extends BaseMVPActivity<IPersonalSpacePageAView, PersonalSpacePageAPresenterImpl>
        implements IPersonalSpacePageAView, View.OnClickListener {

    private static final String[] title = new String[]{"资料", "帖子", "视频"};
    private final List<String> myTitleList = Arrays.asList(title);
    private String cacheDate = null;
    private MagicIndicator mPersonalspaceIdt;
    private ViewPager mPersonalspaceViewpager;
    private DialogInter bottomDialog;
    private SpbSelectImage spbSelectImage;

    private FragmentSpbAvtivityBar bar;
    private AppBarLayout mPersonalspaceAppbarlayout;
    private RelativeLayout mPersonalspaceBarR;
    private RoundedImageView mPersonalspaceUserHeadimg;
    private RelativeLayout mR1;
    private RelativeLayout mR2;
    private RelativeLayout mR3;
    private TextView mPersonalspaceUsername;
    private ImageView mPersonalspaceUsersex;
    private TextView mPersonalspaceUsersign;
    private RefreshMsg refreshMsg;
    private TextView mPersonalspaceUsertopicNum;
    public String userAccount = null;
    private UserDto toUserDto;
    private RelativeLayout mExcessR;
    private Button mPersonalspaceAttentionBtn;
    private String USERNAME = "";
    private SmartRefreshLayout mPersonalspaceRefresh;
    private MySmartRefresh mySmartRefresh;
    private GifImageView personalspace_refresh_tgif;
    private TextView mPersonalspaceUserattentionNum;
    private TextView mPersonalspaceUserfansNum;
    private RefreshFollow refreshFollow;
    private ImageView mPersonalspaceMessageBtn;
    private boolean imageKey = false;
    private RelativeLayout mPersonalspaceRBg;
    private CollapsingToolbarLayout mPersonalspaceCollapsinglayout;
    private ImageView mPersonalspaceUserbadge;
    private TextView mPersonalspaceUserbadgeAll;
    private DialogInter badgeDialog;
    private TextView mFavoriteClose;
    private RecyclerView mUserBadgeRcl;
    private GifImageView mGif;
    private RefreshBadgeImg refreshBadgeImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space_page);
        cacheDate = MyDateClass.showNowDate();
        userAccount = getIntent().getStringExtra(InValues.send(R.string.intent_User_account));
        refreshMsg = new RefreshMsg();
        refreshFollow = new RefreshFollow();
        refreshBadgeImg = new RefreshBadgeImg();
        SpbBroadcast.obtainRecriver(this, InValues.send(R.string.Bcr_refresh_headimg), refreshBadgeImg);
        SpbBroadcast.obtainRecriver(this, InValues.send(R.string.Bcr_re_Follow), refreshFollow);
        SpbBroadcast.obtainRecriver(this, InValues.send(R.string.Bcr_refresh_userMsg), refreshMsg);
        initActView();
    }

    @Override
    protected PersonalSpacePageAPresenterImpl createPresenter() {
        return new PersonalSpacePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setBar();
        mPresenter.setUserFollowKey(getDataFollowPresenter().determineFollow(userAccount));
        spbSelectImage = new SelectImage(this);
        mPersonalspaceUserbadge = findViewById(R.id.personalspace_userbadge);
        mExcessR = findViewById(R.id.excess_r);
        mPersonalspaceRBg = findViewById(R.id.personalspace_R_bg);
        mPersonalspaceCollapsinglayout = findViewById(R.id.personalspace_collapsinglayout);
        mPersonalspaceIdt = findViewById(R.id.personalspace_idt);
        mPersonalspaceViewpager = findViewById(R.id.personalspace_viewpager);
        mPersonalspaceAppbarlayout = findViewById(R.id.personalspace_appbarlayout);
        mPersonalspaceBarR = findViewById(R.id.personalspace_bar_R);
        mPersonalspaceUserHeadimg = findViewById(R.id.personalspace_user_headimg);
        mPersonalspaceUsername = findViewById(R.id.personalspace_username);
        mPersonalspaceUsersex = findViewById(R.id.personalspace_usersex);
        mPersonalspaceUsersign = findViewById(R.id.personalspace_usersign);
        mPersonalspaceUsertopicNum = findViewById(R.id.personalspace_usertopic_num);
        mPersonalspaceAttentionBtn = findViewById(R.id.personalspace_attention_btn);
        mPersonalspaceRefresh = findViewById(R.id.personalspace_refresh);
        personalspace_refresh_tgif = findViewById(R.id.personalspace_refresh_tgif);
        mPersonalspaceUserattentionNum = findViewById(R.id.personalspace_userattention_num);
        mPersonalspaceUserfansNum = findViewById(R.id.personalspace_userfans_num);
        mPersonalspaceMessageBtn = findViewById(R.id.personalspace_message_btn);
        mPersonalspaceUserbadgeAll = findViewById(R.id.personalspace_userbadgeAll);
        mR1 = findViewById(R.id.r1);
        mR2 = findViewById(R.id.r2);
        mR3 = findViewById(R.id.r3);
        intFollowViewPager();
        totalData();
        setActivityBar();
        setMyListener();
        createDialog();
        createRefresh();
    }

    public void totalData() {
        if (!DataVerificationTool.isEmpty(userAccount) && !userAccount.equals(getDataUserMsgPresenter().getUser_account())) {
            mPresenter.getUser(userAccount, new PersonalSpacePageAPresenterImpl.OnReturn() {
                @Override
                public void onReturn(UserDto userDto) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toUserDto = userDto;
                            initUserDate();
                        }
                    });
                }
            });
            mPresenter.getFollowNum(userAccount, new PersonalSpacePageAPresenterImpl.OnReturnNum() {
                @Override
                public void onReturn(int num) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPersonalspaceUserattentionNum.setText(String.valueOf(num));
                            mPersonalspaceUserattentionNum.postInvalidate();
                        }
                    });
                }
            });
            mPresenter.getFollowedNum(userAccount, new PersonalSpacePageAPresenterImpl.OnReturnNum() {
                @Override
                public void onReturn(int num) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mPersonalspaceUserfansNum.setText(String.valueOf(num));
                            mPersonalspaceUserfansNum.postInvalidate();
                        }
                    });
                }
            });
        } else {
            initData();
        }
        mPresenter.obtainPersonalBar(userAccount, new PersonalSpacePageAPresenterImpl.OnReturnBar() {
            @Override
            public void onReturn() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        finishRRefresh(0);
                    }
                });
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.setNowBadge(getDataUserMsgPresenter().getUser_badge());
        setData(getDataUserMsgPresenter());
        mPersonalspaceUsertopicNum.setText(String.valueOf(getDataAttentionTopicPresenter().attentionTopicList.size()));
        mPersonalspaceUserattentionNum.setText(String.valueOf(getDataFollowPresenter().obtainFollowNum()));
        mPersonalspaceUserfansNum.setText(String.valueOf(getDataFollowedPresenter().obtainFollowedNum()));

        mPersonalspaceUsername.postInvalidate();
        mPersonalspaceUsersign.postInvalidate();
        mPersonalspaceUsertopicNum.postInvalidate();
        mPersonalspaceUserattentionNum.postInvalidate();
        mPersonalspaceUserfansNum.postInvalidate();
    }

    private void setData(UserDto user) {
        mExcessR.setVisibility(View.GONE);
        mPersonalspaceUsername.setText(user.getUser_name());
        mPersonalspaceUsersign.setText(user.getUser_profile());
        USERNAME = mPersonalspaceUsername.getText().toString();
        if (user.getStu_sex().equals("男")) {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_girl);
        }
        if (DataVerificationTool.isEmpty(user.getUser_badge())) {
            mPersonalspaceUserbadge.setVisibility(View.INVISIBLE);
        } else {
            mPersonalspaceUserbadge.setVisibility(View.VISIBLE);
            //显示徽章
            setBadgeImg(user.getUser_badge());
        }
        if (mPersonalspaceUserHeadimg.getTag() == null || !mPersonalspaceUserHeadimg.getTag().equals(cacheDate)) {
            setHeadImg(user.getUser_account());
        }
        if (mPersonalspaceRBg.getTag() == null || !mPersonalspaceRBg.getTag().equals(cacheDate)) {
            setBgImg(user.getUser_account());
        }
    }

    private void initUserDate() {
        setData(toUserDto);
        for (int i = 5; i < mPresenter.getKeys().size(); i++) {
            switch (i) {
                case 5:
                    if (mPresenter.getKeys().get(5) == 1) {
                        mR1.setVisibility(View.VISIBLE);
                        mR2.setVisibility(View.VISIBLE);
                    } else {
                        mR1.setVisibility(View.INVISIBLE);
                        mR2.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 6:
                    if (mPresenter.getKeys().get(6) == 1) {
                        mPersonalspaceUserbadgeAll.setVisibility(View.VISIBLE);
                    } else {
                        mPersonalspaceUserbadgeAll.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 7:
                    if (mPresenter.getKeys().get(7) == 2) {
                        mPersonalspaceViewpager.setVisibility(View.VISIBLE);
                    } else {
                        if (getDataFollowPresenter().determineFollow(toUserDto.getUser_account()) && getDataFollowedPresenter().determineFollowed(toUserDto.getUser_account())) {
                            mPersonalspaceViewpager.setVisibility(View.VISIBLE);
                        } else {
                            mPersonalspaceViewpager.setVisibility(View.INVISIBLE);
                            MyToastClass.ShowToast(MyApplication.getContext(),"双向关注才能查看空间哦");
                        }
                    }
                    break;
            }
        }
        //获取用户关注和粉丝
        if (mPresenter.isUserFollowKey()) {
            yesAtt();
        } else {
            noAtt();
        }
        mPersonalspaceUsername.postInvalidate();
        mPersonalspaceUsersign.postInvalidate();
        mR3.setVisibility(View.GONE);
        mR1.setClickable(false);
        mR2.setClickable(false);
        mPersonalspaceAttentionBtn.setVisibility(View.VISIBLE);
        mPersonalspaceMessageBtn.setVisibility(View.VISIBLE);
        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_UserSpace_user), 0, toUserDto);
    }

    private void intFollowViewPager() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new BasicInformation());
        fragments.add(new PersonalPostBar());
        fragments.add(new PersonalVideo());

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentViewPageAdapter pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 3);
        mPersonalspaceViewpager.setOffscreenPageLimit(2);
        mPersonalspaceViewpager.setAdapter(pagerAdapter);
        highViewPager();
    }

    private void highViewPager() {
        mPersonalspaceViewpager = SetCommonNavigator.setNavigator(myTitleList, mPersonalspaceIdt, mPersonalspaceViewpager, 1);
        mPersonalspaceViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_stop_voice), 0, null);
            }
        });
    }

    private AppBarLayout.OnOffsetChangedListener listenViewMove() {
        return new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int persent = -i * 2 / 3;
                if (persent > 255) {
                    persent = 255;
                    bar.barCentralTxt(USERNAME, new FragmentSpbAvtivityBar.OnMyClick() {
                        @Override
                        public void onClick() {
                            mPersonalspaceAppbarlayout.setExpanded(true);
                        }
                    });
                    bar.barLeftImg(R.drawable.left_return, null);
                } else {
                    bar.barCentralTxt("", null);
                    bar.barLeftImg(R.drawable.left_return_white, null);
                }
                int color = Color.argb(persent, 249, 249, 249);
                mPersonalspaceBarR.setBackgroundColor(color);
            }
        };
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (responseFlag) {
                    case RETURN_HEADIMAGE:
                        if (imageKey) {
                            MyToastClass.ShowToast(MyApplication.getContext(), "头像更换成功");
                            SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_headimg), 0, null);
                            setHeadImg(getDataUserMsgPresenter().getUser_account());
                            mPresenter.updateRong(getDataUserMsgPresenter().getUser_account(), getDataUserMsgPresenter().getUser_name());
                        } else {
                            MyToastClass.ShowToast(MyApplication.getContext(), "背景更换成功");
                            setBgImg(getDataUserMsgPresenter().getUser_account());
                        }
                        break;
                }
            }
        });
    }

    private TextView mDialogCamera;
    private TextView mDialogPhotoalbum;
    private TextView mDialogClose;
    private TextView mDialogTitle;

    @Override
    public void createDialog() {
        badgeDialog = new ComponentDialog(this, R.layout.dialog_user_badge, R.style.bottomdialog, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mFavoriteClose = view.findViewById(R.id.favorite_close);
                mUserBadgeRcl = view.findViewById(R.id.user_badge_rcl);
                mGif = view.findViewById(R.id.gif);
                MyListAnimation.setListAnimation(PersonalSpacePage.this, mUserBadgeRcl);
            }

            @Override
            public void initData() {
                mPresenter.obtainUserBadge(userAccount, new PersonalSpacePageAPresenterImpl.OnReturnBar() {
                    @Override
                    public void onReturn() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mGif.setVisibility(View.GONE);
                                mUserBadgeRcl.setVisibility(View.VISIBLE);
                                mPresenter.setBadgeAdapter(PersonalSpacePage.this, getDataUserMsgPresenter().getUser_account().equals(userAccount), mUserBadgeRcl,
                                        new GridLayoutManager(MyApplication.getContext(), 4));
                            }
                        });
                    }
                });
            }

            @Override
            public void initListener() {
                mFavoriteClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BADGEDIALOG);
                    }
                });
            }
        });
        badgeDialog.setBottomStyle();
        badgeDialog.setAnimation(R.style.bottomdialog_animStyle);
        bottomDialog = new ComponentDialog(this, R.layout.dialog_selectpicture, R.style.bottomdialog, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mDialogTitle = view.findViewById(R.id.dialog_title);
                mDialogCamera = view.findViewById(R.id.dialog_camera);
                mDialogPhotoalbum = view.findViewById(R.id.dialog_photoalbum);
                mDialogClose = view.findViewById(R.id.dialog_close);
            }

            @Override
            public void initData() {
                if (imageKey) {
                    mDialogTitle.setText(DIALOGTITLE);
                } else {
                    mDialogTitle.setText(DIALOGTITLE2);
                }
            }

            @Override
            public void initListener() {
                mDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(IMAGEDIALOG);
                    }
                });
                mDialogPhotoalbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(IMAGEDIALOG);
                        if (imageKey) {
                            spbSelectImage.selectOneImg(IMAGENAME, new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    mPresenter.getHeadImage(getDataUserMsgPresenter().getUser_account(), result);
                                }

                                @Override
                                public void onCancel() {
                                    //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                                }
                            });
                        } else {
                            spbSelectImage.selectOneImg2(IMAGENAME2, new OnResultCallbackListener<LocalMedia>() {
                                @Override
                                public void onResult(List<LocalMedia> result) {
                                    mPresenter.getBgImage(getDataUserMsgPresenter().getUser_account(), result);
                                }

                                @Override
                                public void onCancel() {
                                    //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                                }
                            });
                        }
                    }
                });
                mDialogCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(IMAGEDIALOG);
                        if (imageKey) {
                            spbSelectImage.selectCameraImg(IMAGENAME, new OnResultCallbackListener() {
                                @Override
                                public void onResult(List result) {
                                    mPresenter.getHeadImage(getDataUserMsgPresenter().getUser_account(), result);
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        } else {
                            spbSelectImage.selectCameraImg2(IMAGENAME2, new OnResultCallbackListener() {
                                @Override
                                public void onResult(List result) {
                                    mPresenter.getBgImage(getDataUserMsgPresenter().getUser_account(), result);
                                }

                                @Override
                                public void onCancel() {

                                }
                            });
                        }
                    }
                });
            }
        });
        bottomDialog.setBottomStyle();
        bottomDialog.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case IMAGEDIALOG:
                bottomDialog.showMyDialog();
                break;
            case BADGEDIALOG:
                badgeDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case IMAGEDIALOG:
                bottomDialog.closeMyDialog();
                break;
            case BADGEDIALOG:
                badgeDialog.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {
        mPersonalspaceAppbarlayout.addOnOffsetChangedListener(listenViewMove());
        mPersonalspaceUserHeadimg.setOnClickListener(this);
        mPersonalspaceAttentionBtn.setOnClickListener(this);
        mPersonalspaceMessageBtn.setOnClickListener(this);
        mPersonalspaceRBg.setOnClickListener(this);
        mPersonalspaceCollapsinglayout.setOnClickListener(this);
        mPersonalspaceUserbadgeAll.setOnClickListener(this);
        mR1.setOnClickListener(this);
        mR2.setOnClickListener(this);
        mR3.setOnClickListener(this);
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.personalspace_bar);
        bar.setBarBackground(R.color.picture_color_transparent);
        bar.barLeftImg(R.drawable.left_return_white, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void createRefresh() {
        mySmartRefresh = new MySmartRefresh(mPersonalspaceRefresh, personalspace_refresh_tgif, null);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                totalData();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mySmartRefresh.closeLoadMore();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    public void yesAtt() {
        mPersonalspaceAttentionBtn.setBackground(getDrawable(R.drawable.already_attention));
        mPersonalspaceAttentionBtn.setText("已关注");
    }

    public void noAtt() {
        mPersonalspaceAttentionBtn.setBackground(getDrawable(R.drawable.enter_next_login));
        mPersonalspaceAttentionBtn.setText("关注");
    }

    private void setBadgeImg(String badge) {
        Glide.with(MyApplication.getContext())
                .load(InValues.send(R.string.prefix_badge_img) + badge)
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .centerCrop()
                .into(mPersonalspaceUserbadge);
    }


    private void setHeadImg(String account) {
        Glide.with(MyApplication.getContext())
                .load(InValues.send(R.string.prefix_img) + account + InValues.send(R.string.suffix_head_img))
                .placeholder(R.drawable.logo2)
                .error(R.drawable.logo2)
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .centerCrop()
                .into(mPersonalspaceUserHeadimg);
        mPersonalspaceUserHeadimg.setTag(cacheDate);
    }

    private void setBgImg(String account) {
        Glide.with(MyApplication.getContext())
                .load(InValues.send(R.string.prefix_img) + account + InValues.send(R.string.suffix_bg_img))
                .placeholder(R.drawable.enterbg)
                .error(R.drawable.enterbg)
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .centerCrop()
                .into(new CustomViewTarget<RelativeLayout, Drawable>(mPersonalspaceRBg) {
                    @Override
                    protected void onResourceCleared(@Nullable Drawable placeholder) {

                    }

                    @Override
                    public void onLoadFailed(@Nullable Drawable errorDrawable) {

                    }

                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        mPersonalspaceRBg.setBackground(resource);
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personalspace_user_headimg:
                if (toUserDto != null) {

                } else {
                    imageKey = true;
                    createDialog();
                    showDialogS(IMAGEDIALOG);
                }
                break;
            case R.id.personalspace_collapsinglayout:
                if (toUserDto != null) {

                } else {
                    imageKey = false;
                    createDialog();
                    showDialogS(IMAGEDIALOG);
                }
                break;
            case R.id.r1:
                JumpIntent.startMsgIntent(AttentionUserPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(STRINGEXTRA, PAGENUMONE);
                    }
                });
                break;
            case R.id.r2:
                JumpIntent.startMsgIntent(AttentionUserPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(STRINGEXTRA, PAGENUMTWO);
                    }
                });
                break;
            case R.id.r3:
                JumpIntent.startMyIntent(AttentionTopicPage.class);
                break;
            case R.id.personalspace_attention_btn:
                if (mPresenter.isUserFollowKey()) {
                    noAtt();
                    getDataFollowPresenter().removeFollow(userAccount);
                } else {
                    yesAtt();
                    getDataFollowPresenter().addFollow(userAccount);
                }
                break;
            case R.id.personalspace_message_btn:
                Conversation.ConversationType conversationType = Conversation.ConversationType.PRIVATE;
                String targetId = toUserDto.getUser_account();
                String title = toUserDto.getUser_name();
                RongIM.getInstance().startConversation(this, conversationType, targetId, title);
                break;
            case R.id.personalspace_userbadgeAll:
                showDialogS(BADGEDIALOG);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshFollow);
        SpbBroadcast.destroyBrc(refreshMsg);
        SpbBroadcast.destroyBrc(refreshBadgeImg);
        SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_stop_voice), 0, null);
        if (getEasyVoice() != null) {
            getEasyVoice().stopPlayer();
            setEasyVoice(null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getEasyVoice() != null) {
            getEasyVoice().stopPlayer();
            setEasyVoice(null);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_stop_voice), 0, null);
    }

    class RefreshMsg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mPersonalspaceUsername.setText(getDataUserMsgPresenter().getUser_name());
            mPersonalspaceUsersign.setText(getDataUserMsgPresenter().getUser_profile());
            mPersonalspaceUsername.postInvalidate();
            mPersonalspaceUsersign.postInvalidate();
        }
    }

    class RefreshFollow extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            if (a == 0) {
                mPresenter.setUserFollowKey(true);
            } else {
                mPresenter.setUserFollowKey(false);
            }
        }
    }

    class RefreshBadgeImg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            if (a == 1) {
                String badge = intent.getStringExtra("key_two");
                mPresenter.updateUserBadge(getDataUserMsgPresenter().getUser_account(), badge);
                mPersonalspaceUserbadge.setVisibility(View.VISIBLE);
                setBadgeImg(badge);
            }
        }
    }
}
