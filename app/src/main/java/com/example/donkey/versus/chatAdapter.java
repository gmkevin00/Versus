package com.example.donkey.versus;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/9/19.
 */
public class chatAdapter extends RecyclerView.Adapter<chatAdapter.ViewHolder> {
    private ArrayList<chat> chatList=new ArrayList();
    private User user;
    private ViewGroup parent;

    public chatAdapter(ArrayList<chat> chatList,User user)
    {
        this.chatList=chatList;
        this.user=user;
    }

    @Override
    public chatAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.parent=parent;
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chatitem, parent, false);
        // set the view's size, margins, paddings and layout parameters

        chatAdapter.ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(chatAdapter.ViewHolder holder, int position) {
        Picasso.with(holder.chatPhoto.getContext()).load(String.format("https://graph.facebook.com/%s/picture",chatList.get(position).getUserid())).into(holder.chatPhoto);
        holder.chatTime.setText("[" + chatList.get(position).getTime() + "]");
        holder.chatContent.setText(chatList.get(position).getContent());
        if(user.getFbid().equals(chatList.get(position).getUserid()))
        {
            holder.chatCard.setBackgroundColor(Color.rgb(203, 224, 254));
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams

            holder.chatCard.gravity = RelativeLayout.ALIGN_PARENT_RIGHT;
        }
        else
        {
            holder.chatCard.setBackgroundColor(Color.rgb(255, 255, 255));
        }
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }

    public  class ViewHolder extends RecyclerView.ViewHolder {
        ImageView chatPhoto;
        TextView chatTime;
        TextView chatContent;
        LinearLayout chatCard;

        public ViewHolder(View itemLayoutView){
            super(itemLayoutView);

            this.chatPhoto=(ImageView)itemLayoutView.findViewById(R.id.chatPhoto);
            this.chatTime=(TextView)itemLayoutView.findViewById(R.id.chatTime);
            this.chatContent=(TextView)itemLayoutView.findViewById(R.id.chatContent);
            this.chatCard=(LinearLayout)itemLayoutView.findViewById(R.id.chatCard);
        }


    }
}
