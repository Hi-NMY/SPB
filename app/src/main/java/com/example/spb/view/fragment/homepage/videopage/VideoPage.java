package com.example.spb.view.fragment.homepage.videopage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.PostBarAdapter;
import com.example.spb.adapter.PostVideoAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Comment;
import com.example.spb.presenter.impl.VideoPageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.Component.RefreshTipAnima;
import com.example.spb.view.fragment.homepage.postbarpage.NewPostPage;
import com.example.spb.view.inter.IVideoPageFView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.example.spb.view.littlefun.ScrollCalculatorHelper;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import io.rong.imageloader.utils.L;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class VideoPage extends BaseMVPFragment<IVideoPageFView, VideoPageFPresenterImpl> implements IVideoPageFView {

    private GifImageView mVideopageRefreshTgif;
    private RecyclerView mVideopageRecyclerview;
    private GifImageView mVideopageRefreshBgif;
    private SmartRefreshLayout mVideopageRefresh;
    private MySmartRefresh mySmartRefresh;
    private BaseMVPActivity baseMVPActivity;
    private AddVideoList addVideoList;
    private PostVideoAdapter postVideoAdapter;
    private ScrollCalculatorHelper scrollCalculatorHelper;
    private GridLayoutManager gridLayoutManager;
    boolean mFull = false;
    private RefreshThumb refreshThumb;
    private RefreshComment refreshComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseMVPActivity = (BaseMVPActivity)getActivity();
        addVideoList = new AddVideoList();
        refreshThumb = new RefreshThumb();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb),refreshThumb);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_new_video),addVideoList);
    }


    @Override
    protected VideoPageFPresenterImpl createPresenter() {
        return new VideoPageFPresenterImpl(getActivity());
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_video_page;
    }

    @Override
    protected void initFragView(View view) {
        mVideopageRefreshTgif = (GifImageView) view.findViewById(R.id.videopage_refresh_tgif);
        mVideopageRecyclerview = (RecyclerView) view.findViewById(R.id.videopage_recyclerview);
        mVideopageRefreshBgif = (GifImageView) view.findViewById(R.id.videopage_refresh_bgif);
        mVideopageRefresh = (SmartRefreshLayout) view.findViewById(R.id.videopage_refresh);
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(baseMVPActivity, R.anim.layout_animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        gridLayoutManager = new GridLayoutManager(baseMVPActivity, 1);
        mVideopageRecyclerview.setLayoutManager(gridLayoutManager);
        mVideopageRecyclerview.setLayoutAnimation(controller);
        //限定范围为屏幕一半的上下偏移180
        int playTop = CommonUtil.getScreenHeight(MyApplication.getContext()) / 2 - CommonUtil.dip2px(MyApplication.getContext(), 100);
        int playBottom = CommonUtil.getScreenHeight(MyApplication.getContext()) / 2 + CommonUtil.dip2px(MyApplication.getContext(), 100);
        //自定播放帮助类
        scrollCalculatorHelper = new ScrollCalculatorHelper(R.id.detail_player, playTop, playBottom);

        createDialog();
        createRefresh();
        initData();
    }

    @Override
    protected void initData() {
        postVideoAdapter = new PostVideoAdapter(getActivity(), baseMVPActivity.getDataPostBarPresenter().barVideos);
        mVideopageRecyclerview.setAdapter(postVideoAdapter);
        ((DefaultItemAnimator)mVideopageRecyclerview.getItemAnimator()).setSupportsChangeAnimations(false);
        mVideopageRecyclerview.startLayoutAnimation();
        mVideopageRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int firstVisibleItem, lastVisibleItem;
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                firstVisibleItem = gridLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();

                //这是滑动自动播放的代码
                if (!mFull) {
                    scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);
                }
            }
        });

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        mFull = newConfig.orientation == ActivityInfo.SCREEN_ORIENTATION_USER;

    }

    @Override
    public void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        GSYVideoManager.onResume();
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

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
        mySmartRefresh = new MySmartRefresh(mVideopageRefresh,mVideopageRefreshTgif,mVideopageRefreshBgif);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                baseMVPActivity.getDataPostBarPresenter().obtainUserVideo(true);
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                baseMVPActivity.getDataPostBarPresenter().obtainUserVideo(false);
            }
        });
    }

    @Override
    public void finishRRefresh(int num) {
        switch (num) {
            case FINISH_REFRESH:
                mySmartRefresh.finishMyRefresh();
                break;
            case FINISH_MORE:
                mySmartRefresh.finishMyLoadMore();
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        SpbBroadcast.destroyBrc(addVideoList);
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(refreshComment);
    }

    class RefreshThumb extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            String pbId = intent.getStringExtra("key_two");
            postVideoAdapter.refreshLikeItem(a,pbId);
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
                    postVideoAdapter.refreshNowCommentItem(comments.size());
                    break;
                case 1:
                    postVideoAdapter.refreshCommentItem(Integer.valueOf(num));
                    break;
            }
        }
    }

    class AddVideoList extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            List<Bar> moreBars = (List<Bar>) intent.getSerializableExtra("key_two");
            switch (a) {
                case 1:
                    if (postVideoAdapter != null) {
                        postVideoAdapter.addMorePostBar(moreBars);
                    }
                    finishRRefresh(FINISH_MORE);
                    break;
                case 0:
                    initData();
                    finishRRefresh(FINISH_REFRESH);
                    break;
                case 3:
                    postVideoAdapter.deleteBar(baseMVPActivity.getDeletePbId());
                    break;
            }
        }
    }
}
