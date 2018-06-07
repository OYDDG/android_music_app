package com.oyd.music.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.oyd.music.R;
import com.oyd.music.Song;

import java.util.ArrayList;

/**
 * Created by oyd on 2018/6/2.
 */

public class CurrentSongWinAdapter extends ArrayAdapter<Song> implements View.OnClickListener{
    private int resource;
    private DeleteCallback deleteCallback;
    private int position;
    private int selectItem=-1;
    private ArrayList<Song> data ;
    private LayoutInflater mInflater;
    public CurrentSongWinAdapter(Context context, int resource,ArrayList<Song> data) {
        super(context, resource,data);
        mInflater = LayoutInflater.from(context);
        this.resource = resource;
        this.data = data;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView ==null){
            convertView = mInflater.inflate(resource,parent,false);
                       holder = new ViewHolder();
                     holder.songName = (TextView) convertView.findViewById(R.id.cslv_song);
                    holder.songer = (TextView)convertView.findViewById(R.id.cslv_songer);
                    holder.button = (Button)convertView.findViewById(R.id.cslv_btn);
                     convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.songName.setText(data.get(position).getSongName());
        holder.songer.setText(data.get(position).getSonger());
        holder.button.setOnClickListener(this);
        holder.button.setTag(position);
        if (selectItem==position){
            convertView.setBackgroundColor(Color.YELLOW);
        }else convertView.setBackgroundColor(Color.WHITE);
        return convertView;

/*
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        TextView songName = (TextView)view.findViewById(R.id.cslv_song);
        TextView songer = (TextView)view.findViewById(R.id.cslv_songer);
        songName.setText(data.get(position).getSongName());
        songer.setText(data.get(position).getSonger());
        this.position = position;
        Button del = (Button) view.findViewById(R.id.cslv_btn);
        del.setOnClickListener(this);

        if (selectItem==this.position){
           view.setBackgroundColor(Color.YELLOW);
        }
        //注册按钮监听事件
        return view;
        */
    }

    public void setData(ArrayList<Song> data){
        this.data = data;
    }

    @Override
    public void onClick(View v) {
        Log.i("button tag", String.valueOf(v.getTag()));
        deleteCallback.deletePosition((Integer) v.getTag());
    }

    public interface DeleteCallback{
        void deletePosition(int position);
    }

    public void setDeleteCallback(DeleteCallback deleteCallback){
        this.deleteCallback = deleteCallback;
    }
    public void setSelectItem(int item){
        this.selectItem = item;
    }

    public class ViewHolder{
        public TextView songName;
        public TextView songer;
        public Button button;
    }
}
