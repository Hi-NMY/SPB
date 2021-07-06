package com.example.spb.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.SearchUserPageAPresenterImpl;
import com.example.spb.presenter.littlefun.RemoveNullCharacter;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.FragmentSpbAvtivityBar;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.inter.ISearchUserPageAView;
import com.example.spb.view.littlefun.HideKeyboard;
import com.example.spb.view.littlefun.MyListAnimation;
import com.gyf.immersionbar.ImmersionBar;

public class SearchUserPage extends BaseMVPActivity<ISearchUserPageAView, SearchUserPageAPresenterImpl> implements ISearchUserPageAView {

    private FragmentSpbAvtivityBar bar;
    private EditText mSearchMessage;
    private RecyclerView mSearchuserList;
    private DialogInter easyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_user_page);
        initActView();
    }

    @Override
    protected SearchUserPageAPresenterImpl createPresenter() {
        return new SearchUserPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        mSearchMessage = (EditText) findViewById(R.id.search_message);
        mSearchuserList = (RecyclerView) findViewById(R.id.searchuser_list);
        mSearchuserList = MyListAnimation.setListAnimation(this,mSearchuserList);
        setBar();
        setActivityBar();
        initData();
        createDialog();
        setMyListener();
    }

    @Override
    protected void initData() {

    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {
        closeDialog(0);
    }

    @Override
    public void createDialog() {
        easyDialog = new EasyDialog(this,R.drawable.loading);
    }

    @Override
    public void showDialogS(int i) {
        easyDialog.showMyDialog();
    }

    @Override
    public void closeDialog(int i) {
        easyDialog.closeMyDialog();
    }

    @Override
    public void setMyListener() {
        mSearchMessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                RemoveNullCharacter.setRemoveNull(mSearchMessage,s).setSelection(mSearchMessage.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!mSearchMessage.getText().toString().trim().equals("")){
                        HideKeyboard.hideboard(mSearchMessage);
                        showDialogS(0);
                        mPresenter.searUser(mSearchMessage.getText().toString().trim(),mSearchuserList);
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void setBar() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .fitsSystemWindows(true)
                .statusBarColor(R.color.beijing)
                .init();
    }

    @Override
    public void setActivityBar() {
        bar = setMyActivityBar(R.id.searchuser_actbar);
        bar.setBarBackground(R.color.TransColor);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }
}
