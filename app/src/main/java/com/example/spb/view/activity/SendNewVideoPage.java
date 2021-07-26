package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.SendNewVideoAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.RequestForAccess;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.presenter.littlefun.VideoTool;
import com.example.spb.view.Component.*;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISendNewVideoAView;
import com.example.spb.view.littlefun.SearchFun;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.Arrays;
import java.util.List;

public class SendNewVideoPage extends BaseMVPActivity<ISendNewVideoAView, SendNewVideoAPresenterImpl> implements ISendNewVideoAView {

    private String[] titleTag = new String[]{"我的位置", "添加话题"};
    private List<String> titleTagList = Arrays.asList(titleTag);
    private FragmentSpbAvtivityBar bar;
    private TransferLoc transferLoc;
    private EditText mSendnewvideoTxt;
    private TagFlowLayout mSelectTopicFlowlayout;
    private TagFlowLayout mBottomFlowlayout;
    private RelativeLayout mBottomR;
    public DialogInter dialogLoading;
    public DialogInter bottomLocation;
    public DialogInter bottomTopic;
    private TextView mLocationClose;
    private EditText mLocationSearchEdt;
    private RelativeLayout mLocationSearchR;
    private GifImageView mLocationLoading;
    private RelativeLayout mLocationErrorR;
    private RecyclerView mLocationRecycler;
    private TextView mTopicClose;
    private EditText mTopicSearchEdt;
    private TagFlowLayout mHotTopicTag;
    private RelativeLayout mHotTopicR;
    private TagFlowLayout mSearchTopicTag;
    private RelativeLayout mSearchTopicR;
    private LayoutInflater layoutInflater;
    private StandardGSYVideoPlayer mDetailPlayer;
    private RoundedImageView mSendnewvideoAdd;
    private SpbSelectImage spbSelectImage;
    private VideoTool videoTool;
    private CardView mVideoCard;
    private TextView mVideoDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_video);
        transferLoc = new TransferLoc();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_transfer_loc), transferLoc);
        layoutInflater = LayoutInflater.from(this);
        initActView();
        setAccess();
    }

    private boolean returnAccess = false;

    private boolean setAccess() {
        RequestForAccess.setSendNewBarAccess(this, new RequestForAccess.OnReturn() {
            @Override
            public void allTrue() {
                returnAccess = true;
            }

            @Override
            public void someTrue() {
                returnAccess = false;
            }

            @Override
            public void allFalse() {
                returnAccess = false;
            }

            @Override
            public void toTure() {
                returnAccess = false;
            }

            @Override
            public void low() {
                returnAccess = false;
            }
        });
        return returnAccess;
    }

    @Override
    protected SendNewVideoAPresenterImpl createPresenter() {
        return new SendNewVideoAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        spbSelectImage = new SelectImage(this);
        mSendnewvideoAdd = (RoundedImageView) findViewById(R.id.sendnewvideo_add);
        mDetailPlayer = (StandardGSYVideoPlayer) findViewById(R.id.detail_player);
        mSendnewvideoTxt = (EditText) findViewById(R.id.sendnewvideo_txt);
        mSelectTopicFlowlayout = (TagFlowLayout) findViewById(R.id.selectTopic_flowlayout);
        mBottomFlowlayout = (TagFlowLayout) findViewById(R.id.bottom_flowlayout);
        mBottomR = (RelativeLayout) findViewById(R.id.bottom_r);
        mVideoCard = (CardView) findViewById(R.id.video_card);
        mVideoDelete = (TextView) findViewById(R.id.video_delete);
        videoTool = new VideoTool(SendNewVideoPage.this, MyApplication.getContext(), mDetailPlayer);
        initData();
        setMyListener();
        setActivityBar();
        setBar();
        createDialog();
    }

    @Override
    protected void initData() {
        LayoutInflater mInflater = LayoutInflater.from(this);
        mBottomFlowlayout.setAdapter(new TagAdapter<String>(titleTagList) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                View v = mInflater.inflate(R.layout.other_newbar_tag, mBottomFlowlayout, false);
                TextView t = (TextView) v.findViewById(R.id.text);
                ImageView i = (ImageView) v.findViewById(R.id.image);
                if (position == 0) {
                    i.setBackground(getDrawable(R.drawable.icon_location));
                    if (!o.equals("我的位置")) {
                        t.setTextColor(getColor(R.color.theme_color));
                    } else {
                        t.setTextColor(getColor(R.color.qihei));
                    }
                } else {
                    i.setBackground(getDrawable(R.drawable.icon_topic));
                }
                t.setText(o);
                return v;
            }
        });
        mBottomFlowlayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (position == 0) {
                    if (setAccess()) {
                        showDialogS(BOTTOMLOCATION);
                    }
                }
                if (position == 1) {
                    showDialogS(BOTTOMTOPIC);
                }
                return true;
            }
        });
        mSendnewvideoTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.setNewVideoTxt(mSendnewvideoTxt.getText().toString());
            }
        });
    }


    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                switch (responseFlag) {
                    case RESPONSE_SUCCESS:
                        closeDialog(DIALOGLOADING);
                        finish();
                        MyToastClass.ShowToast(MyApplication.getContext(),"发布成功");
                        break;
                    case RESPONSE_ERROR:
                        closeDialog(DIALOGLOADING);
                        MyToastClass.ShowToast(MyApplication.getContext(),"错误，请重试");
                        break;
                    case RESPONSE_ONE:
                        createDialog();
                        break;
                    case RESPONSE_TWO:
                        setSearchTopic();
                        break;
                    case RESPONSE_SEVEN:
                        mLocationRecycler.setVisibility(View.VISIBLE);
                        mLocationErrorR.setVisibility(View.GONE);
                        mLocationLoading.setVisibility(View.GONE);
                        break;
                    case RESPONSE_EIGHT:
                        mLocationRecycler.setVisibility(View.GONE);
                        mLocationErrorR.setVisibility(View.VISIBLE);
                        mLocationLoading.setVisibility(View.GONE);
                        break;
                    case VIDEO_OBTAIN:
                        closeDialog(DIALOGLOADING);
                        mVideoCard.setVisibility(View.VISIBLE);
                        mSendnewvideoAdd.setVisibility(View.GONE);
                        videoTool.creatVideo(mPresenter.getVideoPath());
                        mPresenter.setVideoImagePath(videoTool.getCacheVideoImage());
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        videoTool.setonBackPressed();
        super.onBackPressed();
    }


    @Override
    protected void onPause() {
        videoTool.setonPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        videoTool.setonResume();
        super.onResume();
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        videoTool.setonConfigurationChanged(newConfig);
    }

    @Override
    public void createDialog() {
        dialogLoading = new EasyDialog(this, R.drawable.loading);
        dialogLoading.setCancelable(false);
        bottomLocation = new ComponentDialog(this, R.layout.dialog_bottom_location, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mLocationClose = (TextView) view.findViewById(R.id.location_close);
                mLocationSearchEdt = (EditText) view.findViewById(R.id.location_search_edt);
                mLocationSearchR = (RelativeLayout) view.findViewById(R.id.location_search_r);
                mLocationLoading = (GifImageView) view.findViewById(R.id.location_loading);
                mLocationErrorR = (RelativeLayout) view.findViewById(R.id.location_error_r);
                mLocationRecycler = (RecyclerView) view.findViewById(R.id.location_recycler);
            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {
                mLocationClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMLOCATION);
                    }
                });
                mLocationErrorR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mLocationErrorR.setVisibility(View.GONE);
                        mLocationLoading.setVisibility(View.VISIBLE);
                        mPresenter.initLocationList(SendNewVideoPage.this, new LinearLayoutManager(MyApplication.getContext()), mLocationRecycler);
                    }
                });
                SearchFun.search(mLocationSearchEdt, new SearchFun.GoSearch() {
                    @Override
                    public void afterTextChangedSearch(String text) {
                        if (text.equals("")) {
                            mPresenter.initLocationList(SendNewVideoPage.this, new LinearLayoutManager(MyApplication.getContext()), mLocationRecycler);
                        } else {
                            mPresenter.searchLocation(text, SendNewVideoPage.this, new LinearLayoutManager(MyApplication.getContext()), mLocationRecycler);
                        }
                    }
                });
            }
        });
        bottomLocation.returnAlertDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closeDialog(BOTTOMLOCATION);
                    return true;
                } else {
                    return false;
                }
            }
        });
        bottomLocation.setBottomStyle();
        bottomLocation.setAnimation(R.style.bottomdialog_animStyle);
        bottomTopic = new ComponentDialog(this, R.layout.dialog_bottom_topic, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mTopicClose = (TextView) view.findViewById(R.id.topic_close);
                mTopicSearchEdt = (EditText) view.findViewById(R.id.topic_search_edt);
                mHotTopicTag = (TagFlowLayout) view.findViewById(R.id.hot_topic_tag);
                mHotTopicR = (RelativeLayout) view.findViewById(R.id.hot_topic_r);
                mSearchTopicTag = (TagFlowLayout) view.findViewById(R.id.search_topic_tag);
                mSearchTopicR = (RelativeLayout) view.findViewById(R.id.search_topic_r);
            }

            @Override
            public void initData() {
                mHotTopicTag.setAdapter(new TagAdapter<Topic>(mPresenter.hotTopics) {
                    @Override
                    public View getView(FlowLayout parent, int position, Topic topic) {
                        View view = layoutInflater.inflate(R.layout.item_tag_one, mHotTopicTag, false);
                        TextView textView = (TextView) view.findViewById(R.id.text);
                        textView.setText(topic.getTopic_name());
                        return view;
                    }
                });
            }

            @Override
            public void initListener() {
                mTopicClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMTOPIC);
                    }
                });
                SearchFun.search(mTopicSearchEdt, new SearchFun.GoSearch() {
                    @Override
                    public void afterTextChangedSearch(String text) {
                        if (!text.equals(" ")) {
                            mHotTopicR.setVisibility(View.GONE);
                            mSearchTopicR.setVisibility(View.VISIBLE);
                            mPresenter.searchTopic(mTopicSearchEdt.getText().toString().trim());
                        } else {
                            mHotTopicR.setVisibility(View.VISIBLE);
                            mSearchTopicR.setVisibility(View.GONE);
                        }
                    }
                });
                mHotTopicTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        TextView textView = (TextView) view.findViewById(R.id.text);
                        if (mPresenter.topics.size() == 5) {
                            MyToastClass.ShowToast(MyApplication.getContext(), "最多添加5个话题噢");
                        } else {
                            mPresenter.addTopic(textView.getText().toString());
                        }
                        closeDialog(BOTTOMTOPIC);
                        setSelectTopic();
                        return true;
                    }
                });
            }
        });
        bottomTopic.setBottomStyle();
        bottomTopic.setAnimation(R.style.bottomdialog_animStyle);
    }

    public void setSelectTopic() {
        mSelectTopicFlowlayout.setAdapter(new TagAdapter<Topic>(mPresenter.topics) {
            @Override
            public View getView(FlowLayout parent, int position, Topic topic) {
                View view = layoutInflater.inflate(R.layout.item_tag_two, mSelectTopicFlowlayout, false);
                TextView textView = (TextView) view.findViewById(R.id.text);
                ImageView imageView = (ImageView) view.findViewById(R.id.delete_tag);
                textView.setText(topic.getTopic_name());
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPresenter.removeTopic(position);
                        setSelectTopic();
                    }
                });
                return view;
            }
        });
    }

    public void setSearchTopic() {
        mSearchTopicTag.setAdapter(new TagAdapter<Topic>(mPresenter.searchTopics) {
            @Override
            public View getView(FlowLayout parent, int position, Topic topic) {
                View view = layoutInflater.inflate(R.layout.item_tag_one, mSearchTopicTag, false);
                TextView textView = (TextView) view.findViewById(R.id.text);
                textView.setText(topic.getTopic_name());
                return view;
            }
        });
        mSearchTopicTag.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                TextView textView = (TextView) view.findViewById(R.id.text);
                if (mPresenter.topics.size() == 5) {
                    MyToastClass.ShowToast(MyApplication.getContext(), "最多添加5个话题噢");
                } else {
                    if (textView.getText().toString().substring(0, 2).equals("新增")) {
                        mPresenter.addTopic(textView.getText().toString().substring(2));
                    } else {
                        mPresenter.addTopic(textView.getText().toString());
                    }
                }
                closeDialog(BOTTOMTOPIC);
                mTopicSearchEdt.setText("");
                mTopicSearchEdt.clearFocus();
                mSearchTopicR.setVisibility(View.GONE);
                setSelectTopic();
                return true;
            }
        });
    }

    @Override
    public void showDialogS(int i) {
        createDialog();
        switch (i) {
            case BOTTOMLOCATION:
                mPresenter.initLocationList(SendNewVideoPage.this, new LinearLayoutManager(MyApplication.getContext()), mLocationRecycler);
                bottomLocation.showMyDialog();
                break;
            case BOTTOMTOPIC:
                bottomTopic.showMyDialog();
                break;
            case DIALOGLOADING:
                dialogLoading.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case BOTTOMLOCATION:
                mPresenter.onStopGps();
                bottomLocation.closeMyDialog();
                break;
            case BOTTOMTOPIC:
                bottomTopic.closeMyDialog();
                break;
            case DIALOGLOADING:
                dialogLoading.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {
        mSendnewvideoAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spbSelectImage.selectVideo("myVideo", new OnResultCallbackListener() {
                    @Override
                    public void onResult(List result) {
                        showDialogS(DIALOGLOADING);
                        mPresenter.obtainVideo(result);
                    }

                    @Override
                    public void onCancel() {

                    }
                });
            }
        });
        mVideoDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.setVideoPath("");
                mVideoCard.setVisibility(View.GONE);
                mSendnewvideoAdd.setVisibility(View.VISIBLE);
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
        bar = setMyActivityBar(R.id.sendnewvideo_actbar);
        bar.barLeftImg(R.drawable.close_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.getmBarRightTxt1().setTextColor(ContextCompat.getColor(this, R.color.theme_color));
        bar.barRightTxt1(SENDTITLE, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                if (mSendnewvideoTxt.getText().toString().trim().equals("")){
                    MyToastClass.ShowToast(MyApplication.getContext(),"内容不能为空噢");
                }else if (mPresenter.getVideoPath() == null || mPresenter.getVideoPath().equals("")){
                    MyToastClass.ShowToast(MyApplication.getContext(),"请传入视频文件");
                }else {
                    showDialogS(DIALOGLOADING);
                    mPresenter.sendNewMessage();
                }
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onStopGps();
        videoTool.setonDestroy();
    }

    class TransferLoc extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String loc = intent.getStringExtra("key_two");
            int id = intent.getIntExtra("key_one", 0);
            if (id != 0) {
                titleTagList.set(0, loc);
                mPresenter.setLocationName(loc);
            } else {
                titleTagList.set(0, "我的位置");
            }
            initData();
            closeDialog(BOTTOMLOCATION);
        }
    }
}
