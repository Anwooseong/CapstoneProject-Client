package com.example.capstoneproject.fragment;

import android.os.Bundle;
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
    private String local;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_match, container, false);
        initView(root);

        spinnerHandler();
        getList("ONLINE", null, null);
        return root;
    }

    private void spinnerHandler() {
        ArrayAdapter<String> localSpinnerAdapter = new ArrayAdapter<String>(
                getContext(), android.R.layout.simple_spinner_item, localItems
        );
        localSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        localSpinner.setAdapter(localSpinnerAdapter);
        localSpinner.setSelection(0);
        localSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                local = localItems[i];
                getMatchCity(local);

                getList("OFFLINE", local, null);
                citySpinner.setVisibility(View.VISIBLE);
                ArrayAdapter<String> citySpinnerAdapter = new ArrayAdapter<String>(
                        getContext(), android.R.layout.simple_spinner_item, cityItems
                );
                citySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
                citySpinner.setSelection(0);
                citySpinner.setAdapter(citySpinnerAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                getList("OFFLINE", localItems[i], cityItems.get(i));
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
    public void onStart() {
        super.onStart();
        toggleBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if (isChecked) {
                    if (checkedId == R.id.online_btn) {
                        type = "ONLINE";
                        getList("ONLINE", null, null);
                    }
                    if (checkedId == R.id.offline_btn) {
                        type = "OFFLINE";
                        getList("OFFLINE", null, null);
                    }
                } else {
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
            System.out.println(getMatchRoomResult.getMatchIdx() + " --- " + getMatchRoomResult.getNumbers() + " -----" + getMatchRoomResult.getPlace());
        }
        initRecyclerView(type, result);
    }


    @Override
    public void onCheckSocketActiveViewFailure() {

    }

    @Override
    public void onGetMatchCitySuccess(GetMatchCityResponse response) {
        List<String> result = response.getResult();
        cityItems = new ArrayList<>();
        cityItems.add("-- 선택 --");
        for (String s : result) {
            cityItems.add(s);
        }
    }

    @Override
    public void onGetMatchRoomFailure() {

    }

}
