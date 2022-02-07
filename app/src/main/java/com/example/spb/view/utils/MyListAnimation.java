package com.example.spb.view.utils;

import android.app.Activity;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;

public class MyListAnimation {

    public static RecyclerView setListAnimation(Activity activity, RecyclerView recyclerView) {
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(activity, R.anim.layout_animation);
        controller.setOrder(LayoutAnimationController.ORDER_NORMAL);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(activity, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setLayoutAnimation(controller);
        return recyclerView;
    }

}
