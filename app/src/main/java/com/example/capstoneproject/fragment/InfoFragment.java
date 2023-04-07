package com.example.capstoneproject.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.capstoneproject.R;
import com.example.capstoneproject.activity.LoginActivity;
import com.example.capstoneproject.common.SharedPreferencesManager;

public class InfoFragment extends Fragment {

    private ImageView logoutBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_info, container, false);
        initView(root);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesManager.clearPreferences(getContext());
                removeInfo();
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return root;
    }


    private void removeInfo(){
        SharedPreferences spf = getActivity().getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE);
        SharedPreferences.Editor editor = spf.edit();
        editor.clear();
        editor.apply();
    }

    private void initView(View root) {
        logoutBtn = root.findViewById(R.id.logout_btn);
    }

}
