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
import com.example.spb.entity.Comment;
import com.example.spb.presenter.impl.HotTopicBarFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.fragment.homepage.postbarpage.AttentionPage;
import com.example.spb.view.inter.IHotTopicBarFView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class HotTopicBar extends BaseMVPFragment<IHotTopicBarFView, HotTopicBarFPresenterImpl> implements IHotTopicBarFView {

    private AddHotTopicBar addHotTopicBar;
    private RecyclerView mHottopicbarRecyclerview;
    private GifImageView mHottopicbarMoreGif;
    private SmartRefreshLayout mHottopicbarRefresh;
    private MySmartRefresh mySmartRefresh;
    private TopicBarPage topicBarPage;
    private RefreshThumb refreshThumb;
    private RemoveVoice removeVoice;
    private RefreshComment refreshComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicBarPage = (TopicBarPage) getActivity();
        addHotTopicBar = new AddHotTopicBar();
        refreshThumb = new RefreshThumb();
        removeVoice = new RemoveVoice();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_stop_voice),removeVoice);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), refreshThumb);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_hottopicbar), addHotTopicBar);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected HotTopicBarFPresenterImpl createPresenter() {
        return new HotTopicBarFPresenterImpl(topicBarPage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_topicbar_hot_topic_bar;
    }

    @Override
    protected void initFragView(View view) {
        mHottopicbarRecyclerview = (RecyclerView)view.findViewById(R.id.hottopicbar_recyclerview);
        mHottopicbarMoreGif = (GifImageView)view.findViewById(R.id.hottopicbar_more_gif);
        mHottopicbarRefresh = (SmartRefreshLayout)view.findViewById(R.id.hottopicbar_refresh);
        mHottopicbarRecyclerview = MyListAnimation.setListAnimation(topicBarPage,mHottopicbarRecyclerview);
        mySmartRefresh = new MySmartRefresh(mHottopicbarRefresh,null,mHottopicbarMoreGif);
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
                mPresenter.obtainMoreHotTopicBar();
            }
        });
        mySmartRefresh.closeRefresh();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyLoadMore();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(addHotTopicBar);
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(removeVoice);
        SpbBroadcast.destroyBrc(refreshComment);
    }

    class AddHotTopicBar extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String name = intent.getStringExtra("key_two");
            List<Bar> bars = (List<Bar>) intent.getSerializableExtra("key_three");
            mPresenter.settName(name);
            if (bars != null && bars.size() != 0){
                mPresenter.setCacheNum(bars.get(bars.size() - 1).getPb_thumb_num());
            }
            switch (a){
                case 1:
                    mPresenter.addHotTopicList(bars,mHottopicbarRecyclerview,false);
                    finishRRefresh(0);
                    break;
                case 0:
                    mPresenter.addHotTopicList(bars,mHottopicbarRecyclerview,true);
                    break;
                case 3:
                    mPresenter.deleteBarData(topicBarPage.getDeletePbId());
                    break;
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

    class RemoveVoice extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.stopVoice();
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
                    mPresenter.refreshNowComment(comments.size());
                    break;
                case 1:
                    mPresenter.refreshComment(Integer.valueOf(num));
                    break;
            }
        }
    }
}
