package com.jesen.custom_view.other.customsingleview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.jesen.custom_view.R
import kotlin.properties.Delegates

class RectView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mColor by Delegates.notNull<Int>()

    init {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.RectView)
        mColor = typeArray.getColor(R.styleable.RectView_rect_color,Color.GREEN)
        typeArray.recycle()

        initDraw()
    }

    private fun initDraw() {
        mPaint.color = mColor
        mPaint.strokeWidth = 1.5f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)

        // (warp_content,warp_content)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(200,200)
        }else if (widthSpecMode == MeasureSpec.AT_MOST){ // (warp_content,match_content or .dp)
            setMeasuredDimension(200, heightSpecSize)
        }else if (heightSpecMode == MeasureSpec.AT_MOST){
            setMeasuredDimension(widthSpecSize,200) // (match_content or .dp,warp_content)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        val width = width - paddingStart - paddingEnd
        val height = height - paddingTop - paddingBottom

        canvas?.drawRect(
            0f + paddingStart, 0f + paddingTop, width.toFloat() + paddingEnd,
            height.toFloat() + paddingBottom, mPaint
        )
    }
}