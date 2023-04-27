package com.jesen.custom_view.surfaceview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.SurfaceView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimSurfaceView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : SurfaceView(context, attrs, defStyleAttr) {

    private val colors = arrayOf(Color.RED, Color.YELLOW, Color.BLUE, Color.GREEN)

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 6f
        pathEffect = ComposePathEffect(
            CornerPathEffect(50f),
            DiscretePathEffect(30f, 12f)
        )
        //pathEffect = DiscretePathEffect(30f,26f)
    }

    private data class Bubble(val x: Float, val y: Float, val color: Int, var radius: Float)

    private val bubbleList = mutableListOf<Bubble>()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            while (true) {
                if (holder.surface.isValid && !bubbleList.isNullOrEmpty()) {
                    val canvas = holder.lockCanvas()
                    canvas.drawColor(Color.BLACK)
                    bubbleList.toList().filter { it.radius < 1000 }.forEach {
                        paint.color = it.color
                        canvas.drawCircle(it.x, it.y, it.radius, paint)
                        it.radius += 20f
                    }
                    holder.unlockCanvasAndPost(canvas)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        val x = event?.x ?: 0f
        val y = event?.y ?: 0f
        val color = colors.random()
        val bubble = Bubble(x, y, color, 1f)
        bubbleList.add(bubble)
        if (bubbleList.size > 30) bubbleList.removeAt(0)
        return super.onTouchEvent(event)
    }

}