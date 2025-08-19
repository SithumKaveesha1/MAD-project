package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication.databinding.ActivityViewAfeedbackBinding
import com.example.myapplication.databinding.ActivityViewAllFeedbacksBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ViewAFeedback : AppCompatActivity() {
    private lateinit var binding: ActivityViewAfeedbackBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewAfeedbackBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var uid = 100.toString()
        var pid = 20.toString()
        var ratingScore = 0
        var currentFedId = ""

        //connect to database
        databaseRef = FirebaseDatabase.getInstance().reference.child("feedback")

        //fetch values from the intent
        val id = intent.getStringExtra("id").toString()
        val des = intent.getStringExtra("des").toString()
        val name = intent.getStringExtra("name").toString()
        val rating = intent.getStringExtra("rating").toString()

        binding.etNewDescription.setText(des)
        binding.etName.setText(name)
        binding.ratingBar.rating = rating.toFloat()
        currentFedId = id

        binding.updateButton.setOnClickListener {
            val dis = binding.etNewDescription.text.toString()
            val userName = binding.etName.text.toString()

            if (dis.isEmpty() || name.isEmpty()) {
                if (dis.isEmpty()) {
                    binding.etNewDescription.error = "Enter feedback"
                }
                if (name.isEmpty()) {
                    binding.etName.error = "Enter your name"
                }
            }/* else if( ratingScore == 0) {
                Toast.makeText(
                    this, "Please add your rating", Toast.LENGTH_SHORT).show()
            }*/else {
                val map = HashMap<String, Any>()

                //add data to hashMap
                map["dis"] = dis
                map["userName"] = userName
                map["ratingScore"] = ratingScore.toString()


                //update database from hashMap
                databaseRef.child(currentFedId).updateChildren(map).addOnCompleteListener {
                    if (it.isSuccessful) {
                        intent = Intent(applicationContext, ViewAllFeedbacks::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Feedback updated", Toast.LENGTH_SHORT)
                            .show()
                    } else {
                        Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }


        binding.ratingBar.setOnRatingBarChangeListener { bar, fl, b ->

            when (bar.rating.toInt()) {
                1 -> ratingScore = 1
                2 -> ratingScore = 2
                3 -> ratingScore = 3
                4 -> ratingScore = 4
                5 -> ratingScore = 5
                else -> ratingScore = 0
            }
        }

        binding.deleteButton.setOnClickListener {
            databaseRef.child(currentFedId).removeValue().addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Feedback deleted", Toast.LENGTH_SHORT).show()
                    intent = Intent(applicationContext, ViewAllFeedbacks::class.java)
                    startActivity(intent)
                }
            }
        }
    }
}