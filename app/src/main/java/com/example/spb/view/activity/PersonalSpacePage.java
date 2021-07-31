package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
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
import com.example.spb.entity.User;
import com.example.spb.presenter.impl.PersonalSpacePageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.SpbBroadcast;
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
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.view.littlefun.MyListAnimation;
import com.example.spb.view.littlefun.ScaleTransitionPagerTitleView;
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
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;
import pl.droidsonroids.gif.GifImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalSpacePage extends BaseMVPActivity<IPersonalSpacePageAView, PersonalSpacePageAPresenterImpl>
        implements IPersonalSpacePageAView, View.OnClickListener {

    private SimplePagerTitleView simplePagerTitleView;
    private static final String[] title = new String[]{"资料", "帖子", "视频"};
    private List<String> myTitleList = Arrays.asList(title);
    private String cacheDate = null;
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentViewPageAdapter pagerAdapter;
    private CommonNavigator commonNavigator;
    private MagicIndicator mPersonalspaceIdt;
    private ViewPager mPersonalspaceViewpager;
    private NestedScrollView mPersonalspaceScrollview;
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
    private User toUser;
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
        mPersonalspaceUserbadge = (ImageView) findViewById(R.id.personalspace_userbadge);
        mExcessR = (RelativeLayout) findViewById(R.id.excess_r);
        mPersonalspaceRBg = (RelativeLayout) findViewById(R.id.personalspace_R_bg);
        mPersonalspaceCollapsinglayout = (CollapsingToolbarLayout) findViewById(R.id.personalspace_collapsinglayout);
        mPersonalspaceIdt = (MagicIndicator) findViewById(R.id.personalspace_idt);
        mPersonalspaceViewpager = (ViewPager) findViewById(R.id.personalspace_viewpager);
        mPersonalspaceScrollview = (NestedScrollView) findViewById(R.id.personalspace_scrollview);
        mPersonalspaceAppbarlayout = (AppBarLayout) findViewById(R.id.personalspace_appbarlayout);
        mPersonalspaceBarR = (RelativeLayout) findViewById(R.id.personalspace_bar_R);
        mPersonalspaceUserHeadimg = (RoundedImageView) findViewById(R.id.personalspace_user_headimg);
        mPersonalspaceUsername = (TextView) findViewById(R.id.personalspace_username);
        mPersonalspaceUsersex = (ImageView) findViewById(R.id.personalspace_usersex);
        mPersonalspaceUsersign = (TextView) findViewById(R.id.personalspace_usersign);
        mPersonalspaceUsertopicNum = (TextView) findViewById(R.id.personalspace_usertopic_num);
        mPersonalspaceAttentionBtn = (Button) findViewById(R.id.personalspace_attention_btn);
        mPersonalspaceRefresh = (SmartRefreshLayout) findViewById(R.id.personalspace_refresh);
        personalspace_refresh_tgif = (GifImageView) findViewById(R.id.personalspace_refresh_tgif);
        mPersonalspaceUserattentionNum = (TextView) findViewById(R.id.personalspace_userattention_num);
        mPersonalspaceUserfansNum = (TextView) findViewById(R.id.personalspace_userfans_num);
        mPersonalspaceMessageBtn = (ImageView) findViewById(R.id.personalspace_message_btn);
        mPersonalspaceUserbadgeAll = (TextView) findViewById(R.id.personalspace_userbadgeAll);
        mR1 = (RelativeLayout) findViewById(R.id.r1);
        mR2 = (RelativeLayout) findViewById(R.id.r2);
        mR3 = (RelativeLayout) findViewById(R.id.r3);
        totalData();
        intFollowViewPager();
        setActivityBar();
        setMyListener();
        createDialog();
        createRefresh();
    }

    public void totalData() {
        if (userAccount != null && !userAccount.equals("") && !userAccount.equals(getDataUserMsgPresenter().getUser_account())) {
            mPresenter.getUser(userAccount, new PersonalSpacePageAPresenterImpl.OnReturn() {
                @Override
                public void onReturn(User user) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toUser = user;
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
            mExcessR.setVisibility(View.GONE);
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
        mPersonalspaceUsername.setText(getDataUserMsgPresenter().getUser_name());
        USERNAME = mPersonalspaceUsername.getText().toString();
        mPersonalspaceUsersign.setText(getDataUserMsgPresenter().getUser_profile());
        mPersonalspaceUsertopicNum.setText(String.valueOf(getDataAttentionTopicPresenter().attentionTopicList.size()));
        mPersonalspaceUserattentionNum.setText(String.valueOf(getDataFollowPresenter().obtainFollowNum()));
        mPersonalspaceUserfansNum.setText(String.valueOf(getDataFollowedPresenter().obtainFollowedNum()));
        if (getDataUserMsgPresenter().getStu_sex().equals("男")) {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_girl);
        }
        if (getDataUserMsgPresenter().getUser_badge() == null || getDataUserMsgPresenter().getUser_badge().equals("")) {
            mPersonalspaceUserbadge.setVisibility(View.INVISIBLE);
        } else {
            mPersonalspaceUserbadge.setVisibility(View.VISIBLE);
            //显示徽章
            Glide.with(PersonalSpacePage.this)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + getDataUserMsgPresenter().getUser_badge())
                    .centerCrop()
                    .into(mPersonalspaceUserbadge);
        }
        if (mPersonalspaceUserHeadimg.getTag() == null || !mPersonalspaceUserHeadimg.getTag().equals(cacheDate)) {
            Glide.with(MyApplication.getContext())
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                    .placeholder(R.drawable.logo2)
                    .error(R.drawable.logo2)
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(mPersonalspaceUserHeadimg);
            mPersonalspaceUserHeadimg.setTag(cacheDate);
        }
        if (mPersonalspaceRBg.getTag() == null || !mPersonalspaceRBg.getTag().equals(cacheDate)) {
            Glide.with(MyApplication.getContext())
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/BackgroundImage/myBackgroundImage.png")
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
        mPersonalspaceUsername.postInvalidate();
        mPersonalspaceUsersign.postInvalidate();
        mPersonalspaceUsertopicNum.postInvalidate();
        mPersonalspaceUserattentionNum.postInvalidate();
        mPersonalspaceUserfansNum.postInvalidate();
    }

    private void initUserDate() {
        mExcessR.setVisibility(View.GONE);
        mPersonalspaceUsername.setText(toUser.getUser_name());
        mPersonalspaceUsersign.setText(toUser.getUser_profile());
        USERNAME = mPersonalspaceUsername.getText().toString();

        for (int i = 5 ; i < mPresenter.getKeys().size() ; i++){
            switch (i){
                case 5:
                    if (mPresenter.getKeys().get(5) == 1){
                        mR1.setVisibility(View.VISIBLE);
                        mR2.setVisibility(View.VISIBLE);
                    }else {
                        mR1.setVisibility(View.INVISIBLE);
                        mR2.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 6:
                    if (mPresenter.getKeys().get(6) == 1){
                        mPersonalspaceUserbadgeAll.setVisibility(View.VISIBLE);
                    }else {
                        mPersonalspaceUserbadgeAll.setVisibility(View.INVISIBLE);
                    }
                    break;
                case 7:
                    if (mPresenter.getKeys().get(7) == 2){
                        mPersonalspaceViewpager.setVisibility(View.VISIBLE);
                    }else {
                        if (getDataFollowPresenter().determineFollow(toUser.getUser_account()) && getDataFollowedPresenter().determineFollowed(toUser.getUser_account())){
                            mPersonalspaceViewpager.setVisibility(View.VISIBLE);
                        }else {
                            mPersonalspaceViewpager.setVisibility(View.INVISIBLE);
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
        if (toUser.getStu_sex().equals("男")) {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_girl);
        }
        if (toUser.getUser_badge() == null || toUser.getUser_badge().equals("")) {
            mPersonalspaceUserbadge.setVisibility(View.INVISIBLE);
        } else {
            mPersonalspaceUserbadge.setVisibility(View.VISIBLE);
            //显示徽章
            Glide.with(PersonalSpacePage.this)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + toUser.getUser_badge())
                    .centerCrop()
                    .into(mPersonalspaceUserbadge);
        }
        if (mPersonalspaceUserHeadimg.getTag() == null || !mPersonalspaceUserHeadimg.getTag().equals(cacheDate)) {
            Glide.with(this)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + toUser.getUser_account() + "/HeadImage/myHeadImage.png")
                    .placeholder(R.drawable.logo2)
                    .error(R.drawable.logo2)
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(mPersonalspaceUserHeadimg);
            mPersonalspaceUserHeadimg.setTag(cacheDate);
        }
        if (mPersonalspaceRBg.getTag() == null || !mPersonalspaceRBg.getTag().equals(cacheDate)) {
            Glide.with(MyApplication.getContext())
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + toUser.getUser_account() + "/BackgroundImage/myBackgroundImage.png")
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
        mPersonalspaceUsername.postInvalidate();
        mPersonalspaceUsersign.postInvalidate();
        mR3.setVisibility(View.GONE);
        mR1.setClickable(false);
        mR2.setClickable(false);
        mPersonalspaceAttentionBtn.setVisibility(View.VISIBLE);
        mPersonalspaceMessageBtn.setVisibility(View.VISIBLE);
        SpbBroadcast.sendReceiver(this, InValues.send(R.string.Bcr_UserSpace_user), 0, toUser);
    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new BasicInformation());
        fragments.add(new PersonalPostBar());
        fragments.add(new PersonalVideo());

        fragmentManager = getSupportFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 3);
        mPersonalspaceViewpager.setOffscreenPageLimit(2);
        mPersonalspaceViewpager.setAdapter(pagerAdapter);
        highViewPager();
    }

    private void highViewPager() {
        commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return myTitleList == null ? 0 : myTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(myTitleList.get(index));
                simplePagerTitleView.setTextSize(18);
                simplePagerTitleView.setNormalColor(Color.parseColor("#808080"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPersonalspaceViewpager.setCurrentItem(index);
                    }
                });

                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_EXACTLY);
                indicator.setLineHeight(UIUtil.dip2px(context, 4));
                indicator.setLineWidth(UIUtil.dip2px(context, 12));
                indicator.setRoundRadius(UIUtil.dip2px(context, 3));
                indicator.setStartInterpolator(new AccelerateInterpolator());
                indicator.setEndInterpolator(new DecelerateInterpolator(2.0f));
                indicator.setColors(Color.parseColor("#46B3E6"));
                return indicator;
            }
        });

        mPersonalspaceIdt.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mPersonalspaceIdt, mPersonalspaceViewpager);
        mPersonalspaceViewpager.setCurrentItem(1);
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
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
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
        return onOffsetChangedListener;
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
                            Glide.with(MyApplication.getContext())
                                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                                    .placeholder(R.drawable.logo2)
                                    .error(R.drawable.logo2)
                                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                                    .centerCrop()
                                    .into(mPersonalspaceUserHeadimg);
                            mPresenter.updateRong(getDataUserMsgPresenter().getUser_account(), getDataUserMsgPresenter().getUser_name());
                        } else {
                            MyToastClass.ShowToast(MyApplication.getContext(), "背景更换成功");
                            Glide.with(MyApplication.getContext())
                                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/BackgroundImage/myBackgroundImage.png")
                                    .placeholder(R.drawable.logo2)
                                    .error(R.drawable.logo2)
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
                mFavoriteClose = (TextView) view.findViewById(R.id.favorite_close);
                mUserBadgeRcl = (RecyclerView) view.findViewById(R.id.user_badge_rcl);
                mGif = (GifImageView) view.findViewById(R.id.gif);
                mUserBadgeRcl = MyListAnimation.setListAnimation(PersonalSpacePage.this,mUserBadgeRcl);
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
                                mPresenter.setBadgeAdapter(PersonalSpacePage.this,getDataUserMsgPresenter().getUser_account().equals(userAccount) ? true:false,mUserBadgeRcl,
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
                mDialogTitle = (TextView) view.findViewById(R.id.dialog_title);
                mDialogCamera = (TextView) view.findViewById(R.id.dialog_camera);
                mDialogPhotoalbum = (TextView) view.findViewById(R.id.dialog_photoalbum);
                mDialogClose = (TextView) view.findViewById(R.id.dialog_close);
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
        switch (i){
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
        switch (i){
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personalspace_user_headimg:
                if (toUser != null) {

                } else {
                    imageKey = true;
                    createDialog();
                    showDialogS(IMAGEDIALOG);
                }
                break;
            case R.id.personalspace_collapsinglayout:
                if (toUser != null) {

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
                String targetId = toUser.getUser_account();
                String title = toUser.getUser_name();
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
        if (getEasyVoice() != null){
            getEasyVoice().stopPlayer();
            setEasyVoice(null);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (getEasyVoice() != null){
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
                MyToastClass.ShowToast(MyApplication.getContext(), "关注成功");
            } else {
                mPresenter.setUserFollowKey(false);
                MyToastClass.ShowToast(MyApplication.getContext(), "取消关注");
            }
        }
    }

    class RefreshBadgeImg extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            if (a == 1){
                String badge = intent.getStringExtra("key_two");
                mPresenter.updateUserBadge(getDataUserMsgPresenter().getUser_account(),badge);
                mPersonalspaceUserbadge.setVisibility(View.VISIBLE);
                Glide.with(PersonalSpacePage.this)
                        .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + badge)
                        .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                        .centerCrop()
                        .into(mPersonalspaceUserbadge);
            }
        }
    }
}
