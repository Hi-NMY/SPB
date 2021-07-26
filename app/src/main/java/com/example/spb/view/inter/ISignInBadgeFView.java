package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface ISignInBadgeFView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    int LOADINGDIALOG = 1;
    int TEXTDIALOG = 2;

    String CANCEL = "取消";
    String SUBMIT = "确认";
    String TITLE = "生日选择";

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
