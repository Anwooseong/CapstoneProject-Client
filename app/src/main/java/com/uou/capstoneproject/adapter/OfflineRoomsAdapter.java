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
        ConstraintLayout constraintLayout; // 아이템 뷰의 ConstraintLayout
        ImageView imageView; // 아이템 뷰의 이미지를 표시하는 ImageView
        TextView date, average, category, place; // 아이템 뷰의 날짜, avg를 표시하는 TextView

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

        // 아이템 뷰의 레이아웃 파일을 inflate하여 ViewHolder를 생성
        View view = inflater.inflate(R.layout.item_offline, parent, false);
        OfflineRoomsAdapter.ViewHolder vh = new OfflineRoomsAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull OfflineRoomsAdapter.ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        int categoryNumber = result.get(touchIndex).getNumbers() / 2;

        // 이미지 표시
        holder.imageView.setImageResource(R.drawable.main_logo);
        // 날짜 표시
        holder.date.setText(result.get(touchIndex).getDate());
        // 경기 인원수와 개인전/팀전 표시
        if (categoryNumber == 1) {
            holder.category.setText("" + categoryNumber + " : " + categoryNumber + "개인전");
        } else {
            holder.category.setText("" + categoryNumber + " : " + categoryNumber + "팀전");
        }
        // 장소 표시
        holder.place.setText(result.get(touchIndex).getPlace());
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
