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

    // 볼링 점수 관련 배열
    private ArrayList<String> queue = new ArrayList<>();
    private ArrayList<Integer> queueCount = new ArrayList<>();
    private int [][] frameScoresPerPitch = new int[10][3];
    private int [] frameScores = new int[10];

    int updateScoreIndex = 0;
    int sum = 0;
    int temp = 0, temp1;
    int result = 0;
    int n1, n2 = 0;
    int [] pitchScore, lastPitchScore = {0, 0, 0};
    int i = 0, j = 0, first_pitch = 0, second_pitch = 0, third_pitch = -1;// index

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
            Object obj = parser.parse(topicMessage.getPayload());
            Log.d("Recv Msg: ", obj.toString());
            Log.d("Recv Payload",topicMessage.getPayload());
            BroadCastDataResponse data = new Gson().fromJson(topicMessage.getPayload(),BroadCastDataResponse.class);
            System.out.println(data.getMatchIdx());
            System.out.println(data.getWriter());
            System.out.println((data.getFrame())-1);
            System.out.println(data.getScore());
            //player1.frames[(data.getFrame())-1].scores[0].setText(String.valueOf(data.getScore()));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (i < 9){
                        normalFrame(Integer.parseInt(String.valueOf(data.getScore())));
                    }
                    else if (i == 9){
                        if (j < 3){
                            lastFrame(Integer.parseInt(String.valueOf(data.getScore())));
                        }
                        if(j == 3){
                            player1.frames[9].frameScore.setText(String.valueOf(sum));
                            frameScoresPerPitch[9] = lastPitchScore;
                            printFrameScore(frameScores);
                            printFrameScorePerPitch(frameScoresPerPitch);
                        }
                    }
                }
            });


        }, System.out::println);

//        sockClient.topic("/sub/game/start/"+matchIdx).subscribe(topicMessage -> {
//            BroadCastDataResponse data = new Gson().fromJson(topicMessage.getPayload(),BroadCastDataResponse.class);
////            System.out.println(data.getMatchIdx());
////            System.out.println(data.getWriter());
//            System.out.println((data.getFrame())-1);
//            System.out.println(data.getScore());
//            player1.frames[(data.getFrame())-1].scores[0].setText(String.valueOf(data.getScore()));
//        });

//        JsonObject data = new JsonObject();
//        data.addProperty("matchIdx", matchIdx);
//        data.addProperty("writer", "client");
//        Log.d("Send Msg: ", data.toString());
//        sockClient.send("/pub/game/message", data.toString()).subscribe(); // 서버에 메세지 보냄
    }

    private void bowlingFirstPitch(int inputScore){
        pitchScore = new int[]{-1, -1, -1};

        temp1=temp; //temp1 에 전전 프레임 결과 저장
        temp=result; //temp에 지난 프레임 결과 저장
        result=0;

        n1 = inputScore;
        player1.frames[i].scores[0].setText(String.valueOf(inputScore).equals("10")?"X":String.valueOf(inputScore));

        // 점수 저장
        pitchScore[0] = n1;
        frameScoresPerPitch[i] = pitchScore;
        updateScoreIndex = countUp();
        System.out.println("updateScoreIndex: " + updateScoreIndex);

        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        n2 = 0;

        if(n1==10){ //스트라이크
            player1.frames[i].scores[1].setText("");

            result=2;
            sum+=n1;

            queue.add(i+"S");
            queueCount.add(0);

            first_pitch = 0;
            second_pitch = 0;
        }
        else{
            first_pitch = 1;
            second_pitch = 1;
        }
    }

    private void bowlingSecondPitch(int inputScore){
        n2=inputScore;

        pitchScore[1] = n2; frameScoresPerPitch[i] = pitchScore; player1.frames[i].scores[1].setText(String.valueOf(inputScore));
        frameScoresPerPitch[i] = pitchScore;
        updateScoreIndex = countUp();
        System.out.println("updateScoreIndex: " + updateScoreIndex);

        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        sum+=n1+n2;

        if(n1+n2==10) { //스페어

            player1.frames[i].scores[1].setText("/");
            result = 1;

            queue.add(i+"P");
            queueCount.add(0);
        }
        else {
            result=0;
        }

        first_pitch = 0;
        second_pitch = 0;
    }

    private void bowlingThirdPitch(){

    }

    private int countUp() {
        int updateIndex = -1;
        queueCount.replaceAll(count -> ++count);
        int afterPitches;
        if (queueCount.size() != 0) {
            String prevTenPointIndex = queue.get(0);
            afterPitches = queueCount.get(0);
            if ((prevTenPointIndex.charAt(1) == 'S' && afterPitches == 2) ||
                    (prevTenPointIndex.charAt(1) == 'P' && afterPitches == 1)) {
                updateIndex = Integer.valueOf(prevTenPointIndex.charAt(0)) - 48;
            }
        }
        return updateIndex;
    }

    private void updateFrameScore(int updateScoreIndex, int afterPitches){
        int i = updateScoreIndex + 1;
        int pitchCountAfterTenPoint = 0;
        int frameResult = updateScoreIndex <= 0 ? 0 : frameScores[updateScoreIndex-1];

        if (afterPitches == 2){ // 스트라이크일 경우
            frameResult += frameScoresPerPitch[updateScoreIndex][0];
        }
        else if (afterPitches == 1){ // 스페어일 경우
            frameResult += frameScoresPerPitch[updateScoreIndex][0] + frameScoresPerPitch[updateScoreIndex][1];
        }

        while(pitchCountAfterTenPoint < afterPitches){
            for(int j = 0; j < 3; j++){
                if (pitchCountAfterTenPoint >= afterPitches) break;
                if (frameScoresPerPitch[i][j] != -1){
                    frameResult += frameScoresPerPitch[i][j];
                    pitchCountAfterTenPoint++;
                }
            }
            i++;
        }
        frameScores[updateScoreIndex] = frameResult; player1.frames[updateScoreIndex].frameScore.setText(String.valueOf(frameResult));
        queueCount.remove(0);
        queue.remove(0);
    }

    private void printFrameScore(int[] frameScores){
        for(int i = 0; i<10; i++){
            System.out.print(frameScores[i] + " ");
        }
        System.out.println();
    }

    private void printFrameScorePerPitch(int[][] frameScoresPerPitch){
        for(int i = 0; i<10; i++){
            System.out.print("[");
            for (int j = 0; j<3; j++){
                System.out.print(frameScoresPerPitch[i][j] + " ");
            }
            System.out.print("], ");
        }
        System.out.println();
    }

    public void normalFrame(int inputScore){
        if (first_pitch == 0){
            bowlingFirstPitch(inputScore);
            if (result != 0){
                switch(temp){
                    case 2 :
                        if (temp1==2)
                            sum+=n1+n1+n2; // 더블 스트라이크
                        else
                            sum += n1 + n2; //스트라이크
                        break;
                    case 1 :
                        sum+=n1;
                        break; //스페어
                    default :
                        break;
                }
            }

            if (inputScore == 10){
                i ++;
            }
            System.out.println("firsth_pitch");
        }
        else if (second_pitch == 1){
            bowlingSecondPitch(inputScore);
            switch(temp){
                case 2 :
                    if (temp1==2)
                        sum+=n1+n1+n2;
                    else
                        sum += n1 + n2;
                    break; //스트라이크
                case 1 :
                    sum+=n1;
                    break; //스페어
                default :
                    break;
            }
            if (result == 0){
                frameScores[i] = sum;
                player1.frames[i].frameScore.setText(String.valueOf(sum));
            }
            i++;
            System.out.println("second_pitch");
        }

        System.out.println(i + ", " +sum);
        System.out.println("queue: " + queue);
        System.out.println("queueCount: " + queueCount);
        System.out.println("updateIndex: " + updateScoreIndex);

        printFrameScore(frameScores);
        printFrameScorePerPitch(frameScoresPerPitch);
    }

    private void lastFrame(int inputScore){
        sum += inputScore;
        lastPitchScore[j] = inputScore;
        player1.frames[9].scores[j++].setText(String.valueOf(inputScore).equals("10")?"X":String.valueOf(inputScore));
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
        player1_frame = findViewById(R.id.admin_view_match_member_1_frame_count_input_et);
        player1_score = findViewById(R.id.admin_view_match_member_1_score_input_et);
        player2_frame = findViewById(R.id.admin_view_match_member_2_frame_count_input_et);
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
        Log.d("TAG","roomIdx : "+ matchIdx);
        initStomp(matchIdx); // 관리자가 서버에 매칭시작한다고 알림 -> 소켓 열기
    }

    @Override
    public void onPostMatchCodeFailure() {

    }

}