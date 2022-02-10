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
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Notice;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.MyDateClass;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.PostBarDetailPage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import static com.example.spb.xserver.AndroidUnicast.*;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.ViewHolder> {

    private final List<Notice> notices;
    private final BaseMVPActivity activity;
    private Notice notice;
    private final String cacheKey;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemNoticeListHeadimg;
        TextView mItemNoticeListTitleName;
        ImageView mItemNoticeListTitleImg;
        TextView mItemNoticeListTitleBlock;
        TextView mItemNoticeListTitle;
        TextView mItemNoticeListDate;
        RelativeLayout mItemNoticeListR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemNoticeListHeadimg = itemView.findViewById(R.id.item_notice_list_headimg);
            mItemNoticeListTitleName = itemView.findViewById(R.id.item_notice_list_title_name);
            mItemNoticeListTitleImg = itemView.findViewById(R.id.item_notice_list_title_img);
            mItemNoticeListTitleBlock = itemView.findViewById(R.id.item_notice_list_title_block);
            mItemNoticeListTitle = itemView.findViewById(R.id.item_notice_list_title);
            mItemNoticeListDate = itemView.findViewById(R.id.item_notice_list_date);
            mItemNoticeListR = itemView.findViewById(R.id.item_notice_list_R);
        }
    }

    public NoticeAdapter(List<Notice> notices, Activity activity) {
        this.notices = notices;
        this.activity = (BaseMVPActivity) activity;
        cacheKey = MyDateClass.showNowDate();
    }

    public void refreshSee(int position, int see) {
        if (see == 1) {
            activity.getDataNoticePresenter().updateSee(notices.get(position).getNotice_date());
            notices.get(position).setSee(0);
            notifyItemChanged(position);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        notice = notices.get(position);
        if (holder.mItemNoticeListHeadimg.getTag() == null || !holder.mItemNoticeListHeadimg.getTag().equals(cacheKey)) {
            Glide.with(activity)
                    .load(InValues.send(R.string.prefix_img) + notice.getUser_account() + InValues.send(R.string.suffix_head_img))
                    .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()), 1, 1))
                    .into(holder.mItemNoticeListHeadimg);
            holder.mItemNoticeListHeadimg.setTag(cacheKey);
        }

        holder.mItemNoticeListHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转用户主页
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account), notice.getUser_account());
                    }
                });
            }
        });

        if (notice.getSee() == 1) {
            noSee(holder);
        } else {
            yesSee(holder);
        }

        holder.mItemNoticeListTitleName.setText(notice.getUser_name() + " ");
        holder.mItemNoticeListDate.setText(MyDateClass.showDateClass(notice.getNotice_date()));
        switch (notice.getPush_fun()) {
            case PUSHLIKEKEY:
                holder.mItemNoticeListTitleImg.setVisibility(View.VISIBLE);
                holder.mItemNoticeListTitleBlock.setText("赞");
                holder.mItemNoticeListTitleBlock.setTranslationX(-12);
                holder.mItemNoticeListTitle.setText(" 了我的帖子");
                holder.mItemNoticeListR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpPostBarDetail(position);
                    }
                });
                break;
            case PUSHCOMMENTKEY:
                holder.mItemNoticeListTitleImg.setVisibility(View.GONE);
                holder.mItemNoticeListTitleBlock.setText("评论");
                holder.mItemNoticeListTitle.setText(" 了我的帖子");
                holder.mItemNoticeListR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpPostBarDetailComment(position);
                    }
                });
                break;
            case PUSHCOLLECTKEY:
                holder.mItemNoticeListTitleImg.setVisibility(View.GONE);
                holder.mItemNoticeListTitleBlock.setText("收藏");
                holder.mItemNoticeListTitle.setText(" 了我的帖子");
                holder.mItemNoticeListR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpPostBarDetail(position);
                    }
                });
                break;
            case PUSHFOLLOWKEY:
                holder.mItemNoticeListTitleImg.setVisibility(View.GONE);
                holder.mItemNoticeListTitleBlock.setText("关注");
                holder.mItemNoticeListTitle.setText(" 了我");
                holder.mItemNoticeListR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //跳转用户主页
                        JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                            @Override
                            public void setMessage(Intent intent) {
                                intent.putExtra(InValues.send(R.string.intent_User_account), notices.get(position).getUser_account());
                            }
                        });
                        refreshSee(position, notices.get(position).getSee());
                    }
                });
                break;
            case PUSHCOMMENTTOUSERKEY:
                holder.mItemNoticeListTitleImg.setVisibility(View.GONE);
                holder.mItemNoticeListTitleBlock.setText("回复");
                holder.mItemNoticeListTitle.setText(" 了我的评论");
                holder.mItemNoticeListR.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        jumpPostBarDetailComment(position);
                    }
                });
                break;
            default:
                break;
        }
    }

    private void jumpPostBarDetailComment(int position) {
        refreshSee(position, notices.get(position).getSee());
        //直接跳转动态详细
        JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
            @Override
            public void setMessage(Intent intent) {
                intent.putExtra(InValues.send(R.string.intent_pbid_start), notices.get(position).getPb_id());
                intent.putExtra(InValues.send(R.string.intent_commentid_start), notices.get(position).getComment_id());
            }
        });
    }

    private void jumpPostBarDetail(int position) {
        refreshSee(position, notices.get(position).getSee());
        //直接跳转动态详细
        JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
            @Override
            public void setMessage(Intent intent) {
                intent.putExtra(InValues.send(R.string.intent_pbid_start), notices.get(position).getPb_id());
            }
        });
    }

    private void noSee(@NonNull ViewHolder holder) {
        holder.mItemNoticeListTitleName.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.qihei));
        holder.mItemNoticeListDate.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.qihei));
        holder.mItemNoticeListTitleBlock.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.qihei));
        holder.mItemNoticeListTitle.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.qihei));
    }

    private void yesSee(@NonNull ViewHolder holder) {
        holder.mItemNoticeListTitleName.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.grey));
        holder.mItemNoticeListDate.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.grey));
        holder.mItemNoticeListTitleBlock.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.grey));
        holder.mItemNoticeListTitle.setTextColor(ContextCompat.getColor(MyApplication.getContext(), R.color.grey));
    }

    @Override
    public int getItemCount() {
        return notices == null ? 0 : notices.size();
    }
}
