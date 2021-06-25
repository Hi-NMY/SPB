package com.example.spb.view.fragment.topicbarpage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.impl.NewTopicBarFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.INewTopicBarFView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class NewTopicBar extends BaseMVPFragment<INewTopicBarFView, NewTopicBarFPresenterImpl> implements INewTopicBarFView {

    private AddNewTopicBar addNewTopicBar;
    private RecyclerView mNewtopicbarRecyclerview;
    private GifImageView mNewtopicbarMoreGif;
    private SmartRefreshLayout mNewtopicbarRefresh;
    private MySmartRefresh mySmartRefresh;
    private TopicBarPage topicBarPage;
    private RefreshThumb refreshThumb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicBarPage = (TopicBarPage) getActivity();
        addNewTopicBar = new AddNewTopicBar();
        refreshThumb = new RefreshThumb();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), refreshThumb);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicbar), addNewTopicBar);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected NewTopicBarFPresenterImpl createPresenter() {
        return new NewTopicBarFPresenterImpl(topicBarPage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_topicbar_new_topic_bar;
    }

    @Override
    protected void initFragView(View view) {
        mNewtopicbarRecyclerview = (RecyclerView)view.findViewById(R.id.newtopicbar_recyclerview);
        mNewtopicbarMoreGif = (GifImageView)view.findViewById(R.id.newtopicbar_more_gif);
        mNewtopicbarRefresh = (SmartRefreshLayout)view.findViewById(R.id.newtopicbar_refresh);
        mNewtopicbarRecyclerview = MyListAnimation.setListAnimation(topicBarPage,mNewtopicbarRecyclerview);
        mySmartRefresh = new MySmartRefresh(mNewtopicbarRefresh,null,mNewtopicbarMoreGif);
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
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(addNewTopicBar);
    }

    class AddNewTopicBar extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String name = intent.getStringExtra("key_two");
            List<Bar> bars = (List<Bar>) intent.getSerializableExtra("key_three");
            mPresenter.settName(name);
            if (a == 0){
                mPresenter.addNewTopicList(bars,mNewtopicbarRecyclerview,true);
            }else {
                mPresenter.addNewTopicList(bars,mNewtopicbarRecyclerview,false);
            }
        }
    }

    class RefreshThumb extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String pbId = intent.getStringExtra("key_two");
            mPresenter.refreshThumb(a,pbId);
        }
    }
}
