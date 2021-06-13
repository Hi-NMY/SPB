package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.activity.HomePage;
import com.example.spb.view.inter.INewPostPageFView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView, NewPostPageFPresenterImpl> implements INewPostPageFView {

    private GifImageView mNewpostpageRefreshTgif;
    private GifImageView mNewpostpageRefreshBgif;
    private SmartRefreshLayout mNewpostpageRefresh;
    private MySmartRefresh mySmartRefresh;
    public HomePage homePage;
    private RecyclerView mNewpostpageRecyclerview;
    private PostBarAdapter postBarAdapter;
    private GridLayoutManager gridLayoutManager;
    private TextView mNewpostpageRefreshTip;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        return new NewPostPageFPresenterImpl(getActivity());
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_new_post_page;
    }

    @Override
    protected void initFragView(View view) {
        homePage = (HomePage) getActivity();
        mNewpostpageRefreshTgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_tgif);
        mNewpostpageRefreshBgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_bgif);
        mNewpostpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.newpostpage_refresh);
        mNewpostpageRecyclerview = (RecyclerView) view.findViewById(R.id.newpostpage_recyclerview);
        mNewpostpageRefreshTip = (TextView)view.findViewById(R.id.newpostpage_refresh_tip);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(homePage, R.anim.layout_animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        gridLayoutManager = new GridLayoutManager(homePage, 1);
        mNewpostpageRecyclerview.setLayoutManager(gridLayoutManager);
        mNewpostpageRecyclerview.setLayoutAnimation(controller);
        mySmartRefresh = new MySmartRefresh(mNewpostpageRefresh, mNewpostpageRefreshTgif, mNewpostpageRefreshBgif);
        createRefresh();
        initData();
    }

    @Override
    protected void initData() {
        postBarAdapter = new PostBarAdapter(homePage, homePage.getDataPostBarPresenter().bars);
        mNewpostpageRecyclerview.setAdapter(postBarAdapter);
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
                mPresenter.obtainNewBar(homePage, new NewPostPageFPresenterImpl.OnReturn() {
                    @Override
                    public void onReturn() {
                        finishRRefresh(FINISH_REFRESH);
                        initData();
                    }
                });
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

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
}
