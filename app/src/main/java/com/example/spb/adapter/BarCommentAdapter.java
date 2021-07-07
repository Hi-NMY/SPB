package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Comment;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.example.spb.presenter.littlefun.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.io.Serializable;
import java.util.List;

public class BarCommentAdapter extends RecyclerView.Adapter<BarCommentAdapter.ViewHolder> {

    private List<Comment> comments;
    private BaseMVPActivity baseMVPActivity;
    private Comment comment;
    private String cacheKey = "";
    private ComponentDialog componentDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mtxt;
    private TextView mtitle;
    private int cacheIdKey = -1;
    private String cacheBarUser = "";

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemCommentUserHeadimg;
        TextView mItemCommentUsername;
        TextView mItemCommentDate;
        TextView mItemCommentFloor;
        TextView mItemCommentTxt;
        RelativeLayout mItemCommentR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemCommentUserHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_comment_user_headimg);
            mItemCommentUsername = (TextView) itemView.findViewById(R.id.item_comment_username);
            mItemCommentDate = (TextView) itemView.findViewById(R.id.item_comment_date);
            mItemCommentFloor = (TextView) itemView.findViewById(R.id.item_comment_floor);
            mItemCommentTxt = (TextView) itemView.findViewById(R.id.item_comment_txt);
            mItemCommentR = (RelativeLayout) itemView.findViewById(R.id.item_comment_R);
        }
    }

    public BarCommentAdapter(List<Comment> comments, Activity activity) {
        this.comments = comments;
        this.baseMVPActivity = (BaseMVPActivity) activity;
        cacheKey = MyDateClass.showNowDate();
    }

    public void setBarUser(String account){
        this.cacheBarUser = account;
    }

    public void addNewAcooment(Comment comment){
        comments.add(0,comment);
        notifyItemRangeRemoved(0,comments.size() + 1);
    }

    public void removeComment(){
        if (cacheIdKey != -1){
            comments.remove(cacheIdKey);
            notifyItemRemoved(cacheIdKey);
            notifyItemRangeChanged(cacheIdKey, comments.size() + 1);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        comment = comments.get(position);
        holder.mItemCommentDate.setText(MyDateClass.showDateClass(comment.getComment_date()));
        holder.mItemCommentFloor.setText(comment.getComment_id() + "楼");

        if (comment.getComment_user().equals(cacheBarUser)){
            holder.mItemCommentUsername.setText("楼主");
            SpannableStringBuilder builder = new SpannableStringBuilder(holder.mItemCommentUsername.getText().toString());
            builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(baseMVPActivity,R.color.theme_color)), 0, 2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            holder.mItemCommentUsername.setText(builder);
        }else {
            holder.mItemCommentUsername.setText(comment.getUser_name());
        }

        if (comment.getComment_touser() != null && !comment.getComment_touser().equals("")){
            if (comment.getComment_touser().equals(cacheBarUser)){
                holder.mItemCommentTxt.setText("回复 " + "楼主" + " : " + comment.getComment_art());
                SpannableStringBuilder builder = new SpannableStringBuilder(holder.mItemCommentTxt.getText().toString());
                builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(baseMVPActivity,R.color.theme_color)), 3, 5, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mItemCommentTxt.setText(builder);
            }else {
                holder.mItemCommentTxt.setText("回复 " + comment.getUser_toname() + " : " + comment.getComment_art());
                SpannableStringBuilder builder = new SpannableStringBuilder(holder.mItemCommentTxt.getText().toString());
                builder.setSpan(new ForegroundColorSpan(ContextCompat.getColor(baseMVPActivity,R.color.theme_color)), 3, 3 + comment.getUser_toname().length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.mItemCommentTxt.setText(builder);
            }
        }else {
            holder.mItemCommentTxt.setText(comment.getComment_art());
        }

        if(holder.mItemCommentUserHeadimg.getTag() == null || !holder.mItemCommentUserHeadimg.getTag().equals(cacheKey)){
            Glide.with(baseMVPActivity)
                    .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + comment.getComment_user() + "/HeadImage/myHeadImage.png")
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                    .into(holder.mItemCommentUserHeadimg);
            holder.mItemCommentUserHeadimg.setTag(cacheKey);
        }

        holder.mItemCommentUserHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account),comments.get(position).getComment_user());
                    }
                });
            }
        });

        holder.mItemCommentR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //回复功能
                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_comment),3,
                        comments.get(position).getComment_user() + comments.get(position).getUser_name());
            }
        });

        holder.mItemCommentR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                componentDialog = new ComponentDialog(baseMVPActivity, R.layout.dialog_longclick_view, new ComponentDialog.InitDialog() {
                    @Override
                    public void initView(View view) {
                        mButtonClose = (Button)view.findViewById(R.id.button_close);
                        mButtonRight = (Button)view.findViewById(R.id.button_right);
                        mtitle = (TextView)view.findViewById(R.id.topic_name);
                        mtxt = (TextView)view.findViewById(R.id.txt);
                    }

                    @Override
                    public void initData() {
                        mtitle.setVisibility(View.GONE);
                        mtxt.setText("删除评论");
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
                                cacheIdKey = position;
                                SpbBroadcast.sendReceiver(MyApplication.getContext(),InValues.send(R.string.Bcr_add_comment),4,
                                        "",(Serializable)comments.get(position));
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
        return comments.size();
    }
}
