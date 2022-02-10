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

    private final List<Notice> systemNotices;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mOfficialTime;
        TextView mOfficialNotice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mOfficialTime = itemView.findViewById(R.id.official_time);
            mOfficialNotice = itemView.findViewById(R.id.official_notice);
        }
    }

    public NoticeSystemAdapter(List<Notice> systemNotices, Activity activity) {
        this.systemNotices = systemNotices;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_systemnotice_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Notice notice = systemNotices.get(position);
        holder.mOfficialTime.setText(notice.getNotice_date());
        holder.mOfficialNotice.setText(notice.getPb_id());
    }

    @Override
    public int getItemCount() {
        return systemNotices == null ? 0 : systemNotices.size();
    }
}
