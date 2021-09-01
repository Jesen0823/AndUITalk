package com.jesen.custom_view.musicalbum.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AudioReceiver extends BroadcastReceiver {

    private ReceiverListener receiverListener;

    public void setReceiverListener(ReceiverListener listener){
        this.receiverListener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        receiverListener.onReceiver(intent);
    }

    interface ReceiverListener{
        void onReceiver(Intent intent);
    }
}
