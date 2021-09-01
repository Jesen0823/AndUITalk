package com.jesen.custom_view.ripples;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.core.content.ContextCompat;

import com.jesen.custom_view.R;
import com.jesen.custom_view.ui_adapter.UiUtil;

import java.util.ArrayList;
import java.util.Collections;

public class RippleAnimationView extends RelativeLayout {
    public Paint paint;
    private int rippleNum = 0;
    private int rippleColor = 0;
    private int radius = 0;
    private int strokWidth = 0;
    private ArrayList<RipplesCircleView> viewList = new ArrayList<>();
    private AnimatorSet animatorSet;
    private boolean animatorRunning = false;

    public RippleAnimationView(Context context) {
        this(context, null);
    }

    public RippleAnimationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RippleAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        UiUtil.getInstance(getContext());

        paint = new Paint();
        paint.setAntiAlias(true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RippleAnimationView);
        int rippleType = a.getInt(R.styleable.RippleAnimationView_ripple_anim_type, 0);
        rippleNum = a.getInteger(R.styleable.RippleAnimationView_rippleNum, 6);
        radius = a.getInteger(R.styleable.RippleAnimationView_radius, 54);
        strokWidth = a.getInteger(R.styleable.RippleAnimationView_strokWidth, 2);
        rippleColor = a.getColor(R.styleable.RippleAnimationView_ripple_anim_color, ContextCompat.getColor(context, R.color.rippleColor));

        if (rippleType == 0) {
            paint.setStyle(Paint.Style.FILL);
        } else {
            paint.setStyle(Paint.Style.STROKE);
        }
        paint.setStrokeWidth(UiUtil.getInstance().getWidth(strokWidth));
        paint.setColor(rippleColor);

        LayoutParams rippleParams = new LayoutParams(UiUtil.getInstance().getWidth(radius + strokWidth),
                UiUtil.getInstance().getWidth(radius + strokWidth));
        rippleParams.addRule(CENTER_IN_PARENT, TRUE);

        float maxScale = 10;
        int rippleDuration = 3500;
        // 相邻两个波纹的间隔时间
        int singleDelay = rippleDuration / rippleNum;
        ArrayList<Animator> animators = new ArrayList<>();
        for (int i = 0; i < rippleNum; i++) {
            RipplesCircleView circleView = new RipplesCircleView(this);
            addView(circleView, rippleParams);
            viewList.add(circleView);

            final ObjectAnimator scaleXAnim = ObjectAnimator.ofFloat(circleView, "ScaleX", 1.0f, maxScale);
            // 无限循环模式
            scaleXAnim.setRepeatCount(ObjectAnimator.INFINITE);
            scaleXAnim.setRepeatMode(ObjectAnimator.RESTART);
            scaleXAnim.setStartDelay(i * singleDelay);
            scaleXAnim.setDuration(rippleDuration);
            animators.add(scaleXAnim);

            final ObjectAnimator scaleYAnim = ObjectAnimator.ofFloat(circleView, "ScaleY", 1.0f, maxScale);
            scaleYAnim.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            scaleYAnim.setRepeatMode(ObjectAnimator.RESTART);
            scaleYAnim.setStartDelay(i * singleDelay);
            scaleYAnim.setDuration(rippleDuration);
            animators.add(scaleYAnim);

            final ObjectAnimator alphaAnim = ObjectAnimator.ofFloat(circleView, "Alpha", 1.0f, 0f);
            alphaAnim.setRepeatCount(ObjectAnimator.INFINITE);//无限重复
            alphaAnim.setRepeatMode(ObjectAnimator.RESTART);
            alphaAnim.setStartDelay(i * singleDelay);
            alphaAnim.setDuration(rippleDuration);
            animators.add(alphaAnim);
        }
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animators);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
                Log.d("Ripple", "onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animator animator) {

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    public int getStrokWidth() {
        return strokWidth;
    }

    public boolean isAnimatorRunning() {
        return animatorRunning;
    }

    public void startRippleAnimation() {
        if (!animatorRunning) {
            for (RipplesCircleView rippleView : viewList) {
                rippleView.setVisibility(VISIBLE);
            }
            animatorSet.start();
            animatorRunning = true;
        }

    }

    public void stopRippleAnimation() {
        if (animatorRunning) {
            Collections.reverse(viewList);
            for (RipplesCircleView rippleView : viewList) {
                rippleView.setVisibility(INVISIBLE);
            }
            animatorSet.end();
            animatorRunning = false;
        }

    }

}








