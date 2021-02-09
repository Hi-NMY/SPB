package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserRegisteredPageAPresenterImpl;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.view.littlefun.MyToastClass;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;

public class UserRegisteredPage extends BaseMVPActivity<IUserRegisteredPageAView, UserRegisteredPageAPresenterImpl> implements IUserRegisteredPageAView, View.OnClickListener {

    private ISpbAvtivityBarFView bar;
    private RoundedImageView mRegisteredUserHeadimg;
    private EditText mRegUserName;
    private EditText mRegUserAccount;
    private EditText mRegUserPassword;
    private ImageView mRegPasswordEye;
    private EditText mRegUserPasswordAgain;
    private Button mRegStarBtn;

    private DialogInter dialogLoading;

    private boolean SEE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registered_page);
        initActView();
    }

    @Override
    protected UserRegisteredPageAPresenterImpl createPresenter() {
        return new UserRegisteredPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        mRegisteredUserHeadimg = (RoundedImageView) findViewById(R.id.registered_user_headimg);
        mRegUserName = (EditText) findViewById(R.id.reg_user_name);
        mRegUserAccount = (EditText) findViewById(R.id.reg_user_account);
        mRegUserPassword = (EditText) findViewById(R.id.reg_user_password);
        mRegPasswordEye = (ImageView) findViewById(R.id.reg_password_eye);
        mRegUserPasswordAgain = (EditText) findViewById(R.id.reg_user_password_again);
        mRegStarBtn = (Button) findViewById(R.id.reg_star_btn);
        createDialog();
        setBar();
        setActivityBar();
        setMyListener();
        initData();
    }

    @Override
    protected void initData() {

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
        dialogLoading = new EasyDialog(this, R.drawable.loading);
    }

    @Override
    public void showDialogS(int i) {
        dialogLoading.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        dialogLoading.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        mRegStarBtn.setOnClickListener(this);
        mRegPasswordEye.setOnClickListener(this);
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.user_egisterd_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                JumpIntent.finishAct(UserRegisteredPage.this);
//                JumpIntent.startSetResultIntent(UserRegisteredPage.this, 1, new JumpIntent.SetMsg() {
//                     @Override
//                    public void setMessage(Intent intent) {
//
//                    }
//                });
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_star_btn:
                if (submit()){
                    JumpIntent.startSetResultIntent(this, 1, new JumpIntent.SetMsg() {
                        @Override
                        public void setMessage(Intent intent) {
                            intent.putExtra("AccountNumber",account);
                        }
                    });
                }
                break;
            case R.id.reg_password_eye:
                if (SEE){
                    mRegUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mRegPasswordEye.setBackground(getDrawable(R.drawable.eye_no));
                    SEE = false;
                }else {
                    mRegUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mRegPasswordEye.setBackground(getDrawable(R.drawable.eye_yes));
                    SEE = true;
                }
                break;
        }
    }

    private String name;
    private String account;
    private String password;
    private boolean submit() {
        // validate
        name = mRegUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyToastClass.ShowToast(this,"请输入合法用户名");
            return false;
        }

        account = mRegUserAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            MyToastClass.ShowToast(this,"请输入账号");
            return false;
        }

        password = mRegUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            MyToastClass.ShowToast(this,"请输入合法密码");
            return false;
        }

        String again = mRegUserPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            MyToastClass.ShowToast(this,"请确认密码");
            return false;
        }else {
            if (!password.equals(again)){
                MyToastClass.ShowToast(this,"请确认两次密码一致");
                return false;
            }
        }
        return true;
    }
}
