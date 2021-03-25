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

    public void addData(int fun,T data, MyCallBack callBack);

    public void selectData(int fun,T data,MyCallBack callBack);

    public void updateData(int fun,T data,MyCallBack callBack);

    public void deleteData(int fun,T data, MyCallBack callBack);

}
