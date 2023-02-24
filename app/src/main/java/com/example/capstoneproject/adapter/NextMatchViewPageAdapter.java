package com.example.capstoneproject.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.adapter.viewholder.NextMatchViewHolderPage;
import com.example.capstoneproject.R;
import com.example.capstoneproject.viewmodel.NextMatchModel;

import java.util.ArrayList;

public class NextMatchViewPageAdapter extends RecyclerView.Adapter<NextMatchViewHolderPage> {

    private ArrayList<NextMatchModel> listData;

    public NextMatchViewPageAdapter(ArrayList<NextMatchModel> listData) {
        this.listData = listData;
    }

    @NonNull
    @Override
    public NextMatchViewHolderPage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.item_next_match, parent, false);
        return new NextMatchViewHolderPage(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NextMatchViewHolderPage holder, int position) {
        if(holder instanceof NextMatchViewHolderPage){
            NextMatchViewHolderPage viewHolder = (NextMatchViewHolderPage) holder;
            viewHolder.onBind(listData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
