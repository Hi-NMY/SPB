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
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;
import com.example.spb.view.utils.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView, NewPostPageFPresenterImpl> implements INewPostPageFView {

    private MySmartRefresh mySmartRefresh;
    public HomePage homePage;
    private RecyclerView mNewpostpageRecyclerview;
    private PostBarAdapter postBarAdapter;
    private TextView mNewpostpageRefreshTip;
    private RefreshThumb refreshThumb;
    private AddNewPostBar addNewPostBar;
    private RefreshComment refreshComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePage = (HomePage) getActivity();
        refreshThumb = new RefreshThumb();
        addNewPostBar = new AddNewPostBar();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar),addNewPostBar);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb),refreshThumb);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
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
        if (BaseMVPActivity.getEasyVoice() != null){
            BaseMVPActivity.getEasyVoice().stopPlayer();
            BaseMVPActivity.setEasyVoice(null);
            postBarAdapter.refreshNoewVoice(-1);
        }
    }

    @Override
    protected NewPostPageFPresenterImpl createPresenter() {
        return new NewPostPageFPresenterImpl(homePage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_new_post_page;
    }

    @Override
    protected void initFragView(View view) {
        GifImageView mNewpostpageRefreshTgif =  view.findViewById(R.id.newpostpage_refresh_tgif);
        GifImageView mNewpostpageRefreshBgif =  view.findViewById(R.id.newpostpage_refresh_bgif);
        SmartRefreshLayout mNewpostpageRefresh =  view.findViewById(R.id.newpostpage_refresh);
        mNewpostpageRecyclerview =  view.findViewById(R.id.newpostpage_recyclerview);
        mNewpostpageRefreshTip = view.findViewById(R.id.newpostpage_refresh_tip);
        MyListAnimation.setListAnimation(homePage, mNewpostpageRecyclerview);
        mySmartRefresh = new MySmartRefresh(mNewpostpageRefresh, mNewpostpageRefreshTgif, mNewpostpageRefreshBgif);
        createRefresh();
    }

    @Override
    protected void initData() {
        postBarAdapter = new PostBarAdapter(homePage, homePage.getDataPostBarPresenter().bars);
        mNewpostpageRecyclerview.setAdapter(postBarAdapter);
        ((DefaultItemAnimator)mNewpostpageRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        mNewpostpageRecyclerview.startLayoutAnimation();
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
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (BaseMVPActivity.getEasyVoice() != null){
                    BaseMVPActivity.getEasyVoice().stopPlayer();
                    postBarAdapter.refreshNoewVoice(-1);
                }
                mPresenter.obtainNewBar(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (BaseMVPActivity.getEasyVoice() != null){
                    BaseMVPActivity.getEasyVoice().stopPlayer();
                    postBarAdapter.refreshNoewVoice(-1);
                }
                mPresenter.obtainNewBar(false);
            }
        });
    }

    @Override
    public void finishRRefresh(int num) {
        switch (num) {
            case FINISH_REFRESH:
                mySmartRefresh.finishMyRefresh();
                RefreshTipAnima.tipAnimation(mNewpostpageRefreshTip,0);
                break;
            case FINISH_MORE:
                mySmartRefresh.finishMyLoadMore();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(addNewPostBar);
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
                    postBarAdapter.refreshCommentItem(Integer.parseInt(num));
                    break;
            }
        }
    }

    class AddNewPostBar extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            List<Bar> moreBars = (List<Bar>) intent.getSerializableExtra("key_two");
            switch (a){
                case 1:
                    finishRRefresh(FINISH_MORE);
                    if (postBarAdapter != null){
                        postBarAdapter.addMorePostBar(moreBars);
                    }
                    break;
                case 0:
                    finishRRefresh(FINISH_REFRESH);
                    initData();
                    break;
                case 3:
                    postBarAdapter.deleteBar(BaseMVPActivity.getDeletePbId());
                    break;
            }
        }
    }
}
