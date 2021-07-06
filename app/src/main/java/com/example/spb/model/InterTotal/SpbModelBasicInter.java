package com.example.spb.model.InterTotal;

import com.example.spb.presenter.callback.MyCallBack;

public interface SpbModelBasicInter<T> {

    public static int REGISTEREDPAGE = 910;
    public static int FIRSTPAGE_ONE = 920;
    public static int FIRSTPAGE_TWO = 930;
    public static int FIRSTPAGE_THREE = 931;
    public static int DATAUSER_UPDATE_ONE = 940;
    public static int DATAUSER_UPDATE_TWO = 941;


    public static int DATAATTENTIONTOPIC_ADD_ONE = 110;
    public static int DATAATTENTIONTOPIC_SELECT_ONE = 120;
    public static int DATAATTENTIONTOPIC_UPDATE_ONE = 130;
    public static int DATAATTENTIONTOPIC_DELETE_ONE = 140;

    public static int DATACOLLECTBAR_ADD_ONE = 210;
    public static int DATACOLLECTBAR_SELECT_ONE = 220;
    public static int DATACOLLECTBAR_SELECT_TWO = 221;
    public static int DATACOLLECTBAR_UPDATE_ONE = 230;
    public static int DATACOLLECTBAR_DELETE_ONE = 240;

    public static int DATADIARY_ADD_ONE = 310;
    public static int DATADIARY_SELECT_ONE = 320;
    public static int DATADIARY_UPDATE_ONE = 330;
    public static int DATADIARY_DELETE_ONE = 340;

    public static int DATAFOLLOWED_ADD_ONE = 410;
    public static int DATAFOLLOWED_SELECT_ONE = 420;
    public static int DATAFOLLOWED_SELECT_TWO = 421;
    public static int DATAFOLLOWED_UPDATE_ONE = 430;
    public static int DATAFOLLOWED_DELETE_ONE = 440;

    public static int DATAFOLLOW_ADD_ONE = 510;
    public static int DATAFOLLOW_SELECT_ONE = 520;
    public static int DATAFOLLOW_SELECT_TWO = 521;
    public static int DATAFOLLOW_UPDATE_ONE = 530;
    public static int DATAFOLLOW_DELETE_ONE = 540;

    public static int DATALIKE_ADD_ONE = 610;
    public static int DATALIKE_SELECT_ONE = 620;
    public static int DATALIKE_UPDATE_ONE = 630;
    public static int DATALIKE_UPDATE_TWO = 631;
    public static int DATALIKE_DELETE_ONE = 640;
    public static int DATALIKE_DELETE_TWO = 641;

    public static int DATATOPIC_ADD_ONE = 720;
    public static int DATATOPIC_SELECT_ONE = 710;
    public static int DATATOPIC_SELECT_TWO = 711;
    public static int DATATOPIC_SELECT_THREE= 712;
    public static int DATATOPIC_SELECT_FOUR= 713;
    public static int DATATOPIC_UPDATE_ONE = 730;
    public static int DATATOPIC_DELETE_ONE = 740;

    public static int DATABAR_ADD_ONE = 810;
    public static int DATABAR_SELECT_ONE = 820;
    public static int DATABAR_SELECT_TWO = 821;
    public static int DATABAR_SELECT_THREE = 822;
    public static int DATABAR_SELECT_FOUR = 823;
    public static int DATABAR_SELECT_FIVE = 824;
    public static int DATABAR_SELECT_SIX = 825;
    public static int DATABAR_UPDATE_ONE = 830;
    public static int DATABAR_UPDATE_TWO = 831;
    public static int DATABAR_UPDATE_THREE = 832;
    public static int DATABAR_UPDATE_FOUR = 833;
    public static int DATABAR_DELETE_ONE = 840;

    public static int DATACOMMENT_ADD_ONE = 910;
    public static int DATACOMMENT_SELECT_ONE = 920;
    public static int DATACOMMENT_UPDATE_ONE = 930;
    public static int DATACOMMENT_DELETE_ONE = 940;

    public void addData(int fun,T data, MyCallBack callBack);

    public void selectData(int fun,T data,MyCallBack callBack);

    public void updateData(int fun,T data,MyCallBack callBack);

    public void deleteData(int fun,T data, MyCallBack callBack);

}
