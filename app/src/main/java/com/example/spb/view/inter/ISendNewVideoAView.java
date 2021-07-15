package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface ISendNewVideoAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;
    int VIDEO_OBTAIN = 3;
    int RESPONSE_SEVEN = 6;
    int RESPONSE_EIGHT = 7;
    int RESPONSE_SUCCESS = 200;
    int RESPONSE_ERROR = 404;

    String TITLE = "发视频";
    String SENDTITLE = "发布";

    //dialog
    int BOTTOMLOCATION = 0;
    int BOTTOMTOPIC = 1;
    int DIALOGLOADING = 2;

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
