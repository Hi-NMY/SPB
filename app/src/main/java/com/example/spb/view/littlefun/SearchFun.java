package com.example.spb.view.littlefun;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class SearchFun {

    public static void search(EditText editText,GoSearch goSearch){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().contains(" ")) {
                    String[] str = s.toString().split(" ");
                    StringBuffer sb = new StringBuffer();
                    for (int i = 0; i < str.length; i++) {
                        sb.append(str[i]);
                    }
                    editText.setText(sb.toString());
                    editText.setSelection(start);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                goSearch.afterTextChangedSearch(editText.getText().toString().trim());
            }
        });
    }

    public interface GoSearch{
       void afterTextChangedSearch(String text);
    }

}
