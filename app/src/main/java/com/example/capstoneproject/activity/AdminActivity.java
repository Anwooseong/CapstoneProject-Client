package com.example.capstoneproject.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.example.capstoneproject.R;

import ua.naiksoftware.stomp.StompClient;

public class AdminActivity extends AppCompatActivity {
    private TestMember player1 = new TestMember();
    private TestMember player2 = new TestMember();
    private AppCompatButton startMatch,sendBtn;
    private TextView player1_textView,player2_textView;
    private EditText matchCode,player1_frame,player1_score,player2_frame,player2_score;
    private StompClient sockClient;


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
        sendBtn = findViewById(R.id.admin_view_match_send_info_btn);

        player1.frames[1].scores[0].setText("2");
    }
}