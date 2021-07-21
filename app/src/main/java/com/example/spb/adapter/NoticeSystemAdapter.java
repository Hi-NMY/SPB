package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.entity.Notice;

import java.util.List;

public class NoticeSystemAdapter extends RecyclerView.Adapter<NoticeSystemAdapter.ViewHolder> {

    private List<Notice> systemNotices;
    private Activity activity;
    private Notice notice;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mOfficialTime;
        TextView mOfficialNotice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mOfficialTime = (TextView) itemView.findViewById(R.id.official_time);
            mOfficialNotice = (TextView) itemView.findViewById(R.id.official_notice);
        }
    }

    public NoticeSystemAdapter(List<Notice> systemNotices, Activity activity) {
        this.systemNotices = systemNotices;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_systemnotice_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notice = systemNotices.get(position);
        holder.mOfficialTime.setText(notice.getNotice_date());
        holder.mOfficialNotice.setText(notice.getPb_id());
    }

    @Override
    public int getItemCount() {
        return systemNotices.size();
    }
}
