package com.example.spb.view.InterComponent;

import com.luck.picture.lib.listener.OnResultCallbackListener;

public interface SpbSelectImage {

    public void selectOneImg(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void selectMoreImg(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void selectVideo(String videoName, OnResultCallbackListener onResultCallbackListener);

    public void selectCameraImg(String imgName, OnResultCallbackListener onResultCallbackListener);

    public void setTheme();

}
