package com.example.capstoneproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class GameActivity extends AppCompatActivity {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private AppCompatButton sendBtn;
    private StompClient sockClient;
    private String roomId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        initView();
        Intent intent = getIntent();
        roomId = intent.getStringExtra("roomId");
        Log.d("TAG", "roomId: "+roomId);
    }


    @Override
    protected void onStart() {
        super.onStart();
        initStomp(roomId);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = String.valueOf("2");
                sendStomp(msg);
            }
        });
    }

    public void sendStomp(String msg) {
        JsonObject data = new JsonObject();
        data.addProperty("roomId", roomId);
        data.addProperty("writer", "client");
        data.addProperty("message", msg);
        Log.d("Send Msg: ", data.toString());
        sockClient.send("/pub/game/message", data.toString()).subscribe();
    }

    public void initStomp(String roomId) {
//        Log.d("getSocket Start: ", "getSocket Start");
        Log.d("TAG", "initStomp roomId  : "+roomId);

        sockClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://www.seop.site" + "/stomp/game/websocket"); // 소켓연결 (엔드포인트)

        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

        sockClient.connect();

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
                        initStomp(roomId);
                        isUnexpectedClosed.set(false);
                    }
                    break;
            }
        });

//        Log.d("connect Start: ", "connect Start");


//        Log.d("topic Start: ", "topic Start with " + roomId);
        sockClient.topic("/sub/game/room/" + roomId).subscribe(topicMessage -> { // 매칭방 구독
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(topicMessage.getPayload());
            Log.d("Recv Msg: ", obj.toString());
        }, System.out::println);

        JsonObject data = new JsonObject();
        data.addProperty("roomId", roomId);
        data.addProperty("writer", "client");
        Log.d("Send Msg: ", data.toString());
        sockClient.send("/pub/game/message", data.toString()).subscribe(); // 서버에 메세지 보냄
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
            player1.frames[i].scores[2] = findViewById(total_score_id_1);
            player2.frames[i].scores[2] = findViewById(total_score_id_2);
        }
        player1.totalScore = findViewById(R.id.player1_total_score);
        player2.totalScore = findViewById(R.id.player2_total_score);
        sendBtn = findViewById(R.id.game_send_btn);

        player1.frames[1].scores[0].setText("2");
    }

}
