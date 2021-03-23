package com.example.spb.view.inter;

import android.text.TextWatcher;
import com.example.spb.view.InterTotal.SpbInterOne;

public interface IFirstPageAView extends SpbInterOne {
    //响应标记
    int RESPONSE_SUCCESS_ONE = 200;
    int RESPONSE_SUCCESS_TWO = 201;
    int RESPONSE_ONE = 100;//是否注册
    int RESPONSE_ZERO = 000;//错误
    int RESPONSE_ACC = 111;//传递账号
    int RESPONSE_THREE = 300;//验证密码

    String STRINGERROE_ONE = "该账号未注册";
    String STRINGERROE_THREE = "请确认账号或密码正确";
    String STRINGERROE_ZERO = "错误，请重试";

    int DIALOGLOADING = 2;
    int DIALOGUSERNOTICE = 1;
    String STRINGACCESSTRUE = "权限已获取";
    String STRINGACCESSTRUE1 = "部分权限未获取，可能会影响使用";
    String TOASTTXT = "请阅读并同意校吧用户须知";

    public void setEmptyVisibility(boolean id);

    public void setBtnClick(boolean i);

    public void setEmptyPVisibility(boolean id);

    public TextWatcher setPasswordTextWatcher();

    public TextWatcher setAccountTextWatcher();

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
