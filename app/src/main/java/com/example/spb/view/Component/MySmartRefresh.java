package com.example.spb.view.Component;

import androidx.annotation.NonNull;
import com.example.spb.view.littlefun.GIFShow;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import pl.droidsonroids.gif.GifImageView;

public class MySmartRefresh {

    private SmartRefreshLayout refreshLayout;
    private GifImageView tGif;
    private GifImageView bGif;
    private GIFShow tgifShow;
    private GIFShow bgifShow;

    public MySmartRefresh(SmartRefreshLayout smartRefreshLayout, GifImageView topGif,GifImageView bottomGif) {
        this.refreshLayout = smartRefreshLayout;
        if (topGif != null){
            this.tGif = topGif;
            tgifShow = new GIFShow(tGif);
        }
        if (bottomGif != null){
            this.bGif = bottomGif;
            bgifShow = new GIFShow(bGif);
        }
    }

    public void setMyRefreshListener(MyRefreshListener myRefreshListener){
        refreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (bgifShow != null){
                    bgifShow.startGif();
                }
                myRefreshListener.onLoadMore(refreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                if (tgifShow != null){
                    tgifShow.startGif();
                }
                myRefreshListener.onRefresh(refreshLayout);
            }
        });
    }

    public void finishMyRefresh(){
        if (tgifShow != null){
            tgifShow.resetGif();
        }
        refreshLayout.finishRefresh();
    }

    public void finishMyLoadMore(){
        if (bgifShow != null){
            bgifShow.resetGif();
        }
        refreshLayout.finishLoadMore();
    }

    public interface MyRefreshListener{
        void onRefresh(@NonNull RefreshLayout refreshLayout);
        void onLoadMore(@NonNull RefreshLayout refreshLayout);
    }
}
