package com.jesen.custom_view.surfaceview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesen.custom_view.R

class AniSurfaceViewActivity : AppCompatActivity() {

    private lateinit var animSurfaceView:AnimSurfaceView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ani_surface_view)

        animSurfaceView = findViewById<AnimSurfaceView>(R.id.animSurfaceView)
    }

    override fun onResume() {
        super.onResume()
    }
}