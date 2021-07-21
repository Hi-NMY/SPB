package com.example.spb.view.fragment.homepage.messagepage;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.MessagePageFPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.activity.NoticePage;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.inter.IMessagePageFView;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imkit.model.UIConversation;
import io.rong.imlib.model.Conversation;

public class MessagePage extends BaseMVPFragment<IMessagePageFView, MessagePageFPresenterImpl> implements IMessagePageFView, View.OnClickListener {

    private RoundedImageView mMessagepageNotice;
    private RoundedImageView mMessagepageFriend;
    private NewMessage newMessage;
    private BaseMVPActivity baseMVPActivity;
    private View mViewNewMessage;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        baseMVPActivity = (BaseMVPActivity) getActivity();
        newMessage = new NewMessage();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_new_messasge), newMessage);
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
        mMessagepageNotice = (RoundedImageView) view.findViewById(R.id.messagepage_notice);
        mMessagepageFriend = (RoundedImageView) view.findViewById(R.id.messagepage_friend);
        mViewNewMessage = (View) view.findViewById(R.id.view_newMessage);
        setMyListener();
        ConversationListFragment conversationListFragment = new ConversationListFragment();
        // 此处设置 Uri. 通过 appendQueryParameter 去设置所要支持的会话类型. 例如
        // .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(),"false")
        // 表示支持单聊会话, false 表示不聚合显示, true 则为聚合显示
        Uri uri = Uri.parse("rong://" +
                getActivity().getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话是否聚合显示
                .build();

        conversationListFragment.setUri(uri);
        FragmentManager manager = getChildFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationListFragment);
        transaction.commit();

        RongIM.setConversationListBehaviorListener(new RongIM.ConversationListBehaviorListener() {
            /**
             * 会话头像点击监听
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param targetId         被点击的用户id。
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId) {
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), targetId);
                    }
                });
                return true;
            }

            /**
             * 会话头像长按监听
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param targetId         被点击的用户id。
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId) {
                return false;
            }

            /**
             * 会话列表中的 Item 长按监听
             *
             * @param context      上下文。
             * @param view         触发点击的 View。
             * @param conversation 长按时的会话条目
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationLongClick(Context context, View view, UIConversation conversation) {
                return false;
            }

            /**
             * 会话列表中的 Item 点击监听
             *
             * @param context      上下文。
             * @param view         触发点击的 View。
             * @param conversation 长按时的会话条目
             * @return true 拦截事件, false 执行融云 SDK 内部默认处理逻辑
             */
            @Override
            public boolean onConversationClick(Context context, View view, UIConversation conversation) {
                return false;
            }
        });
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
        switch (v.getId()) {
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(newMessage);
    }

    class NewMessage extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int key = intent.getIntExtra("key_one",0);
            if (key == 0){
                mViewNewMessage.setVisibility(View.VISIBLE);
            }else{
                mViewNewMessage.setVisibility(View.INVISIBLE);
            }
        }
    }
}
