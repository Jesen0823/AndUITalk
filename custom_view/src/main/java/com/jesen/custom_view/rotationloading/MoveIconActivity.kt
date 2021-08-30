package com.jesen.custom_view.rotationloading

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MoveIconActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_move_icon)
        setContentView(MoveIcon(this))
    }
}