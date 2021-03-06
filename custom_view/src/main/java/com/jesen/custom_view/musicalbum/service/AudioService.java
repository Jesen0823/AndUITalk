package com.jesen.custom_view.musicalbum.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.jesen.custom_view.musicalbum.MusicActivity;
import com.jesen.custom_view.musicalbum.model.AudioData;

import java.util.ArrayList;
import java.util.List;

public class AudioService extends Service implements MediaPlayer.OnCompletionListener, AudioReceiver.ReceiverListener {

    /*操作指令*/
    public static final String ACTION_OPT_MUSIC_PLAY = "ACTION_OPT_MUSIC_PLAY";
    public static final String ACTION_OPT_MUSIC_PAUSE = "ACTION_OPT_MUSIC_PAUSE";
    public static final String ACTION_OPT_MUSIC_NEXT = "ACTION_OPT_MUSIC_NEXT";
    public static final String ACTION_OPT_MUSIC_LAST = "ACTION_OPT_MUSIC_LAST";
    public static final String ACTION_OPT_MUSIC_SEEK_TO = "ACTION_OPT_MUSIC_SEEK_TO";

    /*状态指令*/
    public static final String ACTION_STATUS_MUSIC_PLAY = "ACTION_STATUS_MUSIC_PLAY";
    public static final String ACTION_STATUS_MUSIC_PAUSE = "ACTION_STATUS_MUSIC_PAUSE";
    public static final String ACTION_STATUS_MUSIC_COMPLETE = "ACTION_STATUS_MUSIC_COMPLETE";
    public static final String ACTION_STATUS_MUSIC_DURATION = "ACTION_STATUS_MUSIC_DURATION";

    public static final String PARAM_MUSIC_DURATION = "PARAM_MUSIC_DURATION";
    public static final String PARAM_MUSIC_SEEK_TO = "PARAM_MUSIC_SEEK_TO";
    public static final String PARAM_MUSIC_CURRENT_POSITION = "PARAM_MUSIC_CURRENT_POSITION";
    public static final String PARAM_MUSIC_IS_OVER = "PARAM_MUSIC_IS_OVER";

    private int mCurrentMusicIndex = 0;
    private boolean mIsMusicPause = false;
    private List<AudioData> mAudioDatas = new ArrayList<>();

    private AudioReceiver mAudioReceiver = new AudioReceiver();
    private MediaPlayer mMediaPlayer = new MediaPlayer();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        initAudioDatas(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBoardCastReceiver();
    }

    private void initAudioDatas(Intent intent) {
        if (intent == null) return;
        List<AudioData> AudioDatas = (List<AudioData>) intent.getSerializableExtra(MusicActivity.PARAM_MUSIC_LIST);
        mAudioDatas.addAll(AudioDatas);
    }

    private void initBoardCastReceiver() {
        IntentFilter intentFilter = new IntentFilter();

        intentFilter.addAction(ACTION_OPT_MUSIC_PLAY);
        intentFilter.addAction(ACTION_OPT_MUSIC_PAUSE);
        intentFilter.addAction(ACTION_OPT_MUSIC_NEXT);
        intentFilter.addAction(ACTION_OPT_MUSIC_LAST);
        intentFilter.addAction(ACTION_OPT_MUSIC_SEEK_TO);

        LocalBroadcastManager.getInstance(this).registerReceiver(mAudioReceiver,intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.release();
        mMediaPlayer = null;
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mAudioReceiver);
    }

    private void play(final int index) {
        if (index >= mAudioDatas.size()) return;
        if (mCurrentMusicIndex == index && mIsMusicPause) {
            mMediaPlayer.start();
        } else {
            mMediaPlayer.stop();
            mMediaPlayer = null;

            mMediaPlayer = MediaPlayer.create(getApplicationContext(), mAudioDatas.get(index)
                    .getAudioResId());
            mMediaPlayer.start();
            mMediaPlayer.setOnCompletionListener(this);
            mCurrentMusicIndex = index;
            mIsMusicPause = false;

            int duration = mMediaPlayer.getDuration();
            sendMusicDurationBroadCast(duration);
        }
        sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PLAY);
    }

    private void pause() {
        mMediaPlayer.pause();
        mIsMusicPause = true;
        sendMusicStatusBroadCast(ACTION_STATUS_MUSIC_PAUSE);
    }

    private void stop() {
        mMediaPlayer.stop();
    }

    private void next() {
        if (mCurrentMusicIndex + 1 < mAudioDatas.size()) {
            play(mCurrentMusicIndex + 1);
        } else {
            stop();
        }
    }

    private void last() {
        if (mCurrentMusicIndex != 0) {
            play(mCurrentMusicIndex - 1);
        }
    }

    private void seekTo(Intent intent) {
        if (mMediaPlayer.isPlaying()) {
            int position = intent.getIntExtra(PARAM_MUSIC_SEEK_TO, 0);
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        sendMusicCompleteBroadCast();
    }

    private void sendMusicCompleteBroadCast() {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_COMPLETE);
        intent.putExtra(PARAM_MUSIC_IS_OVER, (mCurrentMusicIndex == mAudioDatas.size() - 1));
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMusicDurationBroadCast(int duration) {
        Intent intent = new Intent(ACTION_STATUS_MUSIC_DURATION);
        intent.putExtra(PARAM_MUSIC_DURATION, duration);
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    private void sendMusicStatusBroadCast(String action) {
        Intent intent = new Intent(action);
        if (action.equals(ACTION_STATUS_MUSIC_PLAY)) {
            intent.putExtra(PARAM_MUSIC_CURRENT_POSITION,mMediaPlayer.getCurrentPosition());
        }
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public void onReceiver(Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_OPT_MUSIC_PLAY)) {
            play(mCurrentMusicIndex);
        } else if (action.equals(ACTION_OPT_MUSIC_PAUSE)) {
            pause();
        } else if (action.equals(ACTION_OPT_MUSIC_LAST)) {
            last();
        } else if (action.equals(ACTION_OPT_MUSIC_NEXT)) {
            next();
        } else if (action.equals(ACTION_OPT_MUSIC_SEEK_TO)) {
            seekTo(intent);
        }
    }
}
