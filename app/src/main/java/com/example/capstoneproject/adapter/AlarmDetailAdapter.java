package com.example.capstoneproject.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.capstoneproject.R;
import com.example.capstoneproject.data.users.response.push.GetPushListDetail;

import java.util.List;

public class AlarmDetailAdapter extends RecyclerView.Adapter<AlarmDetailAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetPushListDetail> result;
    private Context context;

    public AlarmDetailAdapter(List<GetPushListDetail> result, Context context) {
        this.result = result;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView profileImage;
        TextView title, content;
        AppCompatButton profileButton, agreeButton, disagreeButton, matchDetailButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.alarm_detail_layout);
            profileImage = itemView.findViewById(R.id.opponent_profile_iv);
            title = itemView.findViewById(R.id.title_tv);
            content = itemView.findViewById(R.id.content_tv);
            profileButton = itemView.findViewById(R.id.opponent_profile_btn);
            agreeButton = itemView.findViewById(R.id.agree_btn);
            disagreeButton = itemView.findViewById(R.id.disagree_btn);
            matchDetailButton = itemView.findViewById(R.id.match_detail_btn);
        }

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_detail_alarm, parent, false);
        AlarmDetailAdapter.ViewHolder vh = new AlarmDetailAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        Log.d("ImagUrl", "ImageUrl: " + result.get(touchIndex).getImageUrl());
        RequestOptions requestOptions = RequestOptions.skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(holder.itemView.getContext()).load(result.get(touchIndex).getImageUrl())
                .apply(requestOptions)
                .into(holder.profileImage);

        holder.profileImage.setClipToOutline(true);
//        holder.profileImage.setImageURI(Uri.parse(result.get(touchIndex).getImageUrl()));
        holder.profileImage.setImageResource(R.drawable.main_logo);
        holder.title.setText(result.get(touchIndex).getPushTitle());
        holder.content.setText(result.get(touchIndex).getPushContent());


    }

    @Override
    public int getItemCount() {
        return result.size();
    }

}
