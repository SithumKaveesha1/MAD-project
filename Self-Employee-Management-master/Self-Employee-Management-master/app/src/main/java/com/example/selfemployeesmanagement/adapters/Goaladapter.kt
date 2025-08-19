package com.example.selfemployeesmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.goalsmodel

class goaladapter (private val goallist : ArrayList<goalsmodel>) : RecyclerView.Adapter<goaladapter.ViewHolder>() {

    private lateinit var mListner : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListner: onItemClickListner){
        mListner = clickListner
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.goal_list, parent, false)
        return ViewHolder(itemView, mListner)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentgoal = goallist[position]
        holder.tvgoalName.text = currentgoal.goalname
    }


    override fun getItemCount(): Int {
        return goallist.size
    }

    class ViewHolder(itemView:View, clickListner: onItemClickListner) : RecyclerView.ViewHolder(itemView) {

        val tvgoalName : TextView = itemView.findViewById(R.id.mygoal)

        init {
            itemView.setOnClickListener {
                clickListner.onItemClick(adapterPosition)
            }
        }

    }

}