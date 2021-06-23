package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.inter.IPostBarDetailPageAView;
import com.example.spb.view.littlefun.BarImageInFlater;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_bar_detail_page);
        layoutInflater = this.getLayoutInflater();
        barData = (Bar) getIntent().getSerializableExtra(InValues.send(R.string.intent_Bar));
        initActView();
    }

    @Override
    protected PostBarDetailPageAPresenterImpl createPresenter() {
        return new PostBarDetailPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
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
        mPostbarDetailLikeImg = (ImageView) findViewById(R.id.postbar_detail_like_img);
        mPostbarDetailLikeNum = (TextView) findViewById(R.id.postbar_detail_like_num);
        mPostbarDetailLikeR = (RelativeLayout) findViewById(R.id.postbar_detail_like_R);
        mPostbarDetailDiscussNum = (TextView) findViewById(R.id.postbar_detail_discuss_num);
        mPostbarDetailDiscussList = (RecyclerView) findViewById(R.id.postbar_detail_discuss_list);
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
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                .into(mPostbarDetailUserHeadimg);
        mPostbarDetailUsername.setText(barData.getUser_name());
        mPostbarDetailPostdate.setText(MyDateClass.showDateClass(barData.getPb_date()));
        if (barData.getPb_article() != null && !barData.getPb_article().equals("")){
            mPostbarDetailTxt.setVisibility(View.VISIBLE);
            mPostbarDetailTxt.setText(barData.getPb_article());
        }
        if (barData.getPb_image_url() != null && !barData.getPb_image_url().equals("")){
            mPostbarDetailImagelist.setVisibility(View.VISIBLE);
            GridLayoutManager gridLayoutManager = new GridLayoutManager(this,1);
            mPostbarDetailImagelist.setLayoutManager(gridLayoutManager);
            PostBarImgAdapter postBarImgAdapter = new PostBarImgAdapter(this, MyResolve.InDoubleImage(barData.getPb_image_url()));
            postBarImgAdapter.isDetail(true);
            mPostbarDetailImagelist.setAdapter(postBarImgAdapter);
        }
        if (barData.getPb_voice() != null && !barData.getPb_voice().equals("")){
            mPostbarDetailVoice.setVisibility(View.VISIBLE);
        }
        if (barData.getPb_topic() != null && !barData.getPb_topic().equals("") && !barData.getPb_topic().equals("null")){
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
                                    intent.putExtra(InValues.send(R.string.intent_Topic),o);
                                }
                            });
                        }
                    });
                    return view;
                }
            });
        }
        if (barData.getPb_location() != null && !barData.getPb_location().equals("")){
            mPostbarDetailLocation.setVisibility(View.VISIBLE);
            mPostbarDetailLocation.setText(barData.getPb_location());
        }
        if (barData.getPb_comment_num() != 0){
            mPostbarDetailDiscussNum.setText(MyDateClass.sendMath(barData.getPb_comment_num()));
            mPostbarDetailCommentNum.setText(MyDateClass.sendMath(barData.getPb_comment_num()));
        }
        if (barData.getPb_thumb_num() != 0){
            mPostbarDetailLikeNum.setText(MyDateClass.sendMath(barData.getPb_thumb_num()));
        }
        if (getDataLikePresenter().determineLike(barData.getPb_one_id())){
            mPostbarDetailLikeImg.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        }
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
        mPostbarDetailAttentionbtn.setOnClickListener(this);
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

                break;
        }
    }
}
