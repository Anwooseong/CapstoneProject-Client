package com.example.capstoneproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.data.NextMatchModel;

public class NextMatchViewHolderPage extends RecyclerView.ViewHolder {

    private TextView date;
    private TextView matchType;
    private TextView homeName;
    private TextView awayName;
    private ImageView homeImage;
    private ImageView awayImage;

    NextMatchModel data;

    NextMatchViewHolderPage(View itemView){
        super(itemView);
        date = itemView.findViewById(R.id.next_match_date_tv);
        matchType = itemView.findViewById(R.id.next_match_type_tv);
        homeName = itemView.findViewById(R.id.next_match_home_tv);
        awayName = itemView.findViewById(R.id.next_match_away_tv);
        homeImage = itemView.findViewById(R.id.home_iv);
        awayImage = itemView.findViewById(R.id.away_iv);

    }

    public void onBind(NextMatchModel data){
        this.data = data;

        date.setText(data.getDate());
        matchType.setText(data.getMatchType());
        homeName.setText(data.getHomeName());
        awayName.setText(data.getAwayName());
        homeImage.setClipToOutline(true);
        awayImage.setClipToOutline(true);
    }
}
