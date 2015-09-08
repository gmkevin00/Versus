package com.example.donkey.versus;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dd.CircularProgressButton;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class challengeHomeProcessFragment extends Fragment implements View.OnClickListener {
    private Room roomProfile;
    private User user;
    private ArrayList<Competitor> competitors=new ArrayList<Competitor>();
    private ArrayList<personalProcess> personalProcessList=new ArrayList();
    private int totalCount;

    private View view;

    private int cycle;

    private SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
    private DateTime nowDate;
    private DateTime startDate;
    private DateTime cycleStart;
    private DateTime cycleEnd;
    private int dayPass;

    private personalProcess currentProcess=null;

    private CircularProgressButton successBtn;
    private CircularProgressButton failBtn;

    private TextView dateLabel;

    public challengeHomeProcessFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_challenge_home, container, false);

        Bundle bundle=((challengeHomeActivity)getActivity()).getDataFromAvtivity();
        this.roomProfile=(Room)bundle.getSerializable("Room");
        this.user=(User)bundle.getSerializable("User");
        this.competitors=(ArrayList)bundle.getSerializable("Competitors");
        this.personalProcessList=(ArrayList)bundle.getSerializable("PersonalProcess");
        this.totalCount=bundle.getInt("dayCount");
        cycle=Integer.parseInt(roomProfile.getRoomCycle());

        successBtn=(CircularProgressButton)view.findViewById(R.id.successBtn);
        successBtn.setOnClickListener(this);
        failBtn=(CircularProgressButton)view.findViewById(R.id.failBtn);
        failBtn.setOnClickListener(this);
        try {
            nowDate= new DateTime();
            startDate= new DateTime(format.parse(roomProfile.getRoomStart()));
            dayPass= Days.daysBetween(startDate,nowDate).getDays();

            cycleStart= new DateTime(format.parse(roomProfile.getRoomStart()));
            cycleStart=cycleStart.plusDays((dayPass/cycle)*cycle);
            cycleEnd= new DateTime(format.parse(roomProfile.getRoomStart()));
            cycleEnd=cycleEnd.plusDays((dayPass/cycle+1)*cycle);

            for(int i=0;i<personalProcessList.size();i++)
            {
                DateTime tempDate= new DateTime(format.parse(personalProcessList.get(i).getTime()));
                if((cycleStart.isBefore(tempDate)||cycleStart.isEqual(tempDate))&&cycleEnd.isAfter(tempDate))
                {
                    currentProcess=personalProcessList.get(i);
                    if(currentProcess.getCheck()==0)
                    {
                        failBtn.setProgress(-1);
                    }
                    else if(currentProcess.getCheck()==1)
                    {
                        successBtn.setProgress(100);
                    }
                }
            }



        } catch (ParseException e) {
            e.printStackTrace();
        }

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.successBtn)
        {
            failBtn.setProgress(0);
            successBtn.setProgress(99);
            phpConnect p=new phpConnect(getContext(),"讀取資料中,請稍後...");
            p.setProgresFlag(false);
            p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
            if(currentProcess==null)
            {
                p.addSendData("sql", String.format("INSERT INTO  `process` (`room_id`,`user_id`,`process_check`) VALUES (%s,%s,1) ",roomProfile.getRoomId(), user.getFbid()));
            }
            else
            {
                p.addSendData("sql", String.format("UPDATE `process` SET  `process_check`=1 WHERE `room_id`=%d AND `user_id`='%s' AND '%s' <= DATE(process_time) AND '%s' > DATE(process_time); ", roomProfile.getRoomId(), user.getFbid(), cycleStart.toString(DateTimeFormat.forPattern("yyyy-MM-dd")), cycleEnd.toString(DateTimeFormat.forPattern("yyyy-MM-dd"))));
            }
            p.execute(new GetUserCallback() {
                @Override
                public void done(JSONArray jsonarray) {
                 //   Log.d("DebugLog", jsonarray.toString());

                    successBtn.setProgress(100);
                }
            });
        }
        else if(v.getId()==R.id.failBtn)
        {
            successBtn.setProgress(0);
            failBtn.setProgress(99);
            phpConnect p=new phpConnect(getContext(),"讀取資料中,請稍後...");
            p.setProgresFlag(false);
            p.setUrl(String.format("http://140.115.80.235/~group15/sql_connect.php"));
            if(currentProcess==null)
            {
                p.addSendData("sql", String.format("INSERT INTO  `process` (`room_id`,`user_id`,`process_check`) VALUES (%s,%s,0) ",roomProfile.getRoomId(), user.getFbid()));
            }
            else
            {
                p.addSendData("sql", String.format("UPDATE `process` SET  `process_check`=0 WHERE `room_id`=%d AND `user_id`='%s' AND '%s' <= DATE(process_time) AND '%s' > DATE(process_time); ", roomProfile.getRoomId(), user.getFbid(), cycleStart.toString(DateTimeFormat.forPattern("yyyy-MM-dd")), cycleEnd.toString(DateTimeFormat.forPattern("yyyy-MM-dd"))));
            }
            p.execute(new GetUserCallback() {
                @Override
                public void done(JSONArray jsonarray) {
                    //Log.d("DebugLog", jsonarray.toString());
                    failBtn.setProgress(-1);
                }
            });

        }
    }
}
