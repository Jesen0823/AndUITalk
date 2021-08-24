package com.jesen.canvas_api;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.RequiresApi;

/**
 * 变换操作
 */
public class TransformView extends View {

    private Paint mPaint;

    public TransformView(Context context) {
        this(context, null);
    }

    public TransformView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TransformView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.parseColor("#3300FF00"));
        mPaint.setStrokeWidth(6);
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //1，平移操作
        /*canvas.drawRect(0,0, 400, 400, mPaint);
        canvas.translate(50, 50);
        mPaint.setColor(Color.BLUE);*/

        //canvas.drawRect(0,0, 400, 400, mPaint);
        //canvas.drawLine(0, 0, 600,600, mPaint);

        //缩放
        //canvas.drawRect(200,200, 700,700, mPaint);

        //canvas.scale(0.5f, 0.5f);

        // 缩放的同时平移
        //canvas.scale(0.5f, 0.5f, 200,200);

        //先translate(px, py),再scale(sx, sy),再反响translate
        /*canvas.translate(200, 200);
        canvas.scale(0.5f, 0.5f);
        canvas.translate(-200, -200);*/

        /*mPaint.setColor(Color.BLUE);
        canvas.drawRect(200,200, 700,700, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.drawLine(0,0, 400, 600, mPaint);*/

       //旋转操作
        /*canvas.translate(100, 100);

        canvas.drawRect(0,0, 400,400, mPaint);
        //canvas.rotate(30);
        canvas.drawCircle(98,98,2,mPaint);
        canvas.rotate(30, 100, 100); //px, py表示旋转中心的坐标
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0,0, 300,300, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawLine(0,0, 400, 600, mPaint);*/

        //倾斜操作
       /* canvas.drawRect(0,0, 400, 400, mPaint);
        canvas.skew(0.577f, 0); //在X方向倾斜45度,Y轴逆时针旋转45
        //canvas.skew(0, 1); //在y方向倾斜45度， X轴顺时针旋转45
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        canvas.drawRect(0, 0, 400, 400, mPaint);

        mPaint.setColor(Color.GRAY);
        canvas.drawLine(0,0, 400, 600, mPaint);*/

        //切割

        /*canvas.drawRect(200, 200,700, 700, mPaint);
        mPaint.setColor(Color.GRAY);
        canvas.clipRect(200, 200,700, 700); //画布被裁剪
        canvas.drawCircle(100,100, 100,mPaint); //坐标超出裁剪区域，无法绘制
        canvas.drawCircle(300, 300, 100, mPaint); //坐标区域在裁剪范围内，绘制成功*/

        canvas.drawRect(200, 200,700, 700, mPaint);
        mPaint.setColor(Color.GRAY);
        //canvas.drawRect(200, 800,700, 1300, mPaint);
        canvas.clipOutRect(200,200,700,700); //画布裁剪外的区域
        canvas.drawCircle(100,100,100,mPaint); //坐标区域在裁剪范围内，绘制成功
        canvas.drawCircle(300, 300, 100, mPaint);//坐标超出裁剪区域，无法绘制
        //矩阵
        /*canvas.drawRect(0,0,700,700, mPaint);
        Matrix matrix = new Matrix();
//        matrix.setTranslate(50,50);
//        matrix.setRotate(45);
        matrix.setScale(0.5f, 0.5f);
        canvas.setMatrix(matrix);
        mPaint.setColor(Color.GRAY);
        canvas.drawRect(0,0,700,700, mPaint);*/

    }

}
