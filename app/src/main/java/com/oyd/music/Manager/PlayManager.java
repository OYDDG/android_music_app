package com.oyd.music.Manager;

import android.media.MediaPlayer;

import com.oyd.music.Activity.MainActivity;
import com.oyd.music.Activity.PlayActivity;
import com.oyd.music.Adapter.CurrentSongWinAdapter;

import java.io.IOException;
import java.util.Random;

/**
 * Created by oyd on 2018/5/27.
 * 功能：播放管理
 */

public class PlayManager implements MediaPlayer.OnCompletionListener{
    private MediaPlayer mp;
    public int index;
    public State playState;
    public MusicState musicState;

    private MainActivity mainActivity;
    private PlayActivity playActivity;
    private CurrentSongWinAdapter currentSongWinAdapter;

    public static PlayManager instance;
    public static PlayManager getInstance(){
        if (instance==null) instance = new PlayManager();
        return instance;
    }
    private PlayManager(){

        mp = new MediaPlayer();
        mp.setOnCompletionListener(this);
        index = 0;
        playState = State.PREPARE;
        musicState = MusicState.ORDER;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        switch (musicState){
            case SINGLE:
                reset();
                play();
                break;
            case ORDER:
                next();
                break;
            case RANDOM:
                int num = MusicManager.getInstance().getCurrentMusicAllNum();
                Random random = new Random();
                index =  random.nextInt(num);
                reset();
                play();
                break;
            default:
                break;
        }
        if (playActivity!=null) playActivity.updateUI();
        if (mainActivity!=null)mainActivity.updateUI();
        if (currentSongWinAdapter!=null){
            currentSongWinAdapter.setSelectItem(index);
            currentSongWinAdapter.notifyDataSetChanged();
        }
    }

    public  enum State {
        PLAYING,
        PAUSE,
        PREPARE,
        STOP,
    }

    public enum MusicState{
        SINGLE,//单曲循环
        ORDER,//顺序循环
        RANDOM,//随机循环
    }
    public void play(){
        //本地存在的文件播放
        try {
            mp.setDataSource(MusicManager.getInstance().getCurrentMusicByIndex(index).getPath().toString());
            mp.prepare();
            mp.start();
            playState = State.PLAYING;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void pause(){
        mp.pause();
        playState = State.PAUSE;
    }
    public void resum(){
        mp.start();
    playState = State.PLAYING;
    }
    public void up(){
        --index;
        if (index<0) index = MusicManager.getInstance().getCurrentMusicAllNum()-1;
        reset();
        play();
    }

    public void next(){
        ++index;
        if (index>MusicManager.getInstance().getCurrentMusicAllNum()-1) index = 0;
        reset();
        play();
    }
    public void stop(){
            mp.stop();
    }
    public void reset(){
        mp.reset();
        playState = State.PREPARE;
    }
    public boolean isPlaying(){
        return mp.isPlaying();
    }
    public int getCurrentTime(){
    return mp.getCurrentPosition();
    }

    public int getAllTime(){
        return mp.getDuration();
    }

    public State getState(){
        return playState;
    }

    public void setCurrentTime(int time ){
        mp.seekTo(time);
    }

   public void setMainActivity(MainActivity mainActivity){
       this.mainActivity = mainActivity;
   }
    public void setPlayActivity(PlayActivity playActivity){
        this.playActivity = playActivity;
    }
    public void setCurrentSongWinAdapter(CurrentSongWinAdapter c){
        this.currentSongWinAdapter=c ;
    }
}
