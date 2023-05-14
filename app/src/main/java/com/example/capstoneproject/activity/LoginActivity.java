package com.example.capstoneproject.activity;

import static com.example.capstoneproject.activity.SignUpActivity.PERMISSION_REQUEST;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.capstoneproject.R;
import com.example.capstoneproject.common.SharedPreferencesManager;
import com.example.capstoneproject.data.auth.AuthService;
import com.example.capstoneproject.data.auth.request.User;
import com.example.capstoneproject.data.auth.response.login.LoginResult;
import com.example.capstoneproject.view.LoginView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.messaging.FirebaseMessaging;


public class LoginActivity extends AppCompatActivity implements LoginView {

    private TextInputEditText loginId;
    private TextInputEditText loginPassword;
    private AppCompatButton loginButton, createButton, locationButton;
    private CheckBox loginCb;
    private String token;
    private boolean locationValidate = false;
    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        Intent intent = getIntent();
        boolean permission = intent.getBooleanExtra("permission", true);
        if (!permission) {
            //권한 버튼 보이게
            locationButton.setVisibility(View.VISIBLE);
            //권한 창 뜨게

        } else {
            //권한 버튼 안보이게
            locationButton.setVisibility(View.GONE);
            locationValidate = true;

        }

        locationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCheckPermission();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (!task.isSuccessful()) {
                    Log.w("token fail", "onComplete: " + task.getException());
                    return;
                }

                //토큰 조회 성공
                token = task.getResult();
            }
        });
    }

    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 로그인 버튼 -> 메인 액티비티 이동
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        // 회원가입 버튼 -> 회원가입 액티비티 이동
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
                Log.d("회원가입 버튼", "onClick: 클릭되었습니다.");
            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "onPause: ");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "onDestroy: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        boolean coarseLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;

        boolean fineLocationGranted = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED;
        if (!coarseLocationGranted && !fineLocationGranted) {
        } else {
            Toast.makeText(this, "위치 권한 설정 성공", Toast.LENGTH_SHORT).show();
            locationButton.setVisibility(View.GONE);
            locationValidate = true;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "onStart: ");
    }

    private void login() {
        if (loginId.getText().toString().isEmpty()) {
            Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (loginPassword.getText().toString().isEmpty()) {
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!locationValidate) {
            Toast.makeText(this, "위치 서비스를 허용해주세요.", Toast.LENGTH_SHORT).show();
            androidx.appcompat.app.AlertDialog.Builder localBuilder = new androidx.appcompat.app.AlertDialog.Builder(this);
            localBuilder.setTitle("권한 설정")
                    .setMessage("위치 권한 동의를 해야 로그인이 가능합니다.")
                    .setPositiveButton("권한 설정하러 가기", new DialogInterface.OnClickListener(){
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt){
                            try {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        .setData(Uri.parse("package:" + getPackageName()));
                                startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                e.printStackTrace();
                                Intent intent = new Intent(Settings.ACTION_MANAGE_APPLICATIONS_SETTINGS);
                                startActivity(intent);
                            }
                        }})
                    .setNegativeButton("취소하기", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                            Toast.makeText(getApplication(),"위치 권한 동의를 하지 않으셨습니다.",Toast.LENGTH_SHORT).show();
                        }})
                    .create()
                    .show();
            return;
        }
        AuthService authService = new AuthService();
        authService.setLoginView(this);
        authService.login(loginReq());
    }

    private User loginReq() {
        String uid = loginId.getText().toString();
        String password = loginPassword.getText().toString();
        Log.d("TAG", "토큰값: " + token);
        return new User(uid, password, token);
    }

    // 뷰 초기화
    private void init() {
        loginId = findViewById(R.id.login_id_etv);
        loginPassword = findViewById(R.id.login_pw_etv);
        loginButton = findViewById(R.id.login_btn);
        createButton = findViewById(R.id.login_create_btn);
        locationButton = findViewById(R.id.login_location_btn);
        loginCb = findViewById(R.id.login_cb);
    }

    private void saveDataInShared(String jwt, int userIdx, String name, String nickname) {
        final SharedPreferences spf = getSharedPreferences("auth", MODE_PRIVATE);
        final SharedPreferences.Editor editor = spf.edit();
        editor.putString("jwt", jwt);
        editor.putInt("userIdx", userIdx);
        editor.putString("name", name);
        editor.putString("nickName", nickname);
        editor.apply();
    }

    private int getUserIdx() {
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getInt("userIdx", 0);
    }

    @Override
    public void onLoginSuccess(int code, LoginResult result) {
        if (code == 1000) {
            if (loginCb.isChecked()) {
                SharedPreferencesManager.setLoginInfo(this, result.getJwt()); // 로그인 정보 로컬 저장소에 저장
            }
            Log.d("userIdx1", "" + result.getUserIdx());
            Log.d("userIdx2", "" + getUserIdx());
            if (result.getUserIdx() == 26) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
            } else {
                saveDataInShared(result.getJwt(), result.getUserIdx(), result.getName(), result.getNickName());
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
            finish();

        }
    }

    @Override
    public void onLoginFailure() {
        Toast.makeText(this, "입력값을 다시 확인해주세요.", Toast.LENGTH_SHORT).show();
    }

    //뒤로 2번 눌러 종료
    private long backPressedTime = 0;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backPressedTime + 2000) {
            backPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else if (System.currentTimeMillis() <= backPressedTime + 2000) {
            finish();
        }
    }

    private String getJwt() {
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt", "");
    }

    private String getNickname() {
        SharedPreferences spf = this.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("nickname", "");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(LoginActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음
            locationValidate = true;



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity.this, REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(LoginActivity.this, "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(LoginActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(LoginActivity.this, REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }

    public void onCheckPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                Toast.makeText(this, "로그인을 위해서는 권한을 설정해야합니다", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "로그인을 위한 권한이 설정 되었습니다.", Toast.LENGTH_SHORT).show();
                    locationValidate = true;

                } else {
                    Toast.makeText(this, "로그인을 위한 권한이 취소 되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

}
