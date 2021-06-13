package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.littlefun.InValues;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class RandomTopicAdapter extends RecyclerView.Adapter<RandomTopicAdapter.ViewHolder> {

    private List<Topic> topics;
    private Activity activity;
    private View view;
    private Topic topic;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView roundedImageView;
        TextView topicName;
        TextView hotNum;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            roundedImageView = (RoundedImageView)itemView.findViewById(R.id.item_topic_list_headimg);
            topicName = (TextView)itemView.findViewById(R.id.item_topic_list_title);
            hotNum = (TextView)itemView.findViewById(R.id.item_topic_list_attentionnum);
        }
    }

    public RandomTopicAdapter(Activity activity,List<Topic> topics) {
        this.activity = activity;
        this.topics = topics;
    }

    public void refreshTopic(List<Topic> topics) {
        this.topics = new ArrayList<>();
        this.topics = topics;
        notifyItemRangeChanged(0, topics.size() - 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_topic_list,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        topic = topics.get(position);
        holder.topicName.setText(topic.getTopic_name());
        holder.hotNum.setText("热度  " + topic.getTopic_barnum());
        Glide.with(activity)
                .load(InValues.send(R.string.httpHeadert) + topic.getTopic_image())
                .into(holder.roundedImageView);
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
