package com.example.spb.view.utils;

import android.app.Activity;
import androidx.recyclerview.widget.GridLayoutManager;
import com.example.spb.entity.ImageDouble;
import com.example.spb.presenter.utils.MyResolve;

import java.util.List;

public class BarImageInFlater {

    public static GridLayoutManager getInflater(Activity a, String doubleImages) {
        List<ImageDouble> imageDoubles = MyResolve.InDoubleImage(doubleImages);
        switch (imageDoubles.size()) {
            case 3:
                GridLayoutManager gridLayoutManager = new GridLayoutManager(a, 3);
                return gridLayoutManager;
            case 1:
                gridLayoutManager = new GridLayoutManager(a, 1);
                return gridLayoutManager;
            default:
                return new GridLayoutManager(a, 2);
        }
    }
}
