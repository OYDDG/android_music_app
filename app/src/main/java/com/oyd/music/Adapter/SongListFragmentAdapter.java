package com.oyd.music.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.oyd.music.Fragment.SongListFragment;

import java.util.ArrayList;

/**
 * Created by oyd on 2018/5/31.
 */

public class SongListFragmentAdapter extends FragmentPagerAdapter {
    private ArrayList<SongListFragment> fragments ;
    private ArrayList<String>title ;
    public SongListFragmentAdapter(FragmentManager fm, ArrayList<SongListFragment> arrayList, ArrayList<String> title) {
        super(fm);
        fragments = arrayList;
        this.title = title;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);

    }



    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //return super.getPageTitle(position);
        return title.get(position);
    }
}
