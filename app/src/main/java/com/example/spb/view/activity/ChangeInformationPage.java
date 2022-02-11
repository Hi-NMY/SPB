package com.example.spb.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Dto.UserInformationDto;
import com.example.spb.presenter.impl.ChangeInformationPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.presenter.utils.RemoveNullCharacter;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.example.spb.view.utils.HideKeyboard;
import com.gyf.immersionbar.ImmersionBar;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.Calendar;
import java.util.Date;

public class ChangeInformationPage extends BaseMVPActivity<IChangeInformationPageAView, ChangeInformationPageAPresenterImpl>
        implements IChangeInformationPageAView, View.OnClickListener {

    private DialogInter bottomDialog;
    private DialogInter dialogSign;
    private DialogInter loadingDialog;
    private RelativeLayout mRFavorite;
    private RelativeLayout mRBirth;
    private RelativeLayout mRHome;
    private TextView mChangeinformationBirth;
    private TextView mChangeinformationHome;
    private EditText mChangeinformationUsername;
    private TextView mChangeinformationFavorite;
    private TextView mChangeinformationSign;
    private RelativeLayout mRSign;
    private TextView mX1;
    private EditText mEditText;
    private TextView mClickTrue;
    private TextView mFavoriteClose;
    private TextView mFavoriteNext;
    private TagFlowLayout mFavoriteTag;
    private LayoutInflater layoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_page);
        layoutInflater = LayoutInflater.from(this);
        initActView();
    }

    @Override
    protected ChangeInformationPageAPresenterImpl createPresenter() {
        return new ChangeInformationPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mRFavorite = findViewById(R.id.r_favorite);
        mRBirth = findViewById(R.id.r_birth);
        mRHome = findViewById(R.id.r_home);
        mChangeinformationHome = findViewById(R.id.changeinformation_home);
        mChangeinformationBirth = findViewById(R.id.changeinformation_birth);
        mChangeinformationUsername = findViewById(R.id.changeinformation_username);
        mChangeinformationFavorite = findViewById(R.id.changeinformation_favorite);
        mChangeinformationSign = findViewById(R.id.changeinformation_sign);
        mRSign = findViewById(R.id.r_sign);
        mX1 = findViewById(R.id.x1);
        mPresenter.initCityJsonData();
        initData();
        setMyListener();
        createDialog();
        setBar();
        setActivityBar();
    }

    @Override
    protected void initData() {
        mChangeinformationUsername.setText(getDataUserMsgPresenter().getUser_name());
        if (!getDataUserMsgPresenter().getUser_birth().equals("")) {
            mChangeinformationBirth.setText(getDataUserMsgPresenter().getUser_birth() + "   " + MyDateClass.getConstellation(getDataUserMsgPresenter().getUser_birth().substring(5)));
        }
        mChangeinformationFavorite.setText(getDataUserMsgPresenter().getUser_favorite());
        mChangeinformationHome.setText(getDataUserMsgPresenter().getUser_home());
        mChangeinformationSign.setText(getDataUserMsgPresenter().getUser_profile());
        mChangeinformationUsername.postInvalidate();
        mChangeinformationBirth.postInvalidate();
        mChangeinformationFavorite.postInvalidate();
        mChangeinformationHome.postInvalidate();
        mChangeinformationSign.postInvalidate();
        mX1.setText(mChangeinformationUsername.getText().length() + "/10");
    }

    @Override
    public String getUser_birth() {
        return getDataUserMsgPresenter().getUser_birth();
    }

    @Override
    public String getUser_home() {
        return getDataUserMsgPresenter().getUser_home();
    }

    @Override
    public String getUser_profile() {
        return getDataUserMsgPresenter().getUser_profile();
    }

    @Override
    public String getUser_name() {
        return getDataUserMsgPresenter().getUser_name();
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
                    case 200:
                        getDataUserMsgPresenter().setUpdateUserMsg((UserInformationDto) response);
                        SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_refresh_userMsg), 0, null);
                        closeDialog(LOADINGDIALOG);
                        finish();
                        mPresenter.updateRong(getDataUserMsgPresenter().getUser_account(), getDataUserMsgPresenter().getUser_name());
                        break;
                    default:
                        closeDialog(LOADINGDIALOG);
                        break;
                }
            }
        });
    }

    @Override
    public void createDialog() {
        loadingDialog = new EasyDialog(this, R.drawable.loading);
        loadingDialog.setCancelable(false);
        dialogSign = new ComponentDialog(this, R.layout.dialog_change_sign, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mEditText = view.findViewById(R.id.edit_text);
                mClickTrue = view.findViewById(R.id.click_true);
            }

            @Override
            public void initData() {
                mEditText.setText(mChangeinformationSign.getText());
            }

            @Override
            public void initListener() {
                mClickTrue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mChangeinformationSign.setText(mEditText.getText().toString());
                        mPresenter.setUserProfile(mEditText.getText().toString());
                        closeDialog(CHANGESIGN);
                    }
                });
            }
        });
        dialogSign.setCancelable(true);
        bottomDialog = new ComponentDialog(this, R.layout.dialog_favorite, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mFavoriteClose = view.findViewById(R.id.favorite_close);
                mFavoriteNext = view.findViewById(R.id.favorite_next);
                mFavoriteTag = view.findViewById(R.id.favorite_tag);
            }

            @Override
            public void initData() {
                mPresenter.getUserFavorite(getDataUserMsgPresenter().getUser_favorite());
                mFavoriteTag.setAdapter(new TagAdapter<String>(tagList) {
                    @Override
                    public View getView(FlowLayout parent, int position, String tag) {
                        View view = layoutInflater.inflate(R.layout.item_favorite_tag_one, mFavoriteTag, false);
                        TextView textView = view.findViewById(R.id.text);
                        textView.setText(tag);
                        return view;
                    }

                    @Override
                    public void onSelected(int position, View view) {
                        super.onSelected(position, view);
                        if (mPresenter.uf.size() == 9) {
                            MyToastClass.ShowToast(MyApplication.getContext(), "最多只能选择9个哦");
                        } else {
                            RelativeLayout relativeLayout = view.findViewById(R.id.r);
                            TextView textView = view.findViewById(R.id.text);
                            mPresenter.addFavorite(textView.getText().toString());
                            textView.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color));
                            relativeLayout.setBackground(getDrawable(R.drawable.favorite_tag_two));
                        }
                    }

                    @Override
                    public void unSelected(int position, View view) {
                        super.unSelected(position, view);
                        RelativeLayout relativeLayout = view.findViewById(R.id.r);
                        TextView textView = view.findViewById(R.id.text);
                        mPresenter.clearFavorite(textView.getText().toString());
                        textView.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.grey));
                        relativeLayout.setBackground(getDrawable(R.drawable.favorite_tag_one));
                    }
                });
                for (int a = 0; a < titleTag.length; a++) {
                    if (mPresenter.verificationString(titleTag[a])) {
                        mFavoriteTag.getChildAt(a).performClick();
                    }
                }
            }

            @Override
            public void initListener() {
                mFavoriteClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMDIALOG);
                        mPresenter.deleteFa();
                    }
                });
                mFavoriteNext.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMDIALOG);
                        mPresenter.setUserFavorite();
                    }
                });
            }
        });
        bottomDialog.setBottomStyle();
        bottomDialog.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case CHANGESIGN:
                dialogSign.showMyDialog();
                break;
            case BOTTOMDIALOG:
                bottomDialog.showMyDialog();
                bottomDialog.initData();
                break;
            case LOADINGDIALOG:
                loadingDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case CHANGESIGN:
                dialogSign.closeMyDialog();
                break;
            case BOTTOMDIALOG:
                bottomDialog.closeMyDialog();
                break;
            case LOADINGDIALOG:
                loadingDialog.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {
        mRFavorite.setOnClickListener(this);
        mRBirth.setOnClickListener(this);
        mRHome.setOnClickListener(this);
        mRSign.setOnClickListener(this);
        mChangeinformationUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mChangeinformationUsername, s).setSelection(mChangeinformationUsername.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.setUserName(mChangeinformationUsername.getText().toString().trim());
                mX1.setText(mChangeinformationUsername.getText().length() + "/10");
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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.changeinformation_actbar);
        TextView rightTxt = bar.getmBarRightTxt1();
        rightTxt.setTextColor(ContextCompat.getColor(this, R.color.theme_color));
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.close_black, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barRightTxt1(RIGHTTXT, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                showDialogS(LOADINGDIALOG);
                mPresenter.updateUser(getDataUserMsgPresenter().getUser_account());
            }
        });
    }

    @Override
    public void onClick(View v) {
        HideKeyboard.hideboard(v);
        switch (v.getId()) {
            case R.id.r_favorite:
                showDialogS(BOTTOMDIALOG);
                break;
            case R.id.r_birth:
                Calendar startDate = Calendar.getInstance();
                startDate.set(1985, 0, 1);
                Calendar endDate = Calendar.getInstance();
                Calendar selectedDate = Calendar.getInstance();
                selectedDate.set(2000, 4, 20);
                TimePickerView pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {
                        mPresenter.setUserBirth(date);
                        mChangeinformationBirth.setText(MyDateClass.getStringDate(date) + "   " + MyDateClass.getConstellation(MyDateClass.getStringDateMonth(date)));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText(CANCEL)
                        .setSubmitText(SUBMIT)
                        .setTitleText(BIRTHTITLE)
                        .setSubmitColor(ContextCompat.getColor(this, R.color.theme_color))//确定按钮文字颜色
                        .setCancelColor(ContextCompat.getColor(this, R.color.theme_color))//取消按钮文字颜色
                        .setOutSideCancelable(true)
                        .setDate(selectedDate)
                        .setRangDate(startDate, endDate)
                        .isCenterLabel(true)
                        .build();
                pvTime.show();
                break;
            case R.id.r_home:
                OptionsPickerView pvOptions = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        String opt1tx = mPresenter.getOptions1Items().size() > 0 ?
                                mPresenter.getOptions1Items().get(options1).getPickerViewText() : "";

                        String opt2tx = mPresenter.getOptions2Items().size() > 0
                                && mPresenter.getOptions2Items().get(options1).size() > 0 ?
                                mPresenter.getOptions2Items().get(options1).get(options2) : "";

                        String opt3tx = mPresenter.getOptions2Items().size() > 0
                                && mPresenter.getOptions3Items().get(options1).size() > 0
                                && mPresenter.getOptions3Items().get(options1).get(options2).size() > 0 ?
                                mPresenter.getOptions3Items().get(options1).get(options2).get(options3) : "";

                        String tx = opt1tx + "-" + opt2tx + "-" + opt3tx;
                        mPresenter.setUserHome(tx);
                        mChangeinformationHome.setText(tx);
                    }
                })

                        .setSubmitText(SUBMIT)//确定按钮文字
                        .setCancelText(CANCEL)//取消按钮文字
                        .setTitleText(HOMETITLE)//标题
                        .setSubmitColor(ContextCompat.getColor(this, R.color.theme_color))
                        .setCancelColor(ContextCompat.getColor(this, R.color.theme_color))
                        .isCenterLabel(false)
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(0, 0, 0)//设置默认选中项
                        .setOutSideCancelable(true)//点击外部dismiss default true
                        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                        .build();
                pvOptions.setPicker(mPresenter.getOptions1Items(), mPresenter.getOptions2Items(), mPresenter.getOptions3Items());//三级选择器
                pvOptions.show();
                break;
            case R.id.r_sign:
                showDialogS(CHANGESIGN);
                break;
        }
    }
}
