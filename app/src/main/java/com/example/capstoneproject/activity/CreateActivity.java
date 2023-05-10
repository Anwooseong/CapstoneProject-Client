package com.example.capstoneproject.activity;

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
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.request.PostMatchRoom;
import com.example.capstoneproject.data.match.response.GetMatchCityResponse;
import com.example.capstoneproject.data.match.response.matchroom.PostMatchRoomResponse;
import com.example.capstoneproject.view.CreateMatchRoomView;
import com.example.capstoneproject.view.GetMatchCityView;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class CreateActivity extends AppCompatActivity implements CreateMatchRoomView, GetMatchCityView {
    private EditText titleText, contentText;
    private ImageView closeBtn;
    private AppCompatButton submitBtn;
    private MaterialButtonToggleGroup toggleBtn;
    private TextView date, time, placeText;
    private Spinner personCount;
    Calendar calDate, calTime;
    private TextInputEditText averageScore, cost, place;
    private final String[] battleArray = {"2", "4", "6"};
    private String networkTypeCheck = "ONLINE";
    private Long today;
    private int hour;
    private int minute;
    private TextView localText;
    private String[] localItems = {"-- 선택 --", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"};
    private ArrayList<String> cityItems = new ArrayList<>();
    private Spinner localSpinner, citySpinner;
    private String localName, cityName;


    private final SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:00", Locale.getDefault());

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        initView();
        initialize();
        initListener();
        spinnerHandler();
    }

    @Override
    protected void onStart() {
        super.onStart();
        toggleBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.online_type_btn) {
                        placeText.setVisibility(View.GONE);
                        place.setVisibility(View.GONE);
                        localText.setVisibility(View.GONE);
                        localSpinner.setVisibility(View.GONE);
                        citySpinner.setVisibility(View.GONE);
                        networkTypeCheck = "ONLINE";
                    }
                    if (checkedId == R.id.offline_type_btn) {
                        placeText.setVisibility(View.VISIBLE);
                        place.setVisibility(View.VISIBLE);
                        localText.setVisibility(View.VISIBLE);
                        localSpinner.setVisibility(View.VISIBLE);
                        citySpinner.setVisibility(View.VISIBLE);
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
    private String getJwt() {
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt", "");
    }

    private void submitBtnListener() {
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cityName.equals("-- 선택 --")) {
                    Toast.makeText(getApplicationContext(), "지역을 전부 선택해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                MatchService matchService = new MatchService();
                matchService.setCreateMatchRoomView((CreateMatchRoomView) v.getContext());
                matchService.postMatchRoom(getJwt(), createMatchReq());
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

    private PostMatchRoom createMatchReq() {
        String place;
        String title = titleText.getText().toString();
        String content = contentText.getText().toString();
        String requestDate = date.getText().toString() + " " + time.getText().toString();
        int number = Integer.parseInt(personCount.getSelectedItem().toString());
        String networkType = networkTypeCheck;
        Log.d("Network", "" + networkTypeCheck);
        if (networkType.equals("ONLINE")) {
            place = null;
        } else {
            place = Objects.requireNonNull(this.place.getText()).toString();
        }
        int averageScore = Integer.parseInt(this.averageScore.getText().toString());
        int cost = Integer.parseInt(this.cost.getText().toString());

        Log.d("TAG", ""+getJwt());
        Log.d("TAG", ""+title);
        Log.d("TAG", ""+content);
        Log.d("TAG", ""+requestDate);
        Log.d("TAG", ""+number);
        Log.d("TAG", ""+localName + " " + cityName);
        Log.d("TAG", ""+place);
        Log.d("TAG", ""+localName);
        Log.d("TAG", ""+cityName);
        Log.d("TAG", ""+averageScore);
        Log.d("TAG", ""+networkType);
        Log.d("TAG", ""+cost);
        return new PostMatchRoom(title, content, requestDate, number, localName + " " + cityName, place, localName, cityName, averageScore, networkType, cost);
    }


    @Override
    public void onCreateMatchRoomSuccess() {
        Toast.makeText(getApplicationContext(), "게시글 등록 완료", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onCreateMatchRoomFailure(PostMatchRoomResponse postMatchRoomResponse) {
        Log.d("TAG", "" + postMatchRoomResponse.getMessage());
    }

    private void spinnerHandler() {
        ArrayAdapter<String> localSpinnerAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, localItems
        );
        localSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        localSpinner.setAdapter(localSpinnerAdapter);
        localSpinner.setSelection(0);
        localSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                localName = localItems[i];
                getMatchCity(localName);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getMatchCity(String local) {
        MatchService matchService = new MatchService();
        matchService.setGetMatchCityView(this);
        matchService.getMatchCity(local);
    }

    @Override
    public void onGetMatchCityFailure() {

    }

    @Override
    public void onGetMatchCitySuccess(GetMatchCityResponse response) {
        List<String> result = response.getResult();
        cityItems = new ArrayList<>();
        cityItems.add("-- 선택 --");
        for (String s : result) {
            cityItems.add(s);
        }
        citySpinner.setVisibility(View.VISIBLE);
        ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<String>(
                getApplicationContext(), android.R.layout.simple_spinner_item, cityItems
        );
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpinner.setSelection(0);
        cityName = null;
        citySpinner.setAdapter(citySpinnerAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityName = cityItems.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initView() {
        closeBtn = findViewById(R.id.create_room_close_btn);
        submitBtn = findViewById(R.id.room_submit_btn);
        toggleBtn = findViewById(R.id.toggle_type_btn);
        date = findViewById(R.id.create_match_date_tv);
        time = findViewById(R.id.create_match_time_tv);
        personCount = findViewById(R.id.create_match_battle_sn);
        placeText = findViewById(R.id.create_match_place_text);
        place = findViewById(R.id.create_match_place_etv);
        titleText = findViewById(R.id.create_match_title_etv);
        contentText = findViewById(R.id.create_match_content_etv);
        averageScore = findViewById(R.id.create_match_average_etv);
        cost = findViewById(R.id.create_match_cost_etv);
        localText = findViewById(R.id.create_match_local_text);
        localSpinner = findViewById(R.id.local_spinner);
        citySpinner = findViewById(R.id.city_spinner);
    }
}
