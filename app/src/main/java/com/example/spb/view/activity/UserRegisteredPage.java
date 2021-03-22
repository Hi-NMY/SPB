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
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.User;
import com.example.spb.presenter.impl.UserRegisteredPageAPresenterImpl;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.example.spb.view.littlefun.JumpIntent;
import com.example.spb.view.Component.MyToastClass;
import com.gyf.immersionbar.ImmersionBar;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

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
    private DialogInter bottomDialog;
    private SpbSelectImage spbSelectImage;

    private String imagePath;

    private boolean SEE = false;

    private User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registered_page);
        initActView();
    }

    @Override
    protected UserRegisteredPageAPresenterImpl createPresenter() {
        return new UserRegisteredPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        spbSelectImage = new SelectImage(this);
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
        closeDialog(DIALOGLOADING);
        if (requestFlag == RESPONSE_ZERO){
            MyToastClass.ShowToast(this,"错误，请重试");
        }
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        switch (responseFlag){
            case IMAGE_SUCCESS:
                Glide.with(this)
                        .load((String) response)
                        .centerCrop()
                        .into(mRegisteredUserHeadimg);
                imagePath = (String) response;
                break;
            case RESPONSE_ONE:
                MyToastClass.ShowToast(this,STRINGERRORONE);
                closeDialog(DIALOGLOADING);
                break;
            case RESPONSE_THREE:
                MyToastClass.ShowToast(this,STRINGERRORTHREE);
                closeDialog(DIALOGLOADING);
                break;
            case RESPONSE_FORE:
                MyToastClass.ShowToast(this,STRINGERRORFORE);
                closeDialog(DIALOGLOADING);
                break;
            case RESPONSE_SUCCESS:
                closeDialog(DIALOGLOADING);
                MyToastClass.ShowToast(this,STRINGSUCCESS);
                JumpIntent.startSetResultIntent(this, 1, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(STRINGEXTRA, account);
                    }
                });
                break;
        }
    }

    private TextView mDialogCamera;
    private TextView mDialogPhotoalbum;
    private TextView mDialogClose;
    @Override
    public void createDialog() {
        dialogLoading = new EasyDialog(this, R.drawable.loading);
        dialogLoading.setCancelable(false);
        bottomDialog = new ComponentDialog(this, R.layout.dialog_selectpicture, R.style.bottomdialog, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mDialogCamera = (TextView) view.findViewById(R.id.dialog_camera);
                mDialogPhotoalbum = (TextView) view.findViewById(R.id.dialog_photoalbum);
                mDialogClose = (TextView) view.findViewById(R.id.dialog_close);
            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {
                mDialogClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMDIALOG);
                    }
                });
                mDialogPhotoalbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMDIALOG);
                        spbSelectImage.selectOneImg(IMAGENAME,new OnResultCallbackListener<LocalMedia>(){
                            @Override
                            public void onResult(List<LocalMedia> result) {
                                mPresenter.getHeadImage(result);
                            }

                            @Override
                            public void onCancel() {
                                //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                            }
                        });
                    }
                });
                mDialogCamera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(BOTTOMDIALOG);
                        spbSelectImage.selectCameraImg(IMAGENAME, new OnResultCallbackListener() {
                            @Override
                            public void onResult(List result) {
                                mPresenter.getHeadImage(result);
                            }

                            @Override
                            public void onCancel() {
                                //MyToastClass.ShowToast(MyApplication.getContext(),"出错了");
                            }
                        });
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
            case DIALOGLOADING:
                dialogLoading.showMyDialog();
                break;
            case BOTTOMDIALOG:
                bottomDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case DIALOGLOADING:
                dialogLoading.closeMyDialog();
                break;
            case BOTTOMDIALOG:
                bottomDialog.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {
        mRegStarBtn.setOnClickListener(this);
        mRegPasswordEye.setOnClickListener(this);
        mRegisteredUserHeadimg.setOnClickListener(this);
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .keyboardEnable(true)
                .statusBarDarkFont(true)
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
            }
        });
    }

    private User setUser(){
        user = new User();
        user.setUser_head_image(imagePath);
        user.setUser_account(account);
        user.setUser_name(name);
        user.setUser_password(password);
        return user;
    }

    @Override
    public void onClick(View v) {
        HideKeyboard.hideboard(v);
        switch (v.getId()) {
            case R.id.reg_star_btn:
                if (submit()) {
                    showDialogS(DIALOGLOADING);
                    mPresenter.registerUser(setUser());
                }
                break;
            case R.id.reg_password_eye:
                if (SEE) {
                    mRegUserPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mRegPasswordEye.setBackground(getDrawable(R.drawable.eye_no));
                    SEE = false;
                } else {
                    mRegUserPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mRegPasswordEye.setBackground(getDrawable(R.drawable.eye_yes));
                    SEE = true;
                }
                break;
            case R.id.registered_user_headimg:
                showDialogS(BOTTOMDIALOG);
                break;
        }
    }

    private String name;
    private String account;
    private String password;

    private boolean submit() {
        if (TextUtils.isEmpty(imagePath)) {
            MyToastClass.ShowToast(this, "请设置头像");
            return false;
        }

        name = mRegUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyToastClass.ShowToast(this, "请输入合法用户名");
            return false;
        }

        account = mRegUserAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            MyToastClass.ShowToast(this, "请输入账号");
            return false;
        }

        password = mRegUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password) || mRegUserPassword.length() < 8) {
            MyToastClass.ShowToast(this, "请输入合法密码");
            return false;
        }

        String again = mRegUserPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            MyToastClass.ShowToast(this, "请确认密码");
            return false;
        } else {
            if (!password.equals(again)) {
                MyToastClass.ShowToast(this, "请确认两次密码一致");
                return false;
            }
        }
        return true;
    }
}
