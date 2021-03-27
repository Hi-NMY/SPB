package com.example.spb.model.InterTotal;

import com.example.spb.presenter.callback.MyCallBack;

public interface SpbModelBasicInter<T> {

    public static int REGISTEREDPAGE = 1;
    public static int FIRSTPAGE_ONE = 1;
    public static int FIRSTPAGE_TWO = 2;

    public static int DATEATTENTIONTOPIC_ADD_ONE = 110;
    public static int DATEATTENTIONTOPIC_SELECT_ONE = 120;
    public static int DATEATTENTIONTOPIC_UPDATE_ONE = 130;
    public static int DATEATTENTIONTOPIC_DELETE_ONE = 140;

    public static int DATACOLLECTBAR_ADD_ONE = 210;
    public static int DATACOLLECTBAR_SELECT_ONE = 220;
    public static int DATACOLLECTBAR_UPDATE_ONE = 230;
    public static int DATACOLLECTBAR_DELETE_ONE = 240;

    public static int DATADIARY_ADD_ONE = 310;
    public static int DATADIARY_SELECT_ONE = 320;
    public static int DATADIARY_UPDATE_ONE = 330;
    public static int DATADIARY_DELETE_ONE = 340;

    public static int DATAFOLLOWED_ADD_ONE = 410;
    public static int DATAFOLLOWED_SELECT_ONE = 420;
    public static int DATAFOLLOWED_UPDATE_ONE = 430;
    public static int DATAFOLLOWED_DELETE_ONE = 440;

    public static int DATAFOLLOW_ADD_ONE = 510;
    public static int DATAFOLLOW_SELECT_ONE = 520;
    public static int DATAFOLLOW_UPDATE_ONE = 530;
    public static int DATAFOLLOW_DELETE_ONE = 540;

    public static int DATALIKE_ADD_ONE = 610;
    public static int DATALIKE_SELECT_ONE = 620;
    public static int DATALIKE_UPDATE_ONE = 630;
    public static int DATALIKE_DELETE_ONE = 640;

    public void addData(int fun,T data, MyCallBack callBack);

    public void selectData(int fun,T data,MyCallBack callBack);

    public void updateData(int fun,T data,MyCallBack callBack);

    public void deleteData(int fun,T data, MyCallBack callBack);

}
