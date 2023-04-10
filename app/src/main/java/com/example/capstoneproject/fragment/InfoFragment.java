package com.example.capstoneproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.LoginActivity;
import com.example.capstoneproject.common.SharedPreferencesManager;
import com.example.capstoneproject.data.users.UserService;
import com.example.capstoneproject.data.users.response.info.GetUserInfoResult;
import com.example.capstoneproject.view.GetUserInfoView;

public class InfoFragment extends Fragment implements GetUserInfoView {

    private ImageView logoutBtn;
    private TextView nickName;
    private TextView winText,loseText,drawText,avgText,highScoreText,strikeRateText,gameCountText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        initView(root);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.clearPreferences(getContext());
                removeInfo();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUserInfo();

    }
    private void getUserInfo(){
        UserService userService = new UserService();
        userService.setUserInfoView(this);
        userService.getUserInfo(getJwt());
    }

    private String getJwt(){
        SharedPreferences spf = this.getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    private void removeInfo(){
        SharedPreferences spf = getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.apply();
    }

    private void initView(View root) {
        logoutBtn = root.findViewById(R.id.logout_btn);
        nickName = root.findViewById(R.id.info_nickname_tv);
        winText = root.findViewById(R.id.info_win_tv);
        loseText = root.findViewById(R.id.info_lose_tv);
        drawText = root.findViewById(R.id.info_draw_tv);
        avgText = root.findViewById(R.id.info_avg_tv);
        highScoreText = root.findViewById(R.id.best_score_tv);
        strikeRateText = root.findViewById(R.id.avg_tv);
        gameCountText = root.findViewById(R.id.game_count_tv);
    }

    @Override
    public void onGetUserInfoSuccess(GetUserInfoResult result) {
        nickName.setText(result.getNickName());
        winText.setText(String.valueOf(result.getWinCount()));
        loseText.setText(String.valueOf(result.getLoseCount()));
        drawText.setText(String.valueOf(result.getDrawCount()));
        avgText.setText(String.valueOf(result.getAverage()));
        highScoreText.setText(String.valueOf(result.getHighScore()));
        strikeRateText.setText(String.valueOf(result.getStrikeRate()));
        gameCountText.setText(String.valueOf(result.getGameCount()));
    }

    @Override
    public void onGetUserInfoFailure() {

    }
}
