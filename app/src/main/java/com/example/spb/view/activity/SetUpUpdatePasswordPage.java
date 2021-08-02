package com.example.spb.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SetUpUpdatePasswordPageAPresenterImpl;
import com.example.spb.presenter.littlefun.RemoveNullCharacter;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISetUpUpdatePasswordPageAView;
import com.gyf.immersionbar.ImmersionBar;

public class SetUpUpdatePasswordPage extends BaseMVPActivity<ISetUpUpdatePasswordPageAView, SetUpUpdatePasswordPageAPresenterImpl> implements ISetUpUpdatePasswordPageAView, View.OnClickListener {

    private DialogInter dialogLoading;
    private FragmentSpbAvtivityBar bar;
    private EditText mUpdatepasswordOld;
    private EditText mUpdatepasswordPassword;
    private ImageView mUpdatepasswordPasswordEye;
    private EditText mUpdatepasswordAgain;
    private Button mRegStarBtn;
    private boolean SEE = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_update_password_page);
        initActView();
    }

    @Override
    protected SetUpUpdatePasswordPageAPresenterImpl createPresenter() {
        return new SetUpUpdatePasswordPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mUpdatepasswordOld = (EditText) findViewById(R.id.updatepassword_old);
        mUpdatepasswordPassword = (EditText) findViewById(R.id.updatepassword_password);
        mUpdatepasswordPasswordEye = (ImageView) findViewById(R.id.updatepassword_password_eye);
        mUpdatepasswordAgain = (EditText) findViewById(R.id.updatepassword_again);
        mRegStarBtn = (Button) findViewById(R.id.reg_star_btn);
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
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
        dialogLoading.setCancelable(false);
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
        mUpdatepasswordPasswordEye.setOnClickListener(this);
        mRegStarBtn.setOnClickListener(this);
        mUpdatepasswordOld.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mUpdatepasswordOld,s).setSelection(mUpdatepasswordOld.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUpdatepasswordPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mUpdatepasswordPassword,s).setSelection(mUpdatepasswordPassword.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mUpdatepasswordAgain.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mUpdatepasswordAgain,s).setSelection(mUpdatepasswordAgain.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

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
        bar = setMyActivityBar(R.id.setup_updatepassword_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reg_star_btn:
                if (submit()){
                    showDialogS(0);
                    mPresenter.updatePassword(getDataUserMsgPresenter().getUser_account(), mUpdatepasswordPassword.getText().toString(),
                            mUpdatepasswordOld.getText().toString(), new SetUpUpdatePasswordPageAPresenterImpl.OnReturn() {
                        @Override
                        public void onReturn() {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    closeDialog(0);
                                    MyToastClass.ShowToast(MyApplication.getContext(),"密码修改成功");
                                    finish();
                                }
                            });
                        }
                    });
                }
                break;
            case R.id.updatepassword_password_eye:
                if (SEE) {
                    mUpdatepasswordPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    mUpdatepasswordPasswordEye.setBackground(getDrawable(R.drawable.eye_no));
                    SEE = false;
                } else {
                    mUpdatepasswordPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    mUpdatepasswordPasswordEye.setBackground(getDrawable(R.drawable.eye_yes));
                    SEE = true;
                }
                break;
        }
    }

    private boolean submit() {
        // validate
        String old = mUpdatepasswordOld.getText().toString().trim();
        if (TextUtils.isEmpty(old)) {
            MyToastClass.ShowToast(this, "请输入原始密码");
            return false;
        }

        String password = mUpdatepasswordPassword.getText().toString().trim();
        if (TextUtils.isEmpty(password)) {
            MyToastClass.ShowToast(this, "请输入8-16位合法密码");
            return false;
        }

        String again = mUpdatepasswordAgain.getText().toString().trim();
        if (TextUtils.isEmpty(again)) {
            MyToastClass.ShowToast(this, "请确认密码");
            return false;
        }else {
            if (!password.equals(again)) {
                MyToastClass.ShowToast(this,"请确认两次密码一致");
                return false;
            }
        }
        return true;
    }
}
