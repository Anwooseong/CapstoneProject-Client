package com.example.capstoneproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capstoneproject.data.RecordModel;

import java.util.ArrayList;

public class RecordFragment extends Fragment {


    private RecyclerView recyclerView;

    ArrayList<RecordModel> list = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_record, container, false);
        initView(root);

        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "WIN ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.03.02(목) 오후4시", "온라인 매칭", "LOSE ", "안우성", "230", "안우성","230"));
        list.add(new RecordModel("2023.03.04(토) 오후3시", "울산문수경기장", "WIN ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE ", "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE " , "딜리트팀", "230", "안우성","230"));
        list.add(new RecordModel("2023.02.28(화) 오후3시", "울산문수경기장", "LOSE " , "딜리트팀", "230", "안우성","230"));
        return root;
    }



    @Override
    public void onStart() {
        super.onStart();
        //        profileImage  -> TODO Glide 사용


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //viewPage2 connect
        recyclerView.setAdapter(new RecordRecyclerViewAdapter(list));

    }

    private void initView(View root) {
        recyclerView = root.findViewById(R.id.record_recyclerview);
    }
}
