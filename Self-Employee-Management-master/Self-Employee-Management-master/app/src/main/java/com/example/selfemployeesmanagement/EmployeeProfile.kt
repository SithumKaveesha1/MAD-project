package com.example.selfemployeesmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EmployeeProfile : AppCompatActivity() {

    private lateinit var tvFirstName: TextView
    private lateinit var tvContactNo: TextView
    private lateinit var tvTalentDescription: TextView

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var dbref : DatabaseReference
    private var employee: Employee? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employeeprofile)
        supportActionBar?.hide()


        tvFirstName = findViewById(R.id.textView20)
        tvContactNo = findViewById(R.id.textView29)
        tvTalentDescription = findViewById(R.id.textView31)

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Employees").child(FirebaseAuth.getInstance().currentUser!!.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstName = dataSnapshot.child("fname").getValue(String::class.java)
                    val phone = dataSnapshot.child("uphoneNo").getValue(String::class.java)
                    val talent = dataSnapshot.child("udescription").getValue(String::class.java)

                    tvFirstName.setText(firstName)
                    tvContactNo.setText(phone)
                    tvTalentDescription.setText(talent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //err
            }
        })
    }
}

