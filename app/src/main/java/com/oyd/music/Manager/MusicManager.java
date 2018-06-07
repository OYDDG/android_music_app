package com.oyd.music.Manager;

import android.os.Environment;
import android.util.Log;

import com.oyd.music.Song;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by oyd on 2018/5/27.
 * 功能：
 * 1、管理歌曲，包括本地音乐，下载的音乐，收藏音乐，播放列表的音乐
 */

public class MusicManager {

    private ArrayList<Song> localMusic = new ArrayList<>();
   // private ArrayList<File> downloadMusic = new ArrayList<>();
  //  private ArrayList<File> favoriteMusic = new ArrayList<>();
    private ArrayList<Song> currentMusic = new ArrayList<>();

    private static MusicManager mm;

    private MusicManager(){

            initLocalMusic();
            //currentMusic = (ArrayList<Song>) localMusic.clone();

    }

    public static MusicManager getInstance(){
       if (mm ==null){
           mm = new MusicManager();
       }

        return mm;
    }
    public ArrayList<Song> getLoaclMusic(){
        return (ArrayList<Song>) localMusic.clone();
    }


    public void initLocalMusic(){
        File file = new File(Environment.getExternalStorageDirectory()+"/Music");
        File [] files = file.listFiles();
        Log.i("files:", String.valueOf(files.length));
        for (int i = 0;i<files.length;++i){
            localMusic.add(new Song(files[i]));
        }
    }

    public Song getCurrentMusicByIndex(int i){
        return currentMusic.get(i);
    }

    public int getCurrentMusicAllNum(){
        return currentMusic.size();
    }

    public ArrayList<Song> getCurrentMusic(){
        return currentMusic;
    }


    public void setCurrentMusic(ArrayList<Song> currentMusic) {
        this.currentMusic = currentMusic;
    }
}
