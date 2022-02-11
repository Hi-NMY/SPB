package com.example.spb.view.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.impl.AllSearchPageAPresenterImpl;
import com.example.spb.presenter.utils.RemoveNullCharacter;
import com.example.spb.view.Component.EasyDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.InterComponent.DialogInter;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IAllSearchPageAView;
import com.example.spb.view.utils.HideKeyboard;
import com.gyf.immersionbar.ImmersionBar;

import java.util.List;

public class AllSearchPage extends BaseMVPActivity<IAllSearchPageAView, AllSearchPageAPresenterImpl> implements IAllSearchPageAView {

    private EditText mSearchMessage;
    private TextView mTopicTipOne;
    private RecyclerView mTopicSearchList;
    private TextView mUserTipOne;
    private RecyclerView mUserSearchList;
    private RecyclerView mBarSearchRecyclerview;
    private DialogInter easyDialog;
    private RelativeLayout mR1;
    private RelativeLayout mR2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_search_page);
        initActView();
    }

    @Override
    protected AllSearchPageAPresenterImpl createPresenter() {
        return new AllSearchPageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        mSearchMessage = findViewById(R.id.search_message);
        mTopicTipOne = findViewById(R.id.topic_tip_one);
        mTopicSearchList = findViewById(R.id.topic_search_list);
        mUserTipOne = findViewById(R.id.user_tip_one);
        mUserSearchList = findViewById(R.id.user_search_list);
        mBarSearchRecyclerview = findViewById(R.id.bar_search_recyclerview);
        mR1 = findViewById(R.id.r1);
        mR2 = findViewById(R.id.r2);
        initData();
        setActivityBar();
        setBar();
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
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                closeDialog(0);
                switch (responseFlag) {
                    case BAR_SUCCESS:
                        List<Bar> bars = (List<Bar>) response;
                        if (bars != null) {
                            mBarSearchRecyclerview.setVisibility(View.VISIBLE);
                            mPresenter.setBarAdapter(bars, new GridLayoutManager(MyApplication.getContext(), 1), mBarSearchRecyclerview);
                        } else {
                            mBarSearchRecyclerview.setVisibility(View.INVISIBLE);
                        }
                        break;
                    case TOPIC_SUCCESS:
                        List<Topic> topics = (List<Topic>) response;
                        mR1.setVisibility(View.VISIBLE);
                        if (topics != null) {
                            mTopicTipOne.setVisibility(View.INVISIBLE);
                            mTopicSearchList.setVisibility(View.VISIBLE);
                            mPresenter.setTopicAdapter(topics, new GridLayoutManager(MyApplication.getContext(), 2), mTopicSearchList);
                        } else {
                            mTopicTipOne.setVisibility(View.VISIBLE);
                            mTopicSearchList.setVisibility(View.GONE);
                        }
                        break;
                    case USER_SUCCESS:
                        List<UserDto> userDtos = (List<UserDto>) response;
                        mR2.setVisibility(View.VISIBLE);
                        if (userDtos != null) {
                            mUserTipOne.setVisibility(View.INVISIBLE);
                            mUserSearchList.setVisibility(View.VISIBLE);
                            mPresenter.setUserAdapter(userDtos, new LinearLayoutManager(MyApplication.getContext()), mUserSearchList);
                        } else {
                            mUserTipOne.setVisibility(View.VISIBLE);
                            mUserSearchList.setVisibility(View.GONE);
                        }
                        break;
                }
            }
        });
    }

    @Override
    public void createDialog() {
        easyDialog = new EasyDialog(this, R.drawable.loading);
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
                RemoveNullCharacter.setRemoveNull(mSearchMessage, s).setSelection(mSearchMessage.getText().length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        mSearchMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (submit()) {
                        HideKeyboard.hideboard(mSearchMessage);
                        showDialogS(0);
                        mPresenter.obtainSearch(mSearchMessage.getText().toString().trim());
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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.allsearch_actbar);
        bar.barCentralTxt(TITLE, null);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
    }

    private boolean submit() {
        String message = mSearchMessage.getText().toString().trim();
        if (TextUtils.isEmpty(message)) {
            MyToastClass.ShowToast(this, "请输入内容~");
            return false;
        } else {
            return true;
        }
    }
}
