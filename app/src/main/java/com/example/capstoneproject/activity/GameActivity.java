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
        initStomp(String.valueOf(roomId));
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
        data.addProperty("roomId", "0");
        data.addProperty("writer", "client");
        data.addProperty("message", msg);
        Log.d("Send Msg: ", data.toString());
        sockClient.send("/pub/game/message", data.toString()).subscribe();
    }

    public void initStomp(String roomId) {
//        Log.d("getSocket Start: ", "getSocket Start");
        Log.d("TAG", "initStomp roomId  : "+roomId);

        sockClient = Stomp.over(Stomp.ConnectionProvider.OKHTTP, "wss://www.seop.site" + "/stomp/game/websocket");

        AtomicBoolean isUnexpectedClosed = new AtomicBoolean(false);

//        Log.d("lifecycle Start: ", "lifecycle Start");
        sockClient.lifecycle().subscribe(lifecycleEvent -> {
            switch (lifecycleEvent.getType()) {
                case OPENED:
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
        sockClient.connect();

//        Log.d("topic Start: ", "topic Start with " + roomId);
        sockClient.topic("/sub/game/room/" + roomId).subscribe(topicMessage -> {
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(topicMessage.getPayload());
            Log.d("Recv Msg: ", obj.toString());
        }, System.out::println);

        JsonObject data = new JsonObject();
        data.addProperty("roomId", "0");
        data.addProperty("writer", "client");
        Log.d("Send Msg: ", data.toString());
        sockClient.send("/pub/game/message", data.toString()).subscribe();
    }


    private void initView() {
        player1.frame1_1 = findViewById(R.id.player1_1_1);
        player1.frame1_2 = findViewById(R.id.player1_1_2);
        player1.frame2_1 = findViewById(R.id.player1_2_1);
        player1.frame2_2 = findViewById(R.id.player1_2_2);
        player1.frame3_1 = findViewById(R.id.player1_3_1);
        player1.frame3_2 = findViewById(R.id.player1_3_2);
        player1.frame4_1 = findViewById(R.id.player1_4_1);
        player1.frame4_2 = findViewById(R.id.player1_4_2);
        player1.frame5_1 = findViewById(R.id.player1_5_1);
        player1.frame5_2 = findViewById(R.id.player1_5_2);
        player1.frame6_1 = findViewById(R.id.player1_6_1);
        player1.frame6_2 = findViewById(R.id.player1_6_2);
        player1.frame7_1 = findViewById(R.id.player1_7_1);
        player1.frame7_2 = findViewById(R.id.player1_7_2);
        player1.frame8_1 = findViewById(R.id.player1_8_1);
        player1.frame8_2 = findViewById(R.id.player1_8_2);
        player1.frame9_1 = findViewById(R.id.player1_9_1);
        player1.frame9_2 = findViewById(R.id.player1_9_2);
        player1.frame10_1 = findViewById(R.id.player1_10_1);
        player1.frame10_2 = findViewById(R.id.player1_10_2);
        player1.frame10_3 = findViewById(R.id.player1_10_3);
        player1.score1 = findViewById(R.id.player1_score_1);
        player1.score2 = findViewById(R.id.player1_score_2);
        player1.score3 = findViewById(R.id.player1_score_3);
        player1.score4 = findViewById(R.id.player1_score_4);
        player1.score5 = findViewById(R.id.player1_score_5);
        player1.score6 = findViewById(R.id.player1_score_6);
        player1.score7 = findViewById(R.id.player1_score_7);
        player1.score8 = findViewById(R.id.player1_score_8);
        player1.score9 = findViewById(R.id.player1_score_9);
        player1.score10 = findViewById(R.id.player1_score_10);
        player1.totalScore = findViewById(R.id.player1_total_score);

        player2.frame1_1 = findViewById(R.id.player2_1_1);
        player2.frame1_2 = findViewById(R.id.player2_1_2);
        player2.frame2_1 = findViewById(R.id.player2_2_1);
        player2.frame2_2 = findViewById(R.id.player2_2_2);
        player2.frame3_1 = findViewById(R.id.player2_3_1);
        player2.frame3_2 = findViewById(R.id.player2_3_2);
        player2.frame4_1 = findViewById(R.id.player2_4_1);
        player2.frame4_2 = findViewById(R.id.player2_4_2);
        player2.frame5_1 = findViewById(R.id.player2_5_1);
        player2.frame5_2 = findViewById(R.id.player2_5_2);
        player2.frame6_1 = findViewById(R.id.player2_6_1);
        player2.frame6_2 = findViewById(R.id.player2_6_2);
        player2.frame7_1 = findViewById(R.id.player2_7_1);
        player2.frame7_2 = findViewById(R.id.player2_7_2);
        player2.frame8_1 = findViewById(R.id.player2_8_1);
        player2.frame8_2 = findViewById(R.id.player2_8_2);
        player2.frame9_1 = findViewById(R.id.player2_9_1);
        player2.frame9_2 = findViewById(R.id.player2_9_2);
        player2.frame10_1 = findViewById(R.id.player2_10_1);
        player2.frame10_2 = findViewById(R.id.player2_10_2);
        player2.frame10_3 = findViewById(R.id.player2_10_3);
        player2.score1 = findViewById(R.id.player2_score_1);
        player2.score2 = findViewById(R.id.player2_score_2);
        player2.score3 = findViewById(R.id.player2_score_3);
        player2.score4 = findViewById(R.id.player2_score_4);
        player2.score5 = findViewById(R.id.player2_score_5);
        player2.score6 = findViewById(R.id.player2_score_6);
        player2.score7 = findViewById(R.id.player2_score_7);
        player2.score8 = findViewById(R.id.player2_score_8);
        player2.score9 = findViewById(R.id.player2_score_9);
        player2.score10 = findViewById(R.id.player2_score_10);
        player2.totalScore = findViewById(R.id.player2_total_score);

        sendBtn = findViewById(R.id.game_send_btn);
    }

}
