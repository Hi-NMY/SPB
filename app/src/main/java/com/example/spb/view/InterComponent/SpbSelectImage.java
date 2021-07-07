package com.example.spb.view.InterComponent;

import com.luck.picture.lib.listener.OnResultCallbackListener;

public interface SpbSelectImage {

    public void selectEasyImg(OnResultCallbackListener onResultCallbackListener);

    public void selectOneImg(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void selectOneImg2(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void selectMoreImg(String imgName,int maxPosition, OnResultCallbackListener onResultCallbackListener);

    public void selectVideo(String videoName, OnResultCallbackListener onResultCallbackListener);

    public void selectCameraImg(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void selectCameraImg2(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void setTheme();

}
