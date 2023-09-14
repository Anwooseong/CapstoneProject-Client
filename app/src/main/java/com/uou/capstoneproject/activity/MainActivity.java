//package com.uou.capstoneproject.activity;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//
//import android.annotation.SuppressLint;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.Toast;
//
//import com.uou.capstoneproject.fragment.HomeFragment;
//import com.uou.capstoneproject.fragment.InfoFragment;
//import com.uou.capstoneproject.fragment.MatchFragment;
//import com.uou.capstoneproject.R;
//import com.uou.capstoneproject.fragment.RecordFragment;
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//
//public class MainActivity extends AppCompatActivity {
//
//    private HomeFragment homeFragment;
//    private MatchFragment matchFragment;
//    private RecordFragment recordFragment;
//    private InfoFragment infoFragment;
//    private BottomNavigationView bottomNavigationView;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//        initBottomNavigation();
//    }
//
//    private void initBottomNavigation() { // 뷰 초기화 부분
//        homeFragment = new HomeFragment();
//        matchFragment = new MatchFragment();
//        recordFragment = new RecordFragment();
//        infoFragment = new InfoFragment();
//
//        bottomNavigationView = findViewById(R.id.main_bnv_js); // 처음화면
//        getSupportFragmentManager().beginTransaction().add(R.id.main_frm_js, homeFragment).commitAllowingStateLoss(); // FrameLayout에 fragment_home.xml 띄우기
//
//        //바텀 네비게이션 뷰 안의 아이템 설정
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @SuppressLint("NonConstantResourceId")
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//                switch (item.getItemId()) { // item 클릭 시 id값을 가져와서 FrameLayout에 fragment_xxx.xml띄우기
//                    case R.id.homeFragment:
//                        //main_frm_js라는 ID를 가진 레이아웃 프레임 영역에 homeFragment를 대체
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, homeFragment).commitAllowingStateLoss();
//                        break;
//                    case R.id.matchFragment:
//                        //main_frm_js라는 ID를 가진 레이아웃 프레임 영역에 matchFragment 대체
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, matchFragment).commitAllowingStateLoss();
//                        break;
//                    case R.id.recordFragment:
//                        //main_frm_js라는 ID를 가진 레이아웃 프레임 영역에 recordFragment 대체
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, recordFragment).commitAllowingStateLoss();
//                        break;
//                    case R.id.infoFragment:
//                        //main_frm_js라는 ID를 가진 레이아웃 프레임 영역에 infoFragment 대체
//                        getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, infoFragment).commitAllowingStateLoss();
//                        break;
//                }
//                return true;
//            }
//        });
//    }
//
//    /**
//     * @param index 1인경우 : 하단 네비게이션 뷰(bottomNavigationView)를 숨기고, main_frm_js라는 ID를 가진 레이아웃 프레임 영역에 matchFragment를 대체
//     */
//    public void onFragmentChange(int index) {
//        if (index == 1) {
//            bottomNavigationView.setVisibility(View.GONE);
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, matchFragment).commitAllowingStateLoss();
//        }
//    }
//
//    private long backPressedTime = 0;
//
//    //하단 네비게이션 뷰(bottomNavigationView)를 숨김
//    public void onGoneBottomNavigation() {
//        bottomNavigationView.setVisibility(View.GONE);
//    }
//
//    @Override
//    public void onBackPressed() {
//        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.main_frm_js);
//        if (!(fragment instanceof HomeFragment)) {
//            // 현재 프래그먼트가 HomeFragment가 아닌 경우
//            getSupportFragmentManager().beginTransaction().replace(R.id.main_frm_js, homeFragment)
//                    .addToBackStack(null)
//                    .commit();
//            setHomeItem();
//            bottomNavigationView.setVisibility(View.VISIBLE);
//        } else {
//            // 현재 프래그먼트가 HomeFragment인 경우
//            if (System.currentTimeMillis() > backPressedTime + 1000) {
//                // 이전에 눌린 시간과의 간격이 1초 이상인 경우
//                backPressedTime = System.currentTimeMillis();
//                Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//            } else if (System.currentTimeMillis() <= backPressedTime + 1000) {
//                // 이전에 눌린 시간과의 간격이 1초 이하인 경우
//                finish();
//            }
//        }
//
//    }
//
//    //하단 네비게이션 뷰(bottomNavigationView)에서 HomeFragment를 선택한 상태로 설정
//    public void setHomeItem() {
//        bottomNavigationView.setSelectedItemId(R.id.homeFragment);
//    }
//}