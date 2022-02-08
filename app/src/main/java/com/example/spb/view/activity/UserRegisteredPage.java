package com.example.spb.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Dto.UserRegisteredDto;
import com.example.spb.presenter.impl.UserRegisteredPageAPresenterImpl;
import com.example.spb.presenter.utils.RemoveNullCharacter;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.Component.SelectImage;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IUserRegisteredPageAView;
import com.example.spb.view.utils.HideKeyboard;
import com.example.spb.view.utils.JumpIntent;
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

    private UserRegisteredDto userRegisteredDto = null;

    private Handler userHanlder = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            response(null, msg.what);
        }
    };

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
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        switch (responseFlag) {
            case IMAGE_SUCCESS:
                Glide.with(this)
                        .load((String) response)
                        .centerCrop()
                        .into(mRegisteredUserHeadimg);
                imagePath = (String) response;
                break;
            case RESPONSE_SUCCESS:
                closeDialog(DIALOGLOADING);
                JumpIntent.startSetResultIntent(this, 1, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(STRINGEXTRA, account);
                    }
                });
                break;
            case RESPONSE_ERROR:
                closeDialog(DIALOGLOADING);
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
                        spbSelectImage.selectOneImg(IMAGENAME, new OnResultCallbackListener<LocalMedia>() {
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
        mRegUserName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mRegUserName, s).setSelection(mRegUserName.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRegUserAccount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mRegUserAccount, s).setSelection(mRegUserAccount.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRegUserPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mRegUserPassword, s).setSelection(mRegUserPassword.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mRegUserPasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mRegUserPasswordAgain, s).setSelection(mRegUserPasswordAgain.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private UserRegisteredDto setUser() {
        userRegisteredDto = new UserRegisteredDto();
        userRegisteredDto.setUser_account(account);
        userRegisteredDto.setUser_name(name);
        userRegisteredDto.setUser_password(password);
        return userRegisteredDto;
    }

    @Override
    public void onClick(View v) {
        HideKeyboard.hideboard(v);
        switch (v.getId()) {
            case R.id.reg_star_btn:
                if (submit()) {
                    showDialogS(DIALOGLOADING);
                    mPresenter.registerUser(setUser(), userHanlder);
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
            MyToastClass.ShowToast(this, WHAT_ONE);
            return false;
        }

        name = mRegUserName.getText().toString().trim();
        if (TextUtils.isEmpty(name)) {
            MyToastClass.ShowToast(this, WHAT_TWO);
            return false;
        }

        account = mRegUserAccount.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            MyToastClass.ShowToast(this, WHAT_THREE);
            return false;
        }

        password = mRegUserPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password) || mRegUserPassword.length() < 8) {
            MyToastClass.ShowToast(this, WHAT_FORE);
            return false;
        }

        String again = mRegUserPasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            MyToastClass.ShowToast(this, WHAT_FIVE);
            return false;
        } else {
            if (!password.equals(again)) {
                MyToastClass.ShowToast(this, WHAT_SIX);
                return false;
            }
        }
        return true;
    }
}
