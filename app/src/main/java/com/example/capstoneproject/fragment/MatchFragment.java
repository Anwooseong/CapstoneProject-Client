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
import com.example.capstoneproject.data.getmatch.MatchRoomService;
import com.example.capstoneproject.data.getmatch.response.GetMatchRoomResult;
import com.example.capstoneproject.view.GetMatchRoomView;
import com.google.android.material.button.MaterialButtonToggleGroup;

import java.util.List;

public class MatchFragment extends Fragment implements GetMatchRoomView {

    private RecyclerView recyclerView;
    private MaterialButtonToggleGroup toggleBtn;
    private OnlineRoomsAdapter onlineRoomsAdapter;
    private OfflineRoomsAdapter offlineRoomsAdapter;
    private View root;
    private String type = "ONLINE";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_match, container, false);
        initView(root);
        getList("ONLINE");
        return root;
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
                        getList("ONLINE");
                    }
                    if (checkedId == R.id.offline_btn) {
                        type="OFFLINE";
                        getList("OFFLINE");
                    }
                }else{
                }
            }
        });
    }

    private void initRecyclerView(String type, List<GetMatchRoomResult> result) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        if (type.equals("ONLINE")){
            onlineRoomsAdapter = new OnlineRoomsAdapter(result, getContext());
            recyclerView.setAdapter(onlineRoomsAdapter);
        }else if(type.equals("OFFLINE")){
            offlineRoomsAdapter = new OfflineRoomsAdapter(result, getContext());
            recyclerView.setAdapter(offlineRoomsAdapter);
        }
    }

    private void getList(String type) {
        MatchRoomService matchRoomService = new MatchRoomService();
        matchRoomService.setGetMatchRoomView(this);
        if (type.equals("ONLINE")) {
            matchRoomService.getOnlineMatchRoom();
        } else if (type.equals("OFFLINE")) {
            matchRoomService.getOfflineMatchRoom();
        }
    }


    private void initView(View root) {
        recyclerView = root.findViewById(R.id.rooms_recyclerview);
        toggleBtn = root.findViewById(R.id.toggle_btn);
    }

    @Override
    public void onGetMatchRoomSuccess(List<GetMatchRoomResult> result) {
        for (GetMatchRoomResult getMatchRoomResult : result) {
            System.out.println(getMatchRoomResult.getMatchIdx()+" --- "+getMatchRoomResult.getNumbers()+" -----"+getMatchRoomResult.getPlace() );
        }
        initRecyclerView(type, result);
    }

    @Override
    public void onGetMatchRoomFailure() {

    }
}
