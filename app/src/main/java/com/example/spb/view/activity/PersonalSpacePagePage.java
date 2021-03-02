package com.example.spb.view.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.PersonalSpacePagePageAPresenterImpl;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.fragment.personalspace.BasicInformation;
import com.example.spb.view.fragment.personalspace.PersonalPostBar;
import com.example.spb.view.inter.IPersonalSpacePageAView;
import com.example.spb.view.littlefun.ScaleTransitionPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.gyf.immersionbar.ImmersionBar;
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

public class PersonalSpacePagePage extends BaseMVPActivity<IPersonalSpacePageAView, PersonalSpacePagePageAPresenterImpl> implements IPersonalSpacePageAView, View.OnClickListener {

    private SimplePagerTitleView simplePagerTitleView;
    private static final String[] title = new String[]{"资料", "帖子"};
    private List<String> myTitleList = Arrays.asList(title);
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentViewPageAdapter pagerAdapter;
    private CommonNavigator commonNavigator;
    private MagicIndicator mPersonalspaceIdt;
    private ViewPager mPersonalspaceViewpager;
    private NestedScrollView mPersonalspaceScrollview;

    private String USERNAME = "";

    private FragmentSpbAvtivityBar bar;
    private AppBarLayout mPersonalspaceAppbarlayout;
    private RelativeLayout mPersonalspaceBarR;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_space_page);
        initActView();
    }

    @Override
    protected PersonalSpacePagePageAPresenterImpl createPresenter() {
        return new PersonalSpacePagePageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setBar();
        mPersonalspaceIdt = (MagicIndicator) findViewById(R.id.personalspace_idt);
        mPersonalspaceViewpager = (ViewPager) findViewById(R.id.personalspace_viewpager);
        mPersonalspaceScrollview = (NestedScrollView) findViewById(R.id.personalspace_scrollview);
        mPersonalspaceAppbarlayout = (AppBarLayout) findViewById(R.id.personalspace_appbarlayout);
        mPersonalspaceBarR = (RelativeLayout) findViewById(R.id.personalspace_bar_R);
        intFollowViewPager();
        setActivityBar();
        setMyListener();
    }

    @Override
    protected void initData() {

    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new BasicInformation());
        fragments.add(new PersonalPostBar());

        fragmentManager = getSupportFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 2);

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
                int persent = -i*2/3;
                if (persent > 255) {
                    persent = 255;
                    bar.barCentralTxt(USERNAME, new FragmentSpbAvtivityBar.OnMyClick() {
                        @Override
                        public void onClick() {
                            mPersonalspaceAppbarlayout.setExpanded(true);
                        }
                    });
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
        mPersonalspaceAppbarlayout.addOnOffsetChangedListener(listenViewMove());
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
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
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

        }
    }
}
