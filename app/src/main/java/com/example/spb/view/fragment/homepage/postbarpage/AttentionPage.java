package com.example.spb.view.fragment.homepage.postbarpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.presenter.impl.AttentionPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.IAttentionPageFView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class AttentionPage extends BaseMVPFragment<IAttentionPageFView, AttentionPageFPresenterImpl> implements IAttentionPageFView {

    private GifImageView mAttentionpageRefreshTgif;
    private RecyclerView mAttentionpageRecyclerview;
    private TextView mAttentionpageRefreshTip;
    private GifImageView mAttentionpageRefreshBgif;
    private SmartRefreshLayout mAttentionpageRefresh;
    private MySmartRefresh mySmartRefresh;
    private HomePage homePage;
    private RefreshFollowUserBar refreshFollowUserBar;
    private RefreshThumb refreshThumb;
    private DataRefresh dataRefresh;
    private PostBarAdapter postBarAdapter;
    private RefreshComment refreshComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePage = (HomePage)getActivity();
        refreshFollowUserBar = new RefreshFollowUserBar();
        refreshThumb = new RefreshThumb();
        dataRefresh = new DataRefresh();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow),dataRefresh);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb),refreshThumb);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_FollowUserBar),refreshFollowUserBar);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (homePage.getEasyVoice() != null){
            homePage.getEasyVoice().stopPlayer();
            homePage.setEasyVoice(null);
            postBarAdapter.refreshNoewVoice(-1);
        }
    }

    @Override
    protected AttentionPageFPresenterImpl createPresenter() {
        return new AttentionPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_attention_page;
    }

    @Override
    protected void initFragView(View view) {
        mAttentionpageRefreshTgif = (GifImageView)view.findViewById(R.id.attentionpage_refresh_tgif);
        mAttentionpageRecyclerview = (RecyclerView) view.findViewById(R.id.attentionpage_recyclerview);
        mAttentionpageRefreshTip  = (TextView) view.findViewById(R.id.attentionpage_refresh_tip);
        mAttentionpageRefreshBgif = (GifImageView)view.findViewById(R.id.attentionpage_refresh_bgif);
        mAttentionpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.attentionpage_refresh);
        mAttentionpageRecyclerview = MyListAnimation.setListAnimation(homePage,mAttentionpageRecyclerview);
        createRefresh();
    }

    @Override
    protected void initData() {
        postBarAdapter = new PostBarAdapter(homePage, homePage.getDataPostBarPresenter().followbars);
        mAttentionpageRecyclerview.setAdapter(postBarAdapter);
        ((DefaultItemAnimator)mAttentionpageRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        mAttentionpageRecyclerview.startLayoutAnimation();
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

    }

    @Override
    public void setActivityBar() {

    }

    @Override
    public void createRefresh() {
        mySmartRefresh = new MySmartRefresh(mAttentionpageRefresh,mAttentionpageRefreshTgif,mAttentionpageRefreshBgif);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (homePage.getEasyVoice() != null){
                    homePage.getEasyVoice().stopPlayer();
                    postBarAdapter.refreshNoewVoice(-1);
                }
                homePage.getDataPostBarPresenter().obtainFollowUserBar(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (homePage.getEasyVoice() != null){
                    homePage.getEasyVoice().stopPlayer();
                    postBarAdapter.refreshNoewVoice(-1);
                }
                homePage.getDataPostBarPresenter().obtainFollowUserBar(false);
            }
        });
    }

    @Override
    public void finishRRefresh(int num) {
        switch (num){
            case FINISH_REFRESH:
                mySmartRefresh.finishMyRefresh();
                RefreshTipAnima.tipAnimation(mAttentionpageRefreshTip,0);
                break;
            case FINISH_MORE:
                mySmartRefresh.finishMyLoadMore();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshFollowUserBar);
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(dataRefresh);
        SpbBroadcast.destroyBrc(refreshComment);
    }

    class RefreshThumb extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String pbId = intent.getStringExtra("key_two");
            postBarAdapter.refreshLikeItem(a,pbId);
        }
    }

    class RefreshComment extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",-1);
            String num = intent.getStringExtra("key_two");
            switch (a){
                case 0:
                    List<Comment> comments = (List<Comment>)intent.getSerializableExtra("key_three");
                    postBarAdapter.refreshNowCommentItem(comments.size());
                    break;
                case 1:
                    postBarAdapter.refreshCommentItem(Integer.valueOf(num));
                    break;
            }
        }
    }

    class RefreshFollowUserBar extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            List<Bar> moreBars = (List<Bar>) intent.getSerializableExtra("key_two");
            switch (a){
                case 1:
                    if (postBarAdapter != null){
                        postBarAdapter.addMorePostBar(moreBars);
                    }
                    finishRRefresh(FINISH_MORE);
                    break;
                case 0:
                    initData();
                    finishRRefresh(FINISH_REFRESH);
                    break;
            }
        }
    }

    class DataRefresh extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            homePage.getDataPostBarPresenter().obtainFollowUserBar(true);
        }
    }
}
