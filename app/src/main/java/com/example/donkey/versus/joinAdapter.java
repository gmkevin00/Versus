package com.example.donkey.versus;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/8/23.
 */
public class joinAdapter extends RecyclerView.Adapter<joinAdapter.ViewHolder> {
     static ArrayList<Join> UserJoin=new ArrayList<>();
    private ViewGroup parent;
    private User user;

    public joinAdapter(User user,ArrayList<Join> UserJoin)
    {
        this.user=user;
        this.UserJoin=UserJoin;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.joinitem, parent, false);
        // set the view's size, margins, paddings and layout parameters

       joinAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Picasso.with(holder.inviterPhoto.getContext()).load(String.format("https://graph.facebook.com/%s/picture",UserJoin.get(position).getInviterId())).into(holder.inviterPhoto);
        holder.inviterName.setText(UserJoin.get(position).getInviterName()+" 邀請了你");
        holder.joinRoomName.setText(UserJoin.get(position).getJoinName());
        holder.joinChallenge.setText(UserJoin.get(position).getJoinChallenge());
       holder.joinStart.setText(UserJoin.get(position).getJoinStart());
        holder.joinEnd.setText(UserJoin.get(position).getJoinEnd());

    }

    @Override
    public int getItemCount() {
        return UserJoin.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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

            this.inviterPhoto=(ImageView)itemLayoutView.findViewById(R.id.inviterPhoto);
            this.inviterName=(TextView)itemLayoutView.findViewById(R.id.inviterName);
            this.joinRoomName=(TextView)itemLayoutView.findViewById(R.id.joinRoomName);
            this.joinChallenge=(TextView)itemLayoutView.findViewById(R.id.joinChallenge);
            this.joinStart=(TextView)itemLayoutView.findViewById(R.id.joinStart);
            this.joinEnd=(TextView)itemLayoutView.findViewById(R.id.joinEnd);
            this.joinAccept=(Button)itemLayoutView.findViewById(R.id.joinAccept);;
            this.joinDenied=(Button)itemLayoutView.findViewById(R.id.joinDenied);;
            this.joinAccept.setOnClickListener(this);
            this.joinDenied.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position=getPosition();
            phpConnect p=new phpConnect(parent.getContext(),"讀取資料中,請稍後...");
            p.setUrl(String.format("http://140.115.80.235/~group15/join.php"));
            if(v.getId()==R.id.joinAccept)
            {
                p.addSendData("type", "accept");
            }
            else if(v.getId()==R.id.joinDenied)
            {
                p.addSendData("type", "denied");
            }
            p.addSendData("user_id", "" + user.getFbid());
            p.addSendData("room_id", ""+UserJoin.get(position).getJoinRoomId());
            p.execute(new GetUserCallback() {
                @Override
                public void done(JSONArray jsonarray) {
                    ;
                }
            });

            removeAt(position);
        }
    }
    public void removeAt(int position) {
        UserJoin.remove(position);
        notifyItemRemoved(position);
    }


}
