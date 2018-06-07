package com.oyd.music.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

/**
 * Created by oyd on 2018/6/2.
 */

public class SongAnalysis {

    public static String getSongName(File file){

        byte []buff = getMP3Id3v1Data(file);
        if (buff.length!=128) return null;
        try {
            return new String(buff,3,30,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;

    }
    public static String getSonger(File file){


            byte [] buff = getMP3Id3v1Data(file);


            if (buff.length!=128) return null;
        try {
            return new String(buff,33,30,"GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static byte[] getMP3Id3v1Data(File file){
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(file,"r");
            randomAccessFile.seek(randomAccessFile.length()-128);
            byte [] buff = new byte[128];
            randomAccessFile.read(buff);
            randomAccessFile.close();

           return buff;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
