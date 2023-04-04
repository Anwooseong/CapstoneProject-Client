package com.example.capstoneproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.game.response.BroadCastDataResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class GameActivity extends AppCompatActivity {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private StompClient sockClient;
    private int matchIdx;

    private TestMember nowPlayer = new TestMember();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        Intent intent = getIntent();
        matchIdx = intent.getIntExtra("matchIdx",0);
        Log.d("TAG", "roomId: "+ matchIdx);

        initStomp(matchIdx);
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    public void initStomp(int matchIdx) {
//        Log.d("getSocket Start: ", "getSocket Start");
        Log.d("TAG", "initStomp matchIdx  : "+matchIdx);

        sockClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://www.seop.site" + "/stomp/game/websocket"); // 소켓연결 (엔드포인트)

        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

//        Log.d("lifecycle Start: ", "lifecycle Start");
        sockClient.lifecycle().subscribe(lifecycleEvent -> { // 라이프사이클 동안 일어나는 일들을 정의
            switch (lifecycleEvent.getType()) {
                case OPENED: // 오픈될때는 무슨일을 하고~~~ 이런거 정의
//                    Log.d("Connected: ", "Stomp connection opened");
                    break;
                case ERROR:
//                    Log.d("Errored: ", "Error", lifecycleEvent.getException());
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

        // 처음 매칭코드 > 서버에 전송했을때..
        sockClient.topic("/sub/game/room/" + matchIdx).subscribe(topicMessage -> { // 매칭방 구독
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(topicMessage.getPayload());
            Log.d("Recv Msg: ", obj.toString());
            Log.d("Recv Payload",topicMessage.getPayload());
            BroadCastDataResponse data = new Gson().fromJson(topicMessage.getPayload(),BroadCastDataResponse.class);
            System.out.println(data.getPlayerNum());
            System.out.println(data.getMatchIdx());
            System.out.println(data.getWriter());
            System.out.println(data.getScore());
            //player1.frames[(data.getFrame())-1].scores[0].setText(String.valueOf(data.getScore()));

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
    }

}
