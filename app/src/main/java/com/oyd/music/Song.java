package com.oyd.music;

import com.oyd.music.Util.SongAnalysis;

import java.io.File;

/**
 * Created by oyd on 2018/5/29.
 */

public class Song {
    private String songName;
    private String songer;
    private File path;
    public Song(File path){
       this.path = path;
        songer = SongAnalysis.getSonger(path);
        songName = SongAnalysis.getSongName(path);
    }

    public String getSongName() {
        return songName;
    }


    public String getSonger() {
        return songer;
    }

    public File getPath(){
        return path;
    }
}
