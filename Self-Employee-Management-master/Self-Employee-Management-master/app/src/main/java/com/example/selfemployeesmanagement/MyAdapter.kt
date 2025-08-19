package com.example.selfemployeesmanagement

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val employeeList : ArrayList<Employee>) : RecyclerView.Adapter<MyAdapter.MyViewHolder> () {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.employee_item,parent,false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentitem = employeeList[position]

        holder.name1.text = currentitem.fname
        holder.job.text = currentitem.ujob
        holder.description.text = currentitem.udescription

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, EmployeeProfile::class.java)
            intent.putExtra("employeeId", currentitem.uid)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return employeeList.size
    }



    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val name1 : TextView = itemView.findViewById(R.id.tvEmployeeName)
        val job : TextView = itemView.findViewById(R.id.tvEmployeeJob)
        val description : TextView = itemView.findViewById(R.id.tvEmployeeDescription)

    }
}