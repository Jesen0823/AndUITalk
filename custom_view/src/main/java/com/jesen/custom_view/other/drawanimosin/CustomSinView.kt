package com.jesen.custom_view.other.drawanimosin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.graphics.withRotation
import androidx.core.graphics.withTranslation
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.jesen.custom_view.R
import kotlinx.coroutines.*
import java.lang.Math.*
import kotlin.math.cos
import kotlin.math.sin

class CustomSinView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr), LifecycleObserver {

    private var mWidth = 0f
    private var mHeight = 0f
    private val mAxisPaint = Paint()
    private val mTextPaint = Paint()
    private val mVectorPaint = Paint()
    private val mFillPaint = Paint()
    private val mDashPaint = Paint()
    private var mRadius = 0f
    private var mAngle = 10f

    private var mRotatingJob: Job? = null
    private val mSinPath = Path()

    init {
        mAxisPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = ContextCompat.getColor(context, R.color.blue_e)
        }
        mTextPaint.apply {
            textSize = 50f
            typeface = Typeface.DEFAULT_BOLD
            color = ContextCompat.getColor(context, R.color.black)
        }
        mVectorPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            color = ContextCompat.getColor(context, R.color.purple_200)
        }
        mFillPaint.apply {
            style = Paint.Style.FILL
            strokeWidth = 5f
            color = ContextCompat.getColor(context, R.color.black)
        }
        mDashPaint.apply {
            style = Paint.Style.STROKE
            strokeWidth = 5f
            pathEffect = DashPathEffect(floatArrayOf(10f, 14f), 0f)
            color = ContextCompat.getColor(context, R.color.blue_e)
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
    }


    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        mWidth = w.toFloat()
        mHeight = h.toFloat()
        mRadius = if (w < h / 2) w / 2.toFloat() else h/4.toFloat()
        mRadius -= 20f
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        drawAxises(canvas)
        drawText(canvas)
        drawDashedCircle(canvas)
        drawRadiusLine(canvas)
        drawProjections(canvas)
        drawSinWave(canvas)
    }

    private fun drawSinWave(canvas: Canvas?) {
        canvas?.withTranslation(mWidth/2,mHeight/2) {
            val sampleCount = 100
            val dy = mHeight/2/sampleCount
            mSinPath.apply {
                reset()
                moveTo(mRadius*cos(mAngle.toRadians()),0f)
                repeat(sampleCount){ index->
                    val x = mRadius*cos(-0.15*index+mAngle.toRadians())
                    val y = -dy*index
                    this.quadTo(x.toFloat(),y, x.toFloat(),y)
                }
                drawPath(this,mVectorPaint)
                drawTextOnPath("move move",this, 1000f,0f,mTextPaint)
            }

        }
    }

    private fun drawProjections(canvas: Canvas?) {
        canvas?.withTranslation (mWidth / 2, mHeight / 4 * 3){
            val x = mRadius * cos(mAngle.toRadians())
            val y = mRadius * sin(mAngle.toRadians())
            withTranslation(x,-y) {
                drawLine(0f, 0f, 0f, y, mAxisPaint)
                drawLine(0f, 0f, 0f, -mHeight / 4 + y, mDashPaint)
            }
        }
        // 画x轴的投影小圆点
        canvas?.withTranslation(mWidth / 2, mHeight / 2) {
            drawCircle((mRadius * cos(mAngle.toRadians())), 0f, 20f, mFillPaint)
        }
        // 画x轴的垂直方向的投影线
        canvas?.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            drawCircle((mRadius * cos(mAngle.toRadians())), 0f, 20f, mFillPaint)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startRotating() {
        mRotatingJob = CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(100)
                mAngle += 5f
                invalidate()
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun pauseRotating() {
        mRotatingJob?.cancel()
    }

    private fun drawRadiusLine(canvas: Canvas?) {
        canvas?.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            withRotation(-mAngle) {
                drawLine(0f, 0f, mRadius, 0f, mVectorPaint)
            }
        }
    }

    private fun drawDashedCircle(canvas: Canvas?) {
        canvas?.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            drawCircle(0f, 0f, mRadius, mDashPaint)
        }
    }

    private fun drawText(canvas: Canvas?) {
        canvas?.apply {
            drawRect(100f, 100f, 600f, 250f, mAxisPaint)
            drawText("指数函数", 120f, 195f, mTextPaint)
        }
    }

    private fun drawAxises(canvas: Canvas?) {
        /*canvas?.save()
        canvas?.translate(50f,50f)
        canvas?.drawLine(100f,100f,100f,400f,mPaint)
        canvas?.restore()*/

        // 效果等同上面
        canvas?.withTranslation(mWidth / 2, mHeight / 2) {
            drawLine(-mWidth / 2, 0f, mWidth / 2, 0f, mAxisPaint)
            drawLine(0f, -mHeight / 2, 0f, mHeight / 2, mAxisPaint)
        }
        canvas?.withTranslation(mWidth / 2, mHeight / 4 * 3) {
            drawLine(-mWidth / 2, 0f, mWidth / 2, 0f, mAxisPaint)
        }

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
    }

    fun Float.toRadians(): Float = this / 180 * PI.toFloat()
}