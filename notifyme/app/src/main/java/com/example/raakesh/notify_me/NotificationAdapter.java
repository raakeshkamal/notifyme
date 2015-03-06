package com.example.raakesh.notify_me;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by raakesh on 16/1/15.
 */
public class NotificationAdapter extends BaseAdapter{
    public ArrayList<Notification> posts;
    public LayoutInflater notiinflater;
    int i;
    public NotificationAdapter(Context c,ArrayList<Notification> posts,int i){
        this.posts=posts;
        this.i=i;
        notiinflater=LayoutInflater.from(c);
    }

    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder mViewHolder;
        View view;
        if(convertView == null) {
            view = notiinflater.inflate(R.layout.list_item_layout, null);
            mViewHolder = new MyViewHolder();
            mViewHolder.avatar=(ImageView)view.findViewById(R.id.noti_icon);
            mViewHolder.title=(TextView)view.findViewById(R.id.menu_item_noti_title);
            mViewHolder.author=(TextView)view.findViewById(R.id.menu_item_noti_author);
            view.setTag(mViewHolder);
        } else {
            view=convertView;
            mViewHolder = (MyViewHolder) view.getTag();
        }
  /*      LinearLayout notilayout=(LinearLayout)notiinflater.inflate(R.layout.list_item_layout,parent,false);
        TextView titleView  = (TextView)notilayout.findViewById(R.id.menu_item_noti_title);*/
        Notification noti=posts.get(position);
        String title=noti.getTitle();
        String author=noti.getAuthor();
        if(title.isEmpty())
            mViewHolder.title.setText("<unknown>");
        else
        mViewHolder.title.setText(title);
      //  Log.i("bitch",author);
        if(author.isEmpty())
        mViewHolder.author.setText("<unknown>");
        else
        mViewHolder.author.setText(author);

        mViewHolder.avatar.setImageResource(i);
        //notilayout.setTag(position);
        return view;
    }
    private class MyViewHolder {
        public ImageView avatar;
        public TextView title;
        public TextView author;
    }
}
