package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.adapter.PostBarImgAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.PostBarDetailPageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.MyResolve;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.presenter.otherimpl.DataLikePresenter;
import com.example.spb.view.Component.BarMoreOperateDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.Component.ThumbAnima;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.inter.IPostBarDetailPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.example.spb.view.littlefun.JumpIntent;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

public class PostBarDetailPage extends BaseMVPActivity<IPostBarDetailPageAView, PostBarDetailPageAPresenterImpl> implements IPostBarDetailPageAView, View.OnClickListener {

    private ISpbAvtivityBarFView bar;
    private Bar barData;
    private RoundedImageView mPostbarDetailUserHeadimg;
    private TextView mPostbarDetailUsername;
    private TextView mPostbarDetailPostdate;
    private Button mPostbarDetailAttentionbtn;
    private TextView mPostbarDetailTxt;
    private RecyclerView mPostbarDetailImagelist;
    private GifImageView mVoiceGif;
    private TextView mVoiceTime;
    private RelativeLayout mPostbarDetailVoice;
    private TagFlowLayout mPostbarDetailTopic;
    private TextView mPostbarDetailLocation;
    private TextView mPostbarDetailCommentNum;
    private RelativeLayout mPostbarDetailCommentR;
    private ImageView mPostbarDetailLikeImg;
    private TextView mPostbarDetailLikeNum;
    private RelativeLayout mPostbarDetailLikeR;
    private TextView mPostbarDetailDiscussNum;
    private RecyclerView mPostbarDetailDiscussList;
    private LayoutInflater layoutInflater;
    private RefreshFollow refreshFollow;
    private EditText mCommentText;
    private Button mCommentSend;
    private RelativeLayout mPostbarDetailBottomCommentR;
    private boolean keyboardStartKey = false;
    private ImageView mPostbarDetailCommentImg;
    private BarMoreOperateDialog barMoreOperateDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bar_detail_page);
        refreshFollow = new RefreshFollow();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_re_Follow), refreshFollow);
        layoutInflater = this.getLayoutInflater();
        barData = (Bar) getIntent().getSerializableExtra(InValues.send(R.string.intent_Bar));
        keyboardStartKey = getIntent().getBooleanExtra("intent_keyboard_start", false);
        initActView();
    }

    @Override
    protected PostBarDetailPageAPresenterImpl createPresenter() {
        return new PostBarDetailPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mPresenter.setUserFollowKey(getDataFollowPresenter().determineFollow(barData.getUser_account()));
        mPostbarDetailUserHeadimg = (RoundedImageView) findViewById(R.id.postbar_detail_user_headimg);
        mPostbarDetailUsername = (TextView) findViewById(R.id.postbar_detail_username);
        mPostbarDetailPostdate = (TextView) findViewById(R.id.postbar_detail_postdate);
        mPostbarDetailAttentionbtn = (Button) findViewById(R.id.postbar_detail_attentionbtn);
        mPostbarDetailTxt = (TextView) findViewById(R.id.postbar_detail_txt);
        mPostbarDetailImagelist = (RecyclerView) findViewById(R.id.postbar_detail_imagelist);
        mVoiceGif = (GifImageView) findViewById(R.id.voice_gif);
        mVoiceTime = (TextView) findViewById(R.id.voice_time);
        mPostbarDetailVoice = (RelativeLayout) findViewById(R.id.postbar_detail_voice);
        mPostbarDetailTopic = (TagFlowLayout) findViewById(R.id.postbar_detail_topic);
        mPostbarDetailLocation = (TextView) findViewById(R.id.postbar_detail_location);
        mPostbarDetailCommentNum = (TextView) findViewById(R.id.postbar_detail_comment_num);
        mPostbarDetailCommentR = (RelativeLayout) findViewById(R.id.postbar_detail_comment_R);
        mPostbarDetailCommentImg = (ImageView) findViewById(R.id.postbar_detail_comment_img);
        mPostbarDetailLikeImg = (ImageView) findViewById(R.id.postbar_detail_like_img);
        mPostbarDetailLikeNum = (TextView) findViewById(R.id.postbar_detail_like_num);
        mPostbarDetailLikeR = (RelativeLayout) findViewById(R.id.postbar_detail_like_R);
        mPostbarDetailDiscussNum = (TextView) findViewById(R.id.postbar_detail_discuss_num);
        mPostbarDetailDiscussList = (RecyclerView) findViewById(R.id.postbar_detail_discuss_list);
        mCommentText = (EditText) findViewById(R.id.comment_text);
        mCommentSend = (Button) findViewById(R.id.comment_send);
        mPostbarDetailBottomCommentR = (RelativeLayout) findViewById(R.id.postbar_detail_bottom_commentR);
        barMoreOperateDialog = new BarMoreOperateDialog(this);
        if (keyboardStartKey){
            showKeyBoard();
        }
        setMyListener();
        setBar();
        setActivityBar();
        initData();
        createDialog();
        createRefresh();
    }

    @Override
    protected void initData() {
        Glide.with(this)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + barData.getUser_account() + "/HeadImage/myHeadImage.png")
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .into(mPostbarDetailUserHeadimg);
        if (barData.getUser_account().equals(getDataUserMsgPresenter().getUser_account())) {
            mPostbarDetailAttentionbtn.setVisibility(View.INVISIBLE);
        } else {
            if (mPresenter.isUserFollowKey()) {
                yesAtt();
            } else {
                noAtt();
            }
        }
        mPostbarDetailUsername.setText(barData.getUser_name());
        mPostbarDetailPostdate.setText(MyDateClass.showDateClass(barData.getPb_date()));
        if (barData.getPb_article() != null && !barData.getPb_article().equals("")) {
            mPostbarDetailTxt.setVisibility(View.VISIBLE);
            mPostbarDetailTxt.setText(barData.getPb_article());
        }
        if (barData.getPb_image_url() != null && !barData.getPb_image_url().equals("")) {
            mPostbarDetailImagelist.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
            mPostbarDetailImagelist.setLayoutManager(gridLayoutManager);
            PostBarImgAdapter postBarImgAdapter = new PostBarImgAdapter(this, MyResolve.InDoubleImage(barData.getPb_image_url()));
            postBarImgAdapter.isDetail(true);
            mPostbarDetailImagelist.setAdapter(postBarImgAdapter);
        }
        if (barData.getPb_voice() != null && !barData.getPb_voice().equals("")) {
            mPostbarDetailVoice.setVisibility(View.VISIBLE);
        }
        if (barData.getPb_topic() != null && !barData.getPb_topic().equals("") && !barData.getPb_topic().equals("null")) {
            mPostbarDetailTopic.setAdapter(new TagAdapter<Topic>(MyResolve.InTopic(barData.getPb_topic())) {
                @Override
                public View getView(FlowLayout parent, int position, Topic o) {
                    View view = layoutInflater.inflate(R.layout.item_tag_two, mPostbarDetailTopic, false);
                    TextView textView = (TextView) view.findViewById(R.id.text);
                    ImageView imageView = (ImageView) view.findViewById(R.id.delete_tag);
                    imageView.setVisibility(View.GONE);
                    textView.setText(o.getTopic_name());
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //跳转话题详细页
                            JumpIntent.startMsgIntent(TopicBarPage.class, new JumpIntent.SetMsg() {
                                @Override
                                public void setMessage(Intent intent) {
                                    intent.putExtra(InValues.send(R.string.intent_Topic), o);
                                }
                            });
                        }
                    });
                    return view;
                }
            });
        }
        if (barData.getPb_location() != null && !barData.getPb_location().equals("")) {
            mPostbarDetailLocation.setVisibility(View.VISIBLE);
            mPostbarDetailLocation.setText(barData.getPb_location());
        }
        if (barData.getPb_comment_num() != 0) {
            mPostbarDetailDiscussNum.setText(MyDateClass.sendMath(barData.getPb_comment_num()));
            mPostbarDetailCommentNum.setText(MyDateClass.sendMath(barData.getPb_comment_num()));
        }
        if (barData.getPb_thumb_num() != 0) {
            mPostbarDetailLikeNum.setText(MyDateClass.sendMath(barData.getPb_thumb_num()));
        }
        if (getDataLikePresenter().determineLike(barData.getPb_one_id())) {
            mPostbarDetailLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }
    }

    public void showKeyBoard(){
        mCommentText.setFocusable(true);
        mCommentText.setFocusableInTouchMode(true);
        mCommentText.requestFocus();
        HideKeyboard.showboard(mCommentText);
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
        barMoreOperateDialog = new BarMoreOperateDialog(PostBarDetailPage.this);
        barMoreOperateDialog.setData(getDataFollowPresenter().determineFollow(barData.getUser_account()),
                getDataCollectBarPresenter().determineCollect(barData.getPb_one_id()),barData.getPb_one_id(),barData.getUser_account());
        if (!barData.getUser_account().equals(getDataUserMsgPresenter().getUser_account())){
            barMoreOperateDialog.funChat();
            barMoreOperateDialog.funCollect();
            barMoreOperateDialog.funReport();
        }else {
            barMoreOperateDialog.funCollect();
            barMoreOperateDialog.funReport();
            barMoreOperateDialog.funDeleteBar(new BarMoreOperateDialog.DeleteReturn() {
                @Override
                public void onReturn() {
                    finish();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            MyToastClass.ShowToast(MyApplication.getContext(),"帖子已被删除");
                        }
                    });
                }
            });
        }
    }

    @Override
    public void showDialogS(int i) {
        barMoreOperateDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {

    }

    public void yesAtt() {
        mPostbarDetailAttentionbtn.setBackground(getDrawable(R.drawable.already_attention));
        mPostbarDetailAttentionbtn.setText("已关注");
    }

    public void noAtt() {
        mPostbarDetailAttentionbtn.setBackground(getDrawable(R.drawable.enter_next_login));
        mPostbarDetailAttentionbtn.setText("关注");
    }

    @Override
    public void setMyListener() {
        mPostbarDetailAttentionbtn.setOnClickListener(this);
        mPostbarDetailLikeImg.setOnClickListener(this);
        mCommentSend.setOnClickListener(this);
        mPostbarDetailCommentImg.setOnClickListener(this);
        mPostbarDetailUserHeadimg.setOnClickListener(this);
        mCommentText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (mCommentText.getText().length() > 0) {
                    mCommentSend.setVisibility(View.VISIBLE);
                } else {
                    mCommentSend.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.setCommentText(mCommentText.getText().toString().trim());
            }
        });
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
        bar = setMyActivityBar(R.id.postbar_detail_bar);
        bar.setBarBackground(R.color.beijing);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barRightImg1(R.drawable.icon_more_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                createDialog();
                showDialogS(0);
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    public void createRefresh() {

    }

    @Override
    public void finishRRefresh(int num) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.postbar_detail_attentionbtn:
                if (mPresenter.isUserFollowKey()) {
                    noAtt();
                    getDataFollowPresenter().removeFollow(barData.getUser_account());
                } else {
                    yesAtt();
                    getDataFollowPresenter().addFollow(barData.getUser_account());
                }
                break;
            case R.id.postbar_detail_like_img:
                getDataLikePresenter().updateLikeData(barData.getPb_one_id()
                        , getDataUserMsgPresenter().getUser_account(), barData.getUser_account(), new DataLikePresenter.OnReturn() {
                            @Override
                            public void removeLike() {
                                barData.setPb_thumb_num(barData.getPb_thumb_num() - 1);
                                mPostbarDetailLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_like));
                                if (barData.getPb_thumb_num() == 0) {
                                    mPostbarDetailLikeNum.setVisibility(View.INVISIBLE);
                                }
                                mPostbarDetailLikeNum.setText(MyDateClass.sendMath(barData.getPb_thumb_num()));
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), -1, barData.getPb_one_id());
                            }

                            @Override
                            public void addLike() {
                                barData.setPb_thumb_num(barData.getPb_thumb_num() + 1);
                                ThumbAnima.thumbAnimation(mPostbarDetailLikeImg);
                                mPostbarDetailLikeNum.setVisibility(View.VISIBLE);
                                mPostbarDetailLikeNum.setText(MyDateClass.sendMath(barData.getPb_thumb_num()));
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_thumb), +1, barData.getPb_one_id());
                            }
                        });
                break;
            case R.id.postbar_detail_comment_img:
                showKeyBoard();
                break;
            case R.id.postbar_detail_user_headimg:
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account),barData.getUser_account());
                    }
                });
                break;
            case R.id.comment_send:

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(refreshFollow);
    }

    class RefreshFollow extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            if (a == 0) {
                mPresenter.setUserFollowKey(true);
                MyToastClass.ShowToast(MyApplication.getContext(), "关注成功");
            } else {
                mPresenter.setUserFollowKey(false);
                MyToastClass.ShowToast(MyApplication.getContext(), "取消关注");
            }
        }
    }
}
