package com.jesen.recyclerviewtalk.custom;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/**
 * 自定义Item装饰器
 * 每个Item装饰一个圆
 */
public class LinearItemDecoration extends RecyclerView.ItemDecoration {

    private final Paint mPaint;

    public LinearItemDecoration() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        mPaint.setAntiAlias(true);
    }

    // Canvas是getItemOffsets()函数指定的区域的画布
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);
        int childCount = parent.getChildCount();
        RecyclerView.LayoutManager manager = parent.getLayoutManager();

        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            assert manager != null;
            int left = manager.getLeftDecorationWidth(child);
            int cx = left / 2;
            int cy = child.getTop() + child.getHeight() / 2;
            c.drawCircle(cx, cy, 20, mPaint);
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        c.drawCircle(400, 400, 200, mPaint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 200;
        outRect.bottom = 1;
    }
}
