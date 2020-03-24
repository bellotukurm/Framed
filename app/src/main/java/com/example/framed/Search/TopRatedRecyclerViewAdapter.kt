package com.example.framed

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class TopRatedRecyclerViewAdapter: RecyclerView.Adapter<TopRatedRecyclerViewAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int
    ): TopRatedRecyclerViewAdapter.CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.item_game_poster1, parent, false)
        return CustomViewHolder(cellForRow)
    }





    //abstract val mContext: Context




    override fun getItemCount(): Int {
        return 7
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

    }


    class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //val v: View = LayoutInflater.from(mContext).inflate(R.layout)
    }
}