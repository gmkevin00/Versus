package com.example.donkey.versus;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by donkey on 2015/7/27.
 */
public class inviteFriendAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<String> friendId;
    private ArrayList<String> friendName;
    private HashMap<Integer, Boolean> isSelected=new HashMap<Integer, Boolean>();

    public inviteFriendAdapter(Context context,User user){
        inflater = LayoutInflater.from(context);
        friendId=user.getUser_friendListId();
        friendName=user.getUser_friendListName();
    }
    @Override
    public int getCount() {
        return friendId.size();
    }

    @Override
    public Object getItem(int arg0) {
        return friendId.get(arg0);
    }

    @Override
    public long getItemId(int position) {
        return friendId.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.invitefriendlist_item, null);
            holder = new ViewHolder(
                    (ImageView) convertView.findViewById(R.id.inviteFriendPhoto),
                    (TextView) convertView.findViewById(R.id.inviteFriendId),
                    (TextView) convertView.findViewById(R.id.inviteFriendName),
                    (CheckBox) convertView.findViewById(R.id.checkbox)
            );
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        String id=(String)getItem(position);
        String name=(String)friendName.get(position);
        Picasso.with(parent.getContext()).load(String.format("https://graph.facebook.com/%s/picture",id)).into(holder.photo);
        holder.name.setText(name);
        holder.checkbox.setChecked(getIsSelected().get(position));
        return convertView;
    }

    private class ViewHolder {
        ImageView photo;
        TextView id;
        TextView name;
        CheckBox checkbox;
        public ViewHolder(ImageView photo,TextView id,TextView name,CheckBox checkbox){
            this.photo=photo;
            this.id=id;
            this.name=name;
            this.checkbox=checkbox;
        }
    }

    public HashMap<Integer,Boolean> getIsSelected(){
        return isSelected;
    }
    public  void setIsSelected(HashMap<Integer, Boolean> isSelected){
        this.isSelected=isSelected;
    }
    public boolean selectItem(View view){
        ViewHolder holder=(ViewHolder)view.getTag();
        holder.checkbox.toggle();
        return holder.checkbox.isChecked();
    }


}
