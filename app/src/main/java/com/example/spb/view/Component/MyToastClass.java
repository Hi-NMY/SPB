package com.example.spb.view.Component;

import android.content.Context;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import com.example.spb.R;

import java.lang.reflect.Parameter;

public class MyToastClass {

    private static Toast toast;
    private static View view;
    private static TextView textView;

    public static void ShowToast(Context context, Object value){
        if (view==null){
            view = LayoutInflater.from(context).inflate(R.layout.other_toast_mytoast,null);
        }
        if (textView==null){
            textView = view.findViewById(R.id.ToastText);
        }
        if (toast==null){
            textView.setText(String.valueOf(value));
            textView.postInvalidate();
            toast = new Toast(context);
            toast.setGravity(Gravity.CENTER,0,+300);
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setView(view);
        }else {
            textView.setText(String.valueOf(value));
            textView.postInvalidate();
        }
        toast.show();
    }
}
