package com.jesen.custom_view.musicalbum.model;

import java.io.Serializable;

public class AudioData implements Serializable {

    private int audioResId;
    private int audioPicRes;
    private String audioName;
    private String audioAuthor;

    public AudioData(int audioResId, int audioPicRes, String audioName, String audioAuthor) {
        this.audioResId = audioResId;
        this.audioPicRes = audioPicRes;
        this.audioName = audioName;
        this.audioAuthor = audioAuthor;
    }

    public int getAudioResId() {
        return audioResId;
    }

    public int getAudioPicRes() {
        return audioPicRes;
    }

    public String getAudioName() {
        return audioName;
    }

    public String getAudioAuthor() {
        return audioAuthor;
    }
}
