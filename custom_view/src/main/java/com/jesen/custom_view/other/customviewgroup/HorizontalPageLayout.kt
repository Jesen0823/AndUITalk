package com.jesen.custom_view.other.customviewgroup

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import kotlin.math.abs

/**
 * 自定义控件 横向ViewPager
 * */
class HorizontalPageLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    private var lastInterceptX = 0f
    private var lastInterceptY = 0f
    private var lastX = 0f
    private var lastY = 0f

    private var childWidth = 0
    private var currentIndex = 0
    private var scroller: Scroller

    // 测试滑动速度
    private var velocityTracker: VelocityTracker

    init {
        scroller = Scroller(context)
        velocityTracker = VelocityTracker.obtain()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        measureChildren(widthMeasureSpec, heightMeasureSpec)

        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            // (wrap_content,wrap_content)
            val childFirst = getChildAt(0)
            val childWidth = childFirst.measuredWidth
            val childHeight = childFirst.measuredHeight
            setMeasuredDimension(childWidth * childCount, childHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            val childWidth = getChildAt(0).measuredWidth
            setMeasuredDimension(childWidth * childCount, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            val childHeight = getChildAt(0).measuredHeight
            setMeasuredDimension(widthSize, childHeight * childCount)
        }
    }

    override fun onLayout(p0: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var left = 0
        var child: View? = null
        for (i in 0 until childCount) {
            child = getChildAt(i)
            val width = child?.measuredWidth ?: 0
            childWidth = width
            child.layout(left, 0, left + width, child.measuredHeight)
            // 从左往右排列，left是前面width累加
            left += width
        }
    }

    /**
     * 滑动冲突，如果是水平滑动则Viewgroup需要拦截
     * */
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        var intercept = false
        val x = ev?.x ?: 0f
        val y = ev?.y ?: 0f
        when (ev?.action) {
            MotionEvent.ACTION_DOWN -> {
                // 如果上一次滑动还未完成 则拒绝这次点击滑动事件
                intercept = false
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastInterceptX
                val deltaY = y - lastInterceptY
                // 水平方向变化大于垂直方向，判定为水平滑动
                if (abs(deltaX) - abs(deltaY) > 0) {
                    intercept = true
                }
            }
            MotionEvent.ACTION_UP -> {
            }
        }
        lastX = x
        lastY = y
        lastInterceptX = x
        lastInterceptY = y
        return intercept
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastX
                scrollBy(-deltaX.toInt(), 0)
            }
            MotionEvent.ACTION_UP -> {
                val distance = scrollX - currentIndex * childWidth
                if (abs(distance) > childWidth / 2) {
                    if (distance > 0) currentIndex++ else currentIndex--
                } else {
                    val xVelocity = velocityTracker.xVelocity
                    if (abs(xVelocity) > 50) { // 认为是快速滑动，惯性滑动，需要自动切换
                        if (xVelocity > 0) currentIndex-- else currentIndex++
                    }
                }
                // 限定index范围防止越界
                //currentIndex.coerceIn(0,childCount-1)
                currentIndex =
                    if (currentIndex < 0) 0 else
                        (if (currentIndex > childCount - 1) childCount - 1 else currentIndex
                                )

                Log.d("CustomViewGroup", "onTouchEvent currentIndex: $currentIndex")
                smoothScrollTo(currentIndex * childWidth, 0)

                velocityTracker.clear()
            }
        }
        lastX = x
        lastY = y
        return super.onTouchEvent(event)

    }

    override fun computeScroll() {
        super.computeScroll()
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    private fun smoothScrollTo(destX: Int, destY: Int) {
        scroller.startScroll(
            scrollX, scrollY,
            (destX - scaleX).toInt(), (destY - scaleY).toInt(), 1000
        )
    }
}