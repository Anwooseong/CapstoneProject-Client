package com.example.capstoneproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.data.RecordModel;

import java.util.ArrayList;

public class RecordRecyclerViewAdapter extends RecyclerView.Adapter<RecordRecyclerViewAdapter.ViewHolder> {

    ArrayList<RecordModel> recordModels;

    public RecordRecyclerViewAdapter(ArrayList<RecordModel> recordModels) {
        this.recordModels = recordModels;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.item_record, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RecordModel recordModel = recordModels.get(position);
        holder.setItem(recordModel);
    }

    @Override
    public int getItemCount() {
        return recordModels.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView date;
        TextView type;
        TextView result;
        TextView homeTeam;
        TextView homeScore;
        TextView awayTeam;
        TextView awayScore;

        public ViewHolder(View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.record_date_tv);
            type = itemView.findViewById(R.id.record_type_tv);
            result = itemView.findViewById(R.id.record_result_tv);
            homeTeam = itemView.findViewById(R.id.home_tv);
            homeScore = itemView.findViewById(R.id.home_score_tv);
            awayTeam = itemView.findViewById(R.id.away_tv);
            awayScore = itemView.findViewById(R.id.away_score_tv);

        }

        public void setItem(RecordModel item) {
            date.setText(item.getDate());
            type.setText(item.getType());
            result.setText(item.getResult());
            homeTeam.setText(item.getHomeTeam());
            homeScore.setText(item.getHomeScore());
            awayTeam.setText(item.getAwayTeam());
            awayScore.setText(item.getAwayScore());
        }
    }


}
