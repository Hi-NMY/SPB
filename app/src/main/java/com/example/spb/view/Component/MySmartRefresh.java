package com.example.spb.view.Component;

import androidx.annotation.NonNull;
import com.example.spb.view.utils.GIFShow;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnRefreshLoadMoreListener;
import pl.droidsonroids.gif.GifImageView;

public class MySmartRefresh {

    private final SmartRefreshLayout refreshLayout;
    private GIFShow tgifShow;
    private GIFShow bgifShow;

    public MySmartRefresh(SmartRefreshLayout smartRefreshLayout, GifImageView topGif,GifImageView bottomGif) {
        this.refreshLayout = smartRefreshLayout;
        refreshLayout.setEnableAutoLoadMore(false);
        if (topGif != null){
            tgifShow = new GIFShow(topGif);
        }
        if (bottomGif != null){
            bgifShow = new GIFShow(bottomGif);
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

    public void closeRefresh(){
        refreshLayout.setEnableRefresh(false);
    }

    public void closeLoadMore(){
        refreshLayout.setEnableLoadMore(false);
    }

    public void autoRefresh(){
          refreshLayout.autoRefresh();
    }

    public interface MyRefreshListener{
        void onRefresh(@NonNull RefreshLayout refreshLayout);
        void onLoadMore(@NonNull RefreshLayout refreshLayout);
    }
}
