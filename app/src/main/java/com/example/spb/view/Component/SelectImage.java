package com.example.spb.view.Component;

import android.app.Activity;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import com.example.spb.R;
import com.example.spb.view.InterComponent.SpbSelectImage;
import com.example.spb.view.littlefun.GlideEngine;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.language.LanguageConfig;
import com.luck.picture.lib.listener.OnResultCallbackListener;
import com.luck.picture.lib.style.PictureCropParameterStyle;
import com.luck.picture.lib.style.PictureParameterStyle;

public class SelectImage implements SpbSelectImage {

    private Activity activity;

    public SelectImage(Activity a) {
        activity = a;
        setTheme();
    }



    @Override
    public void selectEasyImg(OnResultCallbackListener onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .setPictureStyle(mPictureParameterStyle)
                .setPictureCropStyle(mCropParameterStyle)
                .setLanguage(-1)
                .isUseCustomCamera(false)
                .maxVideoSelectNum(1)
                .isReturnEmpty(false)
                .selectionMode(PictureConfig.SINGLE)
                .isPreviewImage(false)
                .showCropGrid(false)
                .withAspectRatio(1,1)
                .hideBottomControls(true)
                .showCropFrame(true)
                .isDragFrame(true)
                .rotateEnabled(false)
                .scaleEnabled(true)
                .forResult(onResultCallbackListener);
    }

    @Override
    public void selectOneImg(String imgName, OnResultCallbackListener onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.SINGLE)
                .isSingleDirectReturn(false)
                .setPictureStyle(mPictureParameterStyle)
                .setPictureCropStyle(mCropParameterStyle)
                .isCamera(false)
                .isZoomAnim(true)
                .maxSelectNum(1)
                .minSelectNum(1)
                .isPreviewImage(true)
                .isEnableCrop(true)//是否开启裁剪
                .isCompress(true)
                .withAspectRatio(1,1)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .scaleEnabled(true)
                .isDragFrame(true)
                .synOrAsy(false)
                .setLanguage(LanguageConfig.CHINESE)
                .cameraFileName(System.currentTimeMillis()+imgName)//自定义拍照文件名，
                .renameCompressFile(System.currentTimeMillis()+imgName)//自定义压缩文件名，
                .renameCropFileName(System.currentTimeMillis()+imgName)//自定义裁剪文件名，
                .forResult(PictureConfig.CHOOSE_REQUEST,onResultCallbackListener);
    }

    @Override
    public void selectMoreImg(String imgName,int maxPosition, OnResultCallbackListener onResultCallbackListener) {
        PictureSelector.create(activity)
                .openGallery(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.MULTIPLE)
                .isSingleDirectReturn(false)
                .setPictureStyle(mPictureParameterStyle)
                .setPictureCropStyle(mCropParameterStyle)
                .isCamera(true)
                .isZoomAnim(true)
                .maxSelectNum(maxPosition)
                .isPreviewImage(true)
                .isEnableCrop(true)//是否开启裁剪
                .isCompress(true)
                .withAspectRatio(1,1)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .scaleEnabled(true)
                .isDragFrame(true)
                .synOrAsy(false)
                .setLanguage(LanguageConfig.CHINESE)
                .cameraFileName(System.currentTimeMillis()+imgName)//自定义拍照文件名，
                .renameCompressFile(System.currentTimeMillis()+imgName)//自定义压缩文件名，
                .renameCropFileName(System.currentTimeMillis()+imgName)//自定义裁剪文件名，
                .forResult(PictureConfig.CHOOSE_REQUEST,onResultCallbackListener);
    }

    @Override
    public void selectVideo(String videoName, OnResultCallbackListener onResultCallbackListener) {

    }

    @Override
    public void selectCameraImg(String imgName, OnResultCallbackListener onResultCallbackListener) {
        PictureSelector.create(activity)
                .openCamera(PictureMimeType.ofImage())
                .imageEngine(GlideEngine.createGlideEngine())
                .selectionMode(PictureConfig.SINGLE)
                .isPreviewImage(true)
                .isEnableCrop(true)//是否开启裁剪
                .withAspectRatio(1,1)
                .freeStyleCropEnabled(false)
                .showCropFrame(true)
                .scaleEnabled(true)
                .isDragFrame(true)
                .isCompress(true)
                .synOrAsy(false)
                .setLanguage(LanguageConfig.CHINESE)
                .cameraFileName(System.currentTimeMillis()+imgName)//自定义拍照文件名，
                .renameCompressFile(System.currentTimeMillis()+imgName)//自定义压缩文件名，
                .renameCropFileName(System.currentTimeMillis()+imgName)//自定义裁剪文件名，
                .forResult(PictureConfig.REQUEST_CAMERA,onResultCallbackListener);
    }

    private PictureParameterStyle mPictureParameterStyle;
    private PictureCropParameterStyle mCropParameterStyle;
    @Override
    public void setTheme() {
        mPictureParameterStyle = new PictureParameterStyle();
        // 裁剪主题
        mCropParameterStyle = new PictureCropParameterStyle(
                ContextCompat.getColor(activity, R.color.beijing),
                ContextCompat.getColor(activity, R.color.beijing),
                ContextCompat.getColor(activity, R.color.black),
                mPictureParameterStyle.isChangeStatusBarFontColor);

// 是否改变状态栏字体颜色(黑白切换)
        mPictureParameterStyle.isChangeStatusBarFontColor = true;
// 是否开启右下角已完成(0/9)风格
        mPictureParameterStyle.isOpenCompletedNumStyle = true;
// 是否开启类似QQ相册带数字选择风格
        mPictureParameterStyle.isOpenCheckNumStyle = true;
// 相册状态栏背景色
        mPictureParameterStyle.pictureStatusBarColor = ContextCompat.getColor(activity, R.color.beijing);
// 相册列表标题栏背景色
        mPictureParameterStyle.pictureTitleBarBackgroundColor = ContextCompat.getColor(activity, R.color.beijing);
// 相册列表标题栏右侧上拉箭头
        mPictureParameterStyle.pictureTitleUpResId = R.drawable.top;
// 相册列表标题栏右侧下拉箭头
        mPictureParameterStyle.pictureTitleDownResId = R.drawable.down;
// 相册文件夹列表选中圆点
        mPictureParameterStyle.pictureFolderCheckedDotStyle = 0;
// 相册返回箭头
        mPictureParameterStyle.pictureLeftBackIcon = R.drawable.left_return_small;
// 标题栏字体颜色
        mPictureParameterStyle.pictureTitleTextColor = ContextCompat.getColor(activity, R.color.black);
// 相册右侧取消按钮字体颜色
        mPictureParameterStyle.pictureCancelTextColor = ContextCompat.getColor(activity, R.color.black);
// 相册列表勾选图片样式
        mPictureParameterStyle.pictureCheckedStyle = R.drawable.imageselect;
// 相册列表底部背景色
        mPictureParameterStyle.pictureBottomBgColor = ContextCompat.getColor(activity, R.color.beijing);
// 相册列表底下预览文字色值(预览按钮可点击时的色值)
        mPictureParameterStyle.picturePreviewTextColor = ContextCompat.getColor(activity, R.color.black);
// 相册列表底下不可预览文字色值(预览按钮不可点击时的色值)
        mPictureParameterStyle.pictureUnPreviewTextColor = ContextCompat.getColor(activity, R.color.grey);
// 相册列表已完成色值(已完成 可点击色值)
        mPictureParameterStyle.pictureCompleteTextColor = ContextCompat.getColor(activity, R.color.theme_color);
// 相册列表未完成色值(请选择 不可点击色值)
        mPictureParameterStyle.pictureUnCompleteTextColor = ContextCompat.getColor(activity, R.color.grey);
// 预览界面底部背景色
        mPictureParameterStyle.picturePreviewBottomBgColor = ContextCompat.getColor(activity, R.color.beijing);
    }
}
