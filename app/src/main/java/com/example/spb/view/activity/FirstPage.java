package com.example.spb.view.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.User;
import com.example.spb.presenter.impl.FirstPageAPresenterImpl;
import com.example.spb.presenter.littlefun.RemoveNullCharacter;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.inter.IFirstPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.presenter.littlefun.RequestForAccess;
import com.gyf.immersionbar.ImmersionBar;

public class FirstPage extends BaseMVPActivity<IFirstPageAView, FirstPageAPresenterImpl> implements IFirstPageAView, View.OnClickListener {

    private ISpbAvtivityBarFView bar;
    private EditText mAccountNumberEdit;
    private ImageView mEmptyView;
    private ImageView mEnterCheck;
    private TextView mEnterUsernotice;
    private TextView mEnterUserRegistered;
    private Button mEnterNextBtn;
    private static RelativeLayout mEnterR1;
    private EditText mPasswordNumberEdit;
    private ImageView mPasswordEye;
    private ImageView mEmptyViewP;
    private TextView mEnterUserForgotPassword;
    private static RelativeLayout mEnterR2;

    private DialogInter dialogLoading;
    private DialogInter dialogUserNotice;

    private boolean ENTER_CHECK = false;
    private boolean CLICKYES = true;
    private boolean CLICKNO = false;
    private boolean SEE = false;
    private int ENTER_FUN = 0;

    private static Animation animationa;
    private static Animation animationb;

    private User user;

    static {
        animationa = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.enter_anim);
        animationb = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.enter_anim2);
        animationa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mEnterR2.clearAnimation();
                mEnterR1.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationb.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mEnterR2.clearAnimation();
                mEnterR1.clearAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private Handler verifyAccountHanlder = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            response(msg.obj,msg.what);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_page);
        initActView();
    }

    @Override
    protected FirstPageAPresenterImpl createPresenter() {
        return new FirstPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        setActivityBar();
        user = new User();
        mAccountNumberEdit = (EditText) findViewById(R.id.account_number_edit);
        mEmptyView = (ImageView) findViewById(R.id.empty_view);
        mEnterCheck = (ImageView) findViewById(R.id.enter_check);
        mEnterUsernotice = (TextView) findViewById(R.id.enter_usernotice);
        mEnterUserRegistered = (TextView) findViewById(R.id.enter_user_registered);
        mEnterNextBtn = (Button) findViewById(R.id.enter_next_btn);
        mEnterR1 = (RelativeLayout) findViewById(R.id.enter_r1);
        mPasswordNumberEdit = (EditText) findViewById(R.id.password_number_edit);
        mPasswordEye = (ImageView) findViewById(R.id.password_eye);
        mEmptyViewP = (ImageView) findViewById(R.id.empty_view_p);
        mEnterUserForgotPassword = (TextView) findViewById(R.id.enter_user_forgot_password);
        mEnterR2 = (RelativeLayout) findViewById(R.id.enter_r2);

        mEnterUsernotice.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        createDialog();
        setBar();
        setMyListener();
        initData();
    }

    @Override
    protected void initData() {
        RequestForAccess.setNewAccess(this, new RequestForAccess.OnReturn() {
            @Override
            public void allTrue() {
                MyToastClass.ShowToast(MyApplication.getContext(), STRINGACCESSTRUE);
            }

            @Override
            public void someTrue() {
                MyToastClass.ShowToast(MyApplication.getContext(), STRINGACCESSTRUE1);
            }

            @Override
            public void allFalse() {
                finish();
            }

            @Override
            public void toTure() {

            }

            @Override
            public void low() {

            }
        });
    }

    @Override
    public TextWatcher setAccountTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mAccountNumberEdit,s).setSelection(mAccountNumberEdit.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mAccountNumberEdit.getText().toString().trim().length() != 0) {
                    setEmptyVisibility(true);
                } else {
                    setEmptyVisibility(false);
                }
                if (mAccountNumberEdit.getText().toString().trim().length() == 9) {
                    setBtnClick(CLICKYES);
                } else {
                    setBtnClick(CLICKNO);
                }
            }
        };
        return textWatcher;
    }

    @Override
    public TextWatcher setPasswordTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mPasswordNumberEdit,s).setSelection(mPasswordNumberEdit.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (mPasswordNumberEdit.getText().toString().trim().length() != 0) {
                    setEmptyPVisibility(true);
                } else {
                    setEmptyPVisibility(false);
                }
                if (mPasswordNumberEdit.getText().toString().trim().length() >= 8 && mPasswordNumberEdit.getText().toString().trim().length() <=16) {
                    setBtnClick(CLICKYES);
                } else {
                    setBtnClick(CLICKNO);
                }
            }
        };
        return textWatcher;
    }

    @Override
    public void onClick(View v) {
        HideKeyboard.hideboard(v);
        switch (v.getId()) {
            case R.id.enter_next_btn:
                if (ENTER_FUN == 1) {
                    showDialogS(DIALOGLOADING);
                    user.setUser_password(mPasswordNumberEdit.getText().toString().trim());
                    mPresenter.verifyPassword(user,verifyAccountHanlder);
                } else {
                    if (!ENTER_CHECK) {
                        MyToastClass.ShowToast(this, TOASTTXT);
                    } else {
                        showDialogS(DIALOGLOADING);
                        user.setUser_account(mAccountNumberEdit.getText().toString().trim());
                        mPresenter.verifyAccount(user,verifyAccountHanlder);
                    }
                }
                break;
            case R.id.enter_check:
                if (!ENTER_CHECK) {
                    mEnterCheck.setBackground(getDrawable(R.drawable.enter_check));
                    ENTER_CHECK = true;
                    showDialogS(DIALOGUSERNOTICE);
                } else {
                    mEnterCheck.setBackground(getDrawable(R.drawable.enter_nocheck));
                    ENTER_CHECK = false;
                }
                break;
            case R.id.enter_user_registered:
                JumpIntent.startForResultIntent(this, UserRegisteredPage.class, 1);
                break;
            case R.id.empty_view:
                mAccountNumberEdit.setText("");
                setEmptyVisibility(false);
                break;
            case R.id.enter_usernotice:
                showDialogS(DIALOGUSERNOTICE);
                break;
            case R.id.password_eye:
                if (SEE) {
                    mPasswordNumberEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mPasswordEye.setBackground(getDrawable(R.drawable.eye_no));
                    SEE = false;
                } else {
                    mPasswordNumberEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mPasswordEye.setBackground(getDrawable(R.drawable.eye_yes));
                    SEE = true;
                }
                break;
            case R.id.empty_view_p:
                mPasswordNumberEdit.setText("");
                setEmptyPVisibility(false);
                break;
            case R.id.enter_user_forgot_password:

                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.setAccount(data);
    }

    @Override
    public void setEmptyVisibility(boolean id) {
        if (!id) {
            mEmptyView.setVisibility(View.INVISIBLE);
        } else {
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setEmptyPVisibility(boolean id) {
        if (!id) {
            mEmptyViewP.setVisibility(View.INVISIBLE);
        } else {
            mEmptyViewP.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goIntent() {
        closeDialog(DIALOGLOADING);
        if (mPresenter.setFirstLogIn()){
            JumpIntent.startNewIntent(HomePage.class);
        };
        finish();
    }

    @Override
    public void setBtnClick(boolean i) {
        if (i) {
            mEnterNextBtn.setBackground(getDrawable(R.drawable.enter_next_login));
            mEnterNextBtn.setClickable(true);
        } else {
            mEnterNextBtn.setBackground(getDrawable(R.drawable.enter_next_login_false));
            mEnterNextBtn.setClickable(false);
        }
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        switch (responseFlag){
            case RESPONSE_ACC:
                mAccountNumberEdit.setText((String) response);
                break;
            case RESPONSE_SUCCESS_ONE:
                closeDialog(DIALOGLOADING);
                user = (User)response;
                initUserData(user.getUser_account());
                this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mEnterR1.startAnimation(animationa);
                        mEnterR2.startAnimation(animationb);
                        mEnterR1.setVisibility(View.GONE);
                        mEnterR2.setVisibility(View.VISIBLE);
                        mPasswordNumberEdit.setText("");
                        mEnterNextBtn.setClickable(false);
                    }
                });
                ENTER_FUN = 1;
                break;
            case RESPONSE_SUCCESS_TWO:
                mPresenter.connectRong(user.getUser_token(),user.getUser_name(),user.getUser_account());
                break;
            case RESPONSE_ONE:
                closeDialog(DIALOGLOADING);
                MyToastClass.ShowToast(this,STRINGERROE_ONE);
                break;
            case RESPONSE_THREE:
                closeDialog(DIALOGLOADING);
                MyToastClass.ShowToast(this,STRINGERROE_THREE);
                break;
            case RESPONSE_ZERO:
                closeDialog(DIALOGLOADING);
                MyToastClass.ShowToast(this,STRINGERROE_ZERO);
                break;
        }
    }

    @Override
    public void setMyListener() {
        mEnterNextBtn.setOnClickListener(this);
        mEnterCheck.setOnClickListener(this);
        mEnterUserRegistered.setOnClickListener(this);
        mEmptyView.setOnClickListener(this);
        mEnterUsernotice.setOnClickListener(this);
        mPasswordEye.setOnClickListener(this);
        mEmptyViewP.setOnClickListener(this);
        mEnterUserForgotPassword.setOnClickListener(this);
        mAccountNumberEdit.addTextChangedListener(setAccountTextWatcher());
        mPasswordNumberEdit.addTextChangedListener(setPasswordTextWatcher());
        mEnterNextBtn.setClickable(false);
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
        bar = setMyActivityBar(R.id.first_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                if (ENTER_FUN == 1) {
                    mEnterR1.startAnimation(animationb);
                    mEnterR2.startAnimation(animationa);
                    mEnterR1.setVisibility(View.VISIBLE);
                    mEnterR2.setVisibility(View.GONE);
                    setBtnClick(CLICKYES);
                    ENTER_FUN = 0;
                } else {
                    finish();
                }
            }
        });
    }

    private TextView dialogText;
    private TextView mClickFalse;
    private TextView mClickTrue;

    @Override
    public void createDialog() {
        dialogLoading = new EasyDialog(this, R.drawable.loading);
        dialogLoading.setCancelable(false);
        dialogUserNotice = new ComponentDialog(this, R.layout.dialog_user_notice, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mClickFalse = (TextView) view.findViewById(R.id.click_false);
                mClickTrue = (TextView) view.findViewById(R.id.click_true);
                dialogText = (TextView) view.findViewById(R.id.textView);
                dialogText.setMovementMethod(ScrollingMovementMethod.getInstance());
            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {
                mClickFalse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(DIALOGUSERNOTICE);
                        mEnterCheck.setBackground(getDrawable(R.drawable.enter_nocheck));
                        ENTER_CHECK = false;
                    }
                });
                mClickTrue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mEnterCheck.setBackground(getDrawable(R.drawable.enter_check));
                        ENTER_CHECK = true;
                        closeDialog(DIALOGUSERNOTICE);
                    }
                });
            }
        });
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case DIALOGUSERNOTICE:
                dialogUserNotice.showMyDialog();
                break;
            case DIALOGLOADING:
                dialogLoading.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case DIALOGUSERNOTICE:
                dialogUserNotice.closeMyDialog();
                break;
            case DIALOGLOADING:
                dialogLoading.closeMyDialog();
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && ENTER_FUN == 1) {
            mEnterR1.startAnimation(animationb);
            mEnterR2.startAnimation(animationa);
            mEnterR1.setVisibility(View.VISIBLE);
            mEnterR2.setVisibility(View.GONE);
            setBtnClick(CLICKYES);
            ENTER_FUN = 0;
        }else {
            finish();
        }
        return false;
    }
}
