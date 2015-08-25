package com.example.donkey.versus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/8/23.
 */
public class joinAdapter extends RecyclerView.Adapter<joinAdapter.ViewHolder> {
    private ArrayList<Join> UserJoin=new ArrayList<>();


    public joinAdapter(ArrayList<Join> UserJoin)
    {
        this.UserJoin=UserJoin;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joinitem, parent, false);
        // set the view's size, margins, paddings and layout parameters

       joinAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.inviterPhoto.getContext()).load(String.format("https://graph.facebook.com/%s/picture",UserJoin.get(position).getInviterId())).into(holder.inviterPhoto);
        holder.inviterName.setText(UserJoin.get(position).getInviterName());
        holder.joinRoomName.setText(UserJoin.get(position).getJoinName());
        holder.joinChallenge.setText(UserJoin.get(position).getJoinChallenge());
        holder.joinStart.setText(UserJoin.get(position).getJoinStart());
        holder.joinEnd.setText(UserJoin.get(position).getJoinEnd());

    }

    @Override
    public int getItemCount() {
        return UserJoin.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView inviterPhoto;
        TextView inviterName;
        TextView joinRoomName;
        TextView joinChallenge;
        TextView joinStart;
        TextView joinEnd;
        Button joinAccept;
        Button joinDenied;

        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);
            itemLayoutView.setOnClickListener(this);
            this.inviterPhoto=(ImageView)itemLayoutView.findViewById(R.id.inviterPhoto);
            this.inviterName=(TextView)itemLayoutView.findViewById(R.id.inviterName);
            this.joinRoomName=(TextView)itemLayoutView.findViewById(R.id.joinRoomName);
            this.joinChallenge=(TextView)itemLayoutView.findViewById(R.id.joinChallenge);
            this.joinStart=(TextView)itemLayoutView.findViewById(R.id.joinStart);
            this.joinEnd=(TextView)itemLayoutView.findViewById(R.id.joinEnd);
            this.joinAccept=(Button)itemLayoutView.findViewById(R.id.joinAccept);;
            this.joinDenied=(Button)itemLayoutView.findViewById(R.id.joinDenied);;

        }

        @Override
        public void onClick(View v) {

        }
    }
}
