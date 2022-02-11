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
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.presenter.impl.VideoTopicBarFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.inter.IVideoTopicBarFView;
import com.example.spb.view.utils.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class VideoTopicBar extends BaseMVPFragment<IVideoTopicBarFView, VideoTopicBarFPresenterImpl> implements IVideoTopicBarFView {

    private RecyclerView mNewtopicvideoRecyclerview;
    private GifImageView mNewtopicvideoMoreGif;
    private SmartRefreshLayout mNewtopicvideoRefresh;
    private TopicBarPage topicBarPage;
    private AddNewTopicVideo addNewTopicVideo;
    private RefreshThumb refreshThumb;
    private RefreshComment refreshComment;
    private MySmartRefresh mySmartRefresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicBarPage = (TopicBarPage) getActivity();
        addNewTopicVideo = new AddNewTopicVideo();
        refreshThumb = new RefreshThumb();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), refreshThumb);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_newtopicvideo), addNewTopicVideo);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected VideoTopicBarFPresenterImpl createPresenter() {
        return new VideoTopicBarFPresenterImpl(getActivity());
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_topicbar_video_topic_bar;
    }

    @Override
    protected void initFragView(View view) {
        mNewtopicvideoRecyclerview = view.findViewById(R.id.newtopicvideo_recyclerview);
        mNewtopicvideoMoreGif = view.findViewById(R.id.newtopicvideo_more_gif);
        mNewtopicvideoRefresh = view.findViewById(R.id.newtopicvideo_refresh);
        MyListAnimation.setListAnimation(topicBarPage, mNewtopicvideoRecyclerview);
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
        mySmartRefresh = new MySmartRefresh(mNewtopicvideoRefresh,null,mNewtopicvideoMoreGif);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                mPresenter.obtainNewTopicBar();
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
        SpbBroadcast.destroyBrc(addNewTopicVideo);
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(refreshComment);
    }

    class AddNewTopicVideo extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String name = intent.getStringExtra("key_two");
            List<Bar> bars = (List<Bar>) intent.getSerializableExtra("key_three");
            mPresenter.settName(name);
            if (bars != null && bars.size() != 0){
                mPresenter.setCacheDate(bars.get(bars.size() - 1).getPb_date());
            }
            switch (a){
                case 1:
                    mPresenter.addNewTopicList(bars,mNewtopicvideoRecyclerview,false);
                    finishRRefresh(0);
                    break;
                case 0:
                    mPresenter.addNewTopicList(bars,mNewtopicvideoRecyclerview,true);
                    break;
                case 3:
                    mPresenter.deleteBarData(BaseMVPActivity.getDeletePbId());
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
                    mPresenter.refreshComment(Integer.parseInt(num));
                    break;
            }
        }
    }
}
