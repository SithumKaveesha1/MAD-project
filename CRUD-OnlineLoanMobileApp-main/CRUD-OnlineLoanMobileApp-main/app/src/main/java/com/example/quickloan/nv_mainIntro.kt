package com.example.quickloan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class nv_mainIntro : AppCompatActivity() {
    //nik
    private lateinit var btnIntro:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_main_intro)

        val firebase : DatabaseReference = FirebaseDatabase.getInstance().getReference()
//nik
        btnIntro=findViewById(R.id.btn_getStarted)

        btnIntro.setOnClickListener {
            val intent = Intent(this,activity_nv_signin::class.java)
            startActivity(intent)
        }



    }
}





