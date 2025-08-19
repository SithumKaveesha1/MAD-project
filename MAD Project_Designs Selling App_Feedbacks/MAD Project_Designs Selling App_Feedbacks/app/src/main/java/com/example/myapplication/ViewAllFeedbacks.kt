package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityViewAllFeedbacksBinding
import com.example.white_rabbit.Adapters.FeedbackAdapter
import com.example.white_rabbit.DataClasses.Feedback
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class ViewAllFeedbacks : AppCompatActivity() {
    private lateinit var binding: ActivityViewAllFeedbacksBinding
    private lateinit var databaseRef: DatabaseReference
    private lateinit var recyclerView: RecyclerView
    private var mList = ArrayList<Feedback>()
    private lateinit var adapter: FeedbackAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAllFeedbacksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //connect to database
        databaseRef = FirebaseDatabase.getInstance().reference
            .child("feedback")


        recyclerView = binding.recyclerview
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        retrieveData()
        adapter = FeedbackAdapter(mList)
        recyclerView.adapter = adapter

       //item on click
        adapter.setOnItemClickListner(object: FeedbackAdapter.onItemClickListner {
            override fun onItemClick(position: Int) {
                intent = Intent(applicationContext, ViewAFeedback::class.java).also {
                    it.putExtra("id", mList[position].id)
                    it.putExtra("des", mList[position].dis)
                    it.putExtra("name", mList[position].userName)
                    it.putExtra("rating", mList[position].ratingScore)
                    startActivity(it)
                }
            }
        })

        binding.submitFeedbackBtn.setOnClickListener {
            intent = Intent(applicationContext, AddFeedback::class.java)
            startActivity(intent)
        }


    }


    private fun retrieveData() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                for ( snapshot in snapshot.children){
                    val req = snapshot.getValue(Feedback::class.java)!!
                    if( req != null){
                        mList.add(req)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ViewAllFeedbacks, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}
