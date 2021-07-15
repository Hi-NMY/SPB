package com.example.spb.view.Component;

import android.app.Activity;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.model.impl.CollectBarModelImpl;
import com.example.spb.presenter.callback.MyCallBack;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.activity.HomePage;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;

public class BarMoreOperateDialog extends ComponentDialog {

    private RelativeLayout mChatR;
    private TextView mChatTxt;
    private ImageView mFollowIcon;
    private RelativeLayout mFollowR;
    private TextView mReportTxt;
    private RelativeLayout mReportR;
    private TextView mFollowTxt;
    private ImageView mCollectIcon;
    private RelativeLayout mCollectR;
    private TextView mCollectTxt;
    private RelativeLayout mDeleteR;
    private TextView mDeleteTxt;
    private Button mCloseDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mTopicName;
    private TextView txt;

    public boolean followKey = false;
    public boolean collectKey = false;
    public String barOneId = "";
    public String userAccount = "";
    public String userName = "";
    private BaseMVPActivity baseMVPActivity;
    private ComponentDialog componentDialog;
    private SpbModelBasicInter barModel;
    private SpbModelBasicInter collectBarModel;

    public BarMoreOperateDialog(Activity context) {
        super(context);
        barModel = new BarModelImpl();
        collectBarModel = new CollectBarModelImpl();
        activity = context;
        baseMVPActivity = (BaseMVPActivity)context;
        builder = new AlertDialog.Builder(context);
        view = LayoutInflater.from(context).inflate(R.layout.dialog_bar_more, null);
        builder.setView(view);
        initView(view);
    }

    public boolean isFollowKey() {
        return followKey;
    }

    public void setFollowKey(boolean followKey) {
        this.followKey = followKey;
    }

    public boolean isCollectKey() {
        return collectKey;
    }

    public void setCollectKey(boolean collectKey) {
        this.collectKey = collectKey;
    }

    public String getBarOneId() {
        return barOneId;
    }

    public void setBarOneId(String barOneId) {
        this.barOneId = barOneId;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setData(boolean followKey, boolean collectKey, String barOneId, String userAccount,String name){
        setFollowKey(followKey);
        setCollectKey(collectKey);
        setBarOneId(barOneId);
        setUserAccount(userAccount);
        setUserName(name);
    }

    @Override
    public void initView(View view) {
        mChatR = (RelativeLayout) view.findViewById(R.id.chat_R);
        mChatTxt = (TextView) view.findViewById(R.id.chat_txt);
        mFollowIcon = (ImageView) view.findViewById(R.id.follow_icon);
        mFollowR = (RelativeLayout) view.findViewById(R.id.follow_R);
        mReportTxt = (TextView) view.findViewById(R.id.report_txt);
        mReportR = (RelativeLayout) view.findViewById(R.id.report_R);
        mFollowTxt = (TextView) view.findViewById(R.id.follow_txt);
        mCollectIcon = (ImageView) view.findViewById(R.id.collect_icon);
        mCollectR = (RelativeLayout) view.findViewById(R.id.collect_R);
        mCollectTxt = (TextView) view.findViewById(R.id.collect_txt);
        mDeleteR = (RelativeLayout) view.findViewById(R.id.delete_R);
        mDeleteTxt = (TextView) view.findViewById(R.id.delete_txt);
        mCloseDialog = (Button) view.findViewById(R.id.close_dialog);
        mCloseDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeMyDialog();
            }
        });
    }

    public void funChat(){
        mChatR.setVisibility(View.VISIBLE);
        mChatTxt.setVisibility(View.VISIBLE);
        mChatR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Conversation.ConversationType conversationType  = Conversation.ConversationType.PRIVATE;
                String targetId = getUserAccount();
                String title = getUserName();
                RongIM.getInstance().startConversation(baseMVPActivity , conversationType, targetId, title, null);
            }
        });
    }

    public void funFOllow(){
        mFollowR.setVisibility(View.VISIBLE);
        mFollowTxt.setVisibility(View.VISIBLE);
        if (isFollowKey()){
            mFollowR.setBackground(activity.getDrawable(R.drawable.icon_circle_gb));
            mFollowIcon.setBackground(activity.getDrawable(R.drawable.icon_followed_dialog));
            mFollowTxt.setText("已关注");
        }else {
            mFollowR.setBackground(activity.getDrawable(R.drawable.icon_circle_gb3));
            mFollowIcon.setBackground(activity.getDrawable(R.drawable.icon_follow_dialog));
            mFollowTxt.setText("关注");
        }
        mFollowTxt.postInvalidate();
        mFollowR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFollowKey()){
                    setFollowKey(false);
                    baseMVPActivity.getDataFollowPresenter().removeFollow(getUserAccount());
                }else {
                    setFollowKey(true);
                    baseMVPActivity.getDataFollowPresenter().addFollow(getUserAccount());
                }
                funFOllow();
            }
        });
    }

    public void funCollect(){
        mCollectR.setVisibility(View.VISIBLE);
        mCollectTxt.setVisibility(View.VISIBLE);
        if (isCollectKey()){
            mCollectIcon.setBackground(activity.getDrawable(R.drawable.icon_collected_dialog));
            mCollectTxt.setText("已收藏");
        }else {
            mCollectIcon.setBackground(activity.getDrawable(R.drawable.icon_collect_dialog));
            mCollectTxt.setText("收藏");
        }
        mCollectTxt.postInvalidate();
        mCollectR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCollectKey()){
                    setCollectKey(false);
                    baseMVPActivity.getDataCollectBarPresenter().removeCollectBar(getUserAccount(),getBarOneId());
                }else {
                    setCollectKey(true);
                    baseMVPActivity.getDataCollectBarPresenter().addCollectBar(getUserAccount(),getBarOneId());
                }
                funCollect();
            }
        });

    }

    public void funReport(){
        mReportR.setVisibility(View.VISIBLE);
        mReportTxt.setVisibility(View.VISIBLE);
    }

    public void funDeleteBar(DeleteReturn deleteReturn){
        mDeleteR.setVisibility(View.VISIBLE);
        mDeleteTxt.setVisibility(View.VISIBLE);
        mDeleteR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                componentDialog = new ComponentDialog(activity, R.layout.dialog_longclick_view, new ComponentDialog.InitDialog() {
                    @Override
                    public void initView(View view) {
                        mButtonClose = (Button)view.findViewById(R.id.button_close);
                        mButtonRight = (Button)view.findViewById(R.id.button_right);
                        mTopicName = (TextView)view.findViewById(R.id.topic_name);
                        txt = (TextView)view.findViewById(R.id.txt);
                    }

                    @Override
                    public void initData() {
                        mTopicName.setVisibility(View.GONE);
                        txt.setText("永久删除此帖吗？");
                    }

                    @Override
                    public void initListener() {
                        mButtonClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                componentDialog.closeMyDialog();
                            }
                        });
                        mButtonRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Bar bar = new Bar();
                                bar.setUser_account(getUserAccount());
                                bar.setPb_one_id(getBarOneId());
                                baseMVPActivity.setDeletePbId(getBarOneId());
                                barModel.deleteData(barModel.DATABAR_DELETE_ONE, bar, new MyCallBack() {
                                    @Override
                                    public void onSuccess(@NotNull Response response) {
                                        try {
                                            String a = response.body().string();
                                            if (Integer.valueOf(a) == 200){
                                                //删帖Bcr
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_personal_bar),3,getUserAccount(),null);
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_personal_videobar),3,getUserAccount(),null);
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_new_video),3,null);
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_new_bar),3,null);
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_newtopicbar),3,getUserAccount(),null);
                                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_hottopicbar),3,null,null);
                                                SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
                                                SharedPreferences.Editor editor = MySharedPreferences.saveShared(InValues.send(R.string.Shared_userBar_Num));
                                                editor.putInt(InValues.send(R.string.userBar_num),sharedPreferences.getInt(InValues.send(R.string.userBar_num),0) - 1);
                                                editor.apply();
                                                if (deleteReturn != null){
                                                    deleteReturn.onReturn();
                                                }
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    @Override
                                    public void onError(int t) {

                                    }
                                });
                                componentDialog.closeMyDialog();
                                closeMyDialog();
                            }
                        });
                    }
                });
                componentDialog.showMyDialog();
            }
        });
    }

    @Override
    public void showMyDialog() {
        alertDialog = builder.create();
        window = alertDialog.getWindow();
        setBackgroundTransparent();
        setBottomStyle();
        setAnimation(R.style.bottomdialog_animStyle);
        alertDialog.show();
    }

    public interface DeleteReturn{
        void onReturn();
    }
}
