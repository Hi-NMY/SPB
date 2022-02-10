package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.ImageDouble;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.makeramen.roundedimageview.RoundedImageView;
import com.vansz.glideimageloader.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewBarImageAdapter extends RecyclerView.Adapter<NewBarImageAdapter.ViewHolder> {

    private final Activity activity;
    private final List<String> newBarImageList;
    private Transferee transferee;
    private final RemoveImg removeImg;
    private final TransferConfig config;

    public class ViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView mImageItemView;
        ImageView mImageClear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageItemView = itemView.findViewById(R.id.image_item_view);
            mImageClear = itemView.findViewById(R.id.image_clear);
        }
    }

    public NewBarImageAdapter(Activity a, List<ImageDouble> imgList, RemoveImg r) {
        this.activity = a;
        this.removeImg = r;
        newBarImageList = new ArrayList<>();
        for (ImageDouble img : imgList) {
            newBarImageList.add(img.getMaxPath());
        }
        config = TransferConfig.build()
                .setSourceUrlList(newBarImageList)
                .setProgressIndicator(new ProgressBarIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setImageLoader(GlideImageLoader.with(activity))
                .create();
    }

    public void removeImage(int option) {
        notifyItemRemoved(option);
        newBarImageList.remove(option);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newbar_img_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (transferee == null) {
            transferee = Transferee.getDefault(activity);
        }
        String imageDouble = newBarImageList.get(position);
        Glide.with(MyApplication.getContext())
                .load(imageDouble)
                .centerCrop()
                .override(288, 288)
                .into(holder.mImageItemView);

        holder.mImageItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                config.setNowThumbnailIndex(position);
                transferee.apply(config).show();
            }
        });
        holder.mImageClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeImg.removeImg(position);
            }
        });
    }

    public void destroyTransFeree() {
        transferee.destroy();
    }

    @Override
    public int getItemCount() {
        return newBarImageList == null ? 0 : newBarImageList.size();
    }

    public interface RemoveImg {
        void removeImg(int option);
    }
}
