package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.impl.AttentionUserPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.fragment.attentionuser.Follow;
import com.example.spb.view.fragment.attentionuser.Followed;
import com.example.spb.view.inter.IAttentionUserPageAView;
import com.example.spb.view.utils.ScaleTransitionPagerTitleView;
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

public class AttentionUserPage extends BaseMVPActivity<IAttentionUserPageAView, AttentionUserPageAPresenterImpl> implements IAttentionUserPageAView {

    private FragmentSpbAvtivityBar bar;
    private SimplePagerTitleView simplePagerTitleView;
    private static final String[] title = new String[]{"关注", "被关注"};
    private int NUM = 0;
    private List<String> myTitleList = Arrays.asList(title);
    private ArrayList<Fragment> fragments;
    private FragmentManager fragmentManager;
    private FragmentViewPageAdapter pagerAdapter;
    private CommonNavigator commonNavigator;

    private MagicIndicator mAttentionuserIdt;
    private ViewPager mAttentionuserViewpager;
    private RelativeLayout mExcessR;
    private RefreshFUList refreshFollowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_user_page);
        refreshFollowList = new RefreshFUList();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow),refreshFollowList);
        NUM = getIntent().getIntExtra(SELECTNUM, 0);
        initActView();
    }

    @Override
    protected AttentionUserPageAPresenterImpl createPresenter() {
        return new AttentionUserPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mAttentionuserIdt = (MagicIndicator) findViewById(R.id.attentionuser_idt);
        mAttentionuserViewpager = (ViewPager) findViewById(R.id.attentionuser_viewpager);
        mExcessR = (RelativeLayout) findViewById(R.id.excess_r);
        initData();
        intFollowViewPager();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {
        getDataFollowPresenter().initDate();
        getDataFollowedPresenter().initDate();
    }

    private void intFollowViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new Follow());
        fragments.add(new Followed());

        fragmentManager = getSupportFragmentManager();

        pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 2);

        mAttentionuserViewpager.setAdapter(pagerAdapter);
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
                        mAttentionuserViewpager.setCurrentItem(index);
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

        mAttentionuserIdt.setNavigator(commonNavigator);
        ViewPagerHelper.bind(mAttentionuserIdt, mAttentionuserViewpager);
        mAttentionuserViewpager.setCurrentItem(NUM);
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
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.attentionuser_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshFollowList);
    }

    class RefreshFUList extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            mExcessR.setVisibility(View.GONE);
        }
    }
}
