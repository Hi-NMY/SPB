package com.example.spb.presenter.impl;

import android.app.Activity;
import android.content.SharedPreferences;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.adapter.SubjectClassAdapter;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BasePresenter;
import com.example.spb.entity.Notice;
import com.example.spb.entity.SchoolTable;
import com.example.spb.model.InterTotal.SpbModelBasicInter;
import com.example.spb.model.impl.BarModelImpl;
import com.example.spb.presenter.inter.IUserPageFPresenter;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.MySharedPreferences;
import com.example.spb.view.inter.IUserPageFView;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class UserPageFPresenterImpl extends BasePresenter<IUserPageFView> implements IUserPageFPresenter {

    private SpbModelBasicInter barModel;
    private List<SchoolTable> schoolTables;
    private SubjectClassAdapter subjectClassAdapter;
    private Activity activity;

    public UserPageFPresenterImpl(Activity activity) {
        this.activity = activity;
        barModel = new BarModelImpl();
        schoolTables = new ArrayList<>();
    }

    public void obtainBarNum(OnReturn onReturn){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_userBar_Num));
        int a = sharedPreferences.getInt(InValues.send(R.string.userBar_num),0);
        onReturn.onReturn(a);
    }

    public void obtainMyCard(OnCardReturn onCardReturn){
        SharedPreferences sharedPreferences = MySharedPreferences.getShared(InValues.send(R.string.Shared_assist_setup));
        boolean activeKey = sharedPreferences.getBoolean(InValues.send(R.string.assist_active),true);
        boolean classKey = sharedPreferences.getBoolean(InValues.send(R.string.assist_class),true);
        onCardReturn.onReturn(classKey,activeKey);
    }

    public void obtainSubjectClass(OnClassReturn onClassReturn, RecyclerView recyclerView){
        String date = MyDateClass.showYearMonthDay();
        schoolTables = LitePal.where("class_date = ?",date).order("class_date desc").find(SchoolTable.class);
        if (schoolTables == null || schoolTables.size() == 0){
            onClassReturn.onReturn(false);
        }else {
            onClassReturn.onReturn(true);
            setClassAdapter(recyclerView);
        }
    }

    public void setClassAdapter(RecyclerView recyclerView){
        subjectClassAdapter = new SubjectClassAdapter(schoolTables , activity);
        subjectClassAdapter.setCacheKey(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MyApplication.getContext()));
        recyclerView.setAdapter(subjectClassAdapter);
    }

    public interface OnReturn{
        void onReturn(int num);
    }

    public interface OnCardReturn{
        void onReturn(boolean classKey,boolean activeKey);
    }

    public interface OnClassReturn{
        void onReturn(boolean key);
    }
}
