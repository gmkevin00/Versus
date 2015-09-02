package com.example.donkey.versus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class challengeFriendListActivityFragment extends Fragment {
    private Room roomProfile;
    private User user;
    private ArrayList<Competitor> competitors=new ArrayList<Competitor>();
    private int totalCount=20;

    private RecyclerView processFriendList;
    private LinearLayoutManager mLayoutManager;

    private View view;

    public challengeFriendListActivityFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_challenge_friend_list, container, false);

        Bundle bundle=((challengeHomeActivity)getActivity()).getDataFromAvtivity();
        this.roomProfile=(Room)bundle.getSerializable("Room");
        this.user=(User)bundle.getSerializable("User");
        this.competitors=(ArrayList)bundle.getSerializable("Competitors");


        processFriendList = (RecyclerView)view.findViewById(R.id.processFriendList);

        mLayoutManager = new LinearLayoutManager(getActivity());
        processFriendList.setLayoutManager(mLayoutManager);

        processFriendAdapter f=new processFriendAdapter(user,competitors,totalCount);
        processFriendList.setAdapter(f);


        // Inflate the layout for this fragment
        return view;
    }


}
