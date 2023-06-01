package com.example.capstoneproject.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.adapter.OfflineRoomsAdapter;
import com.example.capstoneproject.adapter.OnlineRoomsAdapter;
import com.example.capstoneproject.R;
import com.example.capstoneproject.data.match.MatchService;
import com.example.capstoneproject.data.match.response.GetMatchCityResponse;
import com.example.capstoneproject.data.match.response.GetMatchRoomResult;
import com.example.capstoneproject.view.GetMatchCityView;
import com.example.capstoneproject.view.GetMatchRoomView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;
import java.util.List;

public class MatchFragment extends Fragment implements GetMatchRoomView, GetMatchCityView {

    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup toggleBtn;
    private OnlineRoomsAdapter onlineRoomsAdapter;
    private OfflineRoomsAdapter offlineRoomsAdapter;
    private View root;
    private String type = "ONLINE";
    private String[] localItems = {"-- 선택 --", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "경기도", "강원도", "충청북도", "충청남도", "전라북도", "전라남도", "경상북도", "경상남도", "제주특별자치도"};
    private ArrayList<String> cityItems = new ArrayList<>();
    private Spinner localSpinner, citySpinner;
    private String localName, cityName;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_match, container, false);
        initView(root);

        // getArguments()->이전프래그먼트로 전달된 값이 있는지 확인합니다.
        if (getArguments() != null) {
            // getArguments()로 전달된 값이 있으면 오프라인 모드로 설정합니다.
            toggleBtn.check(R.id.offline_btn);
        } else {
            // getArguments()로 전달된 값이 없으면 온라인 모드로 설정합니다.
            toggleBtn.check(R.id.online_btn);
        }

        // toggleBtn의 체크된 버튼 ID에 따라 온라인 목록 또는 오프라인 목록을 가져옵니다.
        if (toggleBtn.getCheckedButtonId() == R.id.online_btn) {
            // 온라인 모드인 경우 "ONLINE" 타입의 목록을 가져옵니다.
            getList("ONLINE", null, null);
        } else {
            if (getArguments() != null) {
                // 오프라인 모드이고 getArguments()로 전달된 값이 있는 경우 해당 값으로 오프라인 목록을 가져옵니다.
                getList("OFFLINE", getArguments().getString("localName"), getArguments().getString("cityName"));
            } else {
                // 오프라인 모드이고 getArguments()로 전달된 값이 없는 경우 전체 오프라인 목록을 가져옵니다.
                getList("OFFLINE", null, null);
            }
        }
        return root;
    }

    private void spinnerHandler(int i) {
        ArrayAdapter<String> localSpinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, localItems
        );
        localSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        // 지역 스피너에 어댑터를 설정합니다.
        localSpinner.setAdapter(localSpinnerAdapter);
        // i번째 아이템을 선택합니다.
        localSpinner.setSelection(i);

        // 지역 스피너의 아이템 선택 이벤트 리스너를 설정합니다.
        localSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 선택된 지역 이름을 가져옵니다.
                localName = localItems[i];
                // 선택된 지역에 해당하는 도시를 가져옵니다.
                getMatchCity(localName);
                // 지역 이름이 "-- 선택 --"이 아닌 경우에만 오프라인 목록을 가져옵니다.
                if (!localName.equals("-- 선택 --")) {
                    getList("OFFLINE", localName, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // 아무것도 선택되지 않았을 때 기본적으로 0번째 아이템을 선택합니다.
                localSpinner.setSelection(0);
            }
        });

    }

    //cityName adapter
    private void getMatchCity(String local) {
        MatchService matchService = new MatchService();
        matchService.setGetMatchCityView(this);
        matchService.getMatchCity(local);
    }

    @Override
    public void onStart() {
        super.onStart();

        // getArguments()->이전프래그먼트로 전달된 값이 있는지 확인합니다.
        if (getArguments() != null) {
            //오프라인모드로 설정
            String localName = getArguments().getString("localName");
            spinnerHandler(0);
            toggleBtn.check(R.id.offline_btn);
            type = getArguments().getString("networkType");

        } else {
            // getArguments()이 null일때 온라인 모드로 설정정
           toggleBtn.check(R.id.online_btn);
            type = "ONLINE";
            spinnerHandler(0);
        }

//        spinnerHandler(0);
//        toggleBtn.check(R.id.online_btn);

//        type = "ONLINE";
        toggleBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.online_btn) {
                        // 온라인 버튼이 선택된 경우
                        type = "ONLINE";

                        // 지역 스피너와 도시 스피너를 숨깁니다.
                        localSpinner.setVisibility(View.GONE);
                        citySpinner.setVisibility(View.GONE);

                        // 온라인 목록을 가져옵니다.
                        getList("ONLINE", null, null);
                    }
                    if (checkedId == R.id.offline_btn) {
                        // 오프라인 버튼이 선택된 경우
                        type = "OFFLINE";

                        // 지역 스피너와 도시 스피너를 표시합니다.
                        localSpinner.setVisibility(View.VISIBLE);
                        citySpinner.setVisibility(View.VISIBLE);

                        // 오프라인 목록을 가져옵니다.
                        getList("OFFLINE", null, null);
                    }
                }
            }
        });
    }

    private void initRecyclerView(String type, List<GetMatchRoomResult> result) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (type.equals("ONLINE")) {
            onlineRoomsAdapter = new OnlineRoomsAdapter(result, getContext());
            recyclerView.setAdapter(onlineRoomsAdapter);
        } else if (type.equals("OFFLINE")) {
            offlineRoomsAdapter = new OfflineRoomsAdapter(result, getContext());
            recyclerView.setAdapter(offlineRoomsAdapter);
        }
    }

    private void getList(String type, String local, String city) {
        MatchService matchService = new MatchService();
        matchService.setGetMatchRoomView(this);
        if (type.equals("ONLINE")) {
            // 온라인 모드인 경우에는 OnlineRoomsAdapter를 생성하여 리사이클러뷰에 설정합니다.
            matchService.getOnlineMatchRoom();
        } else if (type.equals("OFFLINE")) {
            // 오프라인 모드인 경우에는 OfflineRoomsAdapter를 생성하여 리사이클러뷰에 설정합니다.
            matchService.getOfflineMatchRoom(local, city);
        }
    }


    private void initView(View root) {
        recyclerView = root.findViewById(R.id.rooms_recyclerview);
        toggleBtn = root.findViewById(R.id.toggle_btn);
        localSpinner = root.findViewById(R.id.offline_local_spinner);
        citySpinner = root.findViewById(R.id.offline_city_spinner);
    }

    @Override
    public void onGetMatchRoomSuccess(List<GetMatchRoomResult> result) {
        for (GetMatchRoomResult getMatchRoomResult : result) {
        }
        initRecyclerView(type, result);
    }


    @Override
    public void onGetMatchCityFailure() {

    }

    @Override
    public void onGetMatchCitySuccess(GetMatchCityResponse response) {
        List<String> result = response.getResult();
        cityItems = new ArrayList<>();

        // 도시 아이템 리스트를 초기화하고 "-- 선택 --"을 추가합니다.
        cityItems.add("-- 선택 --");

        // 응답으로 받은 도시 목록을 순회하면서 도시 아이템 리스트에 추가합니다.
        for (String s : result) {
            cityItems.add(s);
        }

        // 도시 스피너를 표시합니다.
        citySpinner.setVisibility(View.VISIBLE);

        // 도시 스피너용 어댑터를 생성합니다.
        ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, cityItems
        );
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        // 도시 스피너의 초기 선택 항목을 설정합니다.
        citySpinner.setSelection(0);
        cityName = null;

        // 도시 스피너에 어댑터를 설정합니다.
        citySpinner.setAdapter(citySpinnerAdapter);

        // 도시 스피너의 아이템 선택 이벤트 리스너를 설정합니다.
        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // 선택된 도시 이름을 가져옵니다.
                cityName = cityItems.get(i);

                // 선택된 도시 이름이 "-- 선택 --"이 아닌 경우에만 오프라인 목록을 가져옵니다.
                if (!cityItems.get(i).equals("-- 선택 --")) {
                    getList("OFFLINE", localName, cityName);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onGetMatchRoomFailure() {

    }

}