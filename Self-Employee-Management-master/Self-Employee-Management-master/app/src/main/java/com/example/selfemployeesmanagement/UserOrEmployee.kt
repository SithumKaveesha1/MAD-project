package com.example.selfemployeesmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.selfemployeesmanagement.databinding.ActivityMainBinding
import com.example.selfemployeesmanagement.databinding.UserregisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class UserOrEmployee : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.useroremployee)
        supportActionBar?.hide()

        val userButton: Button = findViewById(R.id.generalUserBtn)

        userButton.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val empButton: Button = findViewById(R.id.employeeBtn)

        empButton.setOnClickListener {
            val intent = Intent(this, EmployeeSignup::class.java)
            startActivity(intent)
        }
    }
}