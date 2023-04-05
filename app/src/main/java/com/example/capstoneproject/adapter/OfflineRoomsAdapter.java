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
import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResult;

import java.util.List;

public class OfflineRoomsAdapter extends RecyclerView.Adapter<OfflineRoomsAdapter.ViewHolder> {
    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetMatchRoomResult> result;
    private Context context;

    public OfflineRoomsAdapter(List<GetMatchRoomResult> result, Context context) {
        this.result = result;
        this.context = context;
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView imageView;
        TextView date, average, category, place;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.offline_match_room_layout);
            imageView = itemView.findViewById(R.id.offline_iv);
            category = itemView.findViewById(R.id.category_tv);
            date = itemView.findViewById(R.id.offline_date_tv);
            average = itemView.findViewById(R.id.offline_avg_tv);
            place = itemView.findViewById(R.id.place_tv);
        }
    }

    @NonNull
    @Override
    public OfflineRoomsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_offline, parent, false);
        OfflineRoomsAdapter.ViewHolder vh = new OfflineRoomsAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineRoomsAdapter.ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        int categoryNumber = result.get(touchIndex).getNumbers() / 2;
        //TODO 명세서 추가후 넣기
        holder.imageView.setImageResource(R.drawable.main_logo);
        holder.date.setText(result.get(touchIndex).getDate());
        if (categoryNumber == 1) {
            holder.category.setText("" + categoryNumber + " : " + categoryNumber + "개인전");
        } else {
            holder.category.setText("" + categoryNumber + " : " + categoryNumber + "팀전");
        }
        holder.place.setText(result.get(touchIndex).getPlace());
        holder.average.setText("AVG - " + result.get(touchIndex).getAverage());

        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomActivity.class);
                Log.d("TAG", "onClick: " + result.get(touchIndex).getMatchIdx());
                intent.putExtra("matchIdx", result.get(touchIndex).getMatchIdx());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return result.size();
    }
}
