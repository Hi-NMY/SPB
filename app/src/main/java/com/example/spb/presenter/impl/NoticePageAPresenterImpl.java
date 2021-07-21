package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.NoticeAdapter;
import com.example.spb.adapter.NoticeSystemAdapter;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Notice;
import com.example.spb.presenter.inter.INoticePageAPresenter;
import com.example.spb.view.inter.INoticePageAView;
import org.litepal.LitePal;

import java.util.List;

public class NoticePageAPresenterImpl extends BasePresenter<INoticePageAView> implements INoticePageAPresenter {

    private NoticeAdapter noticeAdapter;
    private NoticeSystemAdapter noticeSystemAdapter;
    private Activity activity;

    public NoticePageAPresenterImpl(Activity activity) {
        this.activity = activity;
    }

    public void obtainNotice(List<Notice> notices,GridLayoutManager gridLayoutManager, RecyclerView recyclerView,OnReturn onReturn){
        noticeAdapter = new NoticeAdapter(notices,activity);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(noticeAdapter);
        onReturn.onReturn();
    }

    public void obtainSystemNotice(List<Notice> notices,GridLayoutManager gridLayoutManager, RecyclerView recyclerView){
        noticeSystemAdapter = new NoticeSystemAdapter(notices,activity);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(noticeSystemAdapter);
    }

    public interface OnReturn{
        void onReturn();
    }
}
