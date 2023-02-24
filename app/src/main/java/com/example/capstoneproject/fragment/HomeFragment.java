package com.example.capstoneproject.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.capstoneproject.adapter.NextMatchViewPageAdapter;
import com.example.capstoneproject.R;
import com.example.capstoneproject.viewmodel.NextMatchModel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private ConstraintLayout profileLayout;
    private ImageView profileImage;
    private TextView profileName;
    private TextView profileAvg;
    private TextView profileOdds;
    private ViewPager2 nextMatchViewPager;
    private TabLayout indicator;
    ArrayList<NextMatchModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        initView(root);
        list.clear();
        list.add(new NextMatchModel("2023.02.28(화) 오후3시", "울산문수경기장", "섭꽁팀", "딜리트팀", "임시", "임시"));
        list.add(new NextMatchModel("2023.03.02(목) 오후4시", "온라인 매칭", "김지섭", "안우성", "임시", "임시"));
        list.add(new NextMatchModel("2023.03.04(토) 오후3시", "울산문수경기장", "섭꽁팀", "딜리트팀", "임시", "임시"));
        list.add(new NextMatchModel("2023.02.28(화) 오후3시", "울산문수경기장", "섭꽁팀", "딜리트팀", "임시", "임시"));
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        //        profileImage  -> TODO Glide 사용

        profileName.setText("김지섭");
        profileAvg.setText("AVG - 230");
        profileOdds.setText("최근 전적 - 8승 2패(승률 80%)");

        //viewPage2 connect
        nextMatchViewPager.setAdapter(new NextMatchViewPageAdapter(list));

        //NEXT MATCH 인디케이터
        new TabLayoutMediator(indicator, nextMatchViewPager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {

            }
        }).attach();
    }

    private void initView(View root) {
        profileLayout = root.findViewById(R.id.profile_layout);
        profileImage = root.findViewById(R.id.profile_iv);
        profileName = root.findViewById(R.id.profile_name_tv);
        profileAvg = root.findViewById(R.id.profile_avg_tv);
        profileOdds = root.findViewById(R.id.profile_odds_tv);
        nextMatchViewPager = root.findViewById(R.id.next_match_viewpager);
        indicator = root.findViewById(R.id.viewpager_indicator);
        profileImage.setClipToOutline(true);
    }
}
