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

            }

            @Override
            public void afterTextChanged(Editable s) {
                goSearch.afterTextChangedSearch();
            }
        });
    }

    public interface GoSearch{
       void afterTextChangedSearch();
    }

}
