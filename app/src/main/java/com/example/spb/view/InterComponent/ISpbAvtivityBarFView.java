package com.example.spb.view.InterComponent;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;

public interface ISpbAvtivityBarFView {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);

    public ImageView getmBarLeftImg();

    public TextView getmBarLeftTxt() ;

    public ImageView getmBarRightImg1() ;

    public View getmBarNewnoticeView();

    public TextView getmBarRightTxt1();

    public ImageView getmBarRightImg2();

    public TextView getmBarRightTxt2();

    public TextView getmBarCentralTxt();

    public void barLeftImg(int imgPath, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barLeftTxt(String title, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barRightImg1(int imgPath, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barRightTxt1(String title, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barRightImg2(int imgPath, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barRightTxt2(String title, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barCentralTxt(String title, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barSearchView(FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barSignView(FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void barNewNoticeView();

    public void barOnClickListener(View view, FragmentSpbAvtivityBar.OnMyClick onMyClick);

    public void removeInvisible(View view);

    public void searchShow(View view,int fun);

    public void setBarBackground(int color);

    public void setBarBackgroundcolor(int color);

}
