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
import com.bumptech.glide.signature.MediaStoreSignature;
import com.example.spb.R;
import com.example.spb.base.BaseMVPActivity;
import com.example.spb.entity.Bar;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.view.activity.PersonalSpacePage;
import com.example.spb.view.activity.PostBarDetailPage;
import com.example.spb.view.littlefun.JumpIntent;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class CollectBarAdapter extends RecyclerView.Adapter<CollectBarAdapter.ViewHolder> {

    private List<Bar> collectBars;
    private BaseMVPActivity baseMVPActivity;
    private Bar bar;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mItemCollectListHeadimg;
        TextView mItemCollectListUsername;
        TextView mItemCollectListTitle;
        RelativeLayout mCollectBarR;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemCollectListHeadimg = (RoundedImageView) itemView.findViewById(R.id.item_collect_list_headimg);
            mItemCollectListUsername = (TextView) itemView.findViewById(R.id.item_collect_list_username);
            mItemCollectListTitle = (TextView) itemView.findViewById(R.id.item_collect_list_title);
            mCollectBarR = (RelativeLayout) itemView.findViewById(R.id.collect_bar_R);
        }
    }

    public CollectBarAdapter(Activity activity, List<Bar> collectBars) {
        this.collectBars = collectBars;
        this.baseMVPActivity = (BaseMVPActivity) activity;
    }

    public void removeData(String pbid){
        Bar cache = collectBars.stream().filter(collectBars -> collectBars.getPb_one_id().equals(pbid)).findAny().orElse(null);
        if (cache != null){
            int a = collectBars.indexOf(cache);
            if (a != -1){
                collectBars.remove(a);
                notifyItemRemoved(a);
                notifyItemRangeChanged(a, collectBars.size() + 1);
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_collect_bar, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        bar = collectBars.get(position);
        holder.mItemCollectListUsername.setText(bar.getUser_name());
        holder.mItemCollectListTitle.setText(bar.getPb_article());
        Glide.with(baseMVPActivity)
                .load(InValues.send(R.string.httpHeader) + "/UserImageServer/" + bar.getUser_account() + "/HeadImage/myHeadImage.png")
                .signature(new MediaStoreSignature(String.valueOf(System.currentTimeMillis()),1,1))
                .into(holder.mItemCollectListHeadimg);

        holder.mCollectBarR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.startMsgIntent(PostBarDetailPage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_Bar),collectBars.get(position));
                    }
                });
            }
        });

        holder.mItemCollectListHeadimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpIntent.startMsgIntent(PersonalSpacePage.class, new JumpIntent.SetMsg() {
                    @Override
                    public void setMessage(Intent intent) {
                        intent.putExtra(InValues.send(R.string.intent_User_account),collectBars.get(position).getUser_account());
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectBars.size();
    }
}
