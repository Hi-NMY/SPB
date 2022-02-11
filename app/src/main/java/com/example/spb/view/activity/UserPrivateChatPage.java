package com.example.spb.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.base.BasePresenter;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.InterTotal.SpbInterOne;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.utils.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;
import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationFragment;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

public class UserPrivateChatPage extends BaseMVPActivity implements SpbInterOne {

    public String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_private_chat_page);

        username = getIntent().getData().getQueryParameter("title");

        setActivityBar();
        setBar();
// 添加会话界面
        ConversationFragment conversationFragment = new ConversationFragment();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container, conversationFragment);
        transaction.commit();

        RongIM.getInstance().setMessageAttachedUserInfo(true);

        RongIM.setConversationClickListener(new RongIM.ConversationClickListener() {

            /**
             * 用户头像点击事件
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param user             被点击的用户的信息。
             * @param targetId         会话 id
             * @return true 拦截事件; false 不拦截, 默认执行 SDK 内部逻辑
             */
            @Override
            public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), user.getUserId());
                    }
                });
                return true;
            }

            /**
             * 用户头像长按事件
             *
             * @param context          上下文。
             * @param conversationType 会话类型。
             * @param user             被点击的用户的信息。
             * @param targetId         会话 id
             * @return true 拦截事件; false 不拦截, 默认执行 SDK 内部逻辑
             */
            @Override
            public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo user, String targetId) {
                return false;
            }

            /**
             * 消息点击事件
             *
             * @param context 上下文。
             * @param view    触发点击的 View。
             * @param message 被点击的消息的实体信息。
             * @return true 拦截事件; false 不拦截, 默认执行 SDK 内部逻辑
             */
            @Override
            public boolean onMessageClick(Context context, View view, Message message) {
                return false;
            }

            /**
             * 消息超链接内容点击事件
             *
             * @param context 上下文。
             * @param link  超链接文本
             * @param message 被点击的消息的实体信息。
             * @return true 拦截事件; false 不拦截, 默认执行 SDK 内部逻辑
             */
            @Override
            public boolean onMessageLinkClick(Context context, String link, Message message) {
                return false;
            }

            /**
             * 消息长按事件
             *
             * @param context 上下文。
             * @param view    触发点击的 View。
             * @param message 被点击的消息的实体信息。
             * @return true 拦截事件; false 不拦截, 默认执行 SDK 内部逻辑
             */
            @Override
            public boolean onMessageLongClick(Context context, View view, Message message) {
                return false;
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initActView() {

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
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.private_message_bar);
        bar.barCentralTxt(username, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
