package com.example.spb.view.Component;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;

public class ThumbAnima {

    public static Animation animationa;

    static {
        animationa = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.thumb_anim);
    }

    public static void thumbAnimation(ImageView imageView){
        imageView.startAnimation(animationa);
        imageView.setBackground(MyApplication.getContext().getDrawable(R.drawable.icon_likeal));
        animationa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
