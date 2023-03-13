package com.example.capstoneproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.RoomActivity;
import com.example.capstoneproject.data.getmatch.request.GetMatchRoomResult;
import com.example.capstoneproject.view.GetMatchRoomView;
import com.example.capstoneproject.viewmodel.OnlineRoomModel;

import java.util.ArrayList;
import java.util.List;

public class OnlineRoomsAdapter extends RecyclerView.Adapter<OnlineRoomsAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetMatchRoomResult> result;
    private Context context;

    public OnlineRoomsAdapter(List<GetMatchRoomResult> result, Context context) {
        this.result = result;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView imageView;
        TextView date, average;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.online_match_room_layout);
            imageView = itemView.findViewById(R.id.online_iv);
            date = itemView.findViewById(R.id.online_date_tv);
            average = itemView.findViewById(R.id.online_avg_tv);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_online, parent, false);
        OnlineRoomsAdapter.ViewHolder vh = new OnlineRoomsAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        //TODO 명세서 추가후 넣기
        holder.imageView.setImageResource(R.drawable.main_logo);
        holder.date.setText(result.get(touchIndex).getDate());
        holder.average.setText("AVG - " + result.get(touchIndex).getAverage());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomActivity.class);
                intent.putExtra("matchIdx", result.get(touchIndex).getMatchIdx());
                Log.d("TAG", "onClick: "+result.get(touchIndex).getMatchIdx());
                Toast.makeText(context, "온라인 매칭방 아이디 값 = "+result.get(touchIndex).getMatchIdx(), Toast.LENGTH_SHORT).show();
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }
}
