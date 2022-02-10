package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.SchoolTable;
import com.example.spb.presenter.utils.MyDateClass;

import java.util.Date;
import java.util.List;

public class SubjectClassAdapter extends RecyclerView.Adapter<SubjectClassAdapter.ViewHolder> {

    private final List<SchoolTable> schoolTables;
    private final Date nowDate;
    private int cachePosition;
    private boolean cacheKey = false;

    public int getCachePosition() {
        return cachePosition;
    }

    public void setCachePosition(int cachePosition) {
        this.cachePosition = cachePosition;
    }

    public void setCacheKey(boolean cacheKey) {
        this.cacheKey = cacheKey;
    }

    public boolean isCacheKey() {
        return cacheKey;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mSubjectName;
        TextView mSubjectDateTip;
        TextView mClassTeacher;
        TextView mClassRoom;
        TextView mDateStart;
        TextView mDateOver;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mSubjectName = itemView.findViewById(R.id.subject_name);
            mSubjectDateTip = itemView.findViewById(R.id.subject_date_tip);
            mClassTeacher = itemView.findViewById(R.id.class_teacher);
            mClassRoom = itemView.findViewById(R.id.class_room);
            mDateStart = itemView.findViewById(R.id.date_start);
            mDateOver = itemView.findViewById(R.id.date_over);
        }
    }

    public SubjectClassAdapter(List<SchoolTable> schoolTables, Activity activity) {
        this.schoolTables = schoolTables;
        nowDate = MyDateClass.stringToTime(MyDateClass.showNowTime2());
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_subject_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SchoolTable schoolTable = schoolTables.get(position);
        holder.mSubjectName.setText(schoolTable.getClass_name());
        holder.mClassTeacher.setText(schoolTable.getClass_teacher());
        holder.mDateStart.setText(schoolTable.getClass_time_start());
        holder.mDateOver.setText(schoolTable.getClass_time_over());
        holder.mClassRoom.setText(schoolTable.getClass_room());

        if (isCacheKey()) {
            switch (nowClass(MyDateClass.stringToTime(schoolTable.getClass_time_start()), MyDateClass.stringToTime(schoolTable.getClass_time_over()))) {
                case 1:
                    holder.mSubjectDateTip.setText("即将上课");
                    holder.mSubjectDateTip.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color5));
                    break;
                case 2:
                    holder.mSubjectDateTip.setText("上课中");
                    holder.mSubjectDateTip.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.theme_color));
                    break;
                case 3:
                    holder.mSubjectDateTip.setText("已结束");
                    holder.mSubjectDateTip.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.darkgrey));
                    break;
                default:
                    holder.mSubjectDateTip.setVisibility(View.INVISIBLE);
                    break;
            }
        } else {
            holder.mSubjectDateTip.setVisibility(View.INVISIBLE);
            if (!cacheKey && (MyDateClass.stringToDate2(schoolTable.getClass_date()).getTime() == MyDateClass.stringToDate2(MyDateClass.showYearMonthDay()).getTime())) {
                setCachePosition(position);
            }
        }
    }

    private int nowClass(Date classStart, Date classOver) {
        if ((classStart.getTime() - nowDate.getTime()) / 60 / 1000 > 0 && (classStart.getTime() - nowDate.getTime()) / 60 / 1000 <= 30) {
            return 1;
        } else if (classStart.getTime() <= nowDate.getTime() && nowDate.getTime() <= classOver.getTime()) {
            return 2;
        } else if (nowDate.getTime() > classOver.getTime()) {
            return 3;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return schoolTables == null ? 0 : schoolTables.size();
    }
}
