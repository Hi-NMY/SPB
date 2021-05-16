package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface ISendNewBarPageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;
    int RESPONSE_FOUR = 3;
    int RESPONSE_FIVE = 4;
    int RESPONSE_SIX = 5;
    int RESPONSE_SEVEN = 6;
    int RESPONSE_EIGHT = 7;
    int SUCCESS_BAR = 8;
    int ERROR_BAR = 9;

    String TITLE = "发新帖";
    String SENDTITLE = "发布";

    //最大图片数量
    int MAXPOSITION = 4;

    //控件变化
    int STARTVOICE1 = 6;
    int STARTVOICE = 5;
    int MAXIMAGE = 4;
    int HAVEIMAGE = 1;
    int NULLIMAGE = 0;

    //dialog
    int BOTTOMLOCATION = 0;
    int BOTTOMTOPIC = 1;
    int DIALOGLOADING = 2;

    //voice控制
    int VOICE_START = 0;
    int VOICE_END = 1;
    int VOICE_GO = 2;
    int VOICE_STOP = 3;



    public void changeIcon(int a);

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
