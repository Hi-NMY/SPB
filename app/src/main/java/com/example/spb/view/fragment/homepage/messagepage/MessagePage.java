package com.example.spb.view.fragment.homepage.messagepage;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.MessagePageFPresenterImpl;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.activity.NoticePage;
import com.example.spb.view.inter.IMessagePageFView;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

public class MessagePage extends BaseMVPFragment<IMessagePageFView, MessagePageFPresenterImpl> implements IMessagePageFView, View.OnClickListener {

    private RoundedImageView mMessagepageNotice;
    private RoundedImageView mMessagepageFriend;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MessagePageFPresenterImpl createPresenter() {
        return new MessagePageFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.fragment_message_page;
    }

    @Override
    protected void initFragView(View view) {
        mMessagepageNotice = (RoundedImageView)view.findViewById(R.id.messagepage_notice);
        mMessagepageFriend = (RoundedImageView)view.findViewById(R.id.messagepage_friend);
        setMyListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.messagepage_notice:
                JumpIntent.startMyIntent(NoticePage.class);
                break;
            case R.id.messagepage_friend:
                JumpIntent.startMyIntent(AttentionUserPage.class);
                break;
        }
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
        mMessagepageNotice.setOnClickListener(this);
        mMessagepageFriend.setOnClickListener(this);
    }

    @Override
    public void setBar() {

    }

    @Override
    public void setActivityBar() {

    }
}
