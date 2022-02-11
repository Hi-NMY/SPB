package com.example.spb.view.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.impl.UserCoursePageAPresenterImpl;
import com.example.spb.view.fragment.FragmentSpbAvtivityBar;
import com.example.spb.view.inter.IUserCoursePageAView;
import com.gyf.immersionbar.ImmersionBar;

public class UserCoursePage extends BaseMVPActivity<IUserCoursePageAView, UserCoursePageAPresenterImpl>
        implements IUserCoursePageAView {

    private RecyclerView mSubjectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_course_page);
        initActView();
    }

    @Override
    protected UserCoursePageAPresenterImpl createPresenter() {
        return new UserCoursePageAPresenterImpl(this);
    }

    @Override
    protected void initActView() {
        mSubjectList = findViewById(R.id.subject_list);
        initData();
        setActivityBar();
        setBar();
        setMyListener();
        createDialog();
    }

    @Override
    protected void initData() {
        mPresenter.obtainSubjectClass(mSubjectList);
    }

    @Override
    public <T> T request(int requestFlag) {
        return null;
    }

    @Override
    public <T> void response(T response, int responseFlag) {

    }

    @Override
    public void createDialog() {

    }

    @Override
    public void showDialogS(int i) {

    }

    @Override
    public void closeDialog(int i) {

    }

    @Override
    public void setMyListener() {

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
        FragmentSpbAvtivityBar bar = setMyActivityBar(R.id.usercourse_actbar);
        bar.barLeftImg(R.drawable.left_return, new FragmentSpbAvtivityBar.OnMyClick() {
            @Override
            public void onClick() {
                finish();
            }
        });
        bar.barCentralTxt(TITLE, null);
    }

    @Override
    public void createRefresh() {

    }

    @Override
    public void finishRRefresh(int num) {

    }
}
