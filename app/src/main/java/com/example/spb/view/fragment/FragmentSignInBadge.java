package com.example.spb.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Sign;
import com.example.spb.presenter.impl.SignInBadgeFPresenterImpl;
import com.example.spb.presenter.utils.*;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.activity.SignInPage;
import com.example.spb.view.inter.ISignInBadgeFView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.Calendar;
import java.util.Date;

public class FragmentSignInBadge extends BaseMVPFragment<ISignInBadgeFView, SignInBadgeFPresenterImpl> implements ISignInBadgeFView, View.OnClickListener {

    private RoundedImageView mBadgeStar;
    private Button mBadgeStarBtn;
    private RoundedImageView mBadgeCertification;
    private Button mBadgeCertificationBtn;
    private Button mBadgeLikeTwoBtn;
    private Button mBadgeLikeOneBtn;
    private Button mBadgeLikeThreeBtn;
    private Button mBadgeTaskTwoBtn;
    private Button mBadgeTaskOneBtn;
    private Button mBadgeTaskThreeBtn;
    private ObtainSignData obtainSignData;
    private SignInPage signInPage;
    private DialogInter loadingDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInPage = (SignInPage) getActivity();
        obtainSignData = new ObtainSignData();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), obtainSignData);
    }

    @Override
    protected SignInBadgeFPresenterImpl createPresenter() {
        return new SignInBadgeFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.frag_sign_in_badge;
    }

    @Override
    protected void initFragView(View view) {
        mBadgeStar = view.findViewById(R.id.badge_star);
        mBadgeStarBtn = view.findViewById(R.id.badge_star_btn);
        mBadgeCertification = view.findViewById(R.id.badge_certification);
        mBadgeCertificationBtn = view.findViewById(R.id.badge_certification_btn);
        mBadgeLikeTwoBtn = view.findViewById(R.id.badge_like_two_btn);
        mBadgeLikeOneBtn = view.findViewById(R.id.badge_like_one_btn);
        mBadgeLikeThreeBtn = view.findViewById(R.id.badge_like_three_btn);
        mBadgeTaskTwoBtn = view.findViewById(R.id.badge_task_two_btn);
        mBadgeTaskOneBtn = view.findViewById(R.id.badge_task_one_btn);
        mBadgeTaskThreeBtn = view.findViewById(R.id.badge_task_three_btn);
        createDialog();
        setMyListener();
    }

    @Override
    protected void initData() {

    }

    public void initBadgeView() {
        mPresenter.obtainBarLike(signInPage.getDataUserMsgPresenter().getUser_account(), new SignInBadgeFPresenterImpl.OnReturn() {
            @Override
            public void onReturn(int likeNum) {
                signInPage.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mPresenter.getLikeBadgeNum() == 0) {
                            setNullBadge(mBadgeLikeOneBtn);
                            setNullBadge(mBadgeLikeTwoBtn);
                            setNullBadge(mBadgeLikeThreeBtn);
                            if (likeNum >= 15 && likeNum < 60) {
                                setReceiveBadge(mBadgeLikeOneBtn);
                            } else if (likeNum >= 60 && likeNum < 150) {
                                setReceiveBadge(mBadgeLikeOneBtn);
                                setReceiveBadge(mBadgeLikeTwoBtn);
                            } else if (likeNum >= 150) {
                                setReceiveBadge(mBadgeLikeOneBtn);
                                setReceiveBadge(mBadgeLikeTwoBtn);
                                setReceiveBadge(mBadgeLikeThreeBtn);
                            }
                        } else if (mPresenter.getLikeBadgeNum() == 1) {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                            setNullBadge(mBadgeLikeTwoBtn);
                            setNullBadge(mBadgeLikeThreeBtn);
                            if (likeNum >= 60 && likeNum < 150) {
                                setReceiveBadge(mBadgeLikeTwoBtn);
                            } else if (likeNum >= 150) {
                                setReceiveBadge(mBadgeLikeTwoBtn);
                                setReceiveBadge(mBadgeLikeThreeBtn);
                            }
                        } else if (mPresenter.getLikeBadgeNum() == 2) {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                            setAlreadyBadge(mBadgeLikeTwoBtn);
                            setNullBadge(mBadgeLikeThreeBtn);
                            if (likeNum >= 150) {
                                setReceiveBadge(mBadgeLikeThreeBtn);
                            }
                        } else if (mPresenter.getLikeBadgeNum() == 3) {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                            setAlreadyBadge(mBadgeLikeTwoBtn);
                            setAlreadyBadge(mBadgeLikeThreeBtn);
                        }
                    }
                });
            }
        });

        int longDay = signInPage.obtainLongDay();
        if (mPresenter.getSignBadgeNum() == 0) {
            setNullBadge(mBadgeTaskOneBtn);
            setNullBadge(mBadgeTaskTwoBtn);
            setNullBadge(mBadgeTaskThreeBtn);
            if (longDay >= 10 && longDay < 50) {
                setReceiveBadge(mBadgeTaskOneBtn);
            } else if (longDay >= 50 && longDay < 110) {
                setReceiveBadge(mBadgeTaskOneBtn);
                setReceiveBadge(mBadgeTaskTwoBtn);
            } else if (longDay >= 110) {
                setReceiveBadge(mBadgeTaskOneBtn);
                setReceiveBadge(mBadgeTaskTwoBtn);
                setReceiveBadge(mBadgeTaskThreeBtn);
            }
        } else if (mPresenter.getSignBadgeNum() == 1) {
            setAlreadyBadge(mBadgeTaskOneBtn);
            setNullBadge(mBadgeTaskTwoBtn);
            setNullBadge(mBadgeTaskThreeBtn);
            if (longDay >= 50 && longDay < 110) {
                setReceiveBadge(mBadgeTaskTwoBtn);
            } else if (longDay >= 110) {
                setReceiveBadge(mBadgeTaskTwoBtn);
                setReceiveBadge(mBadgeTaskThreeBtn);
            }
        } else if (mPresenter.getSignBadgeNum() == 2) {
            setAlreadyBadge(mBadgeTaskOneBtn);
            setAlreadyBadge(mBadgeTaskTwoBtn);
            setNullBadge(mBadgeTaskThreeBtn);
            if (longDay >= 110) {
                setReceiveBadge(mBadgeTaskThreeBtn);
            }
        } else if (mPresenter.getSignBadgeNum() == 3) {
            setAlreadyBadge(mBadgeTaskOneBtn);
            setAlreadyBadge(mBadgeTaskTwoBtn);
            setAlreadyBadge(mBadgeTaskThreeBtn);
        }

        if (mPresenter.getCtSign() == 0) {
            setYesView(mBadgeCertificationBtn);
            //glide展示认证徽章
            mBadgeCertification.setTag(0);
            Glide.with(signInPage)
                    .load(InValues.send(R.string.local_certification_badge))
                    .into(mBadgeCertification);
        } else {
            mBadgeCertification.setTag(1);
        }
        if (!mPresenter.getStarBadge().equals("")) {
            setYesView(mBadgeStarBtn);
            //glide展示星座徽章
            mBadgeStar.setTag(0);
            Glide.with(signInPage)
                    .load(InValues.send(R.string.prefix_badge_img) + mPresenter.getStarBadge())
                    .into(mBadgeStar);
        } else {
            mBadgeStar.setTag(1);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.badge_star:
                if (mBadgeStar.getTag().equals(0)) {
                    MyToastClass.ShowToast(MyApplication.getContext(), "已认证，不可再次认证哦");
                } else {
                    Calendar startDate = Calendar.getInstance();
                    startDate.set(1985, 0, 1);
                    Calendar endDate = Calendar.getInstance();
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(2000, 4, 20);
                    TimePickerView pvTime = new TimePickerBuilder(signInPage, new OnTimeSelectListener() {
                        @Override
                        public void onTimeSelect(Date date, View v) {
                            showDialogS(LOADINGDIALOG);
                            mPresenter.setStarBadge(MyDateClass.getConstellationEImg(MyDateClass.getStringDateMonth(date)));
                            mPresenter.obtainStarBadge(signInPage.getDataUserMsgPresenter().getUser_account(), new SignInBadgeFPresenterImpl.OnReturn() {
                                @Override
                                public void onReturn(int likeNum) {
                                    signInPage.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            closeDialog(LOADINGDIALOG);
                                            setYesView(mBadgeStarBtn);
                                            //glide展示星座徽章
                                            mBadgeStar.setTag(0);
                                            Glide.with(signInPage)
                                                    .load(InValues.send(R.string.prefix_badge_img) + mPresenter.getStarBadge())
                                                    .into(mBadgeStar);
                                        }
                                    });
                                }
                            });
                        }
                    }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                            .setCancelText(CANCEL)
                            .setSubmitText(SUBMIT)
                            .setTitleText(TITLE)
                            .setSubmitColor(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color))//确定按钮文字颜色
                            .setCancelColor(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color))//取消按钮文字颜色
                            .setOutSideCancelable(true)
                            .setDate(selectedDate)
                            .setRangDate(startDate, endDate)
                            .isCenterLabel(true)
                            .build();
                    pvTime.show();
                }
                break;
            case R.id.badge_certification:
                if (mBadgeCertification.getTag().equals(0)) {
                    MyToastClass.ShowToast(MyApplication.getContext(), "已认证，不可再次认证哦");
                } else {

                }
                break;
            case R.id.badge_like_two_btn:
                if (mBadgeLikeTwoBtn.getTag().equals(1)) {
                    sendObtainLikeNum(2);
                }
                break;
            case R.id.badge_like_one_btn:
                if (mBadgeLikeOneBtn.getTag().equals(1)) {
                    sendObtainLikeNum(1);
                }
                break;
            case R.id.badge_like_three_btn:
                if (mBadgeLikeThreeBtn.getTag().equals(1)) {
                    sendObtainLikeNum(3);
                }
                break;
            case R.id.badge_task_two_btn:
                if (mBadgeTaskTwoBtn.getTag().equals(1)) {
                    sendObtainSignNum(2);
                }
                break;
            case R.id.badge_task_one_btn:
                if (mBadgeTaskOneBtn.getTag().equals(1)) {
                    sendObtainSignNum(1);
                }
                break;
            case R.id.badge_task_three_btn:
                if (mBadgeTaskThreeBtn.getTag().equals(1)) {
                    sendObtainSignNum(3);
                }
                break;
        }
    }

    public void sendObtainLikeNum(int a) {
        showDialogS(LOADINGDIALOG);
        mPresenter.obtainLikeBadge(signInPage.getDataUserMsgPresenter().getUser_account(), a, new SignInBadgeFPresenterImpl.OnReturn() {
            @Override
            public void onReturn(int likeNum) {
                signInPage.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog(LOADINGDIALOG);
                        if (likeNum == 1) {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                        } else if (likeNum == 2) {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                            setAlreadyBadge(mBadgeLikeTwoBtn);
                        } else {
                            setAlreadyBadge(mBadgeLikeOneBtn);
                            setAlreadyBadge(mBadgeLikeTwoBtn);
                            setAlreadyBadge(mBadgeLikeThreeBtn);
                        }
                    }
                });
            }
        });
    }

    public void sendObtainSignNum(int a) {
        mPresenter.obtainSignBadge(signInPage.getDataUserMsgPresenter().getUser_account(), a, new SignInBadgeFPresenterImpl.OnReturn() {
            @Override
            public void onReturn(int likeNum) {
                signInPage.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeDialog(LOADINGDIALOG);
                        if (likeNum == 1) {
                            setAlreadyBadge(mBadgeTaskOneBtn);
                        } else if (likeNum == 2) {
                            setAlreadyBadge(mBadgeTaskOneBtn);
                            setAlreadyBadge(mBadgeTaskTwoBtn);
                        } else {
                            setAlreadyBadge(mBadgeTaskOneBtn);
                            setAlreadyBadge(mBadgeTaskTwoBtn);
                            setAlreadyBadge(mBadgeTaskThreeBtn);
                        }
                    }
                });
            }
        });
    }

    public void setYesView(Button thisButton) {
        thisButton.setText("已认证");
        thisButton.setBackground(signInPage.getDrawable(R.drawable.badge_obtain_bg));
    }

    public void setNullBadge(Button thisButton) {
        thisButton.setTag(0);
    }

    public void setReceiveBadge(Button thisButton) {
        thisButton.setTag(1);
        thisButton.setText("领取");
        thisButton.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.beijing));
        thisButton.setBackground(signInPage.getDrawable(R.drawable.badge_obtain_bg));
    }

    public void setAlreadyBadge(Button thisButton) {
        thisButton.setTag(2);
        thisButton.setText("已获得");
        thisButton.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.qihei));
        thisButton.setBackground(signInPage.getDrawable(R.drawable.sign_bg));
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
        loadingDialog = new EasyDialog(signInPage, R.drawable.loading);
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case LOADINGDIALOG:
                loadingDialog.showMyDialog();
                break;
            case TEXTDIALOG:

                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case LOADINGDIALOG:
                loadingDialog.closeMyDialog();
                break;
            case TEXTDIALOG:

                break;
        }
    }

    @Override
    public void setMyListener() {
        mBadgeStar.setOnClickListener(this);
        mBadgeCertification.setOnClickListener(this);
        mBadgeStarBtn.setOnClickListener(this);
        mBadgeCertificationBtn.setOnClickListener(this);
        mBadgeLikeTwoBtn.setOnClickListener(this);
        mBadgeLikeOneBtn.setOnClickListener(this);
        mBadgeLikeThreeBtn.setOnClickListener(this);
        mBadgeTaskTwoBtn.setOnClickListener(this);
        mBadgeTaskOneBtn.setOnClickListener(this);
        mBadgeTaskThreeBtn.setOnClickListener(this);
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
        SpbBroadcast.destroyBrc(obtainSignData);
    }

    class ObtainSignData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Sign sign = (Sign) intent.getSerializableExtra("key_two");
            if (DataVerificationTool.isEmpty(sign.getSign_like_badge())) {
                mPresenter.setLikeBadgeNum(0);
            } else {
                mPresenter.setLikeBadgeNum(MyResolve.InBadge(sign.getSign_like_badge()).size());
            }
            if (DataVerificationTool.isEmpty(sign.getSign_task_badge())) {
                mPresenter.setSignBadgeNum(0);
            } else {
                mPresenter.setSignBadgeNum(MyResolve.InBadge(sign.getSign_task_badge()).size());
            }
            mPresenter.setCtSign(sign.getSign_ct_badge());
            mPresenter.setStarBadge(DataVerificationTool.isEmpty(sign.getSign_star_badge()) ? "" : sign.getSign_star_badge());
            initBadgeView();
        }
    }
}
