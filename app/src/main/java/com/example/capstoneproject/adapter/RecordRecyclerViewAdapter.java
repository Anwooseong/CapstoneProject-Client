package com.example.capstoneproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResult;
//import com.example.capstoneproject.data.users.response.GetRecordResult;
import com.example.capstoneproject.viewmodel.RecordModel;

import java.util.ArrayList;
import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
//    private List<GetRecordResult> result;
    private Context context;

//    public RecordRecyclerViewAdapter(List<GetRecordResult> result, Context context) {
//        this.result = result;
//        this.context = context;
//    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_record, parent, false);
        RecordRecyclerViewAdapter.ViewHolder vh = new RecordRecyclerViewAdapter.ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView date, type, winOrLose, home, away, homeScore, awayScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.record_layout);
            date = itemView.findViewById(R.id.record_date_tv);
            type = itemView.findViewById(R.id.record_type_tv);
            winOrLose = itemView.findViewById(R.id.record_result_tv);
            home = itemView.findViewById(R.id.home_tv);
            away = itemView.findViewById(R.id.away_tv);
            homeScore = itemView.findViewById(R.id.home_score_tv);
            awayScore = itemView.findViewById(R.id.away_score_tv);
        }
    }



}
