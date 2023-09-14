//package com.uou.capstoneproject.activity;
//
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.os.CountDownTimer;
//import android.view.View;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.appcompat.widget.AppCompatButton;
//
//import com.uou.capstoneproject.R;
//import com.uou.capstoneproject.common.DateDiff;
//import com.uou.capstoneproject.data.game.GameService;
//import com.uou.capstoneproject.data.game.request.CheckSocketActiveRequest;
//import com.uou.capstoneproject.data.game.response.CheckSocketActiveResult;
//import com.uou.capstoneproject.data.match.MatchService;
//import com.uou.capstoneproject.data.match.response.plan.GetDetailMatchResponse;
//import com.uou.capstoneproject.data.match.response.plan.GetDetailMatchResultDetail;
//import com.uou.capstoneproject.data.push.PushService;
//import com.uou.capstoneproject.data.push.request.PostCancelMatchReq;
//import com.uou.capstoneproject.data.push.request.PostCancelMatchUser;
//import com.uou.capstoneproject.view.CheckSocketActiveView;
//import com.uou.capstoneproject.view.GetDetailMatchView;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ScheduleActivity extends AppCompatActivity implements GetDetailMatchView, CheckSocketActiveView {
//
//    private TextView date;
//    private TextView homeText, homeHighScore, homeAvgScore, homeGameCount, homeWinCount, homeLoseCount;
//    private TextView awayText, awayHighScore, awayAvgScore, awayGameCount, awayWinCount, awayLoseCount;
//    private TextView matchCode, remainTime;
//    private ImageView homeImageUrl, awayImageUrl, backBtn;
//    private AppCompatButton startBtn, cancelBtn;
//    private int matchIdx;
//    private CountDownTimer countDownTimer;
//    private List<PostCancelMatchUser> userIdxList = null;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_schedule);
//
//        // 인텐트에서 matchIdx 값을 가져온다.
//        Intent intent = getIntent();
//        matchIdx = intent.getIntExtra("matchIdx", 0);
//
//        //뷰 초기화
//        init();
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        MatchService matchService = new MatchService();
//        matchService.setGetDetailMatchView(this);
//
//        // getJwt()와 matchIdx를 이용하여 상세 매치 결과를 가져온다.
//        matchService.getDetailMatchResult(getJwt(), matchIdx);
//
//        backBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//        // 매칭 시작 버튼이 클릭되었을 때 소켓의 활성 여부를 확인
//        startBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                checkSocketActive();
//            }
//       });
//
//        // 매칭 취소 버튼이 클릭되었을 때 매치를 취소하고 액티비티를 종료
//        cancelBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cancelMatch();
//                finish();
//            }
//        });
//    }
//
//    private void cancelMatch() {
//        PushService pushService = new PushService();
//
//        // getJwt()와 matchIdx, userIdxList를 이용하여 매치를 취소
//        pushService.postCancelMatch(getJwt(), new PostCancelMatchReq(matchIdx, userIdxList));
//    }
//
//    //뷰 초기화
//    private void init() {
//        date = findViewById(R.id.next_match_date_tv);
//        homeText = findViewById(R.id.home_match_schedule_tv);
//        homeHighScore = findViewById(R.id.home_score_tv);
//        homeAvgScore = findViewById(R.id.home_average_tv);
//        homeGameCount = findViewById(R.id.home_game_tv);
//        homeWinCount = findViewById(R.id.home_win_tv);
//        homeLoseCount = findViewById(R.id.home_lose_tv);
//
//        awayText = findViewById(R.id.away_match_schedule_tv);
//        awayHighScore = findViewById(R.id.away_score_tv);
//        awayAvgScore = findViewById(R.id.away_average_tv);
//        awayGameCount = findViewById(R.id.away_game_tv);
//        awayWinCount = findViewById(R.id.away_win_tv);
//        awayLoseCount = findViewById(R.id.away_lose_tv);
//
//        matchCode = findViewById(R.id.match_code_tv);
//        remainTime = findViewById(R.id.match_remain_time_tv);
//
//        homeImageUrl = findViewById(R.id.home_match_schedule_iv);
//        awayImageUrl = findViewById(R.id.away_match_schedule_iv);
//
//        startBtn = findViewById(R.id.match_start_btn);
//        cancelBtn = findViewById(R.id.match_cancel_btn);
//        backBtn = findViewById(R.id.match_schedule_back_btn);
//    }
//
//    //SharedPreferences에 있는 jwt를 가져온다.
//    private String getJwt(){
//        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
//        return spf.getString("jwt","");
//    }
//
//    //SharedPreferences에 있는 userIdx를 가져온다.
//    private Integer getUserIdx(){
//        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
//        return spf.getInt("userIdx",-1);
//    }
//
//    //소켓 활성 여부 API 호출 메서드
//    private void checkSocketActive(){
//        GameService gameService = new GameService();
//        gameService.setCheckSocketActiveView(this);
//
//        // matchIdx를 이용하여 소켓 활성 여부를 확인
//        gameService.checkSocketActive(new CheckSocketActiveRequest(matchIdx));
//    }
//
//    //상세 매치 API 호출 성공시
//    @Override
//    public void onDetailMatchSuccess(GetDetailMatchResponse resp) {
//        // 매치의 날짜를 설정
//        date.setText(resp.getResult().getGameTime());
//
//        // 매치 결과 상세 정보를 가져온다.
//        List<GetDetailMatchResultDetail> getDetailResult = resp.getResult().getGetDetailMatchResultDetails();
//        cancelUserIdxList(getDetailResult);
//
//        //프로필 이미지를 설정
//        if (getDetailResult.size() == 1) {
//            if (getDetailResult.get(0).getImageUrl() == " ") {
//                homeImageUrl.setImageResource(R.drawable.default_profile);
//                awayImageUrl.setImageResource(R.drawable.default_profile);
//            } else {
//                homeImageUrl.setImageResource(R.drawable.default_profile);
//                awayImageUrl.setImageResource(R.drawable.default_profile);
//            }
//        }else{
//            if (getDetailResult.get(0).getImageUrl() == " ") {
//                homeImageUrl.setImageResource(R.drawable.default_profile);
//            }else{
//                homeImageUrl.setImageResource(R.drawable.default_profile);
//            }
//            if (getDetailResult.get(1).getImageUrl() == " ") {
//                awayImageUrl.setImageResource(R.drawable.default_profile);
//            } else {
//                awayImageUrl.setImageResource(R.drawable.default_profile);
//            }
//        }
//
//        // 클리핑 영역을 설정
//        homeImageUrl.setClipToOutline(true);
//        awayImageUrl.setClipToOutline(true);
//
//        // 홈 팀 정보를 설정
//        homeText.setText(getDetailResult.get(0).getNickName());
//        homeHighScore.setText(""+getDetailResult.get(0).getHighScore());
//        homeAvgScore.setText(""+getDetailResult.get(0).getAvgScore());
//        homeGameCount.setText("" + getDetailResult.get(0).getGameCount());
//        homeWinCount.setText("" + getDetailResult.get(0).getWinCount());
//        homeLoseCount.setText("" + getDetailResult.get(0).getLoseCount());
//
//        // 어웨이 팀 정보를 설정
//        if (getDetailResult.size() == 1) {
//            awayText.setText("미정");
//            awayHighScore.setText("-");
//            awayAvgScore.setText("-");
//            awayGameCount.setText("-");
//            awayWinCount.setText("-");
//            awayLoseCount.setText("-");
//        } else {
//            awayText.setText(getDetailResult.get(1).getNickName());
//            awayHighScore.setText(""+getDetailResult.get(1).getHighScore());
//            awayAvgScore.setText(""+getDetailResult.get(1).getAvgScore());
//            awayGameCount.setText(""+getDetailResult.get(1).getGameCount());
//            awayWinCount.setText(""+getDetailResult.get(1).getWinCount());
//            awayLoseCount.setText(""+getDetailResult.get(1).getLoseCount());
//        }
//
//        // 매치 코드를 설정
//        matchCode.setText("" + resp.getResult().getMatchCode());
//
//        DateDiff dateDiff = new DateDiff();
//        String gameTime = resp.getResult().getGameTime();
//        int hour, min;
//
//        // 게임 시간을 공백(" ")으로 분할
//        String[] getSplitDate = gameTime.split(" ");
//
//        // 게임 시간이 "오후"인 경우 시간과 분을 추출
//        if (getSplitDate[1].equals("오후")) {
//            String[] getSplitTime = getSplitDate[2].split(":");
//            hour = Integer.valueOf(getSplitTime[0]) + 12;
//            min = Integer.valueOf(getSplitTime[1]);
//        } else {
//            String[] getSplitTime = getSplitDate[2].split(":");
//            hour = Integer.valueOf(getSplitTime[0]);
//            min = Integer.valueOf(getSplitTime[1]);
//        }
//
//        // 게임 시간의 날짜를 분할
//        String[] getDetailSplitDate = getSplitDate[0].split("-");
//
//        // 카운트다운 타이머를 생성하고 시작
//        countDownTimer = new CountDownTimer(200000,1000) {
//            @Override
//            public void onTick(long millisUntilFinished) {
//                // DateDiff 클래스의 getTime() 메소드를 호출하여 남은 시간을 가져와 remainTime에 표시
//                remainTime.setText(dateDiff.getTime(Integer.valueOf(getDetailSplitDate[0]), Integer.valueOf(getDetailSplitDate[1]), Integer.valueOf(getDetailSplitDate[2])+1, hour, min));
//            }
//
//            @Override
//            public void onFinish() {
//                // 타이머가 종료될 때 실행되는 코드를 작성
//            }
//        };
//
//        // 카운트다운 타이머를 시작
//        countDownTimer.start();
//    }
//
//
//
//    @Override
//    public void onDetailMatchFailure() {
//
//    }
//
//    //취소시 보낼 유저Idx리스트 함수(미정인 상태이면 null로 보내기)
//    private void cancelUserIdxList(List<GetDetailMatchResultDetail> getDetailResult) {
//        if (getDetailResult.size() == 1) { // 매칭유저가 한명일 경우
//            userIdxList = null;
//            return;
//        }
//        userIdxList = new ArrayList<>();
//        for (GetDetailMatchResultDetail getDetailMatchResultDetail : getDetailResult) {
//            if (getDetailMatchResultDetail.getUserIdx() == getUserIdx()) { // 자기자신을 제외
//                continue;
//            }
//            userIdxList.add(new PostCancelMatchUser(getDetailMatchResultDetail.getUserIdx())); // Request userIdxList에 userIdx 추가
//        }
//    }
//
//    // 매칭방 소켓활성화 여부 확인
//    @Override
//    public void onCheckSocketActiveViewSuccess(CheckSocketActiveResult result) {
//        if(result.getStatus().equals("WA")){
//            Intent intent = new Intent(getApplicationContext(), GameActivity.class);
//            intent.putExtra("matchIdx", matchIdx);
//            intent.putExtra("matchCode",matchCode.getText());
//            startActivity(intent);
//        }else{
//            Toast.makeText(getApplicationContext(),"활성화된 게임방이 아닙니다.",Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    @Override
//    public void onCheckSocketActiveViewFailure() {
//
//    }
//}
