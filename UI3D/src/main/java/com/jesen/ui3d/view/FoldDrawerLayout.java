package com.jesen.ui3d.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

/**
 * 借助折叠控件 PolyToPolyViewShadowLinearLayout2
 * 改造DrawerLayout为可折叠的
 */
public class FoldDrawerLayout extends DrawerLayout {

    private final DrawerListener listener = new SimpleDrawerListener() {
        @Override
        public void onDrawerSlide(View drawerView, float slideOffset) {
            super.onDrawerSlide(drawerView, slideOffset);
            if (drawerView instanceof PolyToPolyViewShadowLinearLayout2) {
                PolyToPolyViewShadowLinearLayout2 foldLayout = (PolyToPolyViewShadowLinearLayout2) drawerView;
                foldLayout.setFactor(slideOffset);
            }
        }
    };

    public FoldDrawerLayout(@NonNull Context context) {
        super(context);
    }

    public FoldDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FoldDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = getChildAt(i);
            if (idDrawerView(child)) {
                LayoutParams lp = (LayoutParams) child.getLayoutParams();
                PolyToPolyViewShadowLinearLayout2 foldLayout = new PolyToPolyViewShadowLinearLayout2(getContext());
                removeView(child);
                foldLayout.addView(child);
                addView(foldLayout, i, lp);
            }
        }
        addDrawerListener(listener);
    }

    private boolean idDrawerView(View child) {
        final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
        final int absGravity = GravityCompat.getAbsoluteGravity(gravity, ViewCompat.getLayoutDirection(child));
        return (absGravity & (Gravity.START | Gravity.END)) != 0;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeDrawerListener(listener);
    }
}
