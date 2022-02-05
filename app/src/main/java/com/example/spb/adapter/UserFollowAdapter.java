package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.entity.Dto.UserDto;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.activity.AttentionUserPage;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class UserFollowAdapter extends RecyclerView.Adapter<UserFollowAdapter.ViewHolder> {

    private List<UserDto> userDtos;
    private UserDto userDto;
    private AttentionUserPage attentionUserPage;
    private Activity activity;
    private String cacheKey;
    private ComponentDialog componentDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mTopicName;

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

    public UserFollowAdapter(List<UserDto> userDtos, Activity activity) {
        this.userDtos = userDtos;
        this.activity = activity;
        this.attentionUserPage = (AttentionUserPage) activity;
        cacheKey = MyDateClass.showNowDate();
    }

    public void setNewList(List<UserDto> userDtos){
        this.userDtos = userDtos;
        cacheKey = MyDateClass.showNowDate();
        notifyDataSetChanged();
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

        if (userDto.getUser_badge() == null || userDto.getUser_badge().equals("")){
            holder.mItemUserFollowUserbadge.setVisibility(View.INVISIBLE);
        }else {
            holder.mItemUserFollowUserbadge.setVisibility(View.VISIBLE);
            //写入徽章
            Glide.with(activity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/badge/" + userDto.getUser_badge())
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .centerCrop()
                    .into(holder.mItemUserFollowUserbadge);
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
        holder.mItemUserFollowR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                componentDialog = new ComponentDialog(activity, R.layout.dialog_longclick_view, new ComponentDialog.InitDialog() {
                    @Override
                    public void initView(View view) {
                        mButtonClose = (Button)view.findViewById(R.id.button_close);
                        mButtonRight = (Button)view.findViewById(R.id.button_right);
                        mTopicName = (TextView)view.findViewById(R.id.topic_name);
                    }

                    @Override
                    public void initData() {
                        mTopicName.setText(userDtos.get(position).getUser_name());
                    }

                    @Override
                    public void initListener() {
                        mButtonClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                componentDialog.closeMyDialog();
                            }
                        });
                        mButtonRight.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                attentionUserPage.getDataFollowPresenter().removeFollow(userDtos.get(position).getUser_account());
                                componentDialog.closeMyDialog();
                            }
                        });
                    }
                });
                componentDialog.showMyDialog();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return userDtos.size();
    }
}
