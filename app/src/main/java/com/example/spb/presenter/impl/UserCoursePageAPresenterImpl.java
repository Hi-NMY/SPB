package com.example.spb.presenter.impl;

import android.app.Activity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.adapter.SubjectClassAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.SchoolTable;
import com.example.spb.presenter.inter.IUserCoursePageAPresenter;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.view.inter.IUserCoursePageAView;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class UserCoursePageAPresenterImpl extends BasePresenter<IUserCoursePageAView> implements IUserCoursePageAPresenter {

    private List<SchoolTable> schoolTables;
    private SubjectClassAdapter subjectClassAdapter;
    private Activity activity;

    public SubjectClassAdapter getSubjectClassAdapter() {
        return subjectClassAdapter;
    }

    public UserCoursePageAPresenterImpl(Activity activity) {
        schoolTables = new ArrayList<>();
        this.activity = activity;
    }

    public void obtainSubjectClass(RecyclerView recyclerView){
        schoolTables = LitePal.findAll(SchoolTable.class);
        setClassAdapter(recyclerView);
    }

    public void setClassAdapter(RecyclerView recyclerView){
        subjectClassAdapter = new SubjectClassAdapter(schoolTables , activity);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        recyclerView.setAdapter(subjectClassAdapter);
        recyclerView.scrollToPosition(getSubjectClassAdapter().getCachePosition());
    }
}
