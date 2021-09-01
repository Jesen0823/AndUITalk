 package com.jesen.custom_view.musicalbum.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;

import com.jesen.custom_view.R;

 public class BackgroudAnimLayout extends RelativeLayout {

     private final int DURATION_ANIM = 500;
     private final int INDEX_BACKGROUND = 0;
     private final int INDEX_FOREGROUND = 1;

    private LayerDrawable layerDrawable;
    private ObjectAnimator objectAnimator;
    private int musicPicRes = -1;

    public BackgroudAnimLayout(Context context) {
        this(context ,null);
    }

    public BackgroudAnimLayout(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public BackgroudAnimLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @SuppressLint("ObjectAnimatorBinding")
    private void init() {
        Drawable background = getContext().getDrawable(R.drawable.ic_blackground);
        Drawable[] drawables = new Drawable[2];
        drawables[INDEX_BACKGROUND] = background;
        drawables[INDEX_FOREGROUND] = background;
        layerDrawable = new LayerDrawable(drawables);

        // 监听动画
        objectAnimator = ObjectAnimator.ofFloat(this,"number",0f,1.0f);
        objectAnimator.setDuration(DURATION_ANIM);
        objectAnimator.setInterpolator(new AccelerateInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                int foregroundAlpha = (int)(animatedValue * 255);
                layerDrawable.getDrawable(INDEX_FOREGROUND).setAlpha(foregroundAlpha);
                BackgroudAnimLayout.this.setBackground(layerDrawable);
            }
        });
        objectAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onAnimationEnd(Animator animator) {
                layerDrawable.setDrawable(INDEX_BACKGROUND, layerDrawable.getDrawable(
                        INDEX_FOREGROUND));
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void setForeground(Drawable drawable){
        if (layerDrawable != null) {
            layerDrawable.setDrawable(INDEX_FOREGROUND, drawable);
        }
    }

    public void  beginAnimation(){
        objectAnimator.start();
    }

    public boolean isNeedUpdateBackground(int picRes){
        if (musicPicRes == -1) return true;
        if (musicPicRes != picRes){
            return true;
        }
        return false;
    }
}
 