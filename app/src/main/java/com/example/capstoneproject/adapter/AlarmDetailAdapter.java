package com.example.capstoneproject.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
    private int userIdx;

    public AlarmDetailAdapter(List<GetPushListDetail> result, Context context, int userIdx) {
        this.result = result;
        this.context = context;
        this.userIdx = userIdx;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ConstraintLayout constraintLayout;
        ImageView profileImage;
        TextView title, content, agreeTextView, disagreeTextView;
        AppCompatButton agreeButton, disagreeButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.alarm_detail_layout);
            profileImage = itemView.findViewById(R.id.opponent_profile_iv);
            title = itemView.findViewById(R.id.title_tv);
            content = itemView.findViewById(R.id.content_tv);
            agreeTextView = itemView.findViewById(R.id.agree_tv);
            disagreeTextView = itemView.findViewById(R.id.disagree_tv);
            agreeButton = itemView.findViewById(R.id.agree_btn);
            disagreeButton = itemView.findViewById(R.id.disagree_btn);
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
        GetPushListDetail getResult = result.get(touchIndex);

        RequestOptions requestOptions = RequestOptions.skipMemoryCacheOf(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE);
        Glide.with(holder.itemView.getContext()).load(getResult.getImageUrl())
                .apply(requestOptions)
                .into(holder.profileImage);
        holder.profileImage.setClipToOutline(true);
        Log.d("TAG", "onBindViewHolder: "+getResult.getStatus());
        Log.d("TAG", "userIdx: "+userIdx);
        if (getResult.getOwnerUserIdx() != userIdx) { //로그인 유저가 방장이 아닐때
            if (getResult.getStatus().equals("W")) { //대기중 (요청하였습니다 텍스트)
                viewVisibility(holder, View.GONE, View.GONE, View.GONE, View.GONE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 온라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께 요청하였습니다!");
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 오프라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께 요청하였습니다!");
                }

            } else if (getResult.getStatus().equals("A")) {//수락 상태 (상대프로필, 매칭 상세)
                viewVisibility(holder, View.GONE, View.GONE, View.GONE, View.GONE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 온라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께서 수락하였습니다!");
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 오프라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께서 수락하였습니다!");
                }

            } else if (getResult.getStatus().equals("D")) { //거절 상태 ( 아무 것도 없음)
                viewVisibility(holder, View.GONE, View.GONE, View.GONE, View.GONE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 온라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께 거절하였습니다!");
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getGameTime() + " 오프라인 매칭");
                    holder.content.setText(getResult.getOpponentNickName()+"님께 거절하였습니다!");
                }

            }
        } else { //로그인 유저가 방장일때
            if (getResult.getStatus().equals("W")) { //대기중 (수락, 거절)
                viewVisibility(holder, View.VISIBLE, View.VISIBLE, View.GONE, View.GONE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 온라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 오프라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                }

            } else if (getResult.getStatus().equals("A")) {//수락 상태 (상대 프로필)
                viewVisibility(holder, View.GONE, View.GONE, View.VISIBLE, View.GONE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 온라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 오프라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                }

            } else if (getResult.getStatus().equals("D")) { //거절 상태 (아무 것오 없음)
                viewVisibility(holder, View.GONE, View.GONE, View.GONE, View.VISIBLE);
                if (getResult.getNetworkType().equals("ONLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 온라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                } else if (getResult.getNetworkType().equals("OFFLINE")) {
                    holder.title.setText(getResult.getOpponentNickName()+"님의 오프라인 매칭 요청");
                    holder.content.setText(getResult.getGameTime());
                }

            }
        }


    }

    private void viewVisibility(@NonNull ViewHolder holder, int onPress1, int onPress2, int onPress3, int onPress4) {
        holder.agreeButton.setVisibility(onPress1);
        holder.agreeButton.setVisibility(onPress2);
        holder.agreeButton.setVisibility(onPress3);
        holder.disagreeButton.setVisibility(onPress4);
    }


    @Override
    public int getItemCount() {
        return result.size();
    }



}
