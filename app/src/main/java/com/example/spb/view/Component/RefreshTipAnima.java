package com.example.spb.view.Component;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;

public class RefreshTipAnima {

    public static Animation animationa;
    public static Animation animationb;

    static {
        animationa = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.re_tip_anim);
        animationb = AnimationUtils.loadAnimation(MyApplication.getContext(), R.anim.re_tip_anim2);
        animationb.setStartOffset(600);
    }

    public static void tipAnimation(TextView textView,int num){
        textView.setText("叮，又为您更新了"+ String.valueOf(num) +"条动态");
        textView.startAnimation(animationa);
        textView.setVisibility(View.VISIBLE);
        animationa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.startAnimation(animationb);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationb.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                textView.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
