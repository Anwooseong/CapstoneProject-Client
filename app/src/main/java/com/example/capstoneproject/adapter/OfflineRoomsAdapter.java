package com.example.capstoneproject.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.viewmodel.OfflineRoomModel;

import java.util.ArrayList;

public class OfflineRoomsAdapter extends RecyclerView.Adapter<OfflineRoomsAdapter.ViewHolder>{
    ArrayList<OfflineRoomModel> offlineRoomModels;

    public OfflineRoomsAdapter(ArrayList<OfflineRoomModel> offlineRoomModels) {
        this.offlineRoomModels = offlineRoomModels;
    }

    @NonNull
    @Override
    public OfflineRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_offline, parent, false);
        return new OfflineRoomsAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineRoomsAdapter.ViewHolder holder, int position) {
        OfflineRoomModel offlineRoomModel = offlineRoomModels.get(position);
        holder.setItem(offlineRoomModel);
    }

    @Override
    public int getItemCount() {
        return offlineRoomModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        //ImageView img;
        TextView category;
        TextView date;
        TextView place;
        TextView avg;

        public ViewHolder(View itemView) {
            super(itemView);
            category = itemView.findViewById(R.id.category_tv);
            date = itemView.findViewById(R.id.online_date_tv);
            place = itemView.findViewById(R.id.place_tv);
            avg = itemView.findViewById(R.id.online_avg_tv);
        }

        public void setItem(OfflineRoomModel item) {
            category.setText(item.getCategory());
            date.setText(item.getDate());
            place.setText(item.getPlace());
            avg.setText(item.getAvg());
        }
    }
}
