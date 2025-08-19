package com.example.selfemployeesmanagement.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.TaskModel

class TaskAdapter (private  val taskList: ArrayList<TaskModel>):
    RecyclerView.Adapter<TaskAdapter.ViewHolder>(){

    private  lateinit var mListener: onItemClickListener
    interface onItemClickListener{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListener(clickListener: onItemClickListener){
        mListener =clickListener
    }

   /* fun searchDataList(searchList:List<DataClass>){
        dataList=searchList
        notifyDataSetChanged()
    }*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val itemView=LayoutInflater.from(parent.context).inflate(R.layout.task_list_item,parent,false)
        return ViewHolder(itemView,mListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val currenttask= taskList[position]
        holder.tvTaskName.text=currenttask.empTitle
    }



    override fun getItemCount(): Int {
       return taskList.size
    }
    class ViewHolder (itemView: View, clickListener: onItemClickListener):RecyclerView.ViewHolder(itemView){

        val tvTaskName :TextView =itemView.findViewById(R.id.tvTaskName)

        init {
            itemView.setOnClickListener{
                clickListener.onItemClick(adapterPosition)
            }
        }
    }
}
