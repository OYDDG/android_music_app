package com.oyd.music.Fragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.oyd.music.Activity.PlayActivity;
import com.oyd.music.Adapter.SongAdapter;
import com.oyd.music.Manager.MusicManager;
import com.oyd.music.Manager.PlayManager;
import com.oyd.music.R;
import com.oyd.music.Song;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SongListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SongListFragment extends Fragment implements AdapterView.OnItemClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Context context;

    private ArrayList<Song> list = new ArrayList<>();

    public SongListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SongListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SongListFragment newInstance(String param1, String param2,Context context) {
        SongListFragment fragment = new SongListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        fragment.context = context;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Log.i("Fragment",mParam2+"onCreated");
        switch (mParam1){
            case "LOCALMUSIC":
                initLocalSongData();
                break;
            case "DOWNLOADMUSIC":
                break;
            case "FAVORITEMUSIC":
                break;
            default:
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_fragment_song_list, container, false);
        ListView lv = (ListView) view.findViewById(R.id.listview);
        lv.setAdapter(new SongAdapter(getContext(),R.layout.layout_song_information,list));
        lv.setOnItemClickListener(this);
        return view;
    }
    public void initLocalSongData(){

        list = MusicManager.getInstance().getLoaclMusic();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        MusicManager.getInstance().setCurrentMusic(MusicManager.getInstance().getLoaclMusic());
        Log.i("currentSize", String.valueOf(MusicManager.getInstance().getCurrentMusicAllNum()));
        PlayManager.getInstance().reset();
        PlayManager.getInstance().index = position;

        PlayManager.getInstance().play();

        Intent intent = new Intent(context, PlayActivity.class);
        startActivity(intent);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.i("Fragment",mParam2+"onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.i("Fragment",mParam2+"onDetach");
    }
}
