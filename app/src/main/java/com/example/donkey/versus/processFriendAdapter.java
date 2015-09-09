package com.example.donkey.versus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/9/1.
 */
public class processFriendAdapter extends RecyclerView.Adapter<processFriendAdapter.ViewHolder> {
    private ViewGroup parent;

    private ArrayList<Competitor> competitors=new ArrayList<Competitor>();
    private User user;
    private int totalCount;
    private int cycle;

    public processFriendAdapter(User user,ArrayList<Competitor> competitors,int totalCount,int cycle)
    {
        this.competitors=competitors;
        this.user=user;
        this.totalCount=totalCount;
        this.cycle=cycle;
    }

    @Override
    public processFriendAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.processfrienditem, parent, false);
        // set the view's size, margins, paddings and layout parameters

        processFriendAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.processFriendPhoto.getContext()).load(String.format("https://graph.facebook.com/%s/picture",competitors.get(position).getFbid())).into(holder.processFriendPhoto);
        holder.processFriendName.setText(competitors.get(position).getName());
        holder.processFriendStep.setText(competitors.get(position).getTotalCount()*cycle+"/"+totalCount);

    }



    @Override
    public int getItemCount() {
        return competitors.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView processFriendPhoto;
        TextView processFriendName;
        TextView processFriendStep;


        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);

            this.processFriendPhoto=(ImageView)itemLayoutView.findViewById(R.id.processFriendPhoto);
            this.processFriendName=(TextView)itemLayoutView.findViewById(R.id.processFriendName);
            this.processFriendStep=(TextView)itemLayoutView.findViewById(R.id.processFriendStep);

        }

        @Override
        public void onClick(View v) {
            int position=getPosition();



        }
    }
}
