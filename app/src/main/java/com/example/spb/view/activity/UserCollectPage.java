package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.impl.UserCollectPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IUserCollectPageAView;
import com.example.spb.view.utils.MyListAnimation;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class UserCollectPage extends BaseMVPActivity<IUserCollectPageAView, UserCollectPageAPresenterImpl>
        implements IUserCollectPageAView {

    private RecyclerView mUsercollectRecyclerview;
    private MySmartRefresh mySmartRefresh;
    private AddCollectBarData addCollectBarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_collect_page);
        addCollectBarData = new AddCollectBarData();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_CollectBar), addCollectBarData);
        initActView();
    }

    @Override
    protected UserCollectPageAPresenterImpl createPresenter() {
        return new UserCollectPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        GifImageView mUsercollectRefreshTgif = findViewById(R.id.usercollect_refresh_tgif);
        mUsercollectRecyclerview = findViewById(R.id.usercollect_recyclerview);
        SmartRefreshLayout mUsercollectRefresh = findViewById(R.id.usercollect_refresh);
        MyListAnimation.setListAnimation(this, mUsercollectRecyclerview);
        mySmartRefresh = new MySmartRefresh(mUsercollectRefresh, mUsercollectRefreshTgif, null);
        createRefresh();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {

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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.usercollect_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    public void createRefresh() {
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDataCollectBarPresenter().obtainCollectBar(getDataUserMsgPresenter().getUser_account());
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mySmartRefresh.autoRefresh();
        mySmartRefresh.closeLoadMore();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(addCollectBarData);
    }

    class AddCollectBarData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            List<Bar> cbs = (List<Bar>) intent.getSerializableExtra("key_two");
            if (a == 0) {
                mPresenter.addCollectData(cbs, mUsercollectRecyclerview);
                finishRRefresh(0);
            } else {
                mPresenter.removeCollect(cbs.get(0).getPb_one_id());
            }
        }
    }
}
