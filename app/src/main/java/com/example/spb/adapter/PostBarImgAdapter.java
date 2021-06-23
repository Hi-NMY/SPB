package com.example.spb.adapter;

import android.app.Activity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.ImageDouble;
import com.example.spb.presenter.littlefun.InValues;
import com.example.spb.presenter.littlefun.MyDateClass;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class PostBarImgAdapter extends RecyclerView.Adapter<PostBarImgAdapter.ViewHolder> {

    public View view;
    private Activity activity;
    private ViewHolder viewHolder;
    private List<ImageDouble> newBarImageList;
    private ImageDouble imageDouble;
    private Transferee transferee;
    private TransferConfig config;
    private List<String> minImageList;
    private List<String> maxImageList;
    private boolean isDetail = false;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mImageItemView;
        ImageView mImageClear;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageItemView = (RoundedImageView)itemView.findViewById(R.id.image_item_view);
            mImageClear = (ImageView)itemView.findViewById(R.id.image_clear);
        }
    }

    public PostBarImgAdapter(Activity activity, List<ImageDouble> newBarImageList) {
        minImageList = new ArrayList<>();
        maxImageList = new ArrayList<>();
        this.activity = activity;
        this.newBarImageList = newBarImageList;
        for (ImageDouble img : newBarImageList){
            minImageList.add(InValues.send(R.string.httpHeadert) + img.getMinPath());
            maxImageList.add(InValues.send(R.string.httpHeadert) + img.getMaxPath());
        }
        config = TransferConfig.build()
                .setSourceUrlList(maxImageList)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(activity))
                .create();
    }

    public void isDetail(boolean a){
        this.isDetail = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newbar_img_list, parent, false);
        viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.mImageClear.setVisibility(View.GONE);
        if (transferee == null){
            transferee = Transferee.getDefault(activity);
        }
        if (isDetail){
            Glide.with(MyApplication.getContext())
                    .load(minImageList.get(position))
                    .placeholder(R.drawable.enterbg)
                    .fallback(R.drawable.enterbg)
                    .error(R.drawable.enterbg)
                    .into(holder.mImageItemView);
        }else {
            switch (newBarImageList.size()){
                case 1:
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(528, 528)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
                case 3:
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(300, 300)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
                default:
                    Glide.with(MyApplication.getContext())
                            .load(minImageList.get(position))
                            .placeholder(R.drawable.enterbg)
                            .fallback(R.drawable.enterbg)
                            .error(R.drawable.enterbg)
                            .override(360, 360)
                            .centerCrop()
                            .into(holder.mImageItemView);
                    break;
            }
        }
        holder.mImageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                transferee.apply(config).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return maxImageList.size();
    }
}
