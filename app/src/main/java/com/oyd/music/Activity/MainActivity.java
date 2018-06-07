package com.oyd.music.Activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oyd.music.Manager.MusicManager;
import com.oyd.music.Manager.PlayManager;
import com.oyd.music.R;

import static com.oyd.music.R.id.song;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView songName,songer;
    private ImageButton play,up,next;
    private ImageView pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //动态申请权限
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);
        //获取tv实例
        getTextViewInstance();
        //获取btn实例
        getBtnJInstance();
        //获取其他view实例
        getOtherViewInstance();
        //为控件设置监听事件
        setUIListener();

        PlayManager.getInstance().setMainActivity(this);

        ImageButton im = (ImageButton)findViewById(R.id.m_my);

        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(MainActivity.this,SongListActivity.class);
               startActivity(intent);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.pic:
            case R.id.song:
            case R.id.songer:
                //判断播放列表是否有歌曲
                if (MusicManager.getInstance().getCurrentMusicAllNum()!=0) {
                    Intent intent = new Intent(this, PlayActivity.class);
                    startActivity(intent);
                }
                else {
                    Toast.makeText(this,"播放列表无歌曲",Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.play:
                //判断播放列表是否有歌曲
                if (MusicManager.getInstance().getCurrentMusicAllNum()!=0) {
                    switch (PlayManager.getInstance().getState()){
                        case PLAYING:
                            PlayManager.getInstance().pause();
                            play.setBackgroundResource(R.drawable.icon_play);
                            break;
                        case PAUSE:
                            PlayManager.getInstance().resum();
                            play.setBackgroundResource(R.drawable.icon_pause);
                            break;
                        case PREPARE:
                            PlayManager.getInstance().play();
                            play.setBackgroundResource(R.drawable.icon_pause);
                            break;
                        default:
                            break;
                    }
                }
                else {
                    Toast.makeText(this,"播放列表无歌曲",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.next:
                //判断播放列表是否有歌曲
                if (MusicManager.getInstance().getCurrentMusicAllNum()!=0) {
                        PlayManager.getInstance().next();
                        updateUI();
                }
                else {
                    Toast.makeText(this,"播放列表无歌曲",Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.up:
                //判断播放列表是否有歌曲
                if (MusicManager.getInstance().getCurrentMusicAllNum()!=0) {
                        PlayManager.getInstance().up();
                        updateUI();
                }
                else {
                    Toast.makeText(this,"播放列表无歌曲",Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
    }

    public void updateUI(){
            //如果当前播放列表没有音乐
            if (MusicManager.getInstance().getCurrentMusicAllNum()==0){
                songName.setText("歌曲");
                songer.setText("作者");
                play.setBackgroundResource(R.drawable.icon_play);

            }
       else {
                songName.setText(MusicManager.getInstance().getCurrentMusicByIndex(PlayManager.getInstance().index).getSongName());
                songer.setText(MusicManager.getInstance().getCurrentMusicByIndex(PlayManager.getInstance().index).getSonger());
                //设置播放状态UI
                switch (PlayManager.getInstance().getState()){
                    case PLAYING:
                        play.setBackgroundResource(R.drawable.icon_pause);
                        break;
                    case PAUSE:
                        play.setBackgroundResource(R.drawable.icon_play);
                        break;
                    case PREPARE:
                        play.setBackgroundResource(R.drawable.icon_pause);
                        break;
                    default:
                        break;
                }
            }
    }

    public void getTextViewInstance(){
        songName = (TextView)findViewById(song);
        songer = (TextView)findViewById(R.id.songer);
    }
    public void getBtnJInstance(){
        play = (ImageButton)findViewById(R.id.play);
        next = (ImageButton)findViewById(R.id.next);
        up = (ImageButton)findViewById(R.id.up);
    }
    public void getOtherViewInstance(){
     pic = (ImageView)findViewById(R.id.pic);
    }
    public void setUIListener(){
            songName.setOnClickListener(this);
            songer.setOnClickListener(this);
            play.setOnClickListener(this);
            next.setOnClickListener(this);
            up.setOnClickListener(this);
            pic.setOnClickListener(this);
    }
}
