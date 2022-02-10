package com.example.spb.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.spb.R;
import com.example.spb.app.MyApplication;
import com.example.spb.entity.LocationGps;
import com.example.spb.presenter.utils.DataVerificationTool;
import com.example.spb.presenter.utils.InValues;
import com.example.spb.presenter.utils.SpbBroadcast;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private final List<LocationGps> locationList;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView locationName;
        TextView locationDetail;
        RelativeLayout relativeLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            locationName = itemView.findViewById(R.id.location_name);
            locationDetail = itemView.findViewById(R.id.location_detail);
            relativeLayout = itemView.findViewById(R.id.location_list_r);
        }
    }

    public LocationAdapter(List<LocationGps> locationList, Activity activity) {
        this.locationList = locationList;
    }

    @NonNull
    @Override
    public LocationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_location_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationAdapter.ViewHolder holder, int position) {
        LocationGps location = locationList.get(position);
        holder.locationName.setText(location.getLocationName());
        if (!DataVerificationTool.isEmpty(location.getLocationDetail())) {
            holder.locationDetail.setVisibility(View.VISIBLE);
            holder.locationDetail.setText(location.getLocationDetail());
        } else {
            holder.locationDetail.setVisibility(View.GONE);
        }

        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SpbBroadcast.sendReceiver(MyApplication.getContext(), InValues.send(R.string.Bcr_transfer_loc)
                        , position, holder.locationName.getText().toString());
            }
        });

    }

    @Override
    public int getItemCount() {
        return locationList == null ? 0 : locationList.size();
    }
}
