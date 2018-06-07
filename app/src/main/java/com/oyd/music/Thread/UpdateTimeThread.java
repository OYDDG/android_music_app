package com.oyd.music.Thread;

import android.os.Handler;

/**
 * Created by oyd on 2018/6/3.
 */

public class UpdateTimeThread extends Thread {
    private boolean isPause ;
    private Handler uiHandler;
    public UpdateTimeThread(Handler uiHandler){
        this.uiHandler=uiHandler;
        isPause =true;
    }
    UpdateTimeThread(Handler uiHandler,boolean isPause){
        this(uiHandler);
        this.isPause = isPause;
    }
    @Override
    public void run() {
        while (true) {
            if (!isPause) {
                uiHandler.sendEmptyMessage(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setIsPause(boolean pause) {
        isPause = pause;
    }
}
