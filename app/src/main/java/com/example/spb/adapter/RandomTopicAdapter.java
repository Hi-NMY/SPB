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
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.Task;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class RandomTopicAdapter extends RecyclerView.Adapter<RandomTopicAdapter.ViewHolder> {

    private List<Topic> topics;
    private final Activity activity;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView topicName;
        TextView hotNum;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = itemView.findViewById(R.id.item_topic_list_headimg);
            topicName = itemView.findViewById(R.id.item_topic_list_title);
            hotNum = itemView.findViewById(R.id.item_topic_list_attentionnum);
            relativeLayout = itemView.findViewById(R.id.item_topic_list_R);
        }
    }

    public RandomTopicAdapter(Activity activity, List<Topic> topics) {
        this.activity = activity;
        this.topics = topics;
        notifyDataSetChanged();
    }

    public void refreshTopic(List<Topic> topics) {
        this.topics = new ArrayList<>();
        this.topics = topics;
        notifyDataSetChanged();
    }

    public void notifyData() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.topicName.setText(topic.getTopic_name());
        holder.hotNum.setText("热度  " + topic.getTopic_barnum());
        Glide.with(activity)
                .load(InValues.send(R.string.httpHeadert) + topic.getTopic_image())
                .placeholder(R.drawable.icon_topic)
                .error(R.drawable.icon_topic)
                .into(holder.roundedImageView);
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    JumpIntent.startMsgIntent(TopicBarPage.class, new JumpIntent.SetMsg() {
                        @Override
                        public void setMessage(Intent intent) {
                            intent.putExtra(InValues.send(R.string.intent_Topic), topics.get(position));
                            Task.setNewTopicData(topics.get(position).getTopic_name());
                        }
                    });
                } catch (Exception e) {
                    MyToastClass.ShowToast(MyApplication.getContext(), "话题已不存在，请刷新重试");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics == null ? 0 : topics.size();
    }
}
