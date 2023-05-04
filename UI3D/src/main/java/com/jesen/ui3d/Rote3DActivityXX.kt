package com.jesen.ui3d
import android.os.Bundle
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.Animation
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jesen.ui3d.util.Rotate3DAnimationX

/**
 * 升级版：
 * 旋转后展示更换布局的展示，即前后两面的内容不一样
 * */
class Rote3DActivityXX : AppCompatActivity() {

    private lateinit var mContent: View
    private val duration = 600L
    private lateinit var openAnimation: Rotate3DAnimationX
    private lateinit var closeAnimation: Rotate3DAnimationX
    private var isOpen = false

    private lateinit var layoutImage:View
    private lateinit var layoutLogin:View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rotate_3dxx)

        layoutImage = findViewById(R.id.i_image)
        layoutLogin = findViewById(R.id.i_login)

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
        openAnimation = Rotate3DAnimationX(0f, 90f,true)
        openAnimation.duration = duration
        openAnimation.interpolator = AccelerateInterpolator()
        openAnimation.fillAfter = true
        openAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                changeUI(false)
                val openAnimation2 = Rotate3DAnimationX(90f,180f,false)
                openAnimation2.duration = duration
                openAnimation2.interpolator= DecelerateInterpolator()
                openAnimation2.fillAfter = true
                mContent.startAnimation(openAnimation2)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }

    private fun changeUI(default: Boolean){
        if (default){
            layoutImage.visibility = View.VISIBLE
            layoutLogin.visibility = View.GONE
        }else{
            layoutImage.visibility = View.GONE
            layoutLogin.visibility = View.VISIBLE
        }
    }

    private fun initCloseAnimation() {
        closeAnimation = Rotate3DAnimationX(180f, 90f,true)
        closeAnimation.duration = duration
        closeAnimation.interpolator = AccelerateInterpolator()
        closeAnimation.fillAfter = true
        closeAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}
            override fun onAnimationEnd(animation: Animation?) {
                changeUI(true)
                val closeAnimation2 = Rotate3DAnimationX(90f,0f,false)
                closeAnimation2.duration = duration
                closeAnimation2.interpolator= DecelerateInterpolator()
                closeAnimation2.fillAfter = true
                mContent.startAnimation(closeAnimation2)
            }
            override fun onAnimationRepeat(animation: Animation?) {}
        })
    }
}