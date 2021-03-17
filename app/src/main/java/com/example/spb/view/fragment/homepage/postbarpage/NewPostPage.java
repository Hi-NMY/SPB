package com.example.spb.view.fragment.homepage.postbarpage;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.NewPostPageFPresenterImpl;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.inter.INewPostPageFView;
import com.example.spb.view.littlefun.GIFShow;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

public class NewPostPage extends BaseMVPFragment<INewPostPageFView, NewPostPageFPresenterImpl> implements INewPostPageFView {

    private GifImageView mGifImageView;
    private ImageView mItemPostbarLikeImg;
    private GifImageView mNewpostpageRefreshTgif;
    private GifImageView mNewpostpageRefreshBgif;
    private SmartRefreshLayout mNewpostpageRefresh;
    private MySmartRefresh mySmartRefresh;
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
        return new NewPostPageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_postbar_new_post_page;
    }

    @Override
    protected void initFragView(View view) {
        mItemPostbarLikeImg = (ImageView) view.findViewById(R.id.item_postbar_like_img);
        mGifImageView = (GifImageView) view.findViewById(R.id.item_postbar_voice);
        mNewpostpageRefreshTgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_tgif);
        mNewpostpageRefreshBgif = (GifImageView) view.findViewById(R.id.newpostpage_refresh_bgif);
        mNewpostpageRefresh = (SmartRefreshLayout) view.findViewById(R.id.newpostpage_refresh);
        mNewpostpageRefreshTip = (TextView)view.findViewById(R.id.newpostpage_refresh_tip);
        mySmartRefresh = new MySmartRefresh(mNewpostpageRefresh, mNewpostpageRefreshTgif, mNewpostpageRefreshBgif);
        GIFShow gifShow = new GIFShow(mGifImageView);
        mGifImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gifShow.startGif();
            }
        });
        mItemPostbarLikeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySmartRefresh.finishMyRefresh();
                mySmartRefresh.finishMyLoadMore();
                gifShow.stopGif();
                RefreshTipAnima.tipAnimation(mNewpostpageRefreshTip, 12);
                ThumbAnima.thumbAnimation(mItemPostbarLikeImg);
            }
        });
        createRefresh();
    }

    @Override
    protected void initData() {

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

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void finishRefresh(int num) {

    }
}
