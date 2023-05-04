package com.jesen.ui3d.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout
import kotlin.math.sqrt

/**
 * PolyToPolyViewAll2 升级版 可折叠LinearLayout
 * 折叠效果整合,带阴影
 * 左边折叠项带阴影，右边折叠项带渐变
 * */
class PolyToPolyViewShadowLinearLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    // 按宽度分成8份
    private val sFoldsNum = 8

    // 每一份宽度
    private var mFoldWidth = 0f

    // 形变因子，即假设折叠后的总宽度是原宽度的0.8倍
    private var mFactor = 0.8f
    private val mMatrices: Array<Matrix> = Array(sFoldsNum, init = { Matrix() })
    private val mRectArray: Array<Rect> = Array(sFoldsNum, init = { Rect() })

    // 阴影和渐变画笔
    private val mSolidPaint = Paint()
    private val mShadowPaint = Paint()
    private lateinit var mShadowGradientShader: LinearGradient
    private var mWidth = 0
    private var mHeight = 0

    init {
        initView()
    }

    private fun initView() {
        // 越展开，阴影的alpha值越小
        val alpha = (255 * (1 - mFactor)).toInt()
        mSolidPaint.color = Color.argb((alpha * 0.8f).toInt(), 0, 0, 0)
        mShadowPaint.alpha = alpha
        mShadowGradientShader = LinearGradient(
            0f, 0f, mFoldWidth, 0f, Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP
        )
    }

    public fun setFactor(factor: Float){
        mFactor = factor
        updateFold()
        invalidate()
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
        updateFold()
    }

    private fun updateFold() {
        mWidth = measuredWidth
        mHeight = measuredHeight
        mFoldWidth = mWidth / sFoldsNum.toFloat()

        mShadowPaint.shader = mShadowGradientShader

        // 折叠后每一份的宽度
        val foldedItemWidth = mWidth * mFactor / sFoldsNum
        // 折叠变形后每一份的，左右边界高度差，Matrix倾斜改变的是坐标系不是画布，所以坐标系上的宽度
        // 与没有折叠前是一样的宽度，仍然是还是mFoldWidth。勾股定理：
        val depth =
            sqrt((mFoldWidth * mFoldWidth - foldedItemWidth * foldedItemWidth).toDouble() / 2).toFloat()
        for (i in 0 until sFoldsNum) {
            //表示第几个模块，i==0时，表示第一个模块
            val isEven = i % 2 == 0
            val sLeft = mFoldWidth * i
            val sRight = mFoldWidth * (i + 1)
            val src = floatArrayOf(
                sLeft, 0f, sRight, 0f, sRight, mHeight.toFloat(), sLeft, mHeight.toFloat()
            )
            val dst = FloatArray(sFoldsNum)
            dst[0] = foldedItemWidth * i
            dst[1] = if (isEven) 0f else depth
            dst[2] = foldedItemWidth * (i + 1)
            dst[3] = if (isEven) depth else 0f
            dst[4] = foldedItemWidth * (i + 1)
            // 调整：保证底部完整展示
            //dst[5] = if (isEven) (mHeight + depth) else mHeight.toFloat()
            dst[5] = if (isEven) mHeight.toFloat() else (mHeight - depth)
            dst[6] = foldedItemWidth * i
            // 调整：保证底部完整展示
            //dst[7] = if (isEven) mHeight.toFloat() else (mHeight + depth)
            dst[7] = if (isEven) (mHeight - depth) else mHeight.toFloat()
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        for (i in 0 until sFoldsNum) {
            mRectArray[i].set((mFoldWidth * i).toInt(), 0, ((i + 1) * mFoldWidth).toInt(), height)
            canvas?.apply {
                save()
                setMatrix(mMatrices[i])
                clipRect(mRectArray[i])  // 画布剪裁成第一份一样大
                super.dispatchDraw(canvas)

                // 将绘制坐标系移动到矩形左上角
                translate((mFoldWidth * i), 0f)
                // 画阴影
                canvas.drawRect(
                    0f,
                    0f,
                    mFoldWidth,
                    mHeight.toFloat(),
                    if (i % 2 == 0) mSolidPaint else mShadowPaint
                )

                restore()
            }
        }
    }
}