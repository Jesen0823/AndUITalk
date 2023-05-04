package com.jesen.ui3d.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jesen.ui3d.R
import kotlin.math.sqrt

/**
 * 折叠效果整合
 * */
class PolyToPolyViewAll @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.flod_bg)
    // 按宽度分成8份
    private val sFoldsNum = 8

    // 每一份宽度
    private var mFoldWidth = 0

    // 形变因子，即假设折叠后的总宽度是原宽度的0.8倍
    private val mFactor = 0.8f
    private val mMatrixs: Array<Matrix> = Array(sFoldsNum, init = { Matrix() })
    private val mRects: Array<Rect> = Array(sFoldsNum, init = { Rect() })

    init {
        initView()
    }

    private fun initView() {
        mFoldWidth = mBitmap.width / sFoldsNum
        // 折叠后每一份的宽度
        val foldedItemWidth = mBitmap.width * mFactor / sFoldsNum
        // 折叠变形后每一份的，左右边界高度差，Matrix倾斜改变的是坐标系不是画布，所以坐标系上的宽度
        // 与没有折叠前是一样的宽度，仍然是还是mFoldWidth。勾股定理：
        val depth = sqrt((mFoldWidth * mFoldWidth - foldedItemWidth * foldedItemWidth).toDouble())
        for (i in 0 until sFoldsNum) {
            // 点的标号是否奇数
            val isEven = (i and 1) == 1
            val sLeft = mFoldWidth * i
            val sRight = mFoldWidth * (i + 1)
            val src = floatArrayOf(
                sLeft.toFloat(), 0f,
                sRight.toFloat(), 0f,
                sRight.toFloat(), mBitmap.height.toFloat(),
                sLeft.toFloat(), mBitmap.height.toFloat()
            )
            val dst = FloatArray(sFoldsNum)
            dst[0] = foldedItemWidth * i
            dst[1] = if (isEven) 0f else depth.toFloat()
            dst[2] = foldedItemWidth * (i + 1)
            dst[3] = if (isEven) depth.toFloat() else 0f
            dst[4] = foldedItemWidth * (i + 1)
            dst[5] = if (isEven) (mBitmap.height + depth).toFloat() else mBitmap.height.toFloat()
            dst[6] = foldedItemWidth * i
            dst[7] = if (isEven) mBitmap.height.toFloat() else (mBitmap.height + depth).toFloat()
            mMatrixs[i].setPolyToPoly(src, 0, dst, 0, src.size shr 1)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        for (i in 0 until sFoldsNum) {
            mRects[i].set(mFoldWidth * i, 0, (i + 1) * mFoldWidth, height)
            canvas?.apply {
                save()
                setMatrix(mMatrixs[i])
                clipRect(mRects[i])  // 画布剪裁成第一份一样大
                drawBitmap(mBitmap, 0f, 0f, null)
                restore()
            }
        }
    }
}