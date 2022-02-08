package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;

import java.util.List;

public class MyBadgeAdapter extends RecyclerView.Adapter<MyBadgeAdapter.ViewHolder> {

    private List<String> badges;
    private Activity activity;
    private BaseMVPActivity baseMVPActivity;
    private String badge;
    private String nowBadge;
    private boolean accountKey;

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView mItemBadgeImg;
        Button mItemBadgeBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemBadgeImg = (ImageView) itemView.findViewById(R.id.item_badge_img);
            mItemBadgeBtn = (Button) itemView.findViewById(R.id.item_badge_btn);
        }
    }

    public MyBadgeAdapter(List<String> badges,boolean key, String nowBadge ,Activity activity) {
        this.badges = badges;
        this.activity = activity;
        this.nowBadge = nowBadge;
        this.accountKey = key;
        baseMVPActivity = (BaseMVPActivity) activity;
    }

    public void refreshNowBadge(String b){
        nowBadge = b;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_badge, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        badge = badges.get(position);

        if (badge.equals(nowBadge)){
            holder.mItemBadgeBtn.setText("已设置");
            holder.mItemBadgeBtn.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.qihei));
            holder.mItemBadgeBtn.setBackground(activity.getDrawable(R.drawable.sign_bg));
        }else {
            holder.mItemBadgeBtn.setText("设置");
            holder.mItemBadgeBtn.setTextColor(ContextCompat.getColor(MyApplication.getContext(),R.color.beijing));
            holder.mItemBadgeBtn.setBackground(activity.getDrawable(R.drawable.no_sign_bg));
        }

        if (accountKey){
            holder.mItemBadgeBtn.setVisibility(View.VISIBLE);
        }else {
            holder.mItemBadgeBtn.setVisibility(View.GONE);
        }

        Glide.with(activity)
                .load(InValues.send(R.string.prefix_badge_img) + badge)
                .centerCrop()
                .into(holder.mItemBadgeImg);

        holder.mItemBadgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!badges.get(position).equals(nowBadge)){
                    refreshNowBadge(badges.get(position));
                    baseMVPActivity.getDataUserMsgPresenter().setUser_badge(badges.get(position));
                    SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_refresh_headimg),1,badges.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return badges.size();
    }
}
