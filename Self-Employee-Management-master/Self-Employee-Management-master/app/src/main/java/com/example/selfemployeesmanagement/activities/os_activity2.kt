package com.example.selfemployeesmanagement.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.adapters.goaladapter
import com.example.selfemployeesmanagement.models.goalsmodel
import com.google.firebase.database.*

class os_activity2 : AppCompatActivity() {

    private lateinit var goalrecview: RecyclerView
    private lateinit var goallist: ArrayList<goalsmodel>
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_os2)

        goalrecview = findViewById<RecyclerView>(R.id.recycler)
        goalrecview.layoutManager = LinearLayoutManager(this)
        goalrecview.setHasFixedSize(true)

        goallist = arrayListOf<goalsmodel>()

        getGoalsData()
    }

    private fun getGoalsData() {
        goalrecview.visibility = View.GONE

        dbRef = FirebaseDatabase.getInstance().getReference("Goals")  //Referencing database with the database name 'Goals'

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                goallist.clear()
                if (snapshot.exists()) {
                    for (goalsnap in snapshot.children) {
                        val goalsData = goalsnap.getValue(goalsmodel::class.java)
                        goallist.add(goalsData!!)
                    }

                    val mAdapter = goaladapter(goallist)
                    goalrecview.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : goaladapter.onItemClickListner {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(this@os_activity2, detailgoals::class.java)

                            //put extras
                            intent.putExtra("goalid", goallist[position].goalID)
                            intent.putExtra("goalname", goallist[position].goalname)
                            intent.putExtra("goalprice", goallist[position].price)
                            intent.putExtra("goaltime", goallist[position].time)
                            startActivity(intent)
                        }

                    })

                    goalrecview.visibility = View.VISIBLE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })


    }
}