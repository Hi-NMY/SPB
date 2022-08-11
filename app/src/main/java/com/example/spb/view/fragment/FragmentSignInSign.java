package com.example.spb.view.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.entity.Sign;
import com.example.spb.presenter.impl.SignInSignFPresenterImpl;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyResolve;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.activity.SignInPage;
import com.example.spb.view.inter.ISignInSignFView;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.spb.view.inter.ISignInPageAView.*;

public class FragmentSignInSign extends BaseMVPFragment<ISignInSignFView, SignInSignFPresenterImpl> implements ISignInSignFView {

    private ObtainSignData obtainSignData;
    private AddNewSign addNewSign;
    private Sign sign = null;
    private SignInPage signInPage;
    private TextView mLongSignDay;
    private Map<Integer, ImageView> imgMap = null;
    private Map<Integer, View> noSignBgMap = null;
    private Map<Integer, TextView> signTextMap = null;
    private DialogInter loadingDialog;
    private DialogInter coinDialog;
    private ImageView mClose;
    private TextView mCoinSize;
    private int nowSignDay = 0;
    private int longDay = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signInPage = (SignInPage) getActivity();
        obtainSignData = new ObtainSignData();
        addNewSign = new AddNewSign();
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_sign_data), obtainSignData);
        SpbBroadcast.obtainRecriver(MyApplication.getContext(), InValues.send(R.string.Bcr_add_sign), addNewSign);
    }

    @Override
    protected SignInSignFPresenterImpl createPresenter() {
        return new SignInSignFPresenterImpl();
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.frag_sign_in_sign;
    }

    @Override
    protected void initFragView(View view) {
        imgMap = new HashMap<>();
        noSignBgMap = new HashMap<>();
        signTextMap = new HashMap<>();
        mLongSignDay = view.findViewById(R.id.long_sign_day);
        ImageView mImg = view.findViewById(R.id.img);
        View mNoSignBg = view.findViewById(R.id.no_sign_bg);
        TextView mSignText = view.findViewById(R.id.sign_text);
        ImageView mImg1 = view.findViewById(R.id.img1);
        View mNoSignBg1 = view.findViewById(R.id.no_sign_bg1);
        TextView mSignText1 = view.findViewById(R.id.sign_text1);
        ImageView mImg2 = view.findViewById(R.id.img2);
        View mNoSignBg2 = view.findViewById(R.id.no_sign_bg2);
        TextView mSignText2 = view.findViewById(R.id.sign_text2);
        ImageView mImg3 = view.findViewById(R.id.img3);
        View mNoSignBg3 = view.findViewById(R.id.no_sign_bg3);
        TextView mSignText3 = view.findViewById(R.id.sign_text3);
        ImageView mImg4 = view.findViewById(R.id.img4);
        View mNoSignBg4 = view.findViewById(R.id.no_sign_bg4);
        TextView mSignText4 = view.findViewById(R.id.sign_text4);
        ImageView mImg5 = view.findViewById(R.id.img5);
        View mNoSignBg5 = view.findViewById(R.id.no_sign_bg5);
        TextView mSignText5 = view.findViewById(R.id.sign_text5);
        ImageView mImg6 = view.findViewById(R.id.img6);
        View mNoSignBg6 = view.findViewById(R.id.no_sign_bg6);
        TextView mSignText6 = view.findViewById(R.id.sign_text6);
        imgMap.put(1, mImg);
        imgMap.put(2, mImg1);
        imgMap.put(3, mImg2);
        imgMap.put(4, mImg3);
        imgMap.put(5, mImg4);
        imgMap.put(6, mImg5);
        imgMap.put(7, mImg6);
        noSignBgMap.put(1, mNoSignBg);
        noSignBgMap.put(2, mNoSignBg1);
        noSignBgMap.put(3, mNoSignBg2);
        noSignBgMap.put(4, mNoSignBg3);
        noSignBgMap.put(5, mNoSignBg4);
        noSignBgMap.put(6, mNoSignBg5);
        noSignBgMap.put(7, mNoSignBg6);
        signTextMap.put(1, mSignText);
        signTextMap.put(2, mSignText1);
        signTextMap.put(3, mSignText2);
        signTextMap.put(4, mSignText3);
        signTextMap.put(5, mSignText4);
        signTextMap.put(6, mSignText5);
        signTextMap.put(7, mSignText6);
    }

    public void setLongDay(int longday) {
        this.longDay = longday;
        mLongSignDay.setText("已累计签到 " + longday + " 天");
        SpannableStringBuilder builder = new SpannableStringBuilder(mLongSignDay.getText().toString());
        builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color6)), 6, 6 + String.valueOf(longday).length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mLongSignDay.setText(builder);
    }

    @Override
    protected void initData() {
        if (sign == null){
            return;
        }
        List<Integer> days = MyResolve.InSignDay(sign.getSign_day());
        Collections.sort(days);
        for (int i = 1; i <= imgMap.size(); i++) {
            if (days.contains(i)) {
                imgMap.get(i).setBackground(signInPage.getDrawable(R.drawable.icon_already_sign));
                imgMap.get(i).setVisibility(View.VISIBLE);
                noSignBgMap.get(i).setVisibility(View.INVISIBLE);
                signTextMap.get(i).setVisibility(View.INVISIBLE);
            }else{
                imgMap.get(i).setVisibility(View.VISIBLE);
                noSignBgMap.get(i).setVisibility(View.VISIBLE);
                signTextMap.get(i).setVisibility(View.INVISIBLE);
            }
        }
        if (sign.getSign_right() == 1){
            nowSignDay = days.size() + 1;
            createDialog();
            imgMap.get(days.size() + 1).setVisibility(View.INVISIBLE);
            noSignBgMap.get(days.size() + 1).setVisibility(View.VISIBLE);
            signTextMap.get(days.size() + 1).setVisibility(View.VISIBLE);
            noSignBgMap.get(days.size() + 1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDialogS(LOADINGDIALOG);
                    mPresenter.addNewSign(signInPage.getDataUserMsgPresenter().getUser_account(), nowSignDay);
                }
            });
        }
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
        coinDialog = new ComponentDialog(signInPage, R.layout.dialog_coin, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {
                mClose = view.findViewById(R.id.close);
                mCoinSize = view.findViewById(R.id.coin_size);
            }

            @Override
            public void initData() {
                mCoinSize.setText("+" + nowSignDay);
                mCoinSize.postInvalidate();
            }

            @Override
            public void initListener() {
                mClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeDialog(COINDIALOG);
                    }
                });
            }
        });
        coinDialog.setNoBg();
    }

    @Override
    public void showDialogS(int i) {
        switch (i) {
            case LOADINGDIALOG:
                loadingDialog.showMyDialog();
                break;
            case COINDIALOG:
                coinDialog.showMyDialog();
                break;
        }
    }

    @Override
    public void closeDialog(int i) {
        switch (i) {
            case LOADINGDIALOG:
                loadingDialog.closeMyDialog();
                break;
            case COINDIALOG:
                coinDialog.closeMyDialog();
                break;
        }
    }

    @Override
    public void setMyListener() {

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
        SpbBroadcast.destroyBrc(addNewSign);
    }

    class ObtainSignData extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int fun = intent.getIntExtra("key_one", 0);
            sign = (Sign) intent.getSerializableExtra("key_two");
            if (fun == SIGN_RIGHT) {
                initData();
            } else {
                MyToastClass.ShowToast(MyApplication.getContext(), "错误！请重试");
            }
        }
    }

    class AddNewSign extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            int coin = intent.getIntExtra("key_one", 0);
            if (coin != 10) {
                closeDialog(LOADINGDIALOG);
                showDialogS(COINDIALOG);
                String signDay = intent.getStringExtra("key_two");
                sign.setSign_day(signDay);
                sign.setSign_right(0);
                setLongDay(longDay + 1);
                initData();
            }
        }
    }
}
