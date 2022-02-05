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
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.impl.FollowedFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.inter.IFollowedFView;
import com.example.spb.view.utils.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class Followed extends BaseMVPFragment<IFollowedFView, FollowedFPresenterImpl> implements IFollowedFView {

    private AttentionUserPage attentionUserPage;
    private GifImageView mFollowedRefreshTgif;
    private RecyclerView mFollowedRecyclerview;
    private SmartRefreshLayout mFollowedRefresh;
    private MySmartRefresh mySmartRefresh;
    private RefreshFollowedList refreshFollowedList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        attentionUserPage = (AttentionUserPage)getActivity();
        refreshFollowedList = new RefreshFollowedList();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_Followed),refreshFollowedList);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected FollowedFPresenterImpl createPresenter() {
        return new FollowedFPresenterImpl(attentionUserPage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_followed;
    }

    @Override
    protected void initFragView(View view) {
        mFollowedRefreshTgif = (GifImageView)view.findViewById(R.id.followed_refresh_tgif);
        mFollowedRecyclerview = (RecyclerView)view.findViewById(R.id.followed_recyclerview);
        mFollowedRefresh = (SmartRefreshLayout)view.findViewById(R.id.followed_refresh);
        mFollowedRecyclerview = MyListAnimation.setListAnimation(attentionUserPage,mFollowedRecyclerview);
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
        mySmartRefresh = new MySmartRefresh(mFollowedRefresh,mFollowedRefreshTgif,null);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                mPresenter.addFollowedList(attentionUserPage.getDataUserMsgPresenter().getUser_account());
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
        SpbBroadcast.destroyBrc(refreshFollowedList);
    }

    class RefreshFollowedList extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            List<UserDto> userDtos = (List<UserDto>) intent.getSerializableExtra("key_two");
            if (userDtos != null && userDtos.size() != 0){
                mPresenter.addList(userDtos,mFollowedRecyclerview);
            }
            finishRRefresh(0);
        }
    }
}
