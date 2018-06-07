package com.oyd.music.Activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;

import com.oyd.music.Adapter.SongListFragmentAdapter;
import com.oyd.music.Fragment.SongListFragment;
import com.oyd.music.Manager.MusicManager;
import com.oyd.music.R;

import java.util.ArrayList;

/**
 * Created by oyd on 2018/5/31.
 */

public class SongListActivity extends AppCompatActivity {
    private ArrayList<SongListFragment> fragments;
    private ArrayList<String> tabTitle;

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_song_view);
        MusicManager.getInstance().setCurrentMusic(MusicManager.getInstance().getLoaclMusic());


        tabLayout = (TabLayout)findViewById(R.id.tablayout);
        viewPager = (ViewPager)findViewById(R.id.viewpage);

        initData();

        viewPager.setAdapter(new SongListFragmentAdapter(getSupportFragmentManager(),fragments,tabTitle));

        tabLayout.setupWithViewPager(viewPager);
    }

    public void initData(){
        initTitle();
        initFragments();
    }
    public void initTitle(){
        tabTitle = new ArrayList<>();
        tabTitle.add("本地音乐");
        tabTitle.add("收藏音乐");
        tabTitle.add("下载音乐");
    }
    public void initFragments(){
        fragments = new ArrayList<>();
        fragments.add(SongListFragment.newInstance("LOCALMUSIC","111",this));
        fragments.add(SongListFragment.newInstance("222","222",this));
        fragments.add(SongListFragment.newInstance("333","333",this));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            Log.i("songlistactivity","finish down");
        }
        return true;
    }
}
