package com.example.spb.model.InterTotal;

import com.example.spb.presenter.callback.MyCallBack;

public interface SpbModelBasicInter<T> {

    public static int REGISTEREDPAGE = 1;

    public void addData(int fun,T data, MyCallBack callBack);

    public void selectData(int fun,T data,MyCallBack callBack);

    public void updateData(int fun,T data,MyCallBack callBack);

    public void deleteData(int fun,T data, MyCallBack callBack);

}
