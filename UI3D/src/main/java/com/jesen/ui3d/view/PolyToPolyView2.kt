package com.jesen.ui3d.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jesen.ui3d.R
import kotlin.math.sqrt

/**
 * 折叠效果拆分：只绘制第二份
 * */
class PolyToPolyView2 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val mBitmap: Bitmap = BitmapFactory.decodeResource(resources, R.mipmap.flod_bg)
    private val mMatrix: Matrix = Matrix()

    // 右下角向下倾斜100
    private val sTransHeight = 100f

    // 按宽度分成8份
    private val sFoldsNum = 8

    // 每一份宽度
    private var mFoldWidth = 0

    // 第一份所在矩形
    private val rect = Rect()

    // 形变因子，即假设折叠后的总宽度是原宽度的0.8倍
    private val mFactor = 0.8f

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
        // src是没有折叠前的四个顶点
        val src = floatArrayOf(
            mFoldWidth.toFloat(), 0f,
            2 * mFoldWidth.toFloat(), 0f,
            2 * mFoldWidth.toFloat(), mBitmap.height.toFloat(),
            mFoldWidth.toFloat(), mBitmap.height.toFloat(),
        )
        // dst 折叠后的四个顶点
        val dst = floatArrayOf(
            foldedItemWidth, depth.toFloat(),
            2 * foldedItemWidth, 0f,
            2 * foldedItemWidth, mBitmap.height.toFloat(),
            foldedItemWidth, mBitmap.height.toFloat() + depth.toFloat()
        )
        mMatrix.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rect.set(mFoldWidth, 0, 2*mFoldWidth, height)
        canvas?.apply {
            save()
            setMatrix(mMatrix)
            clipRect(rect)  // 画布剪裁成第一份一样大
            drawBitmap(mBitmap, 0f, 0f, null)
            restore()
        }
    }
}