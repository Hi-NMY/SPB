package com.example.spb.view.littlefun;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import com.example.spb.presenter.littlefun.RemoveNullCharacter;

public class SearchFun {

    public static void search(EditText editText,GoSearch goSearch){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(editText,s).setSelection(editText.getText().length());
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
