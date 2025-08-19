package com.example.selfemployeesmanagement.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.adapters.TaskAdapter
import com.example.selfemployeesmanagement.models.TaskModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FetchingTaskActivity : AppCompatActivity() {

    private lateinit var taskRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var taskList: ArrayList<TaskModel>
    private lateinit var tempArrayList: ArrayList<TaskModel>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_fetching)

        taskRecyclerView = findViewById(R.id.rvTask)
        taskRecyclerView.layoutManager = LinearLayoutManager(this)
        taskRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        taskList = arrayListOf<TaskModel>()
        tempArrayList = arrayListOf<TaskModel>()

        getTaskData()

    }




    private fun getTaskData(){

        taskRecyclerView.visibility= View.GONE
        tvLoadingData.visibility=View.VISIBLE
        dbRef= FirebaseDatabase.getInstance().getReference("Tasks")

        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                taskList.clear()
                if(snapshot.exists()){
                    for(taskSnap in snapshot.children){
                        val taskData=taskSnap.getValue(TaskModel::class.java)
                        taskList.add(taskData!!)
                    }

                    tempArrayList.addAll(taskList)

                    val mAdapter = TaskAdapter(taskList)
                    taskRecyclerView.adapter= mAdapter

                    mAdapter.setOnItemClickListener(object :TaskAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent= Intent(this@FetchingTaskActivity, TaskDetailsActivity::class.java)

                            //put extra
                            intent.putExtra("empId", taskList[position].empId)
                            intent.putExtra("empTitle", taskList[position].empTitle)
                            intent.putExtra("empDescription", taskList[position].empDescription)
                            intent.putExtra("empDate", taskList[position].empDate)
                            intent.putExtra("empEvent", taskList[position].empEvent)
                            startActivity(intent)
                        }

                    })
                    taskRecyclerView.visibility= View.VISIBLE
                    tvLoadingData.visibility= View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}