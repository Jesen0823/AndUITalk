package com.jesen.recyclerviewtalk.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jesen.recyclerviewtalk.R;

/**
 * 自定义Item装饰，带小图标和蒙层
 */
public class LinearItemDecoration2 extends RecyclerView.ItemDecoration {
    private Paint mPaint;
    private Bitmap mMedalMap;

    public LinearItemDecoration2(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        BitmapFactory.Options options = new BitmapFactory.Options();
        mMedalMap = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon_hot_topic);
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        // 先绘制图标
        RecyclerView.LayoutManager manager = parent.getLayoutManager();
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int index = parent.getChildAdapterPosition(child);
            int left = manager.getLeftDecorationWidth(child);
            // 定义绘制图标的条件
            if (index % 5 == 0) {
                c.drawBitmap(mMedalMap, left - mMedalMap.getWidth() / 2, child.getTop(), mPaint);
            }
        }

        // 绘制上面蒙层
        View tmp = parent.getChildAt(0);
        LinearGradient gradient = new LinearGradient(parent.getWidth() / 2, 0, parent.getWidth() / 2,
                tmp.getHeight() * 4, 0xff0000ff, 0x000000ff, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        c.drawRect(0, 0, parent.getWidth(), tmp.getHeight() * 3, mPaint);
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = 30;
        //outRect.right = 100;
    }
}
