package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SearchUserAdapter extends RecyclerView.Adapter<SearchUserAdapter.ViewHolder> {

    private List<UserDto> userDtos;
    private UserDto userDto;
    private BaseMVPActivity activity;
    private String cacheKey;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemUserFollowHeadimg;
        TextView mItemUserFollowTitle;
        ImageView mItemUserFollowUsersex;
        ImageView mItemUserFollowUserbadge;
        RelativeLayout mItemUserFollowR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemUserFollowHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_user_follow_headimg);
            mItemUserFollowTitle = (TextView)itemView.findViewById(R.id.item_user_follow_title);
            mItemUserFollowUsersex = (ImageView)itemView.findViewById(R.id.item_user_follow_usersex);
            mItemUserFollowUserbadge = (ImageView)itemView.findViewById(R.id.item_user_follow_userbadge);
            mItemUserFollowR = (RelativeLayout)itemView.findViewById(R.id.item_user_follow_R);
        }
    }

    public SearchUserAdapter(List<UserDto> userDtos, Activity activity) {
        this.userDtos = userDtos;
        this.activity = (BaseMVPActivity) activity;
        cacheKey = MyDateClass.showNowDate();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_follow_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        userDto = userDtos.get(position);
        holder.mItemUserFollowTitle.setText(userDto.getUser_name());
        if(holder.mItemUserFollowHeadimg.getTag() == null || !holder.mItemUserFollowHeadimg.getTag().equals(cacheKey)){
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + userDto.getUser_account() + "/HeadImage/myHeadImage.png")
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                    .into(holder.mItemUserFollowHeadimg);
            holder.mItemUserFollowHeadimg.setTag(cacheKey);
        }
        if (userDto.getStu_sex() != null && userDto.getStu_sex().equals("男")) {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_boy);
        } else {
            holder.mItemUserFollowUsersex.setImageResource(R.drawable.icon_girl);
        }
        holder.mItemUserFollowR.setOnClickListener(new View.OnClickListener() {
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
        return userDtos.size();
    }
}
