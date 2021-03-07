package com.example.spb.view.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.content.ContextCompat;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.ChangeInformationPageAPresenterImpl;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.inter.IChangeInformationPageAView;
import com.gyf.immersionbar.ImmersionBar;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChangeInformationPage extends BaseMVPActivity<IChangeInformationPageAView, ChangeInformationPageAPresenterImpl>
        implements IChangeInformationPageAView, View.OnClickListener {

    private FragmentSpbAvtivityBar bar;
    private String TITLE = "修改资料";
    private String RIGHTTXT = "保存";

    private DialogInter bottomDialog;
    private RelativeLayout mRFavorite;
    private RelativeLayout mRBirth;
    private RelativeLayout mRHome;
    private TextView mChangeinformationBirth;
    private TextView mChangeinformationHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_information_page);
        initActView();
    }

    @Override
    protected ChangeInformationPageAPresenterImpl createPresenter() {
        return new ChangeInformationPageAPresenterImpl();
    }

    @Override
    protected void initActView() {
        mRFavorite = (RelativeLayout) findViewById(R.id.r_favorite);
        mRBirth = (RelativeLayout) findViewById(R.id.r_birth);
        mRHome = (RelativeLayout) findViewById(R.id.r_home);
        mChangeinformationHome = (TextView) findViewById(R.id.changeinformation_home);
        mChangeinformationBirth = (TextView) findViewById(R.id.changeinformation_birth);
        mPresenter.initCityJsonData();
        setMyListener();
        createDialog();
        setBar();
        setActivityBar();
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
        bottomDialog = new ComponentDialog(this, R.layout.dialog_favorite, R.style.dialogHomeSend, new ComponentDialog.InitDialog() {
            @Override
            public void initView(View view) {

            }

            @Override
            public void initData() {

            }

            @Override
            public void initListener() {

            }
        });
        bottomDialog.setBottomStyle();
        bottomDialog.setAnimation(R.style.bottomdialog_animStyle);
    }

    @Override
    public void showDialogS(int i) {
        bottomDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {
        mRFavorite.setOnClickListener(this);
        mRBirth.setOnClickListener(this);
        mRHome.setOnClickListener(this);
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
        bar = setMyActivityBar(R.id.changeinformation_actbar);
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

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.r_favorite:
                showDialogS(0);
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
                        mChangeinformationBirth.setText(MyDateClass.getStringDate(date) + "   " + MyDateClass.getConstellation(MyDateClass.getStringDateMonth(date)));
                    }
                }).setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                        .setCancelText("取消")
                        .setSubmitText("确认")
                        .setTitleText("生日选择")
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

                        String tx = opt1tx + "   " + opt2tx + "   " + opt3tx;
                        mChangeinformationHome.setText(tx);
                    }
                })

                        .setSubmitText("确定")//确定按钮文字
                        .setCancelText("取消")//取消按钮文字
                        .setTitleText("家乡选择")//标题
                        .setSubmitColor(ContextCompat.getColor(this, R.color.theme_color))
                        .setCancelColor(ContextCompat.getColor(this, R.color.theme_color))
                        .isCenterLabel(false)
                        .setCyclic(false, false, false)//循环与否
                        .setSelectOptions(0, 0, 0)//设置默认选中项
                        .setOutSideCancelable(true)//点击外部dismiss default true
                        .isRestoreItem(true)//切换时是否还原，设置默认选中第一项。
                        .build();
                pvOptions.setPicker(mPresenter.getOptions1Items(), mPresenter.getOptions2Items(),mPresenter.getOptions3Items());//三级选择器
                pvOptions.show();
                break;
        }
    }
}
