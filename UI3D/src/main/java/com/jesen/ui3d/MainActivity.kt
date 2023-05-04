package com.jesen.ui3d

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.jesen.ui3d.util.Utils

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btn_simple).setOnClickListener {
            Utils.simpleStartActivity<Simple3DActivity>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_rotate3d).setOnClickListener {
            Utils.simpleStartActivity<Rote3DActivity>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_rotate3d_x).setOnClickListener {
            Utils.simpleStartActivity<Rote3DActivityX>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_rotate3d_xx).setOnClickListener {
            Utils.simpleStartActivity<Rote3DActivityXX>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_rotate_layout).setOnClickListener {
            Utils.simpleStartActivity<RotateLayoutActivity>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_poly_to_poly1).setOnClickListener {
            Utils.simpleStartActivity<PolyToPolyActivity>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_poly_to_poly_shadow).setOnClickListener {
            Utils.simpleStartActivity<PolyToPolyUpdateActivity>(this@MainActivity){}
        }
        findViewById<Button>(R.id.btn_poly_to_poly_drawer).setOnClickListener {
            Utils.simpleStartActivity<DrawerFoldActivity>(this@MainActivity){}
        }
    }
}