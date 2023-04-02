package com.example.capstoneproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstoneproject.R;
import com.example.capstoneproject.data.game.GameService;
import com.example.capstoneproject.data.game.request.PostMatchCodeRequest;
import com.example.capstoneproject.data.game.response.PostMatchCodeResult;
import com.example.capstoneproject.view.PostMatchCodeView;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.concurrent.atomic.AtomicBoolean;

import ua.naiksoftware.stomp.Stomp;
import ua.naiksoftware.stomp.StompClient;

public class AdminActivity extends AppCompatActivity implements PostMatchCodeView {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private AppCompatButton startMatch,sendBtn1,sendBtn2;
    private TextView player1_textView,player2_textView;
    private EditText matchCode,player1_frame,player1_score,player2_frame,player2_score;
    private StompClient sockClient;
    private int matchIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
//        initStomp(roomId);
//        sendBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String msg = String.valueOf("2");
//                sendStomp(msg);
//            }
//        });
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
                sendStomp(Integer.parseInt(player1_frame.getText().toString()),Integer.parseInt(player1_score.getText().toString()));
            }
        });
    }
    // 매칭코드 -> 게임방RoomIdx 뽑아냄
    private void getRoomIdx(){
        GameService gameService = new GameService();
        gameService.setPostMatchCodeView(this);
        gameService.postMatchCode(new PostMatchCodeRequest(matchCode.getText().toString()));
    }

    public void sendStomp(int frame,int score) {
        JsonObject data = new JsonObject();
        data.addProperty("matchIdx", String.valueOf(matchIdx));
        data.addProperty("writer", "Kiosk");
        data.addProperty("frame", frame);
        data.addProperty("score", score);
        Log.d("Send Msg: ", data.toString());
        sockClient.send("/pub/game/start-game", data.toString()).subscribe();
    }
//    public void sendStomp(String msg) {
//        JsonObject data = new JsonObject();
//        data.addProperty("matchCode", matchCode.getText().toString());
//        data.addProperty("writer", "Kiosk");
//        data.addProperty("message", msg);
//        Log.d("Send Msg: ", data.toString());
//        sockClient.send("/pub/game/message", data.toString()).subscribe();
//    }
//
//    @SuppressLint("CheckResult")
    public void initStomp(int matchIdx) {
//        Log.d("getSocket Start: ", "getSocket Start");
        Log.d("TAG", "initStomp matchCode  : "+matchCode.getText().toString());

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

        Log.d("topic Start: ", "topic Start with " + matchCode.getText().toString());
        sockClient.topic("/sub/game/room/" + matchIdx).subscribe(topicMessage -> { // 매칭방 구독
            JsonParser parser = new JsonParser();
            Object obj = parser.parse(topicMessage.getPayload());
            Log.d("Recv Msg: ", obj.toString());
        }, System.out::println);

        JsonObject data = new JsonObject();
        data.addProperty("matchIdx", matchIdx);
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
        matchCode = findViewById(R.id.admin_view_match_code_input_et);
        startMatch = findViewById(R.id.admin_view_match_start_socket_btn);
        sendBtn1 = findViewById(R.id.admin_view_match_send_player1_info_btn);
        sendBtn2 = findViewById(R.id.admin_view_match_send_player2_info_btn);
        player1_frame = findViewById(R.id.admin_view_match_member_1_frame_count_input_et);
        player1_score = findViewById(R.id.admin_view_match_member_1_score_input_et);
        player2_frame = findViewById(R.id.admin_view_match_member_2_frame_count_input_et);
        player2_score = findViewById(R.id.admin_view_match_member_2_score_input_et);
    }

    @Override
    public void onPostMatchCodeSuccess(PostMatchCodeResult result) {
        matchIdx = result.getRoomIdx();
        Log.d("TAG","roomIdx : "+ matchIdx);
        initStomp(matchIdx); // 관리자가 서버에 매칭시작한다고 알림 -> 소켓 열기
    }

    @Override
    public void onPostMatchCodeFailure() {

    }
}