package com.jesen.custom_view.other.customviewgroup

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class HorRcAdapter() : RecyclerView.Adapter<HorRcAdapter.HorViewHolder>() {

    private var datas: List<String>? = null

    fun setDataList(dataList: List<String>) {
        datas = dataList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HorViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_gallery_item, parent, false)
        return HorViewHolder(view)
    }

    override fun onBindViewHolder(holder: HorViewHolder, position: Int) {
        holder.tv.text = datas?.get(position) ?: "default"
    }

    override fun getItemCount(): Int = datas?.size?:0

    inner class HorViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById<TextView>(android.R.id.text1)
    }
}