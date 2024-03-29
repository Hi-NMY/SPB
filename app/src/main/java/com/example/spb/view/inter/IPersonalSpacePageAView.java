package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterTwo;

public interface IPersonalSpacePageAView extends SpbInterTwo {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_SUCCESS = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    int RETURN_HEADIMAGE = 0;

    int IMAGEDIALOG = 1;
    int BADGEDIALOG = 2;

    String IMAGENAME = "UserHeadImage.png";
    String DIALOGTITLE = "设置头像";
    String DIALOGTITLE2 = "设置背景";
    String IMAGENAME2 = "UserBgImage.png";
    String STRINGEXTRA = "SELECTNUM";
    int PAGENUMONE = 0;
    int PAGENUMTWO = 1;

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
