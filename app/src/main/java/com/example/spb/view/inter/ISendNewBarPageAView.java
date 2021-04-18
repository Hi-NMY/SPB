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

    public void changeIcon(int a);

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
