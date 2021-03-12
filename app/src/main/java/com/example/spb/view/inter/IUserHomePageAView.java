package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface IUserHomePageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    String TITLE = "消息";
    String RETURN = "再按一次退出应用";
    String STRINGACCESS = "权限获取失败，请重试或进入手机设置修改";

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
