package com.example.capstoneproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.adapter.OfflineRoomsAdapter;
import com.example.capstoneproject.adapter.OnlineRoomsAdapter;
import com.example.capstoneproject.R;
import com.example.capstoneproject.viewmodel.OfflineRoomModel;
import com.example.capstoneproject.viewmodel.OnlineRoomModel;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.ArrayList;

public class MatchFragment extends Fragment {

    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup toggleBtn;
    private View root;

    private ArrayList<OnlineRoomModel> onlineRoomModels = new ArrayList<>();
    private ArrayList<OfflineRoomModel> offlineRoomModels = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_match, container, false);
        initView(root);
        initOnlineRecyclerView();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //int buttonId = toggleBtn.getCheckedButtonId();
        //MaterialButton btn = toggleBtn.findViewById(buttonId);

        toggleBtn.addOnButtonCheckedListener(new MaterialButtonToggleGroup.OnButtonCheckedListener() {
            @Override
            public void onButtonChecked(MaterialButtonToggleGroup group, int checkedId, boolean isChecked) {
                if(isChecked){
                    if(checkedId == R.id.online_btn){
                        onlineRoomModels.clear();
                        initOnlineRecyclerView();
                    }
                    if(checkedId == R.id.offline_btn){
                        offlineRoomModels.clear();
                        initOfflineRecyclerView();
                    }
                }
                else {
                }
            }
        });
    }
    private void initOnlineRecyclerView() {
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.29(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.30(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.31(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        onlineRoomModels.add(new OnlineRoomModel("아무거나", "2023.02.28(화) 오후3시", "AVG - 125"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OnlineRoomsAdapter(onlineRoomModels));
    }

    private void initOfflineRecyclerView() {
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.29(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.30(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.31(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        offlineRoomModels.add(new OfflineRoomModel("아무거나", "4:4 팀전","2023.02.28(화) 오후3시", "울산 문수 경기장","AVG - 125"));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OfflineRoomsAdapter(offlineRoomModels));
    }



    private void initView(View root) {
        recyclerView = root.findViewById(R.id.rooms_recyclerview);
        toggleBtn = root.findViewById(R.id.toggle_btn);
    }
}
