package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface IUserRegisteredPageAView extends SpbInterOne {

    //响应标记
    int IMAGE_SUCCESS = 001;//选择图片
    int RESPONSE_SUCCESS = 200;  //注册成功
    int RESPONSE_ERROR = 4004;


    String IMAGENAME = "UserHeadImage.png";
    String STRINGEXTRA = "AccountNumber";

    String WHAT_ONE = "请设置头像";
    String WHAT_TWO = "请输入合法用户名";
    String WHAT_THREE = "请输入账号";
    String WHAT_FORE = "请输入合法密码";
    String WHAT_FIVE = "请确认密码";
    String WHAT_SIX = "请确认两次密码一致";

    int DIALOGLOADING = 1;
    int BOTTOMDIALOG = 2;

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
