package com.oyd.music.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.oyd.music.CurrentSongWin;
import com.oyd.music.Manager.MusicManager;
import com.oyd.music.Manager.PlayManager;
import com.oyd.music.R;
import com.oyd.music.Thread.UpdateTimeThread;

/**
 * Created by oyd on 2018/5/27.
 */

public class PlayActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton p_back;
    private Button p_up,p_play,p_next,p_love,p_pattern,p_download,p_songlist;
    private TextView p_song,p_songer,p_contentTime,p_allTime;
    private SeekBar sb;

    private boolean threadIsPause = false;
    private PlayManager pm;

    private UpdateTimeThread updateTimeThread;
    private CurrentSongWin currentSongWin;
    private Handler handler  = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            sb.setProgress(pm.getCurrentTime());
            int currentTime = pm.getCurrentTime() / 1000;//得到总秒数
            int currentMinu = currentTime / 60;//分钟
            Log.i("minu", String.valueOf(currentMinu));
            int currentSec = currentTime % 60;//秒钟
            Log.i("sec", String.valueOf(currentSec));
            p_contentTime.setText(currentMinu+":"+currentSec);
            return false;
        }
    });

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_play);
        pm = PlayManager.getInstance();

        initBtn();
        initTv();
        PlayManager.getInstance().setPlayActivity(this);
        sb = (SeekBar)findViewById(R.id.p_seekbar);
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBar.setProgress(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

                PlayManager.getInstance().setCurrentTime(seekBar.getProgress());
            }
        });

        updateTimeThread = new UpdateTimeThread(handler);
        updateTimeThread.start();
        currentSongWin =new CurrentSongWin(this,PlayActivity.this, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void onResume() {

        updateUI();
        updateTimeThread.setIsPause(false);
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        int id  = v.getId();
        switch (id)
        {
            case R.id.p_back:
                finish();
                break;
            case R.id.p_up:
                pm.up();
                updateUI();
                updateTimeThread.setIsPause(false);
                break;
            case R.id.p_play:
                switch (pm.getState()){
                    case PLAYING:
                        pm.pause();
                        threadIsPause = true;
                        updateTimeThread.setIsPause(true);
                        p_play.setBackgroundResource(R.drawable.icon_play_play);
                        break;
                    case PAUSE:
                        pm.resum();
                        threadIsPause = false;
                        updateTimeThread.setIsPause(false);
                        p_play.setBackgroundResource(R.drawable.icon_play_pause);
                        break;
                    case PREPARE:
                        pm.play();
                        //设置seekbar的总时间 并且重置当前的progress
                        sb.setMax(pm.getAllTime());
                        sb.setProgress(0);

                        //设置总时间
                        int totleTime = pm.getAllTime()/1000;
                        int totleMinu = totleTime/60;
                        int totleSec = totleTime%60;
                        p_allTime.setText(totleMinu+":"+totleSec);
                        p_play.setBackgroundResource(R.drawable.icon_play_pause);
                        break;
                        default:
                            break;
                }
                break;
            case R.id.p_next:
                pm.next();
                updateUI();
                updateTimeThread.setIsPause(false);
                break;
            case R.id.p_love:
                break;
            case R.id.p_pattern:
                switch (PlayManager.getInstance().musicState){
                    case SINGLE:
                        p_pattern.setBackgroundResource(R.drawable.icon_order);
                        PlayManager.getInstance().musicState= PlayManager.MusicState.ORDER;
                        Toast.makeText(this,"顺序播放",Toast.LENGTH_LONG).show();
                        break;
                    case ORDER:
                        p_pattern.setBackgroundResource(R.drawable.icon_random);
                        PlayManager.getInstance().musicState= PlayManager.MusicState.RANDOM;
                        Toast.makeText(this,"随机播放",Toast.LENGTH_LONG).show();
                        break;
                    case RANDOM:
                        p_pattern.setBackgroundResource(R.drawable.icon_single_tune);
                        PlayManager.getInstance().musicState = PlayManager.MusicState.SINGLE;
                        Toast.makeText(this,"单曲循环",Toast.LENGTH_LONG).show();
                        break;
                    default:
                        break;
                }
                break;
            case R.id.p_download:
                break;
            case R.id.p_songlist:

                currentSongWin.showAtLocation(findViewById(R.id.p_songlist), Gravity.BOTTOM,0,0);

                break;
            default:
                break;
        }
    }

    public void updateUI(){
        //设置UI 歌名作者名字
        p_song.setText(MusicManager.getInstance().getCurrentMusicByIndex(pm.index).getSongName());
        p_songer.setText("--"+MusicManager.getInstance().getCurrentMusicByIndex(pm.index).getSonger()+"--");

        //设置播放状态UI
        switch (pm.getState()){
            case PLAYING:
                p_play.setBackgroundResource(R.drawable.icon_play_pause);
                break;
            case PAUSE:
                p_play.setBackgroundResource(R.drawable.icon_play_play);
                break;
            case PREPARE:
                p_play.setBackgroundResource(R.drawable.icon_play_pause);
                break;
            default:
                break;
        }
        //更新timeui
        updateTimeUI();
        //设置seekbar
        sb.setMax(pm.getAllTime());
        sb.setProgress(pm.getCurrentTime());

        //更新播放模式UI
        switch (PlayManager.getInstance().musicState){
            case SINGLE:
                p_pattern.setBackgroundResource(R.drawable.icon_single_tune);
                break;
            case ORDER:
                p_pattern.setBackgroundResource(R.drawable.icon_order);
                break;
            case RANDOM:
                p_pattern.setBackgroundResource(R.drawable.icon_random);
                break;
            default:
                break;
        }
    }
    public void updateTimeUI(){
        //设置当前时间和总时间
        //设置总时间
        int totleTime = pm.getAllTime()/1000;
        int totleMinu = totleTime/60;
        int totleSec = totleTime%60;
        p_allTime.setText(totleMinu+":"+totleSec);
        //设置当前时间
        int currentTime = pm.getCurrentTime() / 1000;//得到总秒数
        int currentMinu = currentTime / 60;//分钟
        int currentSec = currentTime % 60;//秒钟
        p_contentTime.setText(currentMinu+":"+currentSec);

    }


    public void initBtn(){
        p_back = (ImageButton) findViewById(R.id.p_back);
        p_up  = (Button)findViewById(R.id.p_up);
        p_play=(Button)findViewById(R.id.p_play);
        p_next = (Button)findViewById(R.id.p_next);
        p_love =(Button)findViewById(R.id.p_love);
        p_pattern=(Button)findViewById(R.id.p_pattern);
        p_download=(Button)findViewById(R.id.p_download);
        p_songlist=(Button)findViewById(R.id.p_songlist);

        p_back.setOnClickListener(this);
        p_up.setOnClickListener(this);
        p_play.setOnClickListener(this);
        p_next.setOnClickListener(this);
        p_love.setOnClickListener(this);
        p_pattern.setOnClickListener(this);
        p_download.setOnClickListener(this);
        p_songlist.setOnClickListener(this);

    }
    public void initTv(){
        p_song = (TextView) findViewById(R.id.p_song);
        p_songer=(TextView)findViewById(R.id.p_songer);
        p_contentTime=(TextView)findViewById(R.id.p_contentTime);
        p_allTime = (TextView)findViewById(R.id.p_allTime);
    }

    @Override
    protected void onDestroy() {
       currentSongWin.dismiss();
        currentSongWin = null;
        Log.i("PlayActivity","onDestroy");
        super.onDestroy();
    }
}
