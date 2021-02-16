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
    private RelativeLayout mSignRlt;
    private RelativeLayout mSearchRlt;

    private boolean KEYLEFTIMG = false;
    private boolean KEYLEFTTXT = false;
    private boolean KEYRIGHTIMG1 = false;
    private boolean KEYRIGHTTXT1= false;
    private boolean KEYRIGHTIMG2 = false;
    private boolean KEYRIGHTTXT2 = false;
    private boolean KEYNEWNOTICE = false;
    private boolean KEYCENTRALTXT = false;
    private boolean KEYSEARCHRLT = false;
    private boolean KEYSIGHRLT = false;

    private View viewS;

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
        viewS = view;
        mSpbActivityBar = (RelativeLayout) view.findViewById(R.id.spb_activity_bar);
        mBarLeftImg = (ImageView) view.findViewById(R.id.bar_left_img);
        mBarLeftTxt = (TextView) view.findViewById(R.id.bar_left_txt);
        mBarRightImg1 = (ImageView) view.findViewById(R.id.bar_right_img1);
        mBarNewnoticeView = (View) view.findViewById(R.id.bar_newnotice_view);
        mBarRightTxt1 = (TextView) view.findViewById(R.id.bar_right_txt1);
        mBarRightImg2 = (ImageView) view.findViewById(R.id.bar_right_img2);
        mBarRightTxt2 = (TextView) view.findViewById(R.id.bar_right_txt2);
        mBarCentralTxt = (TextView) view.findViewById(R.id.bar_central_txt);
        mSearchRlt = (RelativeLayout)view.findViewById(R.id.search_rlt);
        mSignRlt = (RelativeLayout)view.findViewById(R.id.sign_rlt);
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
    public void barLeftImg(int imgPath, OnMyClick onMyClick) {
        KEYLEFTIMG = true;
        mBarLeftImg.setBackgroundResource(imgPath);
        removeInvisible(mBarLeftImg);
        barOnClickListener(mBarLeftImg, onMyClick);
    }

    @Override
    public void barLeftTxt(String title, OnMyClick onMyClick) {
        KEYLEFTTXT = true;
        removeInvisible(mBarLeftTxt);
        mBarLeftTxt.setText(title);
        barOnClickListener(mBarLeftTxt, onMyClick);
    }

    @Override
    public void barRightImg1(int imgPath, OnMyClick onMyClick) {
        KEYRIGHTIMG1 = true;
        mBarRightImg1.setBackgroundResource(imgPath);
        removeInvisible(mBarRightImg1);
        barOnClickListener(mBarRightImg1, onMyClick);
    }

    @Override
    public void barRightTxt1(String title, OnMyClick onMyClick) {
        KEYRIGHTTXT1 = true;
        removeInvisible(mBarRightTxt1);
        mBarRightTxt1.setText(title);
        barOnClickListener(mBarRightTxt1, onMyClick);
    }

    @Override
    public void barRightImg2(int imgPath, OnMyClick onMyClick) {
        KEYRIGHTIMG2 = true;
        mBarRightImg2.setBackgroundResource(imgPath);
        removeInvisible(mBarRightImg2);
        barOnClickListener(mBarRightImg2, onMyClick);
    }

    @Override
    public void barRightTxt2(String title, OnMyClick onMyClick) {
        KEYRIGHTTXT2 = true;
        removeInvisible(mBarRightTxt2);
        mBarRightTxt2.setText(title);
        barOnClickListener(mBarRightTxt2, onMyClick);
    }

    @Override
    public void barCentralTxt(String title, OnMyClick onMyClick) {
        KEYCENTRALTXT = true;
        removeInvisible(mBarCentralTxt);
        mBarCentralTxt.setText(title);
        barOnClickListener(mBarCentralTxt, onMyClick);
    }

    @Override
    public void barSearchView(OnMyClick onMyClick) {
        KEYSEARCHRLT = true;
        removeInvisible(mSearchRlt);
        barOnClickListener(mSearchRlt,onMyClick);
    }

    @Override
    public void barSignView(OnMyClick onMyClick) {
        KEYSIGHRLT = true;
        removeInvisible(mSignRlt);
        barOnClickListener(mSignRlt,onMyClick);
    }

    @Override
    public void barNewNoticeView() {
        KEYNEWNOTICE = true;
        removeInvisible(mBarNewnoticeView);
    }

    @Override
    public void barOnClickListener(View view, OnMyClick onMyClick) {
        if (onMyClick !=null){
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onMyClick.onClick();
                }
            });
        }
    }



    @Override
    public void removeInvisible(View view) {
        searchShow(view,0);
        view.setVisibility(view.VISIBLE);
    }

    @Override
    public void searchShow(View view,int fun) {
        if (fun == 0){
            if (!KEYLEFTIMG) {
                mBarLeftImg.setVisibility(view.INVISIBLE);
            }
            if (!KEYLEFTTXT) {
                mBarLeftTxt.setVisibility(view.INVISIBLE);
            }
            if (!KEYRIGHTIMG1) {
                mBarRightImg1.setVisibility(view.INVISIBLE);
            }
            if (!KEYNEWNOTICE) {
                mBarNewnoticeView.setVisibility(view.INVISIBLE);
            }
            if (!KEYRIGHTTXT1) {
                mBarRightTxt1.setVisibility(view.INVISIBLE);
            }
            if (!KEYRIGHTIMG2) {
                mBarRightImg2.setVisibility(view.INVISIBLE);
            }
            if (!KEYRIGHTTXT2) {
                mBarRightTxt2.setVisibility(view.INVISIBLE);
            }
            if (!KEYCENTRALTXT) {
                mBarCentralTxt.setVisibility(view.INVISIBLE);
            }
            if (!KEYSEARCHRLT) {
                mSearchRlt.setVisibility(view.INVISIBLE);
            }
            if (!KEYSIGHRLT) {
                mSignRlt.setVisibility(view.GONE);
            }
        }else {
            KEYLEFTIMG = false;
            KEYLEFTTXT = false;
            KEYRIGHTIMG1 = false;
            KEYRIGHTTXT1= false;
            KEYRIGHTIMG2 = false;
            KEYRIGHTTXT2 = false;
            KEYNEWNOTICE = false;
            KEYCENTRALTXT = false;
            KEYSEARCHRLT = false;
            KEYSIGHRLT = false;
        }
    }

    @Override
    public void setBarBackground(int color) {
        mSpbActivityBar.setBackgroundColor(color);
    }

    public interface OnMyClick {
        void onClick();
    }

}
