package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;
import com.example.spb.R;
import com.example.spb.adapter.FragmentViewPageAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.AttentionUserPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.fragment.attentionuser.Follow;
import com.example.spb.view.fragment.attentionuser.Followed;
import com.example.spb.view.inter.IAttentionUserPageAView;
import com.example.spb.view.utils.SetCommonNavigator;
import com.gyf.immersionbar.ImmersionBar;
import net.lucode.hackware.magicindicator.MagicIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AttentionUserPage extends BaseMVPActivity<IAttentionUserPageAView, AttentionUserPageAPresenterImpl> implements IAttentionUserPageAView {

    private static final String[] title = new String[]{"关注", "被关注"};
    private int NUM = 0;
    private final List<String> myTitleList = Arrays.asList(title);

    private MagicIndicator mAttentionuserIdt;
    private ViewPager mAttentionuserViewpager;
    private RelativeLayout mExcessR;
    private RefreshFUList refreshFollowList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_user_page);
        refreshFollowList = new RefreshFUList();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow), refreshFollowList);
        NUM = getIntent().getIntExtra(SELECTNUM, 0);
        initActView();
    }

    @Override
    protected AttentionUserPageAPresenterImpl createPresenter() {
        return new AttentionUserPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mAttentionuserIdt = findViewById(R.id.attentionuser_idt);
        mAttentionuserViewpager = findViewById(R.id.attentionuser_viewpager);
        mExcessR = findViewById(R.id.excess_r);
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
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new Follow());
        fragments.add(new Followed());

        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentViewPageAdapter pagerAdapter = new FragmentViewPageAdapter(fragmentManager, fragments, 2);

        mAttentionuserViewpager.setAdapter(pagerAdapter);
        highViewPager();
    }

    private void highViewPager() {
        mAttentionuserViewpager = SetCommonNavigator.setNavigator(myTitleList, mAttentionuserIdt, mAttentionuserViewpager, NUM);
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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.attentionuser_actbar);
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
