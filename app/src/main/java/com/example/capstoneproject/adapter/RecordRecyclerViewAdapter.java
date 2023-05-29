package com.example.capstoneproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
//import com.example.capstoneproject.data.users.response.record.GetRecordResult;
import com.example.capstoneproject.data.users.response.record.GetRecordResult;

import java.util.List;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetRecordResult> result;

    public RecordRecyclerViewAdapter(List<GetRecordResult> result) {
        this.result = result;
    }

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
        int countType = result.get(touchIndex).getMatchRecordsResList().get(0).getCount()/2;
        if (countType == 1) {
            holder.type.setText(""+countType+" : "+countType+" 개인전");
        }else{
            holder.type.setText(""+countType+" : "+countType+" 팀전");
        }
        holder.date.setText(result.get(touchIndex).getMatchRecordsResList().get(0).getDate());
        holder.winOrLose.setText(result.get(touchIndex).getMatchRecordsResList().get(0).getWinOrLose());
        holder.home.setText(result.get(touchIndex).getMatchRecordsResList().get(0).getNickname());
        holder.homeScore.setText(""+result.get(touchIndex).getMatchRecordsResList().get(0).getTotalScore());

        holder.away.setText(result.get(touchIndex).getMatchRecordsResList().get(1).getNickname());
        holder.awayScore.setText(""+result.get(touchIndex).getMatchRecordsResList().get(1).getTotalScore());
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

    @Override
    public int getItemCount() {
        return result.size();
    }

}
