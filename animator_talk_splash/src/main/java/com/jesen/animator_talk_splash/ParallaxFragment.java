package com.jesen.animator_talk_splash;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class ParallaxFragment extends Fragment {

    // 所有需要实现视差动画的视图
    private List<View> parallaxViews = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int layoutId = bundle.getInt("layoutId");
        ParallaxLayoutInflater parallaxLayoutInflater = new ParallaxLayoutInflater(inflater, getActivity(),this);
        // 由系统的inflater去解析xml布局，我们自定义inflater
        // 是为了拿到解析完的回调Factory接口
        return parallaxLayoutInflater.inflate(layoutId,null);
    }

    // 获取view的集合
    public List<View> getParallaxViews() {
        return parallaxViews;
    }
}
