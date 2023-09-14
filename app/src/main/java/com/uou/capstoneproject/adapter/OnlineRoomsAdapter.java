package com.uou.capstoneproject.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.activity.RoomActivity;
import com.uou.capstoneproject.data.match.response.GetMatchRoomResult;

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
        ConstraintLayout constraintLayout; // 아이템 뷰의 ConstraintLayout
        ImageView imageView; // 아이템 뷰의 이미지를 표시하는 ImageView
        TextView date, average; // 아이템 뷰의 날짜, avg를 표시하는 TextView

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

        // 아이템 뷰의 레이아웃 파일을 inflate하여 ViewHolder를 생성
        View view = inflater.inflate(R.layout.item_online, parent, false);
        OnlineRoomsAdapter.ViewHolder vh = new OnlineRoomsAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        // 이미지 표시
        holder.imageView.setImageResource(R.drawable.main_logo);
        // 날짜 표시
        holder.date.setText(result.get(touchIndex).getDate());
        // avg 표시
        holder.average.setText("AVG - " + result.get(touchIndex).getAverage());

        // 아이템 클릭 이벤트 설정
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RoomActivity.class);
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
