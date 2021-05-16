package com.example.spb.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.*;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.SendNewBarPageAPresenterImpl;
import com.example.spb.view.Component.*;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.inter.ISendNewBarPageAView;
import com.example.spb.view.littlefun.GIFShow;
import com.example.spb.view.littlefun.HideKeyboard;
import com.example.spb.view.littlefun.RequestForAccess;
import com.example.spb.view.littlefun.SearchFun;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.Arrays;
import java.util.List;

public class SendNewBarPage extends BaseMVPActivity<ISendNewBarPageAView, SendNewBarPageAPresenterImpl> implements ISendNewBarPageAView, View.OnClickListener {

    private String[] titleTag = new String[]{"我的位置", "添加话题"};
    private List<String> titleTagList = Arrays.asList(titleTag);
    private FragmentSpbAvtivityBar bar;
    private EditText mSendnewbarTxt;
    private RecyclerView mSendnewbarImageList;
    private RoundedImageView mSendnewbarImageAdd;
    private TagFlowLayout mBottomFlowlayout;
    private ImageView mBottomAddImage;
    private ImageView mBottomAddVoice;
    private SelectImage selectImage;
    private RelativeLayout mBottomVoiceR;
    public DialogInter dialogLoading;
    public String newBarTxt;

    public DialogInter bottomLocation;
    public DialogInter bottomTopic;
    private TextView mTopicClose;
    private EditText mTopicSearchEdt;
    private TagFlowLayout mHotTopicTag;
    private RelativeLayout mHotTopicR;
    private TagFlowLayout mSearchTopicTag;
    private RelativeLayout mSearchTopicR;

    private LayoutInflater layoutInflater;
    private TagFlowLayout mSelectTopicFlowlayout;
    private TextView mVoiceTimeMath;
    private LinearLayout mVoiceTimeL;
    private RoundedImageView mCircleOne;
    private CircleProgressBar mCircleProgressbar;
    private RoundedImageView mCircleTwo;
    private ImageView mVoiceDelete;
    private TextView mVoiceTxtTip;
    private RelativeLayout mCircleR;
    private int voiceNow = 0;
    private static Animation animation;
    private static Animation animation1;
    private static Animation animation2;

    static {
        animation = new ScaleAnimation(1.0f, 0.4f, 1.0f, 0.4f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation.setDuration(70);
        animation.setFillAfter(true);

        animation1 = new ScaleAnimation(0.4f, 1.0f, 0.4f, 1.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation1.setDuration(70);
        animation1.setFillAfter(true);

        animation2 = new ScaleAnimation(1.0f, 1.1f, 1.0f, 1.1f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        );
        animation2.setRepeatCount(Animation.INFINITE);
        animation2.setInterpolator(new LinearInterpolator());
        animation2.setRepeatMode(Animation.REVERSE);
        animation2.setDuration(300);
    }

    private ImageView mVoiceIcon;
    private ImageView mVoiceSubmit;
    private GifImageView mVoiceGif;
    private TextView mVoiceTime;
    private ImageView mVoiceClose;
    private RelativeLayout mSendnewbarVoiceR;
    private GIFShow gifShow;
    private TextView mLocationClose;
    private EditText mLocationSearchEdt;
    private RelativeLayout mLocationSearchR;
    private GifImageView mLocationLoading;
    private RelativeLayout mLocationErrorR;
    private RecyclerView mLocationRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_new_bar_page);
        setAccess();
        layoutInflater = LayoutInflater.from(this);
        initActView();
    }

    private boolean returnAccess = true;

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
    protected SendNewBarPageAPresenterImpl createPresenter() {
        return new SendNewBarPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        selectImage = new SelectImage(this);
        mSendnewbarTxt = (EditText) findViewById(R.id.sendnewbar_txt);
        mSendnewbarImageList = (RecyclerView) findViewById(R.id.sendnewbar_image_list);
        mSendnewbarImageAdd = (RoundedImageView) findViewById(R.id.sendnewbar_image_add);
        mBottomFlowlayout = (TagFlowLayout) findViewById(R.id.bottom_flowlayout);
        mBottomAddImage = (ImageView) findViewById(R.id.bottom_add_image);
        mBottomAddVoice = (ImageView) findViewById(R.id.bottom_add_voice);
        mBottomVoiceR = (RelativeLayout) findViewById(R.id.bottom_voice_r);
        mSelectTopicFlowlayout = (TagFlowLayout) findViewById(R.id.selectTopic_flowlayout);

        mVoiceTimeMath = (TextView) findViewById(R.id.voice_time_math);
        mVoiceTimeL = (LinearLayout) findViewById(R.id.voice_time_l);
        mCircleOne = (RoundedImageView) findViewById(R.id.circle_one);
        mCircleProgressbar = (CircleProgressBar) findViewById(R.id.circleProgressbar);
        mCircleTwo = (RoundedImageView) findViewById(R.id.circle_two);
        mVoiceDelete = (ImageView) findViewById(R.id.voice_delete);
        mVoiceTxtTip = (TextView) findViewById(R.id.voice_txt_tip);
        mCircleR = (RelativeLayout) findViewById(R.id.circle_r);
        mVoiceIcon = (ImageView) findViewById(R.id.voice_icon);
        mVoiceSubmit = (ImageView) findViewById(R.id.voice_submit);
        mVoiceGif = (GifImageView) findViewById(R.id.voice_gif);
        mVoiceTime = (TextView) findViewById(R.id.voice_time);
        mVoiceClose = (ImageView) findViewById(R.id.voice_close);
        mSendnewbarVoiceR = (RelativeLayout) findViewById(R.id.sendnewbar_voice_r);
        gifShow = new GIFShow(mVoiceGif);

        setActivityBar();
        setBar();
        setMyListener();
        initData();
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
                    if (setAccess()){
                        showDialogS(BOTTOMLOCATION);
                    }
                }
                if (position == 1) {
                    showDialogS(BOTTOMTOPIC);
                }
                return true;
            }
        });
        mSendnewbarTxt.setOnClickListener(this);
        mSendnewbarTxt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    mPresenter.setString(mSendnewbarTxt.getText().toString());
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
                    case RESPONSE_ONE:
                        createDialog();
                        break;
                    case RESPONSE_TWO:
                        setSearchTopic();
                        break;
                    case RESPONSE_THREE:
                        mCircleProgressbar.setProgress((float) response);
                        mVoiceTimeMath.setText(String.valueOf(Integer.valueOf(mVoiceTimeMath.getText().toString()) + 1));
                        mVoiceTimeMath.postInvalidate();
                        break;
                    case RESPONSE_FOUR:
                        voiceIcon();
                        break;
                    case RESPONSE_FIVE:
                        if (mBottomVoiceR.getVisibility() != View.VISIBLE) {
                            mVoiceTime.setText(String.valueOf(response));
                            mVoiceTime.postInvalidate();
                        } else {
                            mVoiceTimeMath.setText(String.valueOf(response));
                            mVoiceTimeMath.postInvalidate();
                        }
                        break;
                    case RESPONSE_SIX:
                        if (mBottomVoiceR.getVisibility() != View.VISIBLE) {
                            mVoiceTime.setText(mPresenter.oneTime);
                            mVoiceTime.postInvalidate();
                            gifShow = new GIFShow(mVoiceGif);
                            voiceNow = VOICE_START;
                        } else {
                            mVoiceTimeMath.setText(mPresenter.oneTime);
                            mVoiceTimeMath.postInvalidate();
                            voiceIcon();
                        }
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
                    case SUCCESS_BAR:
                        closeDialog(DIALOGLOADING);
                        break;
                    case ERROR_BAR:
                        closeDialog(DIALOGLOADING);
                        MyToastClass.ShowToast(MyApplication.getContext(),"出错了，请重试");
                        break;
                }
            }
        });
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
                SearchFun.search(mLocationSearchEdt, new SearchFun.GoSearch() {
                    @Override
                    public void afterTextChangedSearch() {

                    }
                });

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
                    public void afterTextChangedSearch() {
                        if (!mTopicSearchEdt.getText().toString().equals("")) {
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
        switch (i) {
            case BOTTOMLOCATION:
                mPresenter.initLocationList(SendNewBarPage.this, new LinearLayoutManager(MyApplication.getContext()),mLocationRecycler);
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
        mSendnewbarImageAdd.setOnClickListener(this);
        mBottomAddImage.setOnClickListener(this);
        mBottomAddVoice.setOnClickListener(this);
        mVoiceDelete.setOnClickListener(this);
        mCircleR.setOnClickListener(this);
        mVoiceSubmit.setOnClickListener(this);
        mSendnewbarVoiceR.setOnClickListener(this);
        mVoiceClose.setOnClickListener(this);
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
        bar = setMyActivityBar(R.id.sendnewbar_actbar);
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
                showDialogS(DIALOGLOADING);
                mPresenter.sendNewMessage();
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    public void onClick(View v) {
        HideKeyboard.hideboard(mBottomAddImage);
        switch (v.getId()) {
            case R.id.sendnewbar_txt:
                mBottomVoiceR.setVisibility(View.GONE);
                break;
            case R.id.sendnewbar_image_add:
                selectImage();
                changeIcon(HAVEIMAGE);
                mBottomVoiceR.setVisibility(View.GONE);
                break;
            case R.id.bottom_add_image:
                selectImage();
                changeIcon(HAVEIMAGE);
                mBottomVoiceR.setVisibility(View.GONE);
                break;
            case R.id.bottom_add_voice:
                if (setAccess()) {
                    changeIcon(STARTVOICE);
                }
                break;
            case R.id.circle_r:
                voiceIcon();
                break;
            case R.id.voice_delete:
                mPresenter.deleteVoice();
                mVoiceIcon.setVisibility(View.INVISIBLE);
                mVoiceIcon.setBackground(getDrawable(R.drawable.icon_voice_go));
                mVoiceDelete.setVisibility(View.GONE);
                mVoiceSubmit.setVisibility(View.GONE);
                mVoiceTimeL.setVisibility(View.INVISIBLE);
                mCircleProgressbar.setProgress(0);
                mVoiceTimeMath.setText("0");
                mVoiceTxtTip.setText("点击录音");
                voiceNow = VOICE_START;
                break;
            case R.id.voice_submit:
                mPresenter.setVoice();
                changeIcon(100);
                mPresenter.stopPlayer();
                mVoiceTime.setText(mPresenter.oneTime);
                mSendnewbarVoiceR.setVisibility(View.VISIBLE);
                mSendnewbarImageAdd.setVisibility(View.GONE);
                mBottomVoiceR.setVisibility(View.GONE);
                mVoiceIcon.setVisibility(View.INVISIBLE);
                mVoiceIcon.setBackground(getDrawable(R.drawable.icon_voice_go));
                mVoiceDelete.setVisibility(View.GONE);
                mVoiceSubmit.setVisibility(View.GONE);
                mVoiceTimeL.setVisibility(View.INVISIBLE);
                mCircleProgressbar.setProgress(0);
                mVoiceTimeMath.setText("0");
                mVoiceTxtTip.setText("点击录音");
                voiceNow = VOICE_START;
                break;
            case R.id.sendnewbar_voice_r:
                if (voiceNow == VOICE_STOP) {
                    mPresenter.stopPlayer();
                    mVoiceTime.setText(mPresenter.oneTime);
                    gifShow = new GIFShow(mVoiceGif);
                    voiceNow = VOICE_START;
                } else {
                    gifShow.startGif();
                    mPresenter.startPlayer(Integer.valueOf(mVoiceTime.getText().toString()));
                    voiceNow = VOICE_STOP;
                }
                break;
            case R.id.voice_close:
                mPresenter.deleteVoice();
                changeIcon(100);
                mPresenter.stopPlayer();
                mSendnewbarVoiceR.setVisibility(View.GONE);
                mSendnewbarImageAdd.setVisibility(View.VISIBLE);
                voiceNow = VOICE_START;
                break;
        }
    }

    public void selectImage() {
        selectImage.selectMoreImg(System.currentTimeMillis() +
                        (int) (Math.random() * 1000) + ".png", MAXPOSITION - mPresenter.barImage.size()
                , new OnResultCallbackListener<LocalMedia>() {
                    @Override
                    public void onResult(List<LocalMedia> result) {
                        mPresenter.obtainImage(result);
                        mPresenter.setImageList(SendNewBarPage.this,
                                new LinearLayoutManager(MyApplication.getContext()), mSendnewbarImageList);
                        mSendnewbarImageList.setVisibility(View.VISIBLE);
                        if (mPresenter.barImage.size() == 4) {
                            changeIcon(MAXIMAGE);
                        } else {
                            changeIcon(HAVEIMAGE);
                        }
                    }

                    @Override
                    public void onCancel() {

                    }
                });
    }

    public void changeIcon(int a) {
        if (mPresenter.barImage == null || mPresenter.barImage.size() == 0) {
            mBottomAddVoice.setClickable(true);
            mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice1));
        } else {
            mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice_no));
            mBottomAddVoice.setClickable(false);
        }
        if (a == 100){
            if (mPresenter.voicePath == null || mPresenter.voicePath.equals("")) {
                mBottomAddVoice.setClickable(true);
                mBottomAddImage.setClickable(true);
                mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice1));
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_img));
            } else {
                mBottomAddVoice.setClickable(false);
                mBottomAddImage.setClickable(false);
                mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice_no));
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_img_no));
            }
        }
        switch (a) {
            case MAXIMAGE:
                mSendnewbarImageAdd.setVisibility(View.GONE);
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_img_no));
                mBottomAddImage.setClickable(false);
                break;
            case STARTVOICE:
                if (mBottomVoiceR.getVisibility() == View.GONE) {
                    mBottomVoiceR.setVisibility(View.VISIBLE);
                    mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice2));
                } else {
                    mBottomVoiceR.setVisibility(View.GONE);
                    mBottomAddVoice.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_voice1));
                }
                break;
            case HAVEIMAGE:
                mSendnewbarImageAdd.setVisibility(View.VISIBLE);
                mBottomAddImage.setBackground(ContextCompat.getDrawable(this, R.drawable.icon_newbar_img));
                mBottomAddImage.setClickable(true);
                break;
        }
    }

    public void voiceIcon() {
        switch (voiceNow) {
            case VOICE_START:
                if (mPresenter.startVoice()) {
                    voiceNow = VOICE_END;
                    mVoiceTimeL.setVisibility(View.VISIBLE);
                    mVoiceTxtTip.setText("录音中");
                    mCircleTwo.startAnimation(animation);
                    mCircleOne.startAnimation(animation2);
                    mCircleProgressbar.setVisibility(View.VISIBLE);
                }
                break;
            case VOICE_END:
                voiceNow = VOICE_GO;
                mPresenter.endVoice(mVoiceTimeMath.getText().toString());
                mCircleTwo.startAnimation(animation1);
                mCircleOne.clearAnimation();
                mVoiceTxtTip.setText("点击播放");
                mVoiceIcon.setVisibility(View.VISIBLE);
                mCircleProgressbar.setVisibility(View.INVISIBLE);
                mVoiceDelete.setVisibility(View.VISIBLE);
                mVoiceSubmit.setVisibility(View.VISIBLE);
                break;
            case VOICE_GO:
                voiceNow = VOICE_STOP;
                mVoiceIcon.setBackground(getDrawable(R.drawable.icon_voice_stop));
                mVoiceDelete.setVisibility(View.GONE);
                mPresenter.startPlayer(Integer.valueOf(mVoiceTimeMath.getText().toString()));
                mVoiceTxtTip.setText("正在播放");
                break;
            case VOICE_STOP:
                voiceNow = VOICE_GO;
                mPresenter.stopPlayer();
                mVoiceDelete.setVisibility(View.VISIBLE);
                mVoiceTimeMath.setText(mPresenter.oneTime);
                mVoiceIcon.setBackground(getDrawable(R.drawable.icon_voice_go));
                mVoiceTxtTip.setText("点击播放");
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter.newBarImageAdapter != null) {
            mPresenter.newBarImageAdapter.destroyTransFeree();
        }
    }
}
