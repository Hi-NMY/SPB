package com.example.spb.view.fragment.attentionuser;

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
import com.example.spb.entity.User;
import com.example.spb.presenter.impl.FollowFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowFView;
import com.example.spb.view.utils.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class Follow extends BaseMVPFragment<IFollowFView, FollowFPresenterImpl> implements IFollowFView {

    private GifImageView mFollowRefreshTgif;
    private RecyclerView mFollowRecyclerview;
    private SmartRefreshLayout mFollowRefresh;
    private MySmartRefresh mySmartRefresh;
    private RefreshFollowList refreshFollowList;
    private RefreshFollow refreshFollow;
    private AttentionUserPage attentionUserPage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attentionUserPage = (AttentionUserPage)getActivity();
        refreshFollowList = new RefreshFollowList();
        refreshFollow = new RefreshFollow();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow),refreshFollow);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Follow),refreshFollowList);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected FollowFPresenterImpl createPresenter() {
        return new FollowFPresenterImpl(attentionUserPage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_follow;
    }

    @Override
    protected void initFragView(View view) {
        mFollowRefreshTgif = (GifImageView)view.findViewById(R.id.follow_refresh_tgif);
        mFollowRecyclerview = (RecyclerView)view.findViewById(R.id.follow_recyclerview);
        mFollowRefresh = (SmartRefreshLayout)view.findViewById(R.id.follow_refresh);
        mFollowRecyclerview = MyListAnimation.setListAnimation(attentionUserPage,mFollowRecyclerview);
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
        mySmartRefresh = new MySmartRefresh(mFollowRefresh,mFollowRefreshTgif,null);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.addFollowList(attentionUserPage.getDataUserMsgPresenter().getUser_account());
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshFollow);
        SpbBroadcast.destroyBrc(refreshFollowList);
    }

    class RefreshFollowList extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            List<User> users = (List<User>) intent.getSerializableExtra("key_two");
            if (users != null){
                mPresenter.addList(users,mFollowRecyclerview);
            }
            finishRRefresh(0);
        }
    }

    class RefreshFollow extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            mPresenter.addFollowList(attentionUserPage.getDataUserMsgPresenter().getUser_account());
        }
    }
}
