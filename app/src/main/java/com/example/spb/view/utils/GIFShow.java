package com.example.spb.view.utils;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class GIFShow {

    private GifDrawable gifDrawable;

    public GIFShow(GifImageView gifImageView) {
        gifDrawable = (GifDrawable) gifImageView.getBackground();
        gifDrawable.stop();
    }

    public void startGif() {
        gifDrawable.setLoopCount(99);
        gifDrawable.reset();
    }

    public void resetGif() {
        gifDrawable.setLoopCount(1);
    }

    public void stopGif() {
        gifDrawable.stop();
    }
}
