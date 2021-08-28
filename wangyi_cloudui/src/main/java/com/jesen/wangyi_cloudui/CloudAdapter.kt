package com.jesen.wangyi_cloudui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CloudAdapter(context: Context):RecyclerView.Adapter<CloudAdapter.CloudViewHolder>() {

    private val mLayoutInflater = LayoutInflater.from(context)
    private val mTitles = context.resources.getStringArray(R.array.music)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CloudViewHolder {
        return CloudViewHolder(mLayoutInflater.inflate(R.layout.item_list,parent,false))
    }

    override fun onBindViewHolder(holder: CloudViewHolder, position: Int) {
        holder.music_name.text = mTitles[position]
        holder.item_position.text = position.toString()
    }

    override fun getItemCount(): Int {
        return mTitles.size
    }

    inner class CloudViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val music_name = itemView.findViewById<TextView>(R.id.music_name)
        val item_position = itemView.findViewById<TextView>(R.id.item_postion)
    }

}