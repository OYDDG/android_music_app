package com.oyd.music.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.oyd.music.R;
import com.oyd.music.Song;

import java.util.List;

/**
 * Created by oyd on 2018/5/31.
 */

public class SongAdapter extends ArrayAdapter<Song> {
    private int resource;

    public SongAdapter(Context context, int resource, List<Song> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Song song = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resource,parent,false);
        TextView songName = (TextView) view.findViewById(R.id.l_song_name);
        TextView songer = (TextView) view.findViewById(R.id.l_songer);
        songName.setText(song.getSongName());
        songer.setText(song.getSonger());
        return view;
    }


}
