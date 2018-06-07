package com.oyd.music;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;

import com.oyd.music.Activity.PlayActivity;
import com.oyd.music.Adapter.CurrentSongWinAdapter;
import com.oyd.music.Manager.MusicManager;
import com.oyd.music.Manager.PlayManager;

import java.util.ArrayList;

/**
 * Created by oyd on 2018/6/2.
 */

public class CurrentSongWin extends PopupWindow implements CurrentSongWinAdapter.DeleteCallback,AdapterView.OnItemClickListener{
    private Context context;
    private ListView lv;
    private CurrentSongWinAdapter currentSongWinAdapter;
    private ArrayList<Song> data;
    private PlayActivity playActivity;
    public CurrentSongWin(Context context){
        this.context = context;
    }
    public CurrentSongWin(PlayActivity playActivity,Context context,int width,int height){
        this.playActivity = playActivity;
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.layout_currentsong,null);
        lv = (ListView)view.findViewById(R.id.cs_lv);
        data = MusicManager.getInstance().getCurrentMusic();

        currentSongWinAdapter = new CurrentSongWinAdapter(context,R.layout.layout_currentsong_list_view,data);
        PlayManager.getInstance().setCurrentSongWinAdapter(currentSongWinAdapter);
        currentSongWinAdapter.setSelectItem(PlayManager.getInstance().index);//设置当前选中的选项
        currentSongWinAdapter.setDeleteCallback(this);
        lv.setAdapter(currentSongWinAdapter);//为listview设置适配器
        lv.setOnItemClickListener(this);//设置点击某项的回调方法
        lv.setFocusable(true);

        Button close = (Button)view.findViewById(R.id.cs_close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        setOutsideTouchable(true);//设置外部可以点击
        setFocusable(false);//设置弹出窗体可以点击
        setTouchable(true);
        setBackgroundDrawable(new BitmapDrawable());
        setAnimationStyle(R.style.take_photo_anim);//设置弹出窗体显示动画，从底部向上弹出
        setWidth(width);
        setHeight(height);
        setContentView(view);
    }


    @Override
    public void deletePosition(int position) {
        //删除的是否是当前播放的歌曲
        if (position==PlayManager.getInstance().index){
            //是否是最后一首歌
            if (MusicManager.getInstance().getCurrentMusicAllNum()==1){
                   playActivity.finish();
                    PlayManager.getInstance().stop();
                    MusicManager.getInstance().setCurrentMusic(null);
            }
            else {

                data.remove(position);
                PlayManager.getInstance().reset();
                PlayManager.getInstance().play();
                currentSongWinAdapter.setSelectItem(PlayManager.getInstance().index);
                currentSongWinAdapter.notifyDataSetChanged();
                playActivity.updateUI();
            }
        }
        else{


            Song cs = MusicManager.getInstance().getCurrentMusicByIndex(PlayManager.getInstance().index);
            //删除该项数据并更新
            data.remove(position);
            for (int i =0;i<MusicManager.getInstance().getCurrentMusic().size();i++){
                Song song = MusicManager.getInstance().getCurrentMusicByIndex(i);
                if (cs.getPath().toString().equals(song.getPath().toString())) {
                    currentSongWinAdapter.setSelectItem(i);
                    PlayManager.getInstance().index=i;
                    break;
                }
            }
            currentSongWinAdapter.notifyDataSetChanged();

        }


    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        currentSongWinAdapter.setSelectItem(position);
        currentSongWinAdapter.notifyDataSetChanged();
        PlayManager.getInstance().reset();
        PlayManager.getInstance().index=position;
        PlayManager.getInstance().play();

        playActivity.updateUI();
    }


}
