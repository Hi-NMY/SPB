package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.entity.User;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AllSearchUserAdapter extends RecyclerView.Adapter<AllSearchUserAdapter.ViewHolder> {

    private List<User> users;
    private Activity activity;
    private User user;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemAllsearchUserHeadimg;
        TextView mItemAllsearchUserName;
        ImageView mItemUserFollowUsersex;
        ImageView mItemUserFollowUserbadge;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemAllsearchUserHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_allsearch_user_headimg);
            mItemAllsearchUserName = (TextView) itemView.findViewById(R.id.item_allsearch_user_name);
            mItemUserFollowUsersex = (ImageView) itemView.findViewById(R.id.item_allsearch_user_usersex);
            mItemUserFollowUserbadge = (ImageView)itemView.findViewById(R.id.item_allsearch_user_userbadge);
        }
    }

    public AllSearchUserAdapter(List<User> users, Activity activity) {
        this.users = users;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allsearch_user_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        user = users.get(position);

        holder.mItemAllsearchUserName.setText(user.getUser_name());

        Glide.with(activity)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + user.getUser_account() + "/HeadImage/myHeadImage.png")
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                .into(holder.mItemAllsearchUserHeadimg);

        if (user.getStu_sex() != null && user.getStu_sex().equals("男")) {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_girl);
        }

        if (user.getUser_badge() == null || user.getUser_badge().equals("")){
            holder.mItemUserFollowUserbadge.setVisibility(View.GONE);
        }else {
            holder.mItemUserFollowUserbadge.setVisibility(View.VISIBLE);
            //写入徽章
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + user.getUser_badge())
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(holder.mItemUserFollowUserbadge);
        }

        holder.mItemAllsearchUserHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户主页
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), users.get(position).getUser_account());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return users == null ? 0 : users.size();
    }
}
