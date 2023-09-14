package com.uou.capstoneproject.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.uou.capstoneproject.R;
import com.uou.capstoneproject.adapter.RecordRecyclerViewAdapter;
import com.uou.capstoneproject.data.users.UserService;
import com.uou.capstoneproject.data.users.response.record.GetRecordResult;
import com.uou.capstoneproject.view.GetUserRecordView;

import java.util.List;

public class RecordFragment extends Fragment implements GetUserRecordView {


    private RecyclerView recyclerView;
    private RecordRecyclerViewAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        initView(root);

        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        getList();

    }

    //전적 기록 호출 API 메서드
    private void getList() {
        UserService userService = new UserService();
        userService.setUserRecordView(this);
        userService.getUserRecord(getJwt());
    }

    //jwt 조회
    private String getJwt(){
        SharedPreferences spf = this.getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        return spf.getString("jwt","");
    }

    //전적 리사이블러뷰 어댑터
    private void initRecyclerView(List<GetRecordResult> result) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new RecordRecyclerViewAdapter(result);
        recyclerView.setAdapter(adapter);

    }

    private void initView(View root) {
        recyclerView = root.findViewById(R.id.record_recyclerview);
    }

    @Override
    public void onGetMatchRoomSuccess(List<GetRecordResult> result) {
        initRecyclerView(result);
    }

    @Override
    public void onGetMatchRoomFailure() {
    }
}
