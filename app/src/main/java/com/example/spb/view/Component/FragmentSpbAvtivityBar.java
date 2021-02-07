package com.example.spb.view.Component;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPFragment;
import com.example.spb.presenter.impl.SpbAvtivityBarFPresenterImpl;
import com.example.spb.view.InterComponent.ISpbAvtivityBarFView;

public class FragmentSpbAvtivityBar extends BaseMVPFragment<ISpbAvtivityBarFView, SpbAvtivityBarFPresenterImpl> implements ISpbAvtivityBarFView {

    private SpbAvtivityBarFPresenterImpl mISpbAvtivityBarFPresenter;
    private ImageView mBarLeftImg;
    private TextView mBarLeftTxt;
    private ImageView mBarRightImg1;
    private View mBarNewnoticeView;
    private TextView mBarRightTxt1;
    private ImageView mBarRightImg2;
    private TextView mBarRightTxt2;
    private TextView mBarCentralTxt;
    private RelativeLayout mSpbActivityBar;

    @Override
    protected SpbAvtivityBarFPresenterImpl createPresenter() {
        mISpbAvtivityBarFPresenter = new SpbAvtivityBarFPresenterImpl();
        return mISpbAvtivityBarFPresenter;
    }

    @Override
    protected int getLayoutViewId() {
        return R.layout.frag_spb_activity_bar;
    }

    @Override
    protected void initFragView(View view) {
        mSpbActivityBar = (RelativeLayout) view.findViewById(R.id.spb_activity_bar);
        mBarLeftImg = (ImageView)view.findViewById(R.id.bar_left_img);
        mBarLeftTxt = (TextView) view.findViewById(R.id.bar_left_txt);
        mBarRightImg1 = (ImageView)view.findViewById(R.id.bar_right_img1);
        mBarNewnoticeView = (View) view.findViewById(R.id.bar_newnotice_view);
        mBarRightTxt1 = (TextView)view.findViewById(R.id.bar_right_txt1);
        mBarRightImg2 = (ImageView)view.findViewById(R.id.bar_right_img2);
        mBarRightTxt2 = (TextView)view.findViewById(R.id.bar_right_txt2);
        mBarCentralTxt = (TextView)view.findViewById(R.id.bar_central_txt);
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
    public void barLeftImg(int imgPath,OnMyClick onMyClick) {
        mBarLeftImg.setBackgroundResource(imgPath);
        removeInvisible(mBarLeftImg);
        barOnClickListener(mBarLeftImg,onMyClick);
    }

    @Override
    public void barLeftTxt(String title,OnMyClick onMyClick) {
        removeInvisible(mBarLeftTxt);
        barOnClickListener(mBarLeftTxt,onMyClick);
    }

    @Override
    public void barRightImg1(int imgPath,OnMyClick onMyClick) {
        removeInvisible(mBarRightImg1);
        barOnClickListener(mBarRightImg1,onMyClick);
    }

    @Override
    public void barRightTxt1(String title,OnMyClick onMyClick) {
        removeInvisible(mBarRightTxt1);
        barOnClickListener(mBarRightTxt1,onMyClick);
    }

    @Override
    public void barRightImg2(int imgPath,OnMyClick onMyClick) {
        removeInvisible(mBarRightImg2);
        barOnClickListener(mBarRightImg2,onMyClick);
    }

    @Override
    public void barRightTxt2(String title,OnMyClick onMyClick) {
        removeInvisible(mBarRightTxt2);
        barOnClickListener(mBarRightTxt2,onMyClick);
    }

    @Override
    public void barCentralTxt(String title,OnMyClick onMyClick) {
        removeInvisible(mBarCentralTxt);
        barOnClickListener(mBarCentralTxt,onMyClick);
    }

    @Override
    public void barNewNoticeView() {
        removeInvisible(mBarNewnoticeView);
    }

    @Override
    public void barOnClickListener(View view, OnMyClick onMyClick) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMyClick.onClick();
            }
        });
    }

    @Override
    public void removeInvisible(View view) {
        view.setVisibility(view.VISIBLE);
    }

    @Override
    public void setBarBackground(int color) {
        mSpbActivityBar.setBackgroundColor(color);
    }

    public interface OnMyClick {
        void onClick();
    }

}
