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

        if (getArguments() != null) {
            //오프라인일때
            toggleBtn.check(R.id.offline_btn);
            Log.d("TAG", "오프라인" + toggleBtn.getCheckedButtonId());
            Log.d("TAG", "오프라인" + R.id.online_btn);
        } else {
            //온라인일때
            toggleBtn.check(R.id.online_btn);
        }

//        toggleBtn.check(R.id.online_btn);


        if (toggleBtn.getCheckedButtonId() == R.id.online_btn) {
            getList("ONLINE", null, null);
        } else {
            if (getArguments() != null) {
                getList("OFFLINE", getArguments().getString("localName"), getArguments().getString("cityName"));

            } else {
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
        localSpinner.setAdapter(localSpinnerAdapter);
        localSpinner.setSelection(i);
        localSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                localName = localItems[i];
                getMatchCity(localName);
                if (!localName.equals("-- 선택 --")) {
                    getList("OFFLINE", localName, null);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
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
        if (getArguments() != null) {
            //오프라인
            String localName = getArguments().getString("localName");
            spinnerHandler(0);
            toggleBtn.check(R.id.offline_btn);
            type = getArguments().getString("networkType");
            Log.d("TAG", "localName: " + localName);

        } else {
            //온라인
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
                Log.d("TAG", "checkedId: " + checkedId);
                Log.d("TAG", "checkedId: " + R.id.online_btn);
                Log.d("TAG", "checkedId: " + R.id.offline_btn);
                if (isChecked) {
                    if (checkedId == R.id.online_btn) {
                        type = "ONLINE";
                        localSpinner.setVisibility(View.GONE);
                        citySpinner.setVisibility(View.GONE);
                        getList("ONLINE", null, null);
                    }
                    if (checkedId == R.id.offline_btn) {
                        type = "OFFLINE";
                        localSpinner.setVisibility(View.VISIBLE);
                        citySpinner.setVisibility(View.VISIBLE);
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
            matchService.getOnlineMatchRoom();
        } else if (type.equals("OFFLINE")) {
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
        cityItems.add("-- 선택 --");
        for (String s : result) {
            cityItems.add(s);
        }
        citySpinner.setVisibility(View.VISIBLE);
        ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, cityItems
        );
        citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        citySpinner.setSelection(0);
        cityName = null;
        citySpinner.setAdapter(citySpinnerAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cityName = cityItems.get(i);
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