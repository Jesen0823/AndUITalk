package com.jesen.ui3d.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jesen.ui3d.R
import kotlin.math.sqrt

/**
 * PolyToPolyViewAll 升级版
 * 折叠效果整合,带阴影
 * 左边折叠项带阴影，右边折叠项带渐变
 * */
class PolyToPolyViewShadow @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.flod_bg)

    // 按宽度分成8份
    private val sFoldsNum = 8

    // 每一份宽度
    private var mFoldWidth = 0f

    // 形变因子，即假设折叠后的总宽度是原宽度的0.8倍
    private val mFactor = 0.8f
    private val mMatrices: Array<Matrix> = Array(sFoldsNum, init = { Matrix() })
    private val mRectArray: Array<Rect> = Array(sFoldsNum, init = { Rect() })

    // 阴影和渐变画笔
    private val mSolidPaint = Paint()
    private val mShadowPaint = Paint()
    private lateinit var mShadowGradientShader: LinearGradient

    init {
        initView()
    }

    private fun initView() {
        mFoldWidth = mBitmap.width / sFoldsNum.toFloat()
        // 越展开，阴影的alpha值越小
        val alpha = (255 * (1 - mFactor)).toInt()
        mShadowGradientShader = LinearGradient(
            0f, 0f, mFoldWidth, 0f,
            Color.BLACK, Color.TRANSPARENT, Shader.TileMode.CLAMP
        )
        mSolidPaint.color = Color.argb((alpha * 0.8f).toInt(), 0, 0, 0)
        mShadowPaint.alpha = alpha
        mShadowPaint.shader = mShadowGradientShader

        // 折叠后每一份的宽度
        val foldedItemWidth = mBitmap.width * mFactor / sFoldsNum
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
                sLeft, 0f, sRight, 0f, sRight, mBitmap.height.toFloat(), sLeft,
                mBitmap.height.toFloat()
            )
            val dst = FloatArray(sFoldsNum)
            dst[0] = foldedItemWidth * i
            dst[1] = if (isEven) 0f else depth
            dst[2] = foldedItemWidth * (i + 1)
            dst[3] = if (isEven) depth else 0f
            dst[4] = foldedItemWidth * (i + 1)
            dst[5] = if (isEven) (mBitmap.height + depth) else mBitmap.height.toFloat()
            dst[6] = foldedItemWidth * i
            dst[7] = if (isEven) mBitmap.height.toFloat() else (mBitmap.height + depth)
            mMatrices[i].setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until sFoldsNum) {
            mRectArray[i].set((mFoldWidth * i).toInt(), 0, ((i + 1) * mFoldWidth).toInt(), height)
            canvas?.apply {
                save()
                setMatrix(mMatrices[i])
                clipRect(mRectArray[i])  // 画布剪裁成第一份一样大
                drawBitmap(mBitmap, 0f, 0f, null)

                // 将绘制坐标系移动到矩形左上角
                translate((mFoldWidth * i), 0f)
                // 画阴影
                canvas.drawRect(
                    0f, 0f, mFoldWidth, mBitmap.height.toFloat(),
                    if (i % 2 == 0) mSolidPaint else mShadowPaint
                )

                restore()
            }
        }
    }
}