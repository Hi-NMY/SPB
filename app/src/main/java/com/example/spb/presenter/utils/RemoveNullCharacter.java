package com.example.spb.presenter.utils;

import android.widget.EditText;

public class RemoveNullCharacter {

    public static EditText setRemoveNull(EditText editText,CharSequence s){
        if (s.toString().contains(" ")) {
            String[] str = s.toString().split(" ");
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < str.length; i++) {
                sb.append(str[i]);
            }
            editText.setText(sb.toString());
            return editText;
        }else {
            return editText;
        }
    }


}
