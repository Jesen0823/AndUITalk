package com.jesen.custom_view.other.customviewgroup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jesen.custom_view.R

class HorizonPagerActivity : AppCompatActivity() {

    private lateinit var recyclerView1: RecyclerView
    private lateinit var recyclerView2:RecyclerView
    val data1 = arrayListOf<String>(
        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
        "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB",
        "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
        "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC",
        "DDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD",
        "EEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE",
        "FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF"
    )

    val data2 = arrayListOf<String>(
        "77887655555555555667777777777777",
        "%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%",
        "YYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY",
        "%^&*(((((((((((((((((((((((((((((",
        "$$$$$$$$$$$55555555555555555555555",
        "67766666666666666666655555555555555"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizon_pager)

        recyclerView1 = findViewById(R.id.rc1)
        recyclerView2 = findViewById(R.id.rc2)

        initView()
    }

    private fun initView() {
        val laoutManager1 = LinearLayoutManager(this)
        val laoutManager2 = LinearLayoutManager(this)
        val adapter1 = HorRcAdapter()
        val adapter2 = HorRcAdapter()

        adapter1.setDataList(data1)
        adapter2.setDataList(data2)

        recyclerView1.layoutManager = laoutManager1
        recyclerView2.layoutManager = laoutManager2
        recyclerView1.adapter = adapter1
        recyclerView2.adapter = adapter2
    }
}