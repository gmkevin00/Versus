package com.example.donkey.versus;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class achievementFragment extends Fragment {
    private RecyclerView achievementRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private achieveAdapter achieveAdapter;

    private ArrayList<achievement> achievementList=new ArrayList();

    public achievementFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_achievement, container, false);


        achievement a=new achievement();
        a.setAchievementName("完成五次挑戰");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("完成十次挑戰");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("完成十五次挑戰");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰達成率30%");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰達成率50%");
        a.setFlag(0);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰達成率70%");
        a.setFlag(0);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰邀請至少一位好友");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰邀請至少三位好友");
        a.setFlag(1);
        achievementList.add(a);
        a=new achievement();
        a.setAchievementName("單次挑戰邀請至少五位好友");
        a.setFlag(0);
        achievementList.add(a);




        achievementRecyclerView=(RecyclerView)view.findViewById(R.id.achievementRecycleView);

        mLayoutManager = new LinearLayoutManager(getActivity());
        achievementRecyclerView.setLayoutManager(mLayoutManager);

        achieveAdapter=new achieveAdapter(achievementList);
        achievementRecyclerView.setAdapter(achieveAdapter);




        return view;
    }


}
