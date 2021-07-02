package com.example.spb.view.fragment.homepage.postbarpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView, NewPostPageFPresenterImpl> implements INewPostPageFView {

    private GifImageView mNewpostpageRefreshTgif;
    private GifImageView mNewpostpageRefreshBgif;
    private SmartRefreshLayout mNewpostpageRefresh;
    private MySmartRefresh mySmartRefresh;
    public HomePage homePage;
    private RecyclerView mNewpostpageRecyclerview;
    private PostBarAdapter postBarAdapter;
    private TextView mNewpostpageRefreshTip;
    private RefreshThumb refreshThumb;
    private AddNewPostBar addNewPostBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePage = (HomePage) getActivity();
        refreshThumb = new RefreshThumb();
        addNewPostBar = new AddNewPostBar();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_bar),addNewPostBar);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb),refreshThumb);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

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
        mNewpostpageRefreshTgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_tgif);
        mNewpostpageRefreshBgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_bgif);
        mNewpostpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.newpostpage_refresh);
        mNewpostpageRecyclerview = (RecyclerView) view.findViewById(R.id.newpostpage_recyclerview);
        mNewpostpageRefreshTip = (TextView)view.findViewById(R.id.newpostpage_refresh_tip);
        mNewpostpageRecyclerview = MyListAnimation.setListAnimation(homePage,mNewpostpageRecyclerview);
        mySmartRefresh = new MySmartRefresh(mNewpostpageRefresh, mNewpostpageRefreshTgif, mNewpostpageRefreshBgif);
        createRefresh();
        initData();
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
                mPresenter.obtainNewBar(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
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
    }

    class RefreshThumb extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String pbId = intent.getStringExtra("key_two");
            postBarAdapter.refreshLikeItem(a,pbId);
        }
    }

    class AddNewPostBar extends BroadcastReceiver{
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
                case 3:
                    postBarAdapter.deleteBar(homePage.getDeletePbId());
                    break;
            }
        }
    }
}
