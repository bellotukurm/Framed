package com.example.framed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


//This is not implemented yet
class TrendingRecyclerViewAdapter: RecyclerView.Adapter<TrendingRecyclerViewAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TrendingRecyclerViewAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_game_poster2, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }

    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val v: View = LayoutInflater.from(mContext).inflate(R.layout)
    }
}