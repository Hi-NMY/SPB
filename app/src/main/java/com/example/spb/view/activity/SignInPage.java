package com.example.spb.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Sign;
import com.example.spb.presenter.impl.SignInPageAPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.fragment.FragmentSignInSign;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.ISignInPageAView;
import com.gyf.immersionbar.ImmersionBar;
import com.makeramen.roundedimageview.RoundedImageView;

public class SignInPage extends BaseMVPActivity<ISignInPageAView, SignInPageAPresenterImpl> implements ISignInPageAView {

    private RoundedImageView mSignInHeadimg;
    private TextView mSignInUsername;
    private TextView mCoinSizeText;
    private RelativeLayout mExcessR;
    private FragmentSpbAvtivityBar bar;
    private ObtainSignData obtainSignData;
    private AddNewSign addNewSign;
    private FragmentSignInSign fragmentSignInSign;
    private NestedScrollView mScrollview;
    private DialogInter tipDilaog;
    private ImageView mCloseTips;
    private TextView mV5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in_page);
        obtainSignData = new ObtainSignData();
        addNewSign = new AddNewSign();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), addNewSign);
        SpbBroadcast.obtainRecriver(this, InValues.send(R.string.Bcr_sign_data), obtainSignData);
        initActView();
    }

    @Override
    protected SignInPageAPresenterImpl createPresenter() {
        return new SignInPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mScrollview = (NestedScrollView) findViewById(R.id.scrollview);
        mSignInHeadimg = (RoundedImageView) findViewById(R.id.sign_in_headimg);
        mSignInUsername = (TextView) findViewById(R.id.sign_in_username);
        mCoinSizeText = (TextView) findViewById(R.id.coin_size_text);
        mExcessR = (RelativeLayout) findViewById(R.id.excess_r);
        initData();
        setMyListener();
        setActivityBar();
        setBar();
        createDialog();
        showDialogS(0);
    }

    @Override
    protected void initData() {
        fragmentSignInSign = (FragmentSignInSign) getSupportFragmentManager().findFragmentById(R.id.sign_signin_frag);
        mPresenter.obtainUserSignDate(getDataUserMsgPresenter().getUser_account(), new SignInPageAPresenterImpl.OnReturn() {
            @Override
            public void onReturn() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mExcessR.setVisibility(View.GONE);
                    }
                });
            }
        });
        fragmentSignInSign.setLongDay(getDataUserMsgPresenter().getUser_longDay());
        Glide.with(this)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + getDataUserMsgPresenter().getUser_account() + "/HeadImage/myHeadImage.png")
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .into(mSignInHeadimg);
        mSignInUsername.setText(getDataUserMsgPresenter().getUser_name());
        mSignInUsername.postInvalidate();
    }

    public int obtainLongDay() {
        return getDataUserMsgPresenter().getUser_longDay();
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
        tipDilaog = new ComponentDialog(this, R.layout.dialog_sign_trouble, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mCloseTips = (ImageView) view.findViewById(R.id.close_tips);
                mV5 = (TextView) view.findViewById(R.id.v5);
            }

            @Override
            public void initData() {
                SpannableStringBuilder builder = new SpannableStringBuilder(mV5.getText().toString());
                builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(MyApplication.getContext(),R.color.theme_color)), 4, 7, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                mV5.setText(builder);
            }

            @Override
            public void initListener() {
                mCloseTips.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(0);
                    }
                });
            }
        });
    }

    @Override
    public void showDialogS(int i) {
        tipDilaog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        tipDilaog.closeMyDialog();
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
        bar = setMyActivityBar(R.id.sign_in_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpbBroadcast.destroyBrc(obtainSignData);
        SpbBroadcast.destroyBrc(addNewSign);
    }

    class ObtainSignData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Sign sign = (Sign) intent.getSerializableExtra("key_two");
            mCoinSizeText.setText(String.valueOf(sign.getSign_coin()));
            mCoinSizeText.postInvalidate();
        }
    }

    class AddNewSign extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int coin = intent.getIntExtra("key_one", 0);
            mCoinSizeText.setText(Integer.valueOf(mCoinSizeText.getText().toString()) + coin + "");
            mCoinSizeText.postInvalidate();
            if (coin != 10) {
                getDataUserMsgPresenter().setUser_longDay(getDataUserMsgPresenter().getUser_longDay() + 1);
            }
        }
    }
}
