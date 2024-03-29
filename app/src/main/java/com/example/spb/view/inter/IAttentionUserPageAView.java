package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

public interface IAttentionUserPageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_SUCCESS = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    String SELECTNUM = "SELECTNUM";

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
