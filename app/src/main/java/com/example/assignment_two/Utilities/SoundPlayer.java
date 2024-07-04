package com.example.assignment_two.Utilities;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SoundPlayer {

    private Context context;
    private Executor executor;
    private MediaPlayer backgroundPlayer;
    private MediaPlayer crashPlayer;

    private MediaPlayer coinPlayer;


    public SoundPlayer(Context context){
        this.context = context;
        this.executor = Executors.newSingleThreadExecutor();
    }

    public void playBackground(int resID){
        if(backgroundPlayer == null){
            executor.execute(() -> {
                backgroundPlayer = MediaPlayer.create(context,resID);
                backgroundPlayer.setLooping(true);
                backgroundPlayer.setVolume(0.4f,0.4f);
                backgroundPlayer.start();
            });
        }
    }

    public void playCrashSound(int resID){
        if(crashPlayer == null){
            executor.execute(() -> {
                crashPlayer = MediaPlayer.create(context,resID);
                crashPlayer.setLooping(false);
                crashPlayer.setVolume(1.0f,1.0f);
                crashPlayer.start();
            });
        }else
            crashPlayer.start();
    }

    public void playCoinSound(int resID){
        if(coinPlayer == null){
            executor.execute(() -> {
                coinPlayer = MediaPlayer.create(context,resID);
                coinPlayer.setLooping(false);
                coinPlayer.setVolume(1.0f,1.0f);
                coinPlayer.start();
            });
        }else
            coinPlayer.start();
    }


    public void stopSound(){
        if(backgroundPlayer != null){
            executor.execute(() -> {
                backgroundPlayer.stop();
                backgroundPlayer.release();
                backgroundPlayer = null;
            });
        }

        if(crashPlayer != null){
            executor.execute(() -> {
                crashPlayer.stop();
                crashPlayer.release();
                crashPlayer = null;
            });
        }

        if(coinPlayer != null){
            executor.execute(() -> {
                coinPlayer.stop();
                coinPlayer.release();
                coinPlayer = null;
            });
        }
    }
}
