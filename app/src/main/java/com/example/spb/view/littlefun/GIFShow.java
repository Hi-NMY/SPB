package com.example.spb.view.littlefun;

import pl.droidsonroids.gif.*;

public class GIFShow{

    private GifDrawable gifDrawable;

    public GIFShow(GifImageView gifImageView) {
        gifDrawable = (GifDrawable) gifImageView.getBackground();
        gifDrawable.stop();
    }

    public void startGif(){
        gifDrawable.setLoopCount(99);
        gifDrawable.reset();
    }

    public void resetGif(){
        gifDrawable.setLoopCount(1);
    }

    public void stopGif(){
        gifDrawable.stop();
    }
}
