package com.example.spb.view.inter;

import android.text.TextWatcher;
import com.example.spb.view.InterTotal.SpbInterOne;

public interface IFirstPageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_ONE = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    public void setEmptyVisibility(boolean id);

    public void setBtnClick(boolean i);

    public void setEmptyPVisibility(boolean id);

    public TextWatcher setPasswordTextWatcher();

    public TextWatcher setAccountTextWatcher();

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
