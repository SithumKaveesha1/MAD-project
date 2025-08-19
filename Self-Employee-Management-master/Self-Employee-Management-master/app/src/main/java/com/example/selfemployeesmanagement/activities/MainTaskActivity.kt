package com.example.selfemployeesmanagement.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.selfemployeesmanagement.R

class MainTaskActivity : AppCompatActivity() {
    private lateinit var btnInsertData:Button
    private lateinit var btnFetchData:Button
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.task_activity_main)

       // val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()

        btnInsertData= findViewById(R.id.btnInsertData)
        btnFetchData=findViewById(R.id.btnFetchData)

        btnInsertData.setOnClickListener{
            val intent = Intent(this, InsertionTaskActivity::class.java)
            startActivity(intent)
        }
        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingTaskActivity::class.java)
            startActivity(intent)



        }
    }
}