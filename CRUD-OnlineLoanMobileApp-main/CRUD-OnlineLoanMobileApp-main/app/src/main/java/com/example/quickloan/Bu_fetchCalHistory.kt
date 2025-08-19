package com.example.quickloan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quickloan.adapters.UserAdapter
import com.example.quickloan.models.CalHistory
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class Bu_fetchCalHistory : AppCompatActivity() {
    private lateinit var userRecyclerView: RecyclerView
    private lateinit var tvLoadingData: TextView
    private lateinit var userList: ArrayList<CalHistory>
    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bu_fetch_cal_history)

        userRecyclerView = findViewById(R.id.rvUser)
        userRecyclerView.layoutManager = LinearLayoutManager(this)
        userRecyclerView.setHasFixedSize(true)
        tvLoadingData = findViewById(R.id.tvLoadingData)

        userList = arrayListOf<CalHistory>()

        getUserData()

    }

    private fun  getUserData() {

        userRecyclerView.visibility = View.GONE
        tvLoadingData.visibility = View.VISIBLE

        dbRef = FirebaseDatabase.getInstance().getReference("Calculator_History")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                if (snapshot.exists()){
                    for (empSnap in snapshot.children){
                        val userData = empSnap.getValue(CalHistory::class.java)
                        userList.add(userData!!)
                    }
                    val mAdapter = UserAdapter(userList)
                    userRecyclerView.adapter = mAdapter

                    mAdapter.setOnItemClickListener(object : UserAdapter.onItemClickListener{
                        override fun onItemClick(position: Int) {

                            val intent = Intent(this@Bu_fetchCalHistory, Bu_calDetails::class.java)

                            //put extras
                            intent.putExtra("userId", userList [position].userId)
                            intent.putExtra("etFName", userList[position].etFName)
                            intent.putExtra("etLName", userList[position].etLName)
                            intent.putExtra("calAmount", userList[position].calAmount)
                            intent.putExtra("calTime", userList[position].calTime)
                            intent.putExtra("calRate", userList[position].calRate)
                            intent.putExtra("calMonthlypayment", userList[position].calMonthlypayment)

                            startActivity(intent)



                        }

                    })

                    userRecyclerView.visibility = View.VISIBLE
                    tvLoadingData.visibility = View.GONE
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}