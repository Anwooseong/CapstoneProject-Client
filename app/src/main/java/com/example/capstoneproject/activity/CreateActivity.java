package com.example.capstoneproject.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.SharedPreferencesManager;
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class CreateActivity extends AppCompatActivity implements CreateMatchRoomView {
    private EditText titleText,contentText;
    private ImageView closeBtn;
    private AppCompatButton submitBtn;
    private MaterialButtonToggleGroup toggleBtn;
    private TextView date, time, placeText,locationText;
    private Spinner personCount;
    Calendar calDate, calTime;
    private TextInputEditText averageScore,cost,location,place;
    private final String[] battleArray = {"2", "4", "6"};
    private String networkTypeCheck = "ONLINE";
    private Long today;
    private int hour;
    private int minute;

    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:00", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initView();
        initialize();
        initListener();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.online_type_btn) {
                        locationText.setVisibility(View.GONE);
                        location.setVisibility(View.GONE);
                        placeText.setVisibility(View.GONE);
                        place.setVisibility(View.GONE);
                        networkTypeCheck = "ONLINE";
                    }
                    if (checkedId == R.id.offline_type_btn) {
                        locationText.setVisibility(View.VISIBLE);
                        location.setVisibility(View.VISIBLE);
                        placeText.setVisibility(View.VISIBLE);
                        place.setVisibility(View.VISIBLE);
                        networkTypeCheck = "OFFLINE";
                    }
                } else {
                }
            }
        });
    }

    private void initialize() {
        calDate = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        calTime = Calendar.getInstance();
        today = MaterialDatePicker.todayInUtcMilliseconds();
        hour = calTime.get(Calendar.HOUR_OF_DAY);
        minute = calTime.get(Calendar.MINUTE);
        ArrayAdapter adapter = new ArrayAdapter(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, battleArray);
        adapter.setDropDownViewResource(androidx.appcompat.R.layout.support_simple_spinner_dropdown_item);
        personCount.setAdapter(adapter);
    }

    private void initListener() {
        closeBtnListener();
        submitBtnListener();
        dateClickListener(today);
        timeClickListener(hour, minute);
        battleTypeSelectedListener();
    }


    private void closeBtnListener() {
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    // jwt 토큰 get
    private String getJwt(){
        SharedPreferences spf = this.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }
    private void submitBtnListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 매칭글 게시글 등록 API
                MatchService matchService = new MatchService();
                matchService.setCreateMatchRoomView((CreateMatchRoomView) v.getContext());
                matchService.postMatchRoom(getJwt(),createMatchReq());
            }
        });
    }

    private void battleTypeSelectedListener() {
        personCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), battleArray[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void dateClickListener(Long today) {
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker materialDatePicker = MaterialDatePicker.Builder.datePicker()
                        .setTitleText("경기할 날짜 선택")
                        .setSelection(today).build();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");

                materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
                    @Override
                    public void onPositiveButtonClick(Long selection) {
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        Date date1 = new Date();
                        date1.setTime(selection);
                        String dateString = simpleDateFormat.format(date1);
                        date.setText(dateString);
                        Log.d("TAG", "DATE: " + date.getText().toString());
                    }
                });
            }
        });
    }

    private void timeClickListener(int hour, int minute) {
        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialTimePicker timePicker = new MaterialTimePicker.Builder().setTimeFormat(TimeFormat.CLOCK_24H).setHour(hour).setMinute(minute).build();
                timePicker.show(getSupportFragmentManager(), "fragment_tag");

                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int newHour = timePicker.getHour();
                        int newMinute = timePicker.getMinute();
                        CreateActivity.this.onTimeSet(newHour, newMinute);
                    }
                });
            }
        });
    }

    private void onTimeSet(int newHour, int newMinute) {
        calTime.set(Calendar.HOUR_OF_DAY, newHour);
        calTime.set(Calendar.MINUTE, newMinute);
        calTime.setLenient(false);

        String format = formatter.format(calTime.getTime());
        time.setText(format);
        Log.d("TAG", "onTimeSet: " + time.getText().toString());
        hour = newHour;
        minute = newMinute;
    }
    private PostMatchRoom createMatchReq(){
        String location,place;
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        String requestDate = date.getText().toString()+" "+time.getText().toString();
        int number = Integer.parseInt(personCount.getSelectedItem().toString());
        String networkType = networkTypeCheck;
        Log.d("Network",""+networkTypeCheck);
        if(networkType.equals("ONLINE")){
            location = null;
            place = null;
        }else{
            location = Objects.requireNonNull(this.location.getText()).toString();
            place = Objects.requireNonNull(this.place.getText()).toString();
        }
        int averageScore = Integer.parseInt(this.averageScore.getText().toString());
        int cost = Integer.parseInt(this.cost.getText().toString());
        return new PostMatchRoom(title,content,requestDate,number,location,place,averageScore,networkType,cost);
    }

    private void initView() {
        closeBtn = findViewById(R.id.create_room_close_btn);
        submitBtn = findViewById(R.id.room_submit_btn);
        toggleBtn = findViewById(R.id.toggle_type_btn);
        date = findViewById(R.id.create_match_date_tv);
        time = findViewById(R.id.create_match_time_tv);
        personCount = findViewById(R.id.create_match_battle_sn);
        locationText = findViewById(R.id.create_match_location_text);
        location = findViewById(R.id.create_match_location_etv);
        placeText = findViewById(R.id.create_match_place_text);
        place = findViewById(R.id.create_match_place_etv);
        titleText = findViewById(R.id.create_match_title_etv);
        contentText = findViewById(R.id.create_match_content_etv);
        averageScore = findViewById(R.id.create_match_average_etv);
        cost = findViewById(R.id.create_match_cost_etv);
    }

    @Override
    public void onCreateMatchRoomSuccess() {
        Toast.makeText(getApplicationContext(), "게시글 등록 완료", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCreateMatchRoomFailure(PostMatchRoomResponse postMatchRoomResponse) {
        Log.d("TAG",""+postMatchRoomResponse.getMessage());
    }
}
