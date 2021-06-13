package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.PersonalSpacePageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.fragment.personalspace.BasicInformation;
import com.example.spb.view.fragment.personalspace.PersonalPostBar;
import com.example.spb.view.fragment.personalspace.PersonalVideo;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.view.littlefun.ScaleTransitionPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PersonalSpacePage extends BaseMVPActivity<IPersonalSpacePageAView, PersonalSpacePageAPresenterImpl>
        implements IPersonalSpacePageAView, View.OnClickListener {

    private SimplePagerTitleView simplePagerTitleView;
    private static final String[] title = new String[]{"资料", "帖子", "视频"};
    private List<String> myTitleList = Arrays.asList(title);
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space_page);
        refreshMsg = new RefreshMsg();
        SpbBroadcast.obtainRecriver(this,InValues.send(R.string.Bcr_refresh_userMsg),refreshMsg);
        initActView();
    }

    @Override
    protected PersonalSpacePageAPresenterImpl createPresenter() {
        return new PersonalSpacePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setBar();
        spbSelectImage = new SelectImage(this);
        mPersonalspaceIdt = (MagicIndicator) findViewById(R.id.personalspace_idt);
        mPersonalspaceViewpager = (ViewPager) findViewById(R.id.personalspace_viewpager);
        mPersonalspaceScrollview = (NestedScrollView) findViewById(R.id.personalspace_scrollview);
        mPersonalspaceAppbarlayout = (AppBarLayout) findViewById(R.id.personalspace_appbarlayout);
        mPersonalspaceBarR = (RelativeLayout) findViewById(R.id.personalspace_bar_R);
        mPersonalspaceUserHeadimg = (RoundedImageView) findViewById(R.id.personalspace_user_headimg);
        mPersonalspaceUsername = (TextView) findViewById(R.id.personalspace_username);
        mPersonalspaceUsersex = (ImageView) findViewById(R.id.personalspace_usersex);
        mPersonalspaceUsersign = (TextView) findViewById(R.id.personalspace_usersign);
        mR1 = (RelativeLayout) findViewById(R.id.r1);
        mR2 = (RelativeLayout) findViewById(R.id.r2);
        mR3 = (RelativeLayout) findViewById(R.id.r3);
        initData();
        intFollowViewPager();
        setActivityBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {
        mPersonalspaceUsername.setText(getDataUserMsgPresenter().getUser_name());
        mPersonalspaceUsersign.setText(getDataUserMsgPresenter().getUser_profile());
        if (getDataUserMsgPresenter().getStu_sex().equals("男")){
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_boy);
        }else {
            mPersonalspaceUsersex.setImageResource(R.drawable.icon_girl);
        }
        Glide.with(this)
                .load(InValues.send(R.string.httpHeader) +"/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                .centerCrop()
                .into(mPersonalspaceUserHeadimg);
        mPersonalspaceUsername.postInvalidate();
    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new BasicInformation());
        fragments.add(new PersonalPostBar());
        fragments.add(new PersonalVideo());

        fragmentManager = getSupportFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 3);

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
    }

    private AppBarLayout.OnOffsetChangedListener listenViewMove() {
        AppBarLayout.OnOffsetChangedListener onOffsetChangedListener = new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                int persent = -i * 2 / 3;
                if (persent > 255) {
                    persent = 255;
                    bar.barCentralTxt(dataUserMsgPresenter.user_name, new FragmentSpbAvtivityBar.OnMyClick() {
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

    }

    private TextView mDialogCamera;
    private TextView mDialogPhotoalbum;
    private TextView mDialogClose;
    private TextView mDialogTitle;

    @Override
    public void createDialog() {
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
                mDialogTitle.setText(DIALOGTITLE);
            }

            @Override
            public void initListener() {
                mDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(0);
                    }
                });
                mDialogPhotoalbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(0);
                        spbSelectImage.selectOneImg(IMAGENAME, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                mPresenter.getHeadImage(result);
                            }

                            @Override
                            public void onCancel() {
                                //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                            }
                        });
                    }
                });
            }
        });
        bottomDialog.setBottomStyle();
        bottomDialog.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        bottomDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        bottomDialog.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        mPersonalspaceAppbarlayout.addOnOffsetChangedListener(listenViewMove());
        mPersonalspaceUserHeadimg.setOnClickListener(this);
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

    }

    @Override
    public void finishRRefresh(int num) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.personalspace_user_headimg:
                showDialogS(0);
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

        }
    }

    class RefreshMsg extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mPersonalspaceUsername.setText(getDataUserMsgPresenter().getUser_name());
            mPersonalspaceUsersign.setText(getDataUserMsgPresenter().getUser_profile());
            mPersonalspaceUsername.postInvalidate();
            mPersonalspaceUsersign.postInvalidate();
        }
    }
}
