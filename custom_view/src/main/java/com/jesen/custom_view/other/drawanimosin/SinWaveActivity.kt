package com.jesen.custom_view.other.drawanimosin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jesen.custom_view.R

class SinWaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sin_wave)

        val csView = findViewById<CustomSinView>(R.id.csView)
         lifecycle.addObserver(csView)
    }
}