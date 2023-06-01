package com.example.capstoneproject.fragment;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstoneproject.activity.AlarmActivity;
import com.example.capstoneproject.activity.CreateActivity;
//import com.example.capstoneproject.adapter.NextMatchViewPageAdapter;
import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.GpsTracker;
import com.example.capstoneproject.activity.MainActivity;
import com.example.capstoneproject.adapter.NextMatchViewPageAdapter;
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.response.GetAllOfflineMatchCountResponse;
import com.example.capstoneproject.data.match.response.GetAllOnlineMatchCountResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResponse;
import com.example.capstoneproject.data.match.response.plan.GetRemainMatchRoomResult;
import com.example.capstoneproject.data.users.UserService;
import com.example.capstoneproject.data.users.response.info.GetSimpleInfoResult;
import com.example.capstoneproject.view.GetAllOfflineMatchCountView;
import com.example.capstoneproject.view.GetAllOnlineMatchCountView;
import com.example.capstoneproject.view.GetRemainMatchRoomView;
import com.example.capstoneproject.view.GetSimpleInfoView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment implements GetRemainMatchRoomView, GetSimpleInfoView, GetAllOnlineMatchCountView, GetAllOfflineMatchCountView {

    private ConstraintLayout profileLayout, allMatchBtn, onlineMatchBtn, offlineMatchBtn;
    private ImageView profileImage, alarmBtn;
    private TextView profileName;
    private TextView profileAvg;
    private TextView profileOdds;
    private TextView nextMatchEmpty;
    private TextView allMatchCountText, allOnlineMatchCountText, allOfflineMatchCountText;
    private ConstraintLayout createMatchRoom;
    private ViewPager2 nextMatchViewPager;
    private TabLayout indicator;
    private NextMatchViewPageAdapter adapter;
    MainActivity activity;
    String localName = null, cityName = null;
    int allOnlineMatchCount = 0, allOfflineMatchCount = 0;

    //GPS
    private GpsTracker gpsTracker;
    View root;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    private static final int PERMISSION_REQUEST_CODE = 1001;
    private boolean permissionAccepted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_home, container, false);
        // initView() 메서드를 호출하여 뷰를 초기화합니다.
        initView(root);
        createMatchingRoomListener();

        // 비동기적으로 GPS 핸들러 호출
        root.post(this::gpsHandler);


        // "allMatchBtn"에 대한 클릭 리스너를 설정합니다.
        allMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 부모 액티비티의 onFragmentChange() 메서드를 호출하여 매개변수로 1을 전달하여 MatchFragment로 이동합니다.
                activity.onFragmentChange(1);
            }
        });

        // "onlineMatchBtn"에 대한 클릭 리스너를 설정합니다.
        onlineMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.onFragmentChange(1);
            }
        });

        // "offlineMatchBtn"에 대한 클릭 리스너를 설정합니다.
        offlineMatchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                activity.onFragmentChange(1);
                // MatchFragment로 데이터를 전달하기 위해 번들(Bundle)을 생성합니다.
                Bundle bundle = new Bundle();
                bundle.putString("networkType", "OFFLINE");
                bundle.putString("localName", localName);
                bundle.putString("cityName", cityName);

                // FragmentTransaction을 생성하여 현재 프래그먼트를 MatchFragment로 대체합니다.
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                MatchFragment matchFragment = new MatchFragment();
                matchFragment.setArguments(bundle);
                transaction.replace(R.id.main_frm_js, matchFragment);
                transaction.commit();
            }
        });

        return root;
    }

    private void gpsHandler() {
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting();
        } else {
            checkRunTimePermission();
        }
    }

    private void checkRunTimePermission() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우 권한 요청
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_REQUEST_CODE);
        } else {
            // 권한이 이미 허용된 경우
            permissionAccepted = true;
            performGPSOperations();
        }
    }

    private void performGPSOperations() {
        TextView textViewAddress = root.findViewById(R.id.possible_offline_region_tv);
        GpsTracker gpsTracker = new GpsTracker(requireContext());

        // 현재 위치의 위도(latitude)와 경도(longitude)를 가져옵니다.
        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        // 위도와 경도를 기반으로 현재 주소를 가져옵니다.
        String address = getCurrentAddress(latitude, longitude);
        String[] detailAddress = address.split(" ");

        // detailAddress 배열에서 localName을 추출합니다.
        localName = detailAddress[1];

        cityName = detailAddress[2].equals("포항시") ? detailAddress[2] + " " + detailAddress[3] : detailAddress[2];

        // 텍스트뷰에 localName과 cityName을 설정하여 현재 위치를 표시합니다.
        textViewAddress.setText(localName + " " + cityName);
        //vidtextViewAddress.setText("울산광역시\n남구");
        getAllOfflineMatchCount(localName, cityName);
    }


    private void showDialogForLocationServiceSetting() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        // 대화상자의 제목을 설정합니다.
        builder.setTitle("위치 서비스 비활성화");
        // 대화상자의 메시지를 설정합니다.
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하시겠습니까?");
        // 대화상자를 취소 가능하도록 설정합니다.
        builder.setCancelable(true);

        // "설정" 버튼을 설정하고 클릭 리스너를 추가합니다.
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // 위치 설정 화면으로 이동
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        // "취소" 버튼을 설정하고 클릭 리스너를 추가합니다.
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 권한이 허용된 경우
                permissionAccepted = true;
                performGPSOperations();
            } else {
                // 권한이 거부된 경우
                Toast.makeText(requireContext(), "위치 권한이 거부되어 현재 위치 정보를 가져올 수 없습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }


    public String getCurrentAddress(double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }


        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString() + "\n";

    }

    public boolean checkLocationServicesStatus() {
        Context context = getActivity();
        LocationManager locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        activity = (MainActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();

        activity = null;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d("TAG", "onStart: onstart");

        getSimpleInfo();
        getList();
        getAllOnlineMatchCount();

        alarmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmActivity.class);
                startActivity(intent);
            }
        });

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                allMatchCountText.setText(String.valueOf(allOnlineMatchCount+allOfflineMatchCount)+"건");
            }
        });
    }

    private void getAllOnlineMatchCount() {
        MatchService matchService = new MatchService();
        matchService.setGetAllOnlineMatchCountView(this);
        matchService.getAllOnlineMatchCount();
    }

    private void getAllOfflineMatchCount(String localName, String cityName) {
        MatchService matchService = new MatchService();
        matchService.setGetAllOfflineMatchCountView(this);
        matchService.getAllOfflineMatchCount(localName, cityName);
    }

    private void getList() {
        MatchService matchService = new MatchService();
        matchService.setGetRemainMatchRoomView(this);
        matchService.getRemainResult(getJwt());
    }

    private void getSimpleInfo() {
        UserService userService = new UserService();
        userService.setSimpleInfoView(this);
        userService.getSimpleInfo(getJwt());
    }

    private void initRecyclerView(List<GetRemainMatchRoomResult> result) {
        if (result.size() == 0) {
            nextMatchEmpty.setVisibility(View.VISIBLE);
            return;
        }
        nextMatchEmpty.setVisibility(View.GONE);
        nextMatchViewPager.setVisibility(View.VISIBLE);
        adapter = new NextMatchViewPageAdapter(result, this.getContext(), getJwt());
        nextMatchViewPager.setAdapter(adapter);
    }

    private void createMatchingRoomListener() {
        createMatchRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreateActivity.class);
                startActivity(intent);
            }
        });
    }

    private String getJwt() {
        SharedPreferences spf = this.getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt", "");
    }

    private void initView(View root) {
        profileLayout = root.findViewById(R.id.profile_layout);
        profileImage = root.findViewById(R.id.profile_iv);
        profileName = root.findViewById(R.id.profile_name_tv);
        profileAvg = root.findViewById(R.id.profile_avg_tv);
        profileOdds = root.findViewById(R.id.profile_odds_tv);
        allMatchCountText = root.findViewById(R.id.possible_count_tv);
        allOnlineMatchCountText = root.findViewById(R.id.possible_online_count_tv);
        allOfflineMatchCountText = root.findViewById(R.id.possible_offline_count_tv);
        nextMatchEmpty = root.findViewById(R.id.next_match_null_tv);
        nextMatchViewPager = root.findViewById(R.id.next_match_viewpager);
        indicator = root.findViewById(R.id.viewpager_indicator);
        createMatchRoom = root.findViewById(R.id.create_match_layout);
        profileImage.setClipToOutline(true);
        alarmBtn = root.findViewById(R.id.alarm_btn);
        allMatchBtn = root.findViewById(R.id.possible_match_layout);
        onlineMatchBtn = root.findViewById(R.id.possible_online_layout);
        offlineMatchBtn = root.findViewById(R.id.possible_offline_layout);
    }


    @Override
    public void onGetRemainMatchRoomSuccess(GetRemainMatchRoomResponse result) {
        initRecyclerView(result.getResult());
        //NEXT MATCH 인디케이터
        if (result.getResult().size() == 0) {
            indicator.setVisibility(View.GONE);
            nextMatchViewPager.setVisibility(View.GONE);
            return;
        }
        indicator.setVisibility(View.VISIBLE);
        new TabLayoutMediator(indicator, nextMatchViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();
    }

    @Override
    public void onGetRemainMatchRoomFailure() {

    }

    @Override
    public void onGetSimpleInfoSuccess(GetSimpleInfoResult result) {
        profileName.setText(result.getNickName());
        profileAvg.setText(String.valueOf(result.getAverage()));
        profileOdds.setText(result.getWinCount() + "승 " + result.getLoseCount() + "패 " + result.getDrawCount() + "무 " + "(승률 " + result.getWinRate() + "%)");
    }

    @Override
    public void onGetSimpleInfoFailure() {
    }

    @Override
    public void onGetAllOnlineMatchCountSuccess(GetAllOnlineMatchCountResponse response) {
        allOnlineMatchCount = response.getResult().getCount();
        allOnlineMatchCountText.setText(String.valueOf(allOnlineMatchCount) + "건");
    }

    @Override
    public void onGetAllOnlineMatchCountFailure(GetAllOnlineMatchCountResponse response) {

    }

    @Override
    public void onGetAllOfflineMatchCountSuccess(GetAllOfflineMatchCountResponse response) {
        allOfflineMatchCount = response.getResult().getCount();
        allOfflineMatchCountText.setText(String.valueOf(allOfflineMatchCount) + "건");
        allMatchCountText.setText(String.valueOf(allOnlineMatchCount+allOfflineMatchCount)+"건");
    }

    @Override
    public void onGetAllOfflineMatchCountFailure(GetAllOfflineMatchCountResponse response) {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}