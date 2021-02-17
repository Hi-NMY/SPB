package com.example.spb.view.fragment.ui.postbarpage;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.PostBarPageFPresenterImpl;
import com.example.spb.view.inter.IPostBarPageFView;
import com.example.spb.view.littlefun.ScaleTransitionPagerTitleView;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

public class PostBarPage extends BaseMVPFragment<IPostBarPageFView, PostBarPageFPresenterImpl> implements IPostBarPageFView {

    private MagicIndicator mPostbarPageIdt;
    private ViewPager mPostbarPageViewpager;
    private static final String[] title = new String[]{"关注", "最新", "话题"};
    private List<String> myTitleList = Arrays.asList(title);
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentViewPageAdapter pagerAdapter;
    private CommonNavigator commonNavigator;

    private AppBarLayout mPostbarAppbarlayout;
    private ImageView mPostbarSearchIcon;
    private CollapsingToolbarLayout mPostbarCollapsinglayout;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected PostBarPageFPresenterImpl createPresenter() {
        return new PostBarPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_page;
    }

    @Override
    protected void initFragView(View view) {
        mPostbarPageIdt = (MagicIndicator) view.findViewById(R.id.postbar_page_idt);
        mPostbarPageViewpager = (ViewPager) view.findViewById(R.id.postbar_page_viewpager);
        mPostbarAppbarlayout = (AppBarLayout) view.findViewById(R.id.postbar_appbarlayout);
        mPostbarSearchIcon = (ImageView) view.findViewById(R.id.postbar_search_icon);
        mPostbarCollapsinglayout = (CollapsingToolbarLayout)view.findViewById(R.id.postbar_collapsinglayout);
        intFollowViewPager();
        listenViewMove();
    }

    @Override
    protected void initData() {

    }

    private void listenViewMove() {
        mPostbarAppbarlayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int i) {
                if (i != 0) {
                    float topA = 1 - (1 - i) / 65f;
                    mPostbarAppbarlayout.setAlpha(topA);
                    topA = 1 - (topA + 0.2f);
                    mPostbarSearchIcon.setAlpha(topA);
                    if (mPostbarSearchIcon.getAlpha() > 0) {
                        mPostbarSearchIcon.setVisibility(View.VISIBLE);
                    } else {
                        mPostbarSearchIcon.setVisibility(View.INVISIBLE);
                    }
                }else {
                    mPostbarAppbarlayout.setAlpha(1 - (1 - appBarLayout.getTotalScrollRange()) / 65f);
                    mPostbarSearchIcon.setAlpha(1-(1 - (1 - appBarLayout.getTotalScrollRange()) / 65f + 0.2f));
                    if (mPostbarSearchIcon.getAlpha() > 0) {
                        mPostbarSearchIcon.setVisibility(View.VISIBLE);
                    } else {
                        mPostbarSearchIcon.setVisibility(View.INVISIBLE);
                    }
                }

            }
        });

    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new AttentionPage());
        fragments.add(new NewPostPage());
        fragments.add(new TopicPage());

        fragmentManager = getChildFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 3);

        mPostbarPageViewpager.setAdapter(pagerAdapter);
        highViewPager();
    }


    private void highViewPager() {
        commonNavigator = new CommonNavigator(MyApplication.getContext());
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return myTitleList == null ? 0 : myTitleList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, int index) {
                SimplePagerTitleView simplePagerTitleView = new ScaleTransitionPagerTitleView(context);
                simplePagerTitleView.setText(myTitleList.get(index));
                simplePagerTitleView.setTextSize(18);

                simplePagerTitleView.setNormalColor(Color.parseColor("#808080"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPostbarPageViewpager.setCurrentItem(index);
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

        mPostbarPageIdt.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mPostbarPageIdt, mPostbarPageViewpager);
        mPostbarPageViewpager.setCurrentItem(1);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }
}
