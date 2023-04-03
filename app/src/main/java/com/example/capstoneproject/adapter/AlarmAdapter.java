package com.example.capstoneproject.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.DateDiff;
import com.example.capstoneproject.data.users.response.push.GetPushListDetail;
import com.example.capstoneproject.data.users.response.push.GetPushListResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlarmAdapter extends RecyclerView.Adapter<AlarmAdapter.ViewHolder> {

    private int oldPosition = -1;
    private int selectedPosition = -1;
    private List<GetPushListResult> result;
    private Context context;
    private int userIdx;
    private String jwt;

    public AlarmAdapter(List<GetPushListResult> result, Context context, int userIdx, String jwt) {
        this.result = result;
        this.context = context;
        this.userIdx = userIdx;
        this.jwt = jwt;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ConstraintLayout constraintLayout;
        TextView date;
        RecyclerView recyclerView;
        AlarmDetailAdapter adapter;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            constraintLayout = itemView.findViewById(R.id.alarm_layout);
            date = itemView.findViewById(R.id.alarm_day_tv);
            recyclerView = itemView.findViewById(R.id.alarm_detail_recyclerview);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.item_alarm, parent, false);
        AlarmAdapter.ViewHolder vh = new AlarmAdapter.ViewHolder(view);

        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int touchIndex = holder.getAdapterPosition();
        //서버에서 보낸 날짜
        String getDate = result.get(touchIndex).getDate();
        String[] getSplitDate = getDate.split("-");
        //오늘 날짜
        Date nowDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String nowConversionDate = simpleDateFormat.format(nowDate);
        String[] nowSplitDate = nowConversionDate.split("-");
        //오늘과 서버에서 보낸 날짜의 차이
        DateDiff dateDiff = new DateDiff();
        int differenceOfDate = dateDiff.getDifferenceOfDate(Integer.parseInt(nowSplitDate[0]), Integer.parseInt(nowSplitDate[1]), Integer.parseInt(nowSplitDate[2]), Integer.parseInt(getSplitDate[0]), Integer.parseInt(getSplitDate[1]), Integer.parseInt(getSplitDate[2]));
        Log.d("diffDate", "onBindViewHolder: " + differenceOfDate);
        if (differenceOfDate == 0) {
            holder.date.setText("오늘");
        }  else {
            holder.date.setText(""+differenceOfDate+"일 전");
        }
        initRecyclerView(result.get(touchIndex).getAlarmDetailList(), holder);
    }

    private void initRecyclerView(List<GetPushListDetail> alarmDetailList, ViewHolder holder) {
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context.getApplicationContext()));
        holder.adapter = new AlarmDetailAdapter(alarmDetailList, context, userIdx, jwt);
        holder.recyclerView.setAdapter(holder.adapter);
    }


    @Override
    public int getItemCount() {
        return result.size();
    }

    public Context getContext() {
        return context;
    }
}
