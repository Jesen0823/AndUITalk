package com.jesen.ui3d.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.jesen.ui3d.R

/**
 * 折叠效果拆分：只绘制第一份
 * */
class PolyToPolyView1 @JvmOverloads constructor(
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

    init {
        initView()
    }

    private fun initView() {
        mFoldWidth = mBitmap.width / sFoldsNum
        val src = floatArrayOf(
            0f, 0f,
            mBitmap.width.toFloat(), 0f,
            mBitmap.width.toFloat(), mBitmap.height.toFloat(),
            0f, mBitmap.height.toFloat()
        )
        val dst = floatArrayOf(
            0f, 0f,
            mBitmap.width.toFloat(), sTransHeight,
            mBitmap.width.toFloat(), mBitmap.height.toFloat() + sTransHeight,
            0f, mBitmap.height.toFloat()
        )
        mMatrix.setPolyToPoly(src, 0, dst, 0, src.size shr 1)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        rect.set(0,0,mFoldWidth,height)
        canvas?.apply {
            save()
            setMatrix(mMatrix)
            clipRect(rect)  // 画布剪裁成第一份一样大
            drawBitmap(mBitmap, 0f, 0f, null)
            restore()
        }
    }
}