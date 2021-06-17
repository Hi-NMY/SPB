package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AttentionTopicAdapter extends RecyclerView.Adapter<AttentionTopicAdapter.ViewHolder> {

    private View view;
    private Activity activity;
    private List<Topic> attTopics;
    private Topic topic;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemAtttopicListHeadimg;
        TextView mItemAtttopicListTitle;
        RelativeLayout mItemAtttopicListR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemAtttopicListHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_atttopic_list_headimg);
            mItemAtttopicListTitle = (TextView) itemView.findViewById(R.id.item_atttopic_list_title);
            mItemAtttopicListR = (RelativeLayout) itemView.findViewById(R.id.item_atttopic_list_R);
        }
    }

    public AttentionTopicAdapter(Activity activity,List<Topic> attTopics) {
        this.activity = activity;
        this.attTopics = attTopics;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attention_topic_list, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        topic = attTopics.get(position);
        holder.mItemAtttopicListTitle.setText(topic.getTopic_name());
        Glide.with(activity)
                .load(InValues.send(R.string.httpHeadert) + topic.getTopic_image())
                .into(holder.mItemAtttopicListHeadimg);
        holder.mItemAtttopicListR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                return true;
            }
        });
        holder.mItemAtttopicListR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JumpIntent.startMsgIntent(TopicBarPage.class, new JumpIntent.SetMsg() {
                        @Override
                        public void setMessage(Intent intent) {
                            intent.putExtra(InValues.send(R.string.intent_Topic),attTopics.get(position));
                        }
                    });
                }catch (Exception e){
                    MyToastClass.ShowToast(MyApplication.getContext(),"话题已不存在，请刷新重试");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return attTopics.size();
    }
}
