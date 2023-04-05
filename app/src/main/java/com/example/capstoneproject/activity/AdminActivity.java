package com.example.capstoneproject.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.game.GameService;
import com.example.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.example.capstoneproject.data.game.response.BroadCastDataResponse;
import com.example.capstoneproject.data.game.response.PostMatchCodeResult;
import com.example.capstoneproject.view.PostMatchCodeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class AdminActivity extends AppCompatActivity implements PostMatchCodeView {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private AppCompatButton startMatch,sendBtn1,sendBtn2;
    private TextView player1_textView_up,player1_textView_down,player2_textView_up,player2_textView_down;
    private EditText matchCode,player1_frame,player1_score,player2_frame,player2_score;
    private StompClient sockClient;
    private int matchIdx;
    
    private TestMember nowPlayer  = new TestMember();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }
    @Override
    protected void onResume() {
        super.onResume();
        startMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getRoomIdx(); // roomIdx 반환하는 api 호출 -> onPostMatchCodeSuccess 함수 호출
            }
        });
        sendBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendStomp(1, Integer.parseInt(player1_score.getText().toString()));
            }
        });
        sendBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendStomp(2, Integer.parseInt(player2_score.getText().toString()));
            }
        });
    }
    // 매칭코드 -> 게임방RoomIdx 뽑아냄
    private void getRoomIdx(){
        GameService gameService = new GameService();
        gameService.setPostMatchCodeView(this);
        gameService.postMatchCode(new PostMatchCodeRequest(matchCode.getText().toString()));
    }

    public void sendStomp(int playerNum, int score) {
        JsonObject data = new JsonObject();
        data.addProperty("playerNum", playerNum);
        data.addProperty("matchIdx", String.valueOf(matchIdx));
        data.addProperty("writer", "Kiosk");
        data.addProperty("score", score);
        sockClient.send("/pub/game/start-game", data.toString()).subscribe();
    }

    public void initStomp(int matchIdx) {

        sockClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://www.seop.site" + "/stomp/game/websocket"); // 소켓연결 (엔드포인트)

        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

        sockClient.lifecycle().subscribe(lifecycleEvent -> { // 라이프사이클 동안 일어나는 일들을 정의
            switch (lifecycleEvent.getType()) {
                case OPENED: // 오픈될때는 무슨일을 하고~~~ 이런거 정의
                    break;
                case ERROR:
                    Log.d("Errored: ", "Error", lifecycleEvent.getException());
                    if (lifecycleEvent.getException().getMessage().contains("EOF")) {
                        isUnexpectedClosed.set(true);
                    }
                    break;
                case CLOSED:
                    Log.d("Closed: ", "Stomp connection closed");
                    if (isUnexpectedClosed.get()) {
                        /**
                         * EOF Error
                         */
                        initStomp(matchIdx);
                        isUnexpectedClosed.set(false);
                    }
                    break;
            }
        });

        Log.d("connect Start: ", "connect Start");
        sockClient.connect();

        Log.d("topic Start: ", "topic Start with " + matchCode.getText().toString());
        // 처음 매칭코드 > 서버에 전송했을때..
        sockClient.topic("/sub/game/room/" + matchIdx).subscribe(topicMessage -> { // 매칭방 구독
            JsonParser parser = new JsonParser();
            BroadCastDataResponse data = new Gson().fromJson(topicMessage.getPayload(),BroadCastDataResponse.class);
            System.out.println(data.getPlayerNum());
            System.out.println(data.getMatchIdx());
            System.out.println(data.getWriter());
            System.out.println(data.getScore());

            if(data.getPlayerNum() == 1){
                nowPlayer = player1;
            }
            else if(data.getPlayerNum() == 2){
                nowPlayer = player2;
            }
            
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    nowPlayer.getScoreFromSock(Integer.parseInt(String.valueOf(data.getScore())));
                }
            });


        }, System.out::println);
    }


    private void initView() {
        for(int i=0;i<10;i++){
            for (int j=0;j<3;j++){
                if(i != 9 && j == 2) break;
                int frame_score_id_1 = getResources().getIdentifier("player1_"+(i+1)+"_"+(j+1),"id",this.getPackageName());
                int frame_score_id_2 = getResources().getIdentifier("player2_"+(i+1)+"_"+(j+1),"id",this.getPackageName());
                player1.frames[i].scores[j] = findViewById(frame_score_id_1);
                player2.frames[i].scores[j] = findViewById(frame_score_id_2);
            }
            int total_score_id_1 = getResources().getIdentifier("player1_score_"+(i+1),"id",this.getPackageName());
            int total_score_id_2 = getResources().getIdentifier("player2_score_"+(i+1),"id",this.getPackageName());
            player1.frames[i].frameScore = findViewById(total_score_id_1);
            player2.frames[i].frameScore = findViewById(total_score_id_2);
        }

        player1.totalScore = findViewById(R.id.player1_total_score);
        player2.totalScore = findViewById(R.id.player2_total_score);
        matchCode = findViewById(R.id.admin_view_match_code_input_et);
        startMatch = findViewById(R.id.admin_view_match_start_socket_btn);
        // 점수전송 버튼
        sendBtn1 = findViewById(R.id.admin_view_match_send_player1_info_btn);
        sendBtn2 = findViewById(R.id.admin_view_match_send_player2_info_btn);
        // 점수기입 EditText
        player1_score = findViewById(R.id.admin_view_match_member_1_score_input_et);
        player2_score = findViewById(R.id.admin_view_match_member_2_score_input_et);
        // 게임시작 시 참여자 이름 렌더링 TextView
        player1_textView_up = findViewById(R.id.home_player_1_tv);
        player1_textView_down = findViewById(R.id.admin_view_match_member_1_tv);
        player2_textView_up = findViewById(R.id.away_player_1_tv);
        player2_textView_down = findViewById(R.id.admin_view_match_member_2_tv);
    }

    @Override
    public void onPostMatchCodeSuccess(PostMatchCodeResult result) {
        matchIdx = result.getRoomIdx();
        // 매칭코드 전송 시 플레이어 이름들 배치
        player1_textView_up.setText(result.getHistoryInfo().get(0).getNickName());
        player1_textView_down.setText(result.getHistoryInfo().get(0).getNickName());
        player2_textView_up.setText(result.getHistoryInfo().get(1).getNickName());
        player2_textView_down.setText(result.getHistoryInfo().get(1).getNickName());
        initStomp(matchIdx); // 관리자가 서버에 매칭시작한다고 알림 -> 소켓 열기
    }

    @Override
    public void onPostMatchCodeFailure() {

    }

}