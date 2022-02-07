package com.example.spb.presenter.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.spb.app.MyApplication;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class VideoTool {

    private static Activity activity;
    private static Context context;
    private StandardGSYVideoPlayer mDetailPlayer;
    private boolean isPlay = false;
    private boolean isPause = false;
    private OrientationUtils orientationUtils;
    private static String cacheVideoImage = "";
    private static Bitmap bitmap;
    private ImageView imageView;
    private String videoPath;
    private int videoKey = 0;

    public VideoTool(Activity a, Context c, StandardGSYVideoPlayer player) {
        this.activity = a;
        this.context = c;
        this.mDetailPlayer = player;
        imageView = new ImageView(c);
    }

    public void creatVideo(String videoPath) {
        this.videoPath = videoPath;
        videoKey = 0;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(getBitmap(MyApplication.getContext(), videoPath));
        setVideo();
    }

    public void creatVideo(String videoPath, Bitmap bitmap) {
        this.videoPath = videoPath;
        videoKey = 1;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageBitmap(bitmap);
        setVideo();
    }

    public void setVideo() {
        mDetailPlayer.getTitleTextView().setVisibility(View.GONE);
        mDetailPlayer.getBackButton().setVisibility(View.GONE);

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(activity, mDetailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);
        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption.setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(false)
                .setShowFullAnimation(false)
                .setNeedLockFull(false)
                .setUrl(videoPath)
                .setCacheWithPlay(false)
                .setNeedShowWifiTip(false)
                .setIsTouchWiget(false)
                .setIsTouchWigetFull(false)
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        switch (videoKey) {
                            case 0:
                                orientationUtils.setEnable(mDetailPlayer.isRotateWithSystem());
                                isPlay = true;
                                break;
                            case 1:
                                if (!mDetailPlayer.isIfCurrentIsFullscreen()) {
                                    //静音
                                    GSYVideoManager.instance().setNeedMute(true);
                                }
                                break;
                        }
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        switch (videoKey) {
                            case 0:
                                if (orientationUtils != null) {
                                    orientationUtils.backToProtVideo();
                                }
                                break;
                            case 1:
                                //全屏不静音
                                GSYVideoManager.instance().setNeedMute(true);
                                break;
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (videoKey == 0) {
                    if (orientationUtils != null) {
                        //配合下方的onConfigurationChanged
                        orientationUtils.setEnable(!lock);
                    }
                }
            }
        }).build(mDetailPlayer);

        mDetailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (videoKey) {
                    case 0:
                        //直接横屏
                        orientationUtils.resolveByClick();
                        //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                        mDetailPlayer.startWindowFullscreen(activity, true, true);
                        break;
                    case 1:
                        mDetailPlayer.startWindowFullscreen(activity, true, true);
                        break;
                }
            }
        });
    }

    public static Bitmap getBitmap(Context context, String url) {
        bitmap = null;
        //MediaMetadataRetriever 是android中定义好的一个类，提供了统一
        //的接口，用于从输入的媒体文件中取得帧和元数据；
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //（）根据文件路径获取缩略图
            retriever.setDataSource(context, Uri.fromFile(new File(url)));
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
            cacheVideoImage = context.getFilesDir() + "/images/";
            File file = new File(cacheVideoImage);
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(cacheVideoImage, "cacheVideoImage.png");
            FileOutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 1, stream);
            stream.flush();
            stream.close();
            cacheVideoImage = cacheVideoImage + "cacheVideoImage.png";
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } finally {
            retriever.release();
        }
        return bitmap;
    }

    public static void gethttpBitmap(Context context, int position, String url, OnReturnBitmap onReturnBitmap) {
        bitmap = null;
        Glide.with(context).asBitmap().load(url + ".png").into(new CustomTarget<Bitmap>() {
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                bitmap = resource;
                onReturnBitmap.onReturn(bitmap, position);
            }

            @Override
            public void onLoadCleared(@Nullable Drawable placeholder) {

            }
        });
    }

    public String getCacheVideoImage() {
        return cacheVideoImage;
    }

    public void setonBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        try {
            if (GSYVideoManager.backFromWindowFull(context)) {
                return;
            }
        } catch (Exception e) {

        }
    }

    public void setonPause() {
        mDetailPlayer.getCurrentPlayer().onVideoPause();
        isPause = true;
    }

    public void setonResume() {
        mDetailPlayer.getCurrentPlayer().onVideoResume(false);
        isPause = false;
    }

    public void setonConfigurationChanged(Configuration newConfig) {
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            mDetailPlayer.onConfigurationChanged(activity, newConfig, orientationUtils, true, true);
        }
    }

    public void setonDestroy() {
        if (isPlay) {
            mDetailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null) {
            orientationUtils.releaseListener();
        }
    }

    public interface OnReturnBitmap {
        void onReturn(Bitmap bitmap, int position);
    }
}
