package com.example.selfemployeesmanagement

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class EmployeeListActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var employeeRecyclerView : RecyclerView
    private lateinit var employeeArrayList : ArrayList<Employee>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employeelist)
        supportActionBar?.hide()

        employeeRecyclerView = findViewById(R.id.rvEmployeeList)
        employeeRecyclerView.layoutManager = LinearLayoutManager(this)
        employeeRecyclerView.setHasFixedSize(true)

        employeeArrayList = arrayListOf<Employee>()

        getUserData()
    }

    private fun getUserData() {

        dbref = FirebaseDatabase.getInstance().getReference("Employees")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()){
                    for (employeeSnapshot in snapshot.children){

                        val emp = employeeSnapshot.getValue(Employee::class.java)
                        employeeArrayList.add(emp!!)
                    }

                    employeeRecyclerView.adapter = MyAdapter(employeeArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}