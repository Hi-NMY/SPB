package com.example.spb.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.Topic;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;
import com.example.spb.view.Component.ComponentDialog;
import com.example.spb.view.Component.MyToastClass;
import com.example.spb.view.activity.TopicBarPage;
import com.example.spb.view.utils.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class AttentionTopicAdapter extends RecyclerView.Adapter<AttentionTopicAdapter.ViewHolder> {

    private final Activity activity;
    private final List<Topic> attTopics;
    private ComponentDialog componentDialog;
    private Button mButtonRight;
    private Button mButtonClose;
    private TextView mTopicName;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemAtttopicListHeadimg;
        TextView mItemAtttopicListTitle;
        RelativeLayout mItemAtttopicListR;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemAtttopicListHeadimg = itemView.findViewById(R.id.item_atttopic_list_headimg);
            mItemAtttopicListTitle = itemView.findViewById(R.id.item_atttopic_list_title);
            mItemAtttopicListR = itemView.findViewById(R.id.item_atttopic_list_R);
        }
    }

    public AttentionTopicAdapter(Activity activity, List<Topic> attTopics) {
        this.activity = activity;
        this.attTopics = attTopics;
    }

    public void refreshA() {
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_attention_topic_list, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Topic topic = attTopics.get(position);
        holder.mItemAtttopicListTitle.setText(topic.getTopic_name());
        Glide.with(activity)
                .load(InValues.send(R.string.httpHeadert) + topic.getTopic_image())
                .into(holder.mItemAtttopicListHeadimg);
        holder.mItemAtttopicListR.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                componentDialog = new ComponentDialog(activity, R.layout.dialog_longclick_view, new ComponentDialog.InitDialog() {
                    @Override
                    public void initView(View view) {
                        mButtonClose = (Button) view.findViewById(R.id.button_close);
                        mButtonRight = (Button) view.findViewById(R.id.button_right);
                        mTopicName = (TextView) view.findViewById(R.id.topic_name);
                    }

                    @Override
                    public void initData() {
                        mTopicName.setText(attTopics.get(position).getTopic_name());
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
                                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_reAttTopic), 0, attTopics.get(position));
                                componentDialog.closeMyDialog();
                            }
                        });
                    }
                });
                componentDialog.showMyDialog();
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
                            intent.putExtra(InValues.send(R.string.intent_Topic), attTopics.get(position));
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
        return attTopics == null ? 0 : attTopics.size();
    }
}
