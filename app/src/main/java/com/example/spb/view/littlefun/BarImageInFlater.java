package com.example.spb.view.littlefun;

import android.app.Activity;
import android.graphics.Rect;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.entity.ImageDouble;
import com.example.spb.presenter.littlefun.MyResolve;

import java.util.List;

public class BarImageInFlater {

    private static GridLayoutManager gridLayoutManager;
    private static List<ImageDouble> imageDoubles;

    public static GridLayoutManager getInflater(Activity a, String doubleImages) {
        imageDoubles = MyResolve.InDoubleImage(doubleImages);
        switch (imageDoubles.size()) {
            case 3:
                gridLayoutManager = new GridLayoutManager(a, 3);
                return gridLayoutManager;
            case 1:
                gridLayoutManager = new GridLayoutManager(a, 1);
                return gridLayoutManager;
            default:
                return new GridLayoutManager(a, 2);
        }
    }
}
