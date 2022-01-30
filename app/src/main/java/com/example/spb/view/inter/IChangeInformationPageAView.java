package com.example.spb.view.inter;

import com.example.spb.view.InterTotal.SpbInterOne;

import java.util.Arrays;
import java.util.List;

public interface IChangeInformationPageAView extends SpbInterOne {
    //请求标记
    int REQUEST_ONE = 0;
    int REQUEST_TWO = 1;
    int REQUEST_THREE = 2;
    //响应标记
    int RESPONSE_SUCCESS = 0;
    int RESPONSE_TWO = 1;
    int RESPONSE_THREE = 2;

    String TITLE = "修改资料";
    String RIGHTTXT = "保存";
    String CANCEL = "取消";
    String SUBMIT = "确认";
    String BIRTHTITLE = "生日选择";
    String HOMETITLE = "家乡选择";

    //dialog
    int BOTTOMDIALOG = 0;
    int CHANGESIGN = 1;
    int LOADINGDIALOG = 2;

    String[] titleTag = new String[]{"吃鸡", "K歌","话痨","王者荣耀","QQ飞车","间接性高冷","旅行","爱画画","抓娃娃很厉害","汉服","摄影","写作","跳舞","美食","德云社",
            "jk","Lolita","穿搭","夜猫子","民谣","电影","流行","steam","篮球","健身","小说","漫画","独一无二的普通人","铁憨憨","学霸","学渣","直男","直女","铲屎官",
            "美妆达人","大长腿","养老"};
    List<String> tagList = Arrays.asList(titleTag);

    public String getUser_birth();

    public String getUser_home();

    public String getUser_profile();

    public String getUser_name();

    <T> T request(int requestFlag);

    <T> void response(T response, int responseFlag);
}
