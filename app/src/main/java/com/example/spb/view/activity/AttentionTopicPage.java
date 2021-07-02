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
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.AttentionTopicPageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.presenter.otherimpl.DataAttentionTopicPresenter;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.inter.IAttentionTopicPageAView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.gyf.immersionbar.ImmersionBar;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class AttentionTopicPage extends BaseMVPActivity<IAttentionTopicPageAView, AttentionTopicPageAPresenterImpl>
        implements IAttentionTopicPageAView {

    private FragmentSpbAvtivityBar bar;
    private GifImageView mAttentiontopicRefreshTgif;
    private RecyclerView mAttentiontopicRecyclerview;
    private SmartRefreshLayout mAttentiontopicRefresh;
    private MySmartRefresh mySmartRefresh;
    private RefreshAttTopic refreshAttTopic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attention_topic_page);
        refreshAttTopic = new RefreshAttTopic();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_reAttTopic),refreshAttTopic);
        initActView();
    }

    @Override
    protected AttentionTopicPageAPresenterImpl createPresenter() {
        return new AttentionTopicPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        mAttentiontopicRefreshTgif = (GifImageView) findViewById(R.id.attentiontopic_refresh_tgif);
        mAttentiontopicRecyclerview = (RecyclerView) findViewById(R.id.attentiontopic_recyclerview);
        mAttentiontopicRefresh = (SmartRefreshLayout) findViewById(R.id.attentiontopic_refresh);
        mAttentiontopicRecyclerview = MyListAnimation.setListAnimation(this,mAttentiontopicRecyclerview);
        mySmartRefresh = new MySmartRefresh(mAttentiontopicRefresh,mAttentiontopicRefreshTgif,null);
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
        createRefresh();
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
        bar = setMyActivityBar(R.id.attentiontopic_actbar);
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
                getDataAttentionTopicPresenter().initDate(new DataAttentionTopicPresenter.ReturnTopic() {
                    @Override
                    public void onReturn() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mPresenter.addAttentionTopic(getDataAttentionTopicPresenter().attentionTopicList,mAttentiontopicRecyclerview);
                                finishRRefresh(0);
                            }
                        });
                    }
                });
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mySmartRefresh.autoRefresh();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshAttTopic);
    }

    class RefreshAttTopic extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getIntExtra("key_one",0) == 0){
                getDataAttentionTopicPresenter().removeAttentionTopic(mPresenter.addAttentionAccount
                        (getDataUserMsgPresenter().getUser_account(), (Topic) intent.getSerializableExtra("key_two")), new DataAttentionTopicPresenter.ReturnTopic() {
                    @Override
                    public void onReturn() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(100);
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_topic), 0, null);
                                    SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_reUserPage_Datanum),0,null);
                                    mPresenter.refreshAdapter();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                });
            }else {
                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_reUserPage_Datanum),0,null);
                mPresenter.refreshAdapter();
            }
        }
    }
}
