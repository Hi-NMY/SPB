package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.NoticePageAPresenterImpl;
import com.example.spb.presenter.otherimpl.DataNoticePresenter;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.inter.INoticePageAView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class NoticePage extends BaseMVPActivity<INoticePageAView, NoticePageAPresenterImpl> implements INoticePageAView {

    private FragmentSpbAvtivityBar bar;
    private View mViewNoticeOfficial;
    private RelativeLayout mOfficialnotice;
    private GifImageView mNoticeRefreshTgif;
    private RecyclerView mNoticeRecyclerview;
    private SmartRefreshLayout mNoticeRefresh;
    private RelativeLayout mExcessR;
    private MySmartRefresh mySmartRefresh;
    private GridLayoutManager gridLayoutManager;
    private ComponentDialog bottomSystemNotice;
    private TextView mNoticeClose;
    private RecyclerView mNoticeRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_page);
        initActView();
    }

    @Override
    protected NoticePageAPresenterImpl createPresenter() {
        return new NoticePageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        gridLayoutManager = new GridLayoutManager(this, 1);
        mExcessR = (RelativeLayout) findViewById(R.id.excess_r);
        mViewNoticeOfficial = (View) findViewById(R.id.view_notice_official);
        mOfficialnotice = (RelativeLayout) findViewById(R.id.officialnotice);
        mNoticeRefreshTgif = (GifImageView) findViewById(R.id.notice_refresh_tgif);
        mNoticeRecyclerview = (RecyclerView) findViewById(R.id.notice_recyclerview);
        mNoticeRefresh = (SmartRefreshLayout) findViewById(R.id.notice_refresh);
        mNoticeRecyclerview = MyListAnimation.setListAnimation(this, mNoticeRecyclerview);
        initData();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
        createRefresh();
    }

    @Override
    protected void initData() {
        getDataNoticePresenter().obtainSystemNotice(new DataNoticePresenter.SystemMessage() {
            @Override
            public void onReturn() {
                mViewNoticeOfficial.setVisibility(View.VISIBLE);
            }
        });
        mPresenter.obtainNotice(getDataNoticePresenter().notices, gridLayoutManager, mNoticeRecyclerview, new NoticePageAPresenterImpl.OnReturn() {
            @Override
            public void onReturn() {
                mExcessR.setVisibility(View.GONE);
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
        bottomSystemNotice = new ComponentDialog(this, R.layout.dialog_bottom_system_notice, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mNoticeClose = (TextView) view.findViewById(R.id.notice_close);
                mNoticeRecycler = (RecyclerView) view.findViewById(R.id.notice_recycler);
                mNoticeRecycler = MyListAnimation.setListAnimation(NoticePage.this,mNoticeRecycler);
            }

            @Override
            public void initData() {
                mPresenter.obtainSystemNotice(getDataNoticePresenter().systemNotices,new GridLayoutManager(MyApplication.getContext(),1),mNoticeRecycler);
            }

            @Override
            public void initListener() {
                mNoticeClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(0);
                    }
                });
            }
        });
        bottomSystemNotice.setBottomStyle();
        bottomSystemNotice.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        bottomSystemNotice.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        bottomSystemNotice.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        mOfficialnotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogS(0);
                getDataNoticePresenter().updateSystemSee();
                mViewNoticeOfficial.setVisibility(View.INVISIBLE);
            }
        });
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
        bar = setMyActivityBar(R.id.notice_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void createRefresh() {
        mySmartRefresh = new MySmartRefresh(mNoticeRefresh, mNoticeRefreshTgif, null);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.obtainNotice(getDataNoticePresenter().obtainNotice(false), gridLayoutManager, mNoticeRecyclerview, new NoticePageAPresenterImpl.OnReturn() {
                    @Override
                    public void onReturn() {
                        finishRRefresh(0);
                    }
                });
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getDataNoticePresenter().alreadySee();
    }
}
