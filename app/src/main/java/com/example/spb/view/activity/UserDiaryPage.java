package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Diary;
import com.example.spb.presenter.impl.UserDiaryPageAPresenterImpl;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.RemoveNullCharacter;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.*;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.inter.IUserDiaryPageAView;
import com.example.spb.view.littlefun.MyListAnimation;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import pl.droidsonroids.gif.GifImageView;

import java.util.ArrayList;
import java.util.List;

public class UserDiaryPage extends BaseMVPActivity<IUserDiaryPageAView, UserDiaryPageAPresenterImpl>
        implements IUserDiaryPageAView {

    private FragmentSpbAvtivityBar bar;
    private DialogInter componentDialog;
    private DialogInter easyDialog;
    private TextView mDiarysendDateYear;
    private TextView mDiarysendDateMonth;
    private TextView mDiarysendDateWeek;
    private TextView mDiarysendDateTime;
    private ImageView mDiarysendWeather1;
    private ImageView mDiarysendWeather2;
    private ImageView mDiarysendWeather3;
    private ImageView mDiarysendWeather4;
    private ImageView mDiarysendWeather5;
    private ImageView mDiarysendWeather6;
    private ImageView mDiarysendWeather7;
    private ImageView mDiarysendWeather8;
    private ImageView mDiarysendWeather9;
    private EditText mDiarysendMessage;
    private RoundedImageView mDiarysendImg;
    private Button mEnterBtn;
    private List<ImageView> imageViews;
    private SpbSelectImage selectImage;
    private AddDiary addDiary;
    private GifImageView mUserdiaryRefreshTgif;
    private RecyclerView mUserdiaryRecyclerview;
    private SmartRefreshLayout mUserdiaryRefresh;
    private MySmartRefresh mySmartRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_diary_page);
        addDiary = new AddDiary();
        SpbBroadcast.obtainRecriver(this, InValues.send(R.string.Bcr_add_Diary), addDiary);
        initActView();
    }

    @Override
    protected UserDiaryPageAPresenterImpl createPresenter() {
        return new UserDiaryPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        selectImage = new SelectImage(this);
        mUserdiaryRefreshTgif = (GifImageView) findViewById(R.id.userdiary_refresh_tgif);
        mUserdiaryRecyclerview = (RecyclerView) findViewById(R.id.userdiary_recyclerview);
        mUserdiaryRefresh = (SmartRefreshLayout) findViewById(R.id.userdiary_refresh);
        mUserdiaryRecyclerview = MyListAnimation.setListAnimation(this,mUserdiaryRecyclerview);
        mySmartRefresh = new MySmartRefresh(mUserdiaryRefresh,mUserdiaryRefreshTgif,null);
        setActivityBar();
        setBar();
        setMyListener();
        initData();
        createDialog();
        createRefresh();
    }

    @Override
    protected void initData() {
        mPresenter.addDiary(getDataDiaryPresenter().diaryList,mUserdiaryRecyclerview);
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
        easyDialog = new EasyDialog(this, R.drawable.loading);
        componentDialog = new ComponentDialog(this, R.layout.dialog_send_diary, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mDiarysendDateYear = (TextView) view.findViewById(R.id.diarysend_date_year);
                mDiarysendDateMonth = (TextView) view.findViewById(R.id.diarysend_date_month);
                mDiarysendDateWeek = (TextView) view.findViewById(R.id.diarysend_date_week);
                mDiarysendDateTime = (TextView) view.findViewById(R.id.diarysend_date_time);
                mDiarysendWeather1 = (ImageView) view.findViewById(R.id.diarysend_weather1);
                mDiarysendWeather2 = (ImageView) view.findViewById(R.id.diarysend_weather2);
                mDiarysendWeather3 = (ImageView) view.findViewById(R.id.diarysend_weather3);
                mDiarysendWeather4 = (ImageView) view.findViewById(R.id.diarysend_weather4);
                mDiarysendWeather5 = (ImageView) view.findViewById(R.id.diarysend_weather5);
                mDiarysendWeather6 = (ImageView) view.findViewById(R.id.diarysend_weather6);
                mDiarysendWeather7 = (ImageView) view.findViewById(R.id.diarysend_weather7);
                mDiarysendWeather8 = (ImageView) view.findViewById(R.id.diarysend_weather8);
                mDiarysendWeather9 = (ImageView) view.findViewById(R.id.diarysend_weather9);
                mDiarysendMessage = (EditText) view.findViewById(R.id.diarysend_message);
                mDiarysendImg = (RoundedImageView) view.findViewById(R.id.diarysend_img);
                mEnterBtn = (Button) view.findViewById(R.id.enter_btn);
                imageViews = new ArrayList<>();
                imageViews.add(mDiarysendWeather1);
                imageViews.add(mDiarysendWeather2);
                imageViews.add(mDiarysendWeather3);
                imageViews.add(mDiarysendWeather4);
                imageViews.add(mDiarysendWeather5);
                imageViews.add(mDiarysendWeather6);
                imageViews.add(mDiarysendWeather7);
                imageViews.add(mDiarysendWeather8);
                imageViews.add(mDiarysendWeather9);
                mPresenter.setWeather(1);
            }

            @Override
            public void initData() {
                mPresenter.obtainDate(new UserDiaryPageAPresenterImpl.OnReturn() {
                    @Override
                    public void onReturn(String monthDay, String month, String year, String week, String timeString) {
                        mDiarysendDateYear.setText(year);
                        mDiarysendDateMonth.setText(month + monthDay);
                        mDiarysendDateWeek.setText(week);
                        mDiarysendDateTime.setText(timeString);
                        mDiarysendDateYear.postInvalidate();
                        mDiarysendDateMonth.postInvalidate();
                        mDiarysendDateWeek.postInvalidate();
                        mDiarysendDateTime.postInvalidate();
                    }
                });
            }

            @Override
            public void initListener() {
                for (ImageView i : imageViews) {
                    i.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int a = imageViews.indexOf(i);
                            for (int ii = 0; ii < imageViews.size(); ii++) {
                                if (ii == a) {
                                    mPresenter.setWeather(a + 1);
                                    imageViews.get(ii).setBackground(getDrawable(R.drawable.favorite_tag_two));
                                } else {
                                    imageViews.get(ii).setBackground(null);
                                }
                            }
                        }
                    });
                }
                mDiarysendMessage.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        RemoveNullCharacter.setRemoveNull(mDiarysendMessage, s).setSelection(mDiarysendMessage.getText().length());
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        mPresenter.setMessgae(mDiarysendMessage.getText().toString().trim());
                    }
                });
                mDiarysendImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        selectImage.selectOneImg(IMAGENAME, new OnResultCallbackListener<LocalMedia>() {
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                mPresenter.getHeadImage(result, new UserDiaryPageAPresenterImpl.ImageReturn() {
                                    @Override
                                    public void onReturn() {
                                        Glide.with(MyApplication.getContext())
                                                .load(mPresenter.getImagePath())
                                                .centerCrop()
                                                .into(mDiarysendImg);
                                    }
                                });
                            }

                            @Override
                            public void onCancel() {
                                //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                            }
                        });
                    }
                });
                mEnterBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mPresenter.getMessgae() == null || mPresenter.getMessgae().equals("")){
                            MyToastClass.ShowToast(MyApplication.getContext(),"请写入内容");
                        }else {
                            showDialogS(EASYDIALOG);
                            mPresenter.sendNewDiary();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case EASYDIALOG:
                easyDialog.showMyDialog();
                break;
            case 1:
                componentDialog.initData();
                componentDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case EASYDIALOG:
                easyDialog.closeMyDialog();
                break;
            case COMPONENTDIALOG:
                componentDialog.closeMyDialog();
                break;
        }
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
        bar = setMyActivityBar(R.id.userdiary_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE, null);
        bar.barRightImg1(R.drawable.icon_add_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                showDialogS(COMPONENTDIALOG);
            }
        });
    }

    @Override
    public void createRefresh() {
        mySmartRefresh.setMyRefreshListener(new MySmartRefresh.MyRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                getDataDiaryPresenter().initDate();
            }

            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        mySmartRefresh.closeLoadMore();
    }

    @Override
    public void finishRRefresh(int num) {
        mySmartRefresh.finishMyRefresh();
    }

    class AddDiary extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int a = intent.getIntExtra("key_one", 0);
            List<Diary> diaryList = (List<Diary>) intent.getSerializableExtra("key_two");
            switch (a) {
                case 0:
                    mySmartRefresh.finishMyRefresh();
                    mPresenter.addDiary(diaryList,mUserdiaryRecyclerview);
                    break;
                case 1:

                    break;
                case 2:
                    if (easyDialog != null){
                        closeDialog(EASYDIALOG);
                    }
                    closeDialog(COMPONENTDIALOG);
                    createDialog();
                    break;
            }
        }
    }
}
