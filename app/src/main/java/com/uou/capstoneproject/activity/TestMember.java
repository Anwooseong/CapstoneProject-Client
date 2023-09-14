package com.uou.capstoneproject.activity;

import android.widget.TextView;

import com.uou.capstoneproject.data.game.request.PostGameEndRequest;

import java.util.ArrayList;

public class TestMember {
    int historyIdx;
    Frame[] frames = new Frame[10];
    // 볼링 점수 관련 변수
    private ArrayList<String> queue = new ArrayList<>(); // 스트라이크/스페어 관리 큐
    private ArrayList<Integer> queueCount = new ArrayList<>(); // 스트라이크/스페어 후 던진 횟수 관리 큐

    private int [][] frameScoresPerPitch = new int[10][3]; // 프레임 투구 점수
    private int [] frameScores = new int[10]; // 프레임 완료 점수

    private int updateScoreIndex; // 스트라이크/스페어 다음 투구 횟수에 따른 수정할 인덱스
    private int sum; // 총 점
    private int temp, temp1; // 더블 스트라이크/ 일반 스트라이크/ 스페어 식별 변수
    private int result; // 10 프레임 관리 및 일반 투구 식별 변수
    private int n1, n2; // 1번째 투구, 2번째 투구
    private int [] pitchScore, lastPitchScore; // 각 프레임 투구 점수
    private int i, j, first_pitch, second_pitch; // index, 투구 횟수 식별 변수
    private int gameEnd; // 게임 종료 확인 변수

    TextView totalScore;
    TextView totalScoreFrame;

    public TestMember() {
        for (int i=0;i<10;i++){
            frames[i] = new Frame();
        }

        updateScoreIndex = 0;
        sum = 0; temp = 0; result = 0;
        n2 = 0; i = 0; j = 0;
        first_pitch = 0; second_pitch = 0;
        gameEnd = 0;

        lastPitchScore = new int[]{-1, -1, -1};
    }

    public void getScoreFromSock(int inputScore){
        if (i < 9){ // 10 프레임 이전 프레임
            normalFrame(inputScore);
        }
        else if (i == 9){ // 10 프레임
            if (j < 3 && gameEnd != 1){
                lastFrame(inputScore);
            }
            // 6-END) 10 프레임 일반투구 - 2번 던지고 끝남, 10프레임 스트라이크/스페어 - 3번 던지고 끝
            if(gameEnd == 1){
                frames[9].frameScore.setText(String.valueOf(sum));
                totalScore.setText(String.valueOf(sum));
                frameScoresPerPitch[9] = lastPitchScore;
                i++;
            }
        }
    }

    // 0) 1~9 프레임 처리 함수
    public void normalFrame(int inputScore){
        // 0-1) 전역변수 first_pitch를 이용해 첫번째 투구인지 확인
        if (first_pitch == 0){
            bowlingFirstPitch(inputScore);
            // 첫번째 투구 함수 이후 first_pitch = 1, second_pitch = 1 이 되어 2번쨰 투구를 진행할 준비를 한다.
            // 첫번째 투구 함수에서 스트라이크일때 result = 2가 된다.
            // 아니라면 함수 수행 후 항상 result = 0
            if (result != 0){
                switch(temp){
                    case 2 :
                        if (temp1==2) // 더블 스트라이크 (10 + 10 + 0)
                            sum+=n1+n1+n2;
                        else // 스트라이크 (10 + 0), n2는 첫번째 투구 함수에서 0으로 초기화됨
                            sum += n1 + n2;
                        // ** lastFrame 에서 6-1-X) 10프레임 첫번째 투구 스트라이크 경우
                        //    9프레임 스트라이크 처리된 sum 에 10이 더해짐
                        //    sum += n1 + n2 += 10프레임 첫번째 투구
                        // ** lasftFrame 에서 6-1-X)경우 다음 6-2-X) 10프레임 두번째 투구에서
                        //     sum += n1 + n2 += 10프레임 첫번째 투구 += 10프레임 두번째 투구 형태로 점수가 처리됨.
                        // ** 6-2-1) 에서 10번 프레임의 1,2 번쨰 투구의 점수를 sum에 더하게 됨
                        break;
                    case 1 :
                        sum+=n1;
                        break; //스페어
                    default :
                        break;
                }
            }
            // 입력된 투구 점수가 10점이라면, 스트라이크이기 때문에 프레임을 1 올려준다
            if (inputScore == 10){
                i ++;
            }
            System.out.println("firsth_pitch");
        }
        // 0-2) 전역변수 second_pitch를 이용해 두번째 투구인지 확인
        else if (second_pitch == 1){
            bowlingSecondPitch(inputScore);
            // 두번째 투구 함수 이후 second_pitch = 0, first_pitch = 0이 되어 2번쨰 투구를 진행할 준비를 한다.
            // 두번째 투구 함수에서 스페어일때 result = 1이 된다. 일반투구는 result = 0
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
            // 일반 투구에 대한 점수 처리 및 출력부 (result == 0) 일때
            // (나중에 updateFraemScore 함수에서 프레임 점수결과를 반환 받은 후 이어서 현재 점수를 유지하는 방식으로 수정해도 될듯함)
            // (또는 FrameScores[i-1]등을 이용한다던지)
            if (result == 0){
                frameScores[i] = sum;
                frames[i].frameScore.setText(String.valueOf(sum));
            }
            // 두번째 투구가 끝나면 다음 프레임으로 이동
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

    // 1) 1~9 프레임 1번째 투구
    private void bowlingFirstPitch(int inputScore){
        pitchScore = new int[]{-1, -1, -1}; // 거터는 0점이기 때문에 초기값을 -1로 넣어서 식별 가능하도록 함함

        temp1=temp; //temp1 에 전전 프레임 결과 저장
        temp=result; //temp에 지난 프레임 결과 저장
        result=0;

        // 1-1) 프레임에서 첫번째 투구 처리
        n1 = inputScore;
        frames[i].scores[0].setText(String.valueOf(inputScore).equals("10")?"X":String.valueOf(inputScore));
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
            frames[i].scores[1].setText(""); // X 후에 2번쨰 투구는 비워둠
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
        frames[i].scores[1].setText(String.valueOf(inputScore));
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
        // ** 6-1-1) lastFrame에서 9번 스페어시에 이 점수에 10프레임 첫번쨰 투구 점수가 더해짐.
        // sum += n1 + n2 + 10프레임 1번째 투구

        // 2-5) 첫번째 + 두번째 투구 합이 10점일때 = 스페어 일때
        if(n1+n2==10) { //스페어
            frames[i].scores[1].setText("/");
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
        // 4-1) 업데이트 할 프레임의 다음 프레임에서 투구 횟수를 확인해야함,
        //  ex) n 프레임 스페어 -> n+1 프레임 1투구, n 프레임 스트라이크 -> n+1 프레임 1, 2 투구 or n+1/n+2 1투구
        // [10번 프레임의 경우 다음 프레임이 없으므로 본인의 프레임 번호를 가짐]
        int i = updateScoreIndex + 1 > 9 ? 9 : updateScoreIndex + 1;

        // 4-2) 스트라이크/ 스페어 이후 몇번 투구했는 지 카운트 변수
        int pitchCountAfterTenPoint = 0;

        // 4-3) n-1 프레임의 스코어를 가져와서 이전 점수에서 n프레임의 스코어를 구하기 위한 시드
        int frameResult = updateScoreIndex <= 0 ? 0 : frameScores[updateScoreIndex-1];

        // 4-4) 1~9 프레임들은 n-1프레임에서의 점수에 본인 프레임에서 투구한 점수를 더함
        if(updateScoreIndex < 9){
            if (afterPitches == 2){ // 스트라이크일 경우
                frameResult += frameScoresPerPitch[updateScoreIndex][0];
            }
            else if (afterPitches == 1){ // 스페어일 경우
                frameResult += frameScoresPerPitch[updateScoreIndex][0] + frameScoresPerPitch[updateScoreIndex][1];
            }
        }

        // 4-5) n+1 프레임에서 부터 스트라이크는 2번, 스페어는 1번의 추가 투구 횟수를 더함
        while(pitchCountAfterTenPoint < afterPitches){
            for(int j = 0; j < 3; j++){
                if(pitchCountAfterTenPoint >= afterPitches) break;
                if (frameScoresPerPitch[i][j] != -1){
                    frameResult += frameScoresPerPitch[i][j];
                    pitchCountAfterTenPoint++;
                }
            }
            i++;
        }
        // ** 10 프레임은 반드시 아래 형태
        // frameScroes[9] = frameScores[8] + lastPitchScore[0] + lastPitchScore[1] + lastPitchScore[2];

        // 4-6) n 프레임의 점수를 구하여 수정하고 관리 큐에서 해당 프레임을 삭제
        frameScores[updateScoreIndex] = frameResult;
        frames[updateScoreIndex].frameScore.setText(String.valueOf(frameResult));
        queueCount.remove(0);
        queue.remove(0);
    }

    // 5) 현재 프레임 및 투구 점수 출력함수
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

    // 6) 마지막 프레임 (10번 프레임)
    private void lastFrame(int inputScore){
        //sum += inputScore;
        // 6-1) 마지막 프레임의 투구 배열을 따로 둔다.
        lastPitchScore[j] = inputScore;
        frameScoresPerPitch[i] = lastPitchScore;

        // 6-2) 8 또는 9 프레임에 대한 스페어/ 스트라이크 처리를 해줘야함.
        updateScoreIndex = countUp();
        if(updateScoreIndex != -1){
            updateFrameScore(updateScoreIndex, queueCount.get(0));
        }

        // 6-1-1) 10 프레임 일반 (첫번째 투구)
        if (lastPitchScore[0] != 10 && j == 0){
            frames[9].scores[j].setText(String.valueOf(inputScore));

            // 6-0-P) 9번째 프레임이 스페어 일때
            if(updateScoreIndex == 8 && frameScoresPerPitch[8][0] + frameScoresPerPitch[8][1] == 10){
                sum += inputScore;
            }
        }
        // 6-2-1) 10 프레임 일반 (2번째 투구)
        // 10프레임 첫번째 투구가 스트라이크가 아니고,
        // 1투구 스트라이크 이후 2투구 거터로 인해 1, 2 투구 합이 10일때 (스트라이크),
        else if(lastPitchScore[0] != 10 && lastPitchScore[0] + lastPitchScore[1] != 10 && j == 1){
            sum += lastPitchScore[0] + lastPitchScore[1];
            frames[9].scores[j].setText(String.valueOf(inputScore));

            // 9번 프레임이 스트라이크 일때
            if(updateScoreIndex == 8 && frameScoresPerPitch[8][0] == 10){
                // updateFrameScore에서 최신화된 9프레임의 점수에 10프레임 1,2 투구의 점수를 더해서
                // 현재 점수를 유지해줌
                sum = frameScores[8] + lastPitchScore[0] + lastPitchScore[1];
            }
            // 10번 프레임 점수 결정 후 게임 종료
            frameScores[9] = sum;
            gameEnd = 1;
        }
        // 6-1-X) 10 프레임 (첫번째 투구) 스트라이크
        else if (lastPitchScore[0] == 10 && j == 0){
            frames[9].scores[j].setText("X");
            sum += lastPitchScore[0]; // 9프레임 스트라이크 처리 후 변수 sum에 + 10

            queue.add(i+"S"); // 여기서 들어간 10번 프레임의 큐는 반드시 3번째 투구에서 updateFrameScore함수가 호출됨
            queueCount.add(0);
        }
        // 6-2-X or 1) 10 프레임 첫번째 투구 스트라이크 후 (2번째 투구) 점수
        else if (lastPitchScore[0] + lastPitchScore[1] != 10 && lastPitchScore[0] == 10 && j == 1){
            // 10 프레임에서 두번쨰 투구가 스트라이크인 경우는 다른 프레임에 영향을 주지 않기 때문에 단순히 점수를 더하는 방식으로 진행
            frames[9].scores[j].setText(String.valueOf(inputScore).equals("10")? "X": String.valueOf(inputScore));

            // 9프레임 스트라이크 처리 후 sum + 6-1-X, 6-1-1) 10프레임 첫번째 투구 점수 + "10프레임 두번째 투구 점수"
            sum += lastPitchScore[1];
        }
        // 6-2-/) 10 프레임 1,2 번째 투구 스페어 (2번째 투구)
        else if(lastPitchScore[0] + lastPitchScore[1]  == 10 && lastPitchScore[0] != 10 && j == 1){
            // 9번 프레임의 스페어 처리는 6-1-1) 에서 처리 되기 때문에
            // 6-1-1)에서 구한 마지막 sum에 1,2 투구 점수의 합(10)을 추가로 sum에 더해줌 -> 3번째 투구에서 sum에 다시한번  더할 예정
            sum += lastPitchScore[0] + lastPitchScore[1];
            frames[9].scores[j].setText("/");

            queue.add(i+"P"); // 여기서 들어간 10번 프레임의 큐는 반드시 3번째 투구에서 updateFrameScore함수가 호출됨
            queueCount.add(0);
        }
        // 6-3) 프레임 (3번째 투구)
        else if(j == 2){
            // 6-3-0) 9번 프레임 점수 + 10프레임 1,2,3 번째 투구 점수를 모두 합한 최종 결과값
            sum = frameScores[8] + lastPitchScore[0] + lastPitchScore[1] + lastPitchScore[2];

            // 6-3-/) 10 프레임 2, 3번째 투구 스페어 -> 6-1-X) and 6-2-X|1) 10 프레임 1번째 투구는 반드시 스트라이크 일때
            if (lastPitchScore[1]+ lastPitchScore[2] == 10){
                frames[9].scores[j].setText("/");
            }
            // 6-3-X) 10 프레임 3번째 투구 스트라이크 -> 6-2-/) 10 프레임 1, 2번째 투구에서 스페어 처리가 났을 때
            else if(lastPitchScore[2] == 10){
                frames[9].scores[j].setText("X");
            }
            // 6-3-1) 10 프레임 3번째 일반 투구
            else{
                frames[9].scores[j].setText(String.valueOf(inputScore));
            }
            // 게임 종료
            gameEnd = 1;
            frameScores[9] = sum;
        }

        // 6-END) 10 프레임 처리 함수 호출마다 투구 횟수를 1 씩 증가 3번째 투구 까지 마칠 시 최대 3까지 증가
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
    public PostGameEndRequest postGameEndRequest(){
        return new PostGameEndRequest(historyIdx,frameScoresPerPitch,frameScores);
    }

    public ArrayList<String> getQueue() {
        return queue;
    }

    public ArrayList<Integer> getQueueCount() {
        return queueCount;
    }

    public int[][] getFrameScoresPerPitch() {
        return frameScoresPerPitch;
    }

    public int[] getFrameScores() {
        return frameScores;
    }

    public int getUpdateScoreIndex() {
        return updateScoreIndex;
    }

    public int getSum() {
        return sum;
    }

    public int getTemp() {
        return temp;
    }

    public int getTemp1() {
        return temp1;
    }

    public int getResult() {
        return result;
    }

    public int getN1() {
        return n1;
    }

    public int getN2() {
        return n2;
    }

    public int[] getPitchScore() {
        return pitchScore;
    }

    public int[] getLastPitchScore() {
        return lastPitchScore;
    }

    public int getI() {
        return i;
    }

    public int getJ() {
        return j;
    }

    public int getFirst_pitch() {
        return first_pitch;
    }

    public int getSecond_pitch() {
        return second_pitch;
    }

    public int getGameEnd() {
        return gameEnd;
    }

    public void setQueue(ArrayList<String> queue) {
        this.queue = queue;
    }

    public void setQueueCount(ArrayList<Integer> queueCount) {
        this.queueCount = queueCount;
    }

    public void setFrameScoresPerPitch(int[][] frameScoresPerPitch) {
        this.frameScoresPerPitch = frameScoresPerPitch;
    }

    public void setFrameScores(int[] frameScores) {
        this.frameScores = frameScores;
    }

    public void setUpdateScoreIndex(int updateScoreIndex) {
        this.updateScoreIndex = updateScoreIndex;
    }

    public void setSum(int sum) {
        this.sum = sum;
    }

    public void setTemp(int temp) {
        this.temp = temp;
    }

    public void setTemp1(int temp1) {
        this.temp1 = temp1;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public void setN1(int n1) {
        this.n1 = n1;
    }

    public void setN2(int n2) {
        this.n2 = n2;
    }

    public void setPitchScore(int[] pitchScore) {
        this.pitchScore = pitchScore;
    }

    public void setLastPitchScore(int[] lastPitchScore) {
        this.lastPitchScore = lastPitchScore;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void setJ(int j) {
        this.j = j;
    }

    public void setFirst_pitch(int first_pitch) {
        this.first_pitch = first_pitch;
    }

    public void setSecond_pitch(int second_pitch) {
        this.second_pitch = second_pitch;
    }

    public void setGameEnd(int gameEnd) {
        this.gameEnd = gameEnd;
    }

    public Frame[] getFrames() {
        return frames;
    }

    public void setFrames(Frame[] frames) {
        this.frames = frames;
    }


    public TextView getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(TextView totalScore) {
        this.totalScore = totalScore;
    }
}
