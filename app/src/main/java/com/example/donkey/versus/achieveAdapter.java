package com.example.donkey.versus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/9/24.
 */
public class achieveAdapter extends RecyclerView.Adapter<achieveAdapter.ViewHolder> {
    private ArrayList<achievement> achievementList=new ArrayList();

    private ViewGroup parent;

    public achieveAdapter(ArrayList<achievement> achievementList)
    {
        this.achievementList=achievementList;
    }

    @Override
    public achieveAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.achievementitem, parent, false);
        // set the view's size, margins, paddings and layout parameters

        achieveAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(achieveAdapter.ViewHolder holder, int position) {
        holder.achievementName.setText(achievementList.get(position).getAchievementName());
        if(achievementList.get(position).getFlag()==1)
        {
            holder.achievementFlag.setImageResource(R.drawable.mark);
        }

    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        TextView achievementName;
        ImageView achievementFlag;



        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            this.achievementFlag=(ImageView)itemLayoutView.findViewById(R.id.achievementflag);
            this.achievementName=(TextView)itemLayoutView.findViewById(R.id.achievementname);
        }


    }
}
