package com.jesen.custom_view.rotationloading

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.jesen.custom_view.R
import kotlin.math.atan2

class MoveIcon @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.arrow)
    private var path = Path()
    private var pathMeasure:PathMeasure
    private val circlePaint = Paint()
    private val iconPaint = Paint()
    private val iconMatrix = Matrix()
    private var distanceRatio = 0F

    private var posArray = FloatArray(2) //记录位置
    private var tanArray = FloatArray(2) //记录切点值xy

    init {
        path.addCircle(0F, 0F, 200F, Path.Direction.CW)
        pathMeasure = PathMeasure(path, false)
        circlePaint.strokeWidth = 2F
        circlePaint.style = Paint.Style.STROKE
        circlePaint.isAntiAlias = true
        circlePaint.color = ContextCompat.getColor(context, R.color.blue_e)

        iconPaint.color = Color.DKGRAY
        iconPaint.strokeWidth = 2F
        iconPaint.style = Paint.Style.STROKE
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawColor(Color.WHITE)
        val width = canvas.width
        val height = canvas.height

        // 移动画布坐标原点到中心
        canvas.translate((width / 2).toFloat(), (height / 2).toFloat())
        iconMatrix.reset()
        distanceRatio += 0.010F
        if (distanceRatio >= 1) {
            distanceRatio = 0F
        }

        val distance = pathMeasure.length * distanceRatio
        pathMeasure.getPosTan(distance, posArray, tanArray)
        //计算icon本身要旋转的角度
        val degree = (atan2(
            tanArray[1].toDouble(),
            tanArray[0].toDouble()
        ) * 180 / Math.PI).toFloat()

        //设置旋转角度和旋转中心
        iconMatrix.postRotate(
            degree,
            (iconBitmap.width / 2).toFloat(),
            (iconBitmap.height / 2).toFloat()
        )

        //这里要将设置到Icon的中心点
        iconMatrix.postTranslate(posArray[0] - iconBitmap.width / 2, posArray[1] - iconBitmap.height / 2)
        canvas.drawPath(path, circlePaint)
        canvas.drawBitmap(iconBitmap, iconMatrix, iconPaint)
        postInvalidate()
    }
}



