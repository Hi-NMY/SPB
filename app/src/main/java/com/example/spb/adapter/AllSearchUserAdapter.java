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
import com.example.spb.presenter.utils.InValues;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AllSearchUserAdapter extends RecyclerView.Adapter<AllSearchUserAdapter.ViewHolder> {

    private List<UserDto> userDtos;
    private Activity activity;
    private UserDto userDto;

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

    public AllSearchUserAdapter(List<UserDto> userDtos, Activity activity) {
        this.userDtos = userDtos;
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
        userDto = userDtos.get(position);

        holder.mItemAllsearchUserName.setText(userDto.getUser_name());

        Glide.with(activity)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + userDto.getUser_account() + "/HeadImage/myHeadImage.png")
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                .into(holder.mItemAllsearchUserHeadimg);

        if (userDto.getStu_sex() != null && userDto.getStu_sex().equals("男")) {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_girl);
        }

        if (userDto.getUser_badge() == null || userDto.getUser_badge().equals("")){
            holder.mItemUserFollowUserbadge.setVisibility(View.GONE);
        }else {
            holder.mItemUserFollowUserbadge.setVisibility(View.VISIBLE);
            //写入徽章
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + userDto.getUser_badge())
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
