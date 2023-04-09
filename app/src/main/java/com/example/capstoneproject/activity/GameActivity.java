package com.example.capstoneproject.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.game.GameService;
import com.example.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.example.capstoneproject.data.game.response.BroadCastDataResponse;
import com.example.capstoneproject.data.game.response.PostMatchCodeResult;
import com.example.capstoneproject.view.PostMatchCodeView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class GameActivity extends AppCompatActivity implements PostMatchCodeView {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private StompClient sockClient;
    private TextView homePlayerName, awayPlayerName;
    private int matchIdx;
    private String matchCode;

    private TestMember nowPlayer = new TestMember();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        Intent intent = getIntent();
        matchIdx = intent.getIntExtra("matchIdx",0);
        matchCode = intent.getStringExtra("matchCode");

        initStomp(matchIdx);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getRoomIdx();
    }

    private void getRoomIdx(){
        GameService gameService = new GameService();
        gameService.setPostMatchCodeView(this);
        gameService.postMatchCode(new PostMatchCodeRequest(matchCode));
    }

    public void initStomp(int matchIdx) {

        sockClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://www.seop.site" + "/stomp/game/websocket"); // 소켓연결 (엔드포인트)

        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

        sockClient.lifecycle().subscribe(lifecycleEvent -> { // 라이프사이클 동안 일어나는 일들을 정의
            switch (lifecycleEvent.getType()) {
                case OPENED: // 오픈될때는 무슨일을 하고~~~ 이런거 정의
                    break;
                case ERROR:
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
        sockClient.connect();

        // 처음 매칭코드 > 서버에 전송했을때..
        sockClient.topic("/sub/game/room/" + matchIdx).subscribe(topicMessage -> { // 매칭방 구독
            JsonParser parser = new JsonParser();
            BroadCastDataResponse data = new Gson().fromJson(topicMessage.getPayload(),BroadCastDataResponse.class);
            System.out.println(data.getPlayerNum());
            System.out.println(data.getMatchIdx());
            System.out.println(data.getWriter());
            System.out.println(data.getScore());
            if(data.getPlayerNum() == 99 && data.getScore() == 99){
                sockClient.disconnect();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
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
                    textViewFocus();
                }
            });
        }, System.out::println);
    }


    private void initView() {
        for(int i=0;i<10;i++){
            for (int j=0;j<3;j++){
                if(i != 9 && j == 2) break;
                int frame_score_id_1 = getResources().getIdentifier("game_player1_"+(i+1)+"_"+(j+1),"id",this.getPackageName());
                int frame_score_id_2 = getResources().getIdentifier("game_player2_"+(i+1)+"_"+(j+1),"id",this.getPackageName());
                player1.frames[i].scores[j] = findViewById(frame_score_id_1);
                player2.frames[i].scores[j] = findViewById(frame_score_id_2);
            }
            int total_score_id_1 = getResources().getIdentifier("game_player1_score_"+(i+1),"id",this.getPackageName());
            int total_score_id_2 = getResources().getIdentifier("game_player2_score_"+(i+1),"id",this.getPackageName());
            player1.frames[i].frameScore = findViewById(total_score_id_1);
            player2.frames[i].frameScore = findViewById(total_score_id_2);
        }
        player1.totalScore = findViewById(R.id.game_player1_total_score);
        player2.totalScore = findViewById(R.id.game_player2_total_score);

        homePlayerName = findViewById(R.id.home_player_1_tv);
        awayPlayerName = findViewById(R.id.away_player_1_tv);
    }
    public void textViewFocus(){
        // #F24726
        if(player1.getI() > player2.getI()){
            player1.frames[player1.getI()].frameCount.setBackgroundColor(Color.parseColor("#F24726"));
            player1.frames[player1.getI()-1 < 0 ? 0 :player1.getI()-1].frameCount.setBackgroundColor(Color.parseColor("#F24726"));
            player2.frames[player2.getI()].frameCount.setBackgroundColor(Color.BLUE);
        }else if(player1.getI() == player2.getI()){
            player2.frames[player2.getI()].frameCount.setBackgroundColor(Color.parseColor("#F24726"));
            player2.frames[player2.getI()-1 < 0 ? 0 :player2.getI()-1].frameCount.setBackgroundColor(Color.parseColor("#F24726"));
            player1.frames[player1.getI()].frameCount.setBackgroundColor(Color.BLUE);
        }
        System.out.println(player1.getI() + " " +player2.getI());
    }

    @Override
    public void onPostMatchCodeSuccess(PostMatchCodeResult result) {
        homePlayerName.setText(result.getHistoryInfo().get(0).getNickName());
        awayPlayerName.setText(result.getHistoryInfo().get(1).getNickName());
    }

    @Override
    public void onPostMatchCodeFailure() {

    }
}
