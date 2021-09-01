package com.jesen.custom_view.pathmeasure;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.graphics.PathParser;

import com.jesen.custom_view.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class SvgPathView extends View {

    private Context mContext;
    private Path mPath;
    private Paint mPaint;
    private float mLength;
    private Path dstPath;
    private float mAnimatorValue;
    private PathMeasure pathMeasure;

    private Thread svgThread = new Thread(new Runnable() {
        @Override
        public void run() {
            final InputStream is = mContext.getResources().openRawResource(R.raw.inclusive_400);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = null;
            try {
                builder = factory.newDocumentBuilder();
                Document doc = builder.parse(is);
                Element rootElement = doc.getDocumentElement();
                NodeList list = rootElement.getElementsByTagName("path");
                for (int i = 0; i < list.getLength(); i++) {
                    Element element = (Element) list.item(i);
                    String pathData = element.getAttribute("android:pathData");
                    Path path = PathParser.createPathFromPathData(pathData);
                    mPath.addPath(path);
                }

                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        initLate();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    });

    public SvgPathView(Context context) {
        this(context,null);
    }

    public SvgPathView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public SvgPathView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8f);

        mPath = new Path();
        dstPath = new Path();

        pathMeasure = new PathMeasure(mPath,true);
        svgThread.start();
    }

    private void initLate(){
        pathMeasure.setPath(mPath,true);
        mLength = pathMeasure.getLength();

        requestLayout();
        invalidate();
        // 属性动画
        final ValueAnimator va = ValueAnimator.ofFloat(0, 1);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                mAnimatorValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        va.setDuration(3600);
        // 无限循环
        va.setRepeatCount(ValueAnimator.INFINITE);
        va.start();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        dstPath.reset();
        float distance = mLength * mAnimatorValue;
        //float start = (float) (distance - ((Math.abs(mAnimatorValue - 0.5)) * mLength));
        float start = 0;
        Log.d("HHH","start = "+start);
        pathMeasure.getSegment(start, distance, dstPath, true);
        canvas.drawPath(dstPath, mPaint);
    }
}
