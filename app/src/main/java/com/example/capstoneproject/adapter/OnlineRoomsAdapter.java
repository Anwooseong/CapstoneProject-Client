package com.example.capstoneproject.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.RoomActivity;
import com.example.capstoneproject.viewmodel.OnlineRoomModel;

import java.util.ArrayList;

public class OnlineRoomsAdapter extends RecyclerView.Adapter<OnlineRoomsAdapter.ViewHolder> {

    ArrayList<OnlineRoomModel> onlineRoomModels;
    //ImageView img;

    public OnlineRoomsAdapter(ArrayList<OnlineRoomModel> onlineRoomModels) {
        this.onlineRoomModels = onlineRoomModels;
    }

    @NonNull
    @Override
    public OnlineRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_online, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull OnlineRoomsAdapter.ViewHolder holder, int position) {
        OnlineRoomModel onlineRoomModel = onlineRoomModels.get(position);
        holder.setItem(onlineRoomModel);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RoomActivity.class);
                intent.putExtra("imsi", onlineRoomModels.get(holder.getAdapterPosition()).getDate());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return onlineRoomModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ConstraintLayout layout;
        TextView date, avg;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.online_date_tv);
            avg = itemView.findViewById(R.id.online_avg_tv);
            layout = itemView.findViewById(R.id.online_match_room_layout);
        }

        public void setItem(OnlineRoomModel item) {
            date.setText(item.getDate());
            avg.setText(item.getAvg());
        }

    }
}
