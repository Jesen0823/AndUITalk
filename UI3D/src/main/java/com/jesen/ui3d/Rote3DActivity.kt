package com.jesen.ui3d

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jesen.ui3d.util.Rotate3DAnimation

/**
 * 图片翻转
 * 但是因为Camera与view的距离默认没有变
 * 翻转过来的部分与camera距离越近，视觉效果会越大
 * */
class Rote3DActivity : AppCompatActivity() {
    private lateinit var mContent: View
    private val duration = 1000L
    private lateinit var openAnimation:Rotate3DAnimation
    private lateinit var closeAnimation:Rotate3DAnimation
    private var isOpen = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_3d)

        mContent = findViewById(R.id.i_content)
        initOpenAnimation()
        initCloseAnimation()

        findViewById<Button>(R.id.button).setOnClickListener {
            if (openAnimation.hasStarted() && !openAnimation.hasEnded()) return@setOnClickListener
            if (closeAnimation.hasStarted() && !closeAnimation.hasEnded()) return@setOnClickListener
            if (isOpen){
                mContent.startAnimation(closeAnimation)
            }else{
                mContent.startAnimation(openAnimation)
            }
            isOpen = !isOpen
        }
    }

    private fun initOpenAnimation() {
        openAnimation = Rotate3DAnimation(0f, 180f)
        openAnimation.duration = duration
        openAnimation.fillAfter = true
    }

    private fun initCloseAnimation() {
        closeAnimation = Rotate3DAnimation(180f, 0f)
        closeAnimation.duration = duration
        closeAnimation.fillAfter = true
    }
}