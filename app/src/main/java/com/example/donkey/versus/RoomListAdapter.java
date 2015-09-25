package com.example.donkey.versus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by donkey on 2015/7/27.
 */
public class RoomListAdapter extends BaseAdapter {
    private int[] cateImgRes = {R.drawable.happying, R.drawable.studying ,R.drawable.sporting};

    private LayoutInflater inflater;
    private ArrayList<Room> RoomUser;
    public RoomListAdapter(Context context,ArrayList<Room> RoomUser){
        inflater = LayoutInflater.from(context);
        this.RoomUser=RoomUser;
    }
    @Override
    public int getCount() {
        return RoomUser.size();
    }

    @Override
    public Object getItem(int arg0) {
        return RoomUser.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return RoomUser.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.roomlist_item, null);
            holder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.imageView),
                    (TextView) convertView.findViewById(R.id.name),
                    (TextView) convertView.findViewById(R.id.start),
                    (TextView) convertView.findViewById(R.id.end)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Room r=(Room)getItem(position);
        holder.photo.setImageResource(cateImgRes[r.getTypeId()-1]);
        holder.name.setText(r.getRoomName());
        holder.start.setText(r.getRoomStart());
        holder.end.setText(r.getRoomEnd());
        return convertView;
    }

    private class ViewHolder {
        ImageView photo;
        TextView name;
        TextView start;
        TextView end;
        public ViewHolder(ImageView photo, TextView name, TextView start, TextView end){
            this.photo=photo;
            this.name=name;
            this.start=start;
            this.end=end;
        }
    }

    public void updateResults(ArrayList<Room> RoomUser) {
        this.RoomUser=RoomUser;
        //Triggers the list update
        notifyDataSetChanged();
    }
}
