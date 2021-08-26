package com.jesen.animator_talk_splash;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import com.nineoldandroids.view.ViewHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * ParallaxContainer 总控件
 * 其中viewPager装载了Fragment
 */

public class ParallaxContainer extends FrameLayout implements ViewPager.OnPageChangeListener {
    private List<ParallaxFragment> fragments;
    private ImageView ivPeople;
    ParallaxPagerAdapter adapter;

    public void setIvPeople(ImageView ivPeople) {
        this.ivPeople = ivPeople;
    }

    public ParallaxContainer(@NonNull Context context) {
        super(context);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ParallaxContainer(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setUp(int... childs) {
        fragments = new ArrayList<ParallaxFragment>();

        for (int i = 0; i < childs.length; i++) {
            ParallaxFragment f = new ParallaxFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("layoutId", childs[i]);
            f.setArguments(bundle);
            fragments.add(f);
        }
        ViewPager viewPager = new ViewPager(getContext());
        // 设置id
        viewPager.setId(R.id.parallax_pager);
        viewPager.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        adapter = new ParallaxPagerAdapter(
                ((SplashActivity) getContext()).getSupportFragmentManager(), 0, fragments);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(this);
        addView(viewPager);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        // 处理动画
        int containerWidth = getWidth();
        ParallaxFragment inFragment = null;

        try {
            inFragment = fragments.get(position - 1);
        } catch (Exception e) {
        }
        //获取到退出的页面
        ParallaxFragment outFragment = null;
        try {
            outFragment = fragments.get(position);
        } catch (Exception e) {
        }

        if (inFragment != null) {
            //获取Fragment上所有的视图，实现动画效果
            List<View> inViews = inFragment.getParallaxViews();

            if (inViews != null) {
                for (View view : inViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    ViewHelper.setTranslationX(view, (containerWidth - positionOffsetPixels) * tag.xIn);
                    ViewHelper.setTranslationY(view, (containerWidth - positionOffsetPixels) * tag.yIn);
                }
            }
        }
        if (outFragment != null) {
            List<View> outViews = outFragment.getParallaxViews();
            if (outViews != null) {
                for (View view : outViews) {
                    ParallaxViewTag tag = (ParallaxViewTag) view.getTag(R.id.parallax_view_tag);
                    if (tag == null) {
                        continue;
                    }
                    //仔细观察退出的fragment中view从原始位置开始向上移动，translationY应为负数
                    ViewHelper.setTranslationY(view, 0 - positionOffsetPixels * tag.yOut);
                    ViewHelper.setTranslationX(view, 0 - positionOffsetPixels * tag.xOut);
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        if (position == adapter.getCount() - 1) {
            ivPeople.setVisibility(INVISIBLE);
        } else {
            ivPeople.setVisibility(VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        AnimationDrawable animation = (AnimationDrawable) ivPeople.getBackground();
        switch (state) {
            case ViewPager.SCROLL_STATE_DRAGGING:
                animation.start();
                break;

            case ViewPager.SCROLL_STATE_IDLE:
                animation.stop();
                break;

            default:
                break;
        }
    }
}
