package com.example.spb.view.fragment.personalspace;

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
import com.example.spb.presenter.impl.PersonalPostBarFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.MySmartRefresh;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IPersonalPostBarFView;
import com.example.spb.view.utils.MyListAnimation;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.List;

public class PersonalPostBar extends BaseMVPFragment<IPersonalPostBarFView, PersonalPostBarFPresenterImpl> implements IPersonalPostBarFView {

    private RefreshThumb refreshThumb;
    private AddPersonalBar addPersonalBar;
    private RecyclerView mPersonalBarRecyclerview;
    private GifImageView mPersonalBarMoreGif;
    private SmartRefreshLayout mPersonalBarRefresh;
    private MySmartRefresh mySmartRefresh;
    private PersonalSpacePage personalSpacePage;
    private RefreshComment refreshComment;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        personalSpacePage = (PersonalSpacePage)getActivity();
        refreshThumb = new RefreshThumb();
        addPersonalBar = new AddPersonalBar();
        refreshComment = new RefreshComment();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_comment),refreshComment);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_personal_bar), addPersonalBar);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), refreshThumb);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    protected PersonalPostBarFPresenterImpl createPresenter() {
        return new PersonalPostBarFPresenterImpl(personalSpacePage);
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_personalspace_personal_post_bar;
    }

    @Override
    protected void initFragView(View view) {
        mPersonalBarRecyclerview = (RecyclerView)view.findViewById(R.id.personal_bar_recyclerview);
        mPersonalBarMoreGif = (GifImageView)view.findViewById(R.id.personal_bar_more_gif);
        mPersonalBarRefresh = (SmartRefreshLayout)view.findViewById(R.id.personal_bar_refresh);
        mPersonalBarRecyclerview = MyListAnimation.setListAnimation(personalSpacePage,mPersonalBarRecyclerview);
        createRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (personalSpacePage.getEasyVoice() != null){
            personalSpacePage.getEasyVoice().stopPlayer();
            personalSpacePage.setEasyVoice(null);
            mPresenter.stopvoice();
        }
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
        mySmartRefresh = new MySmartRefresh(mPersonalBarRefresh,null,mPersonalBarMoreGif);
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {

            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                if (personalSpacePage.getEasyVoice() != null){
                    personalSpacePage.getEasyVoice().stopPlayer();
                    mPresenter.stopvoice();
                }
                mPresenter.obtainMorePersonalBar(personalSpacePage.getDataUserMsgPresenter().getUser_account());
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
        SpbBroadcast.destroyBrc(refreshThumb);
        SpbBroadcast.destroyBrc(addPersonalBar);
        SpbBroadcast.destroyBrc(refreshComment);
    }

    class AddPersonalBar extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one",0);
            List<Bar> bars = (List<Bar>) intent.getSerializableExtra("key_three");
            String account = intent.getStringExtra("key_two");
            if(account.equals(personalSpacePage.userAccount)){
                if (bars != null && bars.size() != 0){
                    mPresenter.setCacheDate(bars.get(bars.size() - 1).getPb_date());
                }
                switch (a){
                    case 1:
                        finishRRefresh(0);
                        mPresenter.addPersonalBarList(bars,mPersonalBarRecyclerview,false);
                        break;
                    case 0:
                        mPresenter.addPersonalBarList(bars,mPersonalBarRecyclerview,true);
                        break;
                    case 3:
                        mPresenter.deleteBarData(personalSpacePage.getDeletePbId());
                        break;
                }
            }
        }
    }

    class RefreshThumb extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
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
                    mPresenter.refreshComment(Integer.valueOf(num));
                    break;
            }
        }
    }
}
