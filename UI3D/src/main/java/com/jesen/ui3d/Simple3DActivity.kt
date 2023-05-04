package com.jesen.ui3d

import android.os.Bundle
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.jesen.ui3d.view.CameraImageView

class Simple3DActivity : AppCompatActivity() {
    private lateinit var imageView: CameraImageView
    private lateinit var textView:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple3_dactivity)

        initView()
    }

    private fun initView() {
        imageView = findViewById(R.id.imageView)
        textView = findViewById(R.id.textView)

        findViewById<SeekBar>(R.id.seekBar).setOnSeekBarChangeListener(object :OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                imageView.setProgress(progress)
                textView.text = "$progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        findViewById<RadioGroup>(R.id.xyzRG).setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.ax->{}
            }
        }
    }
}