package com.jesen.animator_talk.animator;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

/**
 * VSYNC信号刷新动画
 * */
public class VSYNCManager {

    private static final VSYNCManager instance = new VSYNCManager();
    private List<AnimationFrameCallback> callbackList = new ArrayList<>();

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            while (true){
                try {
                    // 模拟VSYNC信号
                    Thread.sleep(16);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (AnimationFrameCallback animationFrameCallback:callbackList){
                    animationFrameCallback.doAnimationFrame(System.currentTimeMillis());
                }
            }
        }
    };

    public static VSYNCManager newInstance() {
        return instance;
    }

    private VSYNCManager(){
        new Thread((runnable)).start();
    }

    public void add(AnimationFrameCallback animationFrameCallback){
        callbackList.add(animationFrameCallback);
    }

    interface AnimationFrameCallback{
        boolean doAnimationFrame(long curTime);
    }

}
