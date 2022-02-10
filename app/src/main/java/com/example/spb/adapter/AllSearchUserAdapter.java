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
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AllSearchUserAdapter extends RecyclerView.Adapter<AllSearchUserAdapter.ViewHolder> {

    private final List<UserDto> userDtos;
    private final Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemAllsearchUserHeadimg;
        TextView mItemAllsearchUserName;
        ImageView mItemUserFollowUsersex;
        ImageView mItemUserFollowUserbadge;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemAllsearchUserHeadimg = itemView.findViewById(R.id.item_allsearch_user_headimg);
            mItemAllsearchUserName = itemView.findViewById(R.id.item_allsearch_user_name);
            mItemUserFollowUsersex = itemView.findViewById(R.id.item_allsearch_user_usersex);
            mItemUserFollowUserbadge = itemView.findViewById(R.id.item_allsearch_user_userbadge);
        }
    }

    public AllSearchUserAdapter(List<UserDto> userDtos, Activity activity) {
        this.userDtos = userDtos;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_allsearch_user_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UserDto userDto = userDtos.get(position);

        holder.mItemAllsearchUserName.setText(userDto.getUser_name());

        Glide.with(activity)
                .load(InValues.send(R.string.prefix_img) + userDto.getUser_account() + InValues.send(R.string.suffix_head_img))
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                .into(holder.mItemAllsearchUserHeadimg);

        if (userDto.getStu_sex() != null && "男".equals(userDto.getStu_sex())) {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_girl);
        }

        if (DataVerificationTool.isEmpty(userDto.getUser_badge())) {
            holder.mItemUserFollowUserbadge.setVisibility(View.GONE);
        } else {
            holder.mItemUserFollowUserbadge.setVisibility(View.VISIBLE);
            //写入徽章
            Glide.with(activity)
                    .load(InValues.send(R.string.prefix_badge_img) + userDto.getUser_badge())
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
                        intent.putExtra(InValues.send(R.string.intent_User_account), userDtos.get(position).getUser_account());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDtos == null ? 0 : userDtos.size();
    }
}
