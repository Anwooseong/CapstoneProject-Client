package com.example.capstoneproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstoneproject.activity.AlarmActivity;
import com.example.capstoneproject.activity.CreateActivity;
//import com.example.capstoneproject.adapter.NextMatchViewPageAdapter;
import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.TestActivity;
import com.example.capstoneproject.adapter.NextMatchViewPageAdapter;
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.response.GetAllMatchCountResponse;
import com.example.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResult;
import com.example.capstoneproject.data.users.UserService;
import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResult;
import com.example.capstoneproject.view.GetAllMatchCountView;
import com.example.capstoneproject.view.GetAllOnlineMatchCountView;
import com.example.capstoneproject.view.GetRemainMatchRoomView;
import com.example.capstoneproject.view.GetSimpleInfoView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

public class HomeFragment extends Fragment implements GetRemainMatchRoomView, GetSimpleInfoView, GetAllMatchCountView, GetAllOnlineMatchCountView {

    private ConstraintLayout profileLayout;
    private ImageView profileImage, alarmBtn;
    private TextView profileName;
    private TextView profileAvg;
    private TextView profileOdds;
    private TextView nextMatchEmpty;
    private TextView allMatchCount, allOnlineMatchCount;
    private ConstraintLayout createMatchRoom;
    private ViewPager2 nextMatchViewPager;
    private TabLayout indicator;
    private NextMatchViewPageAdapter adapter;
    private ImageView testImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);


        createMatchingRoomListener();

        testImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TestActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onStart() {
        super.onStart();

        getSimpleInfo();
        getList();
        getAllMatchCount();
        getAllOnlineMatchCount();

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getAllOnlineMatchCount() {
        MatchService matchService = new MatchService();
        matchService.setGetAllOnlineMatchCountView(this);
        matchService.getAllOnlineMatchCount();
    }

    private void getAllMatchCount() {
        MatchService matchService = new MatchService();
        matchService.setGetAllMatchCountView(this);
        matchService.getAllMatchCount();
    }


    private void getList() {
        MatchService matchService = new MatchService();
        matchService.setGetRemainMatchRoomView(this);
        matchService.getRemainResult(getJwt());
    }

    private void getSimpleInfo(){
        UserService userService = new UserService();
        userService.setSimpleInfoView(this);
        userService.getSimpleInfo(getJwt());
    }

    private void initRecyclerView(List<GetRemainMatchRoomResult> result){
        Log.d("TAG", "result Count: "+result.size());
        if (result.size() == 0) {
            nextMatchEmpty.setVisibility(View.VISIBLE);
            return;
        }
        nextMatchEmpty.setVisibility(View.GONE);
        nextMatchViewPager.setVisibility(View.VISIBLE);
        adapter = new NextMatchViewPageAdapter(result, this.getContext(), getJwt());
        nextMatchViewPager.setAdapter(adapter);
    }

    private void createMatchingRoomListener() {
        createMatchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getJwt(){
        SharedPreferences spf = this.getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void initView(View root) {
        profileLayout = root.findViewById(R.id.profile_layout);
        profileImage = root.findViewById(R.id.profile_iv);
        profileName = root.findViewById(R.id.profile_name_tv);
        profileAvg = root.findViewById(R.id.profile_avg_tv);
        profileOdds = root.findViewById(R.id.profile_odds_tv);
        allMatchCount = root.findViewById(R.id.possible_count_tv);
        allOnlineMatchCount = root.findViewById(R.id.possible_online_count_tv);
        nextMatchEmpty = root.findViewById(R.id.next_match_null_tv);
        nextMatchViewPager = root.findViewById(R.id.next_match_viewpager);
        indicator = root.findViewById(R.id.viewpager_indicator);
        createMatchRoom = root.findViewById(R.id.create_match_layout);
        profileImage.setClipToOutline(true);
        alarmBtn = root.findViewById(R.id.alarm_btn);
        testImg = root.findViewById(R.id.test_btn);
    }


    @Override
    public void onGetRemainMatchRoomSuccess(GetRemainMatchRoomResponse result) {
        initRecyclerView(result.getResult());
        //NEXT MATCH 인디케이터
        if (result.getResult().size() == 0) {
            indicator.setVisibility(View.GONE);
            nextMatchViewPager.setVisibility(View.GONE);
            return;
        }
        indicator.setVisibility(View.VISIBLE);
        new TabLayoutMediator(indicator, nextMatchViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();
    }

    @Override
    public void onGetRemainMatchRoomFailure() {

    }

    @Override
    public void onGetSimpleInfoSuccess(GetSimpleInfoResult result) {
//        profileImage  -> TODO Glide 사용
        profileName.setText(result.getNickName());
        profileAvg.setText(String.valueOf(result.getAverage()));
        profileOdds.setText(result.getWinCount()+"승 "+result.getLoseCount()+"패 "+result.getDrawCount()+"무 "+"(승률 "+result.getWinRate()+"%)");
    }

    @Override
    public void onGetSimpleInfoFailure() {

    }


    @Override
    public void onGetAllMatchCountSuccess(GetAllMatchCountResponse response) {
        allMatchCount.setText(String.valueOf(response.getResult().getCount())+"건");
    }

    @Override
    public void onGetAllMatchCountFailure(GetAllMatchCountResponse response) {
        Log.d("TAG", response.getMessage());
    }

    @Override
    public void onGetAllMatchCountSuccess(GetAllOnlineMatchCountResponse response) {
        allOnlineMatchCount.setText(String.valueOf(response.getResult().getCount())+"건");
    }

    @Override
    public void onGetAllMatchCountFailure(GetAllOnlineMatchCountResponse response) {

    }
}
