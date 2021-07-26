package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface ISignInPageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    String TITLE = "签到";

    //正常已签到
    int SIGN_RIGHT = 1;
    //正常未签到
    int SIGN_NO_RIGHT = 2;
    //异常断签
    int SIGN_ERROR_RIGHT = 3;

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
