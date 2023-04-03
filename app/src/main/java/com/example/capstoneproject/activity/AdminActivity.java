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

    // 볼링 점수 관련 변수
    private ArrayList<String> queue = new ArrayList<>(); // 스트라이크/스페어 관리 큐
    private ArrayList<Integer> queueCount = new ArrayList<>(); // 스트라이크/스페어 후 던진 횟수 관리 큐
    private int [][] frameScoresPerPitch = new int[10][3]; // 프레임 투구 점수
    private int [] frameScores = new int[10]; // 프레임 완료 점수

    int updateScoreIndex = 0; // 스트라이크/스페어 다음 투구 횟수에 따른 수정할 인덱스
    int sum = 0; // 총 점
    int temp = 0, temp1; // 더블 스트라이크/ 일반 스트라이크/ 스페어 식별 변수
    int result = 0; // 10 프레임 관리 및 일반 투구 식별 변수
    int n1, n2 = 0; // 1번째 투구, 2번째 투구
    int [] pitchScore, lastPitchScore = {-1, -1, -1}; // 각 프레임 투구 점수
    int i = 0, j = 0, first_pitch = 0, second_pitch = 0 // index, 투구 횟수 식별 변수
    int gameEnd = 0; // 게임 종료 확인 변수

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
                sendStomp(Integer.parseInt(player1_score.getText().toString()));
            }
        });
    }
    // 매칭코드 -> 게임방RoomIdx 뽑아냄
    private void getRoomIdx(){
        GameService gameService = new GameService();
        gameService.setPostMatchCodeView(this);
        gameService.postMatchCode(new PostMatchCodeRequest(matchCode.getText().toString()));
    }

    public void sendStomp(int score) {
        JsonObject data = new JsonObject();
        data.addProperty("matchIdx", String.valueOf(matchIdx));
        data.addProperty("writer", "Kiosk");
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
            System.out.println(data.getScore());
            //player1.frames[(data.getFrame())-1].scores[0].setText(String.valueOf(data.getScore()));

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (i < 9){ // 10 프레임 이전 프레임
                        normalFrame(Integer.parseInt(String.valueOf(data.getScore())));
                    }
                    else if (i == 9){ // 10 프레임
                        if (j < 3){
                            lastFrame(Integer.parseInt(String.valueOf(data.getScore())));
                        }
                        // 10 프레임 일반투구 - 2번 던지고 끝남, 10프레임 스트라이크/스페어 - 3번 던지고 끝
                        if(gameEnd == 1){
                            player1.frames[9].frameScore.setText(String.valueOf(sum));
                            player1.totalScore.setText(String.valueOf(sum));
                            frameScoresPerPitch[9] = lastPitchScore;
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

    // 1) 1~9 프레임 1번째 투구
    private void bowlingFirstPitch(int inputScore){
        pitchScore = new int[]{-1, -1, -1}; // 거터는 0점이기 때문에 초기값을 -1로 넣어서 식별 가능하도록 함함

        tem1=temp; //temp1 에 전전 프레임 결과 저장
        temp=result; //temp에 지난 프레임 결과 저장
        result=0;

        // 1-1) 프레임에서 첫번째 투구 처리
        n1 = inputScore;
        player1.frames[i].scores[0].setText(String.valueOf(inputScore).equals("10")?"X":String.valueOf(inputScore));
        // 1-2) 볼링 점수 관리 변수(배열)에 점수 저장
        pitchScore[0] = n1; // 프레임 투구 점수 배열
        frameScoresPerPitch[i] = pitchScore;

        // 1-3) 이전 프레임의 스트라이크/스페어가 이후 투구가 몇번 됐는지 확인
        updateScoreIndex = countUp();
        //System.out.println("updateScoreIndex: " + updateScoreIndex);
        // 1-3-0) 만일 이전 프레임의 스트라이크/스페어 이후 2번/1번 투구 가 이뤄졌다면 해당 프레임의 점수를 채워줌
        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        n2 = 0; // 2-0) 2번째 투구 점수 변수 미리 초기화

        // 1-4) 첫번째 투구가 10점일때 = 스트라이크 일때
        if(n1==10){ //스트라이크
            player1.frames[i].scores[1].setText(""); // X 후에 2번쨰 투구는 비워둠
            result=2;
            sum+=n1;

            // 스트라이크/스페어 관리 큐에 값 추가
            queue.add(i+"S");
            queueCount.add(0);

            // 1번째 투구 후에 2번째 투구가 아닌 다음 프레임 1번째 투구로 진행
            first_pitch = 0;
            second_pitch = 0;
        }
        else{ // 1번째 투구 후에 2번째 투구 진행 준비
            first_pitch = 1;
            second_pitch = 1;
        }
    }


    // 2) 1~9 프레임 2번째 투구
    private void bowlingSecondPitch(int inputScore){
        // 2-1) 2번째 투구 값 입력
        n2=inputScore;
        player1.frames[i].scores[1].setText(String.valueOf(inputScore));
        // 2-2) 볼링 점수 관리 변수(배열)에 점수 저장
        pitchScore[1] = n2;
        frameScoresPerPitch[i] = pitchScore;

        // 2-3) 이전 프레임의 스트라이크/스페어가 이후 투구가 몇번 됐는지 확인
        updateScoreIndex = countUp();
        //System.out.println("updateScoreIndex: " + updateScoreIndex);
        // 2-3-0) 만일 이전 프레임의 스트라이크/스페어 이후 2번/1번 투구 가 이뤄졌다면 해당 프레임의 점수를 채워줌
        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        // 2-4) 총점 관리 계산
        sum+=n1+n2;

        // 2-5) 첫번째 + 두번째 투구 합이 10점일때 = 스페어 일때
        if(n1+n2==10) { //스페어
            player1.frames[i].scores[1].setText("/");
            result = 1;

            // 스트라이크/스페어 관리 큐에 값 추가
            queue.add(i+"P");
            queueCount.add(0);
        }
        else { // 일반 투구로 식별
            result=0;
        }

        // 2-6) 2번째 투구 후 다음 프레임 1번째 투구 준비
        first_pitch = 0;
        second_pitch = 0;
    }

    // 3) 이전 프레임에서 스트라이크/스페어가 있다면, 해당 프레임 이후 투구 횟수를 확인
    private int countUp() {
        // 3-0) 만일, 이전프레임의 스트라이크/스페어 이후에 만족하는 투구 횟수를 채우지 못하면 -1을 반환
        //      해당 프레임으로부터 스트라이크-2번 투구 후, 스페어-1번 투구 후 프레임 점수 결과 출력
        int updateIndex = -1;
        // 3-1) 투구를 진행할때마다 함수를 호출, 큐 내에 스트라이크/스페어 프레임 이후의 횟수를 계속해서 1씩 증가 시켜줌
        queueCount.replaceAll(count -> ++count);
        // 3-2) 스트라이크/스페어를 던지고 난 후 제일 오래된 프레임을 우선으로 확인 (LILO)
        int afterPitches;
        // 3-2-0) 만일 스트라이크/스페어를 쳤던 이전 프레임이 존재한다면,
        if (queueCount.size() != 0) {
            // 3-2-1) 큐의 맨 앞에서 어떤 프레임 + 스트라이크/스페어 인지 확인
            String prevTenPointIndex = queue.get(0);
            afterPitches = queueCount.get(0);
            // 3-2-2) 스트라이크 + 해당 프레임으로부터 투구를 두 번 완료 하였거나,
            //           스페어 + 해당 프레임으로부터 투구를 한 번 완료 하였다면
            if ((prevTenPointIndex.charAt(1) == 'S' && afterPitches == 2) ||
                    (prevTenPointIndex.charAt(1) == 'P' && afterPitches == 1)) {
                updateIndex = Integer.valueOf(prevTenPointIndex.charAt(0)) - 48;
            }
            // 3-3) 스트라이크/스페어를 쳤던 프레임의 인덱스를 반환하여, 값을 채울 준비
        }
        // 3-4) 수정할 스트라이크/스페어 프레임이 없거나, 투구 횟수를 만족하지 않은 것
        return updateIndex; // -1 or 수정할 스트라이크/스페어를 친 프레임 return
    }

    // 4) 스트라이크/스페어를 친 프레임의 점수를 수정하는 함수
    private void updateFrameScore(int updateScoreIndex, int afterPitches){
        int i = updateScoreIndex + 1 > 9 ? 9 : updateScoreIndex + 1;
        int pitchCountAfterTenPoint = 0;
        int frameResult = updateScoreIndex <= 0 ? 0 : frameScores[updateScoreIndex-1];

        if(updateScoreIndex < 9){
            if (afterPitches == 2){ // 스트라이크일 경우
                frameResult += frameScoresPerPitch[updateScoreIndex][0];
            }
            else if (afterPitches == 1){ // 스페어일 경우
                frameResult += frameScoresPerPitch[updateScoreIndex][0] + frameScoresPerPitch[updateScoreIndex][1];
            }
        }

        while(pitchCountAfterTenPoint < afterPitches){
            for(int j = 0; j < 3; j++){
                if (frameScoresPerPitch[i][j] != -1){
                    frameResult += frameScoresPerPitch[i][j];
                    pitchCountAfterTenPoint++;
                }
            }
            i++;
        }
        frameScores[updateScoreIndex] = frameResult;
        player1.frames[updateScoreIndex].frameScore.setText(String.valueOf(frameResult));
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
        //sum += inputScore;
        lastPitchScore[j] = inputScore;
        frameScoresPerPitch[i] = lastPitchScore;
        updateScoreIndex = countUp();

        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        // 1-1) 10 프레임 일반 첫번째 투구
        if (lastPitchScore[0] != 10 && j == 0){
            player1.frames[9].scores[j].setText(String.valueOf(inputScore));

            // 0-S) 9번째 프레임에서 스페어 처리 났을때
            if(updateScoreIndex == 8 && frameScoresPerPitch[8][0] + frameScoresPerPitch[8][1] == 10){
                sum += inputScore;
            }
        }
        // 1-2) 10 프레임 일반 두번째 투구
        else if(lastPitchScore[0] != 10 && lastPitchScore[0] + lastPitchScore[1] != 10 && j == 1){
            sum += lastPitchScore[0] + lastPitchScore[1];
            player1.frames[9].scores[j].setText(String.valueOf(inputScore));

            if(updateScoreIndex == 8 && frameScoresPerPitch[8][0] == 10){
                sum = frameScores[8] + lastPitchScore[0] + lastPitchScore[1];
            }

            frameScores[9] = sum;
            gameEnd = 1;
        }
        // 1-1-X) 10 프레임 첫번째 투구 스트라이크
        else if (lastPitchScore[0] == 10 && j == 0){
            player1.frames[9].scores[j].setText("X");
            sum += lastPitchScore[0];

            queue.add(i+"S");
            queueCount.add(0);
        }
        // 1-2-X) 10 프레임 첫번째 투구 스트라이크 후 2번째 투구 점수
        else if (lastPitchScore[0] + lastPitchScore[1] != 10 && lastPitchScore[0] == 10 && j == 1){
            player1.frames[9].scores[j].setText(String.valueOf(inputScore).equals("10")? "X": String.valueOf(inputScore));
            sum += lastPitchScore[1];
        }
        // 1-1-/) 10 프레임 1,2 번째 투구 스페어
        else if(lastPitchScore[0] + lastPitchScore[1]  == 10 && lastPitchScore[0] != 10 && j == 1){
            sum += lastPitchScore[0] + lastPitchScore[1];
            player1.frames[9].scores[j].setText("/");

            queue.add(i+"P");
            queueCount.add(0);
        }
        // 1-3) 프레임 3번째 투구
        else if(j == 2){
            sum = frameScores[8] + lastPitchScore[0] + lastPitchScore[1] + lastPitchScore[2];
            // 1-3-/) 10 프레임 2, 3번째 투구 스페어
            if (lastPitchScore[1]+ lastPitchScore[2] == 10){
                player1.frames[9].scores[j].setText("/");
            }
            // 1-3-X) 10 프레임 3번째 투구 스트라이크
            else if(lastPitchScore[2] == 10){
                player1.frames[9].scores[j].setText("X");
            }
            // 1-3-0)10 프레임 3번째 일반 투구
            else{
                player1.frames[9].scores[j].setText(String.valueOf(inputScore));
            }
            gameEnd = 1;
        }
        j++;

        System.out.println(i + ", " +sum);
        System.out.println("j: "+ j);
        System.out.println("queue: " + queue);
        System.out.println("queueCount: " + queueCount);
        System.out.println("updateIndex: " + updateScoreIndex);

        printFrameScore(frameScores);
        printFrameScorePerPitch(frameScoresPerPitch);

        //player1.frames[9].scores[j++].setText(String.valueOf(inputScore).equals("10")?"X":String.valueOf(inputScore));
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
        Log.d("TAG","roomIdx : "+ matchIdx);
        initStomp(matchIdx); // 관리자가 서버에 매칭시작한다고 알림 -> 소켓 열기
    }

    @Override
    public void onPostMatchCodeFailure() {

    }

}