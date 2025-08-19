package com.example.quickloan.ksactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


import android.view.View
import android.widget.Button
import android.widget.ImageButton

import android.widget.ImageView
import com.example.quickloan.Bu_dashboard
import com.example.quickloan.R

class Ks_ButtonActivity : AppCompatActivity() {

    private lateinit var btnApply: ImageView
    private lateinit var btnManege: ImageView
    private lateinit var btnBack: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ks_button)


        btnApply = findViewById(R.id.btnApply)
        btnManege = findViewById(R.id.btnManege)
        btnBack = findViewById(R.id.btnBack4)


        btnApply.setOnClickListener {
            navigateToNextActivity()


        }

        btnManege.setOnClickListener {
            navigateTonextActivity()
        }
        btnBack.setOnClickListener {
            val intent = Intent(this, Bu_dashboard::class.java)
            startActivity(intent)
            finish()
        }

    }

    private fun navigateToNextActivity() {
        val intent = Intent(this, ks_ApplyLoan::class.java)
        startActivity(intent)
    }

    private fun  navigateTonextActivity() {
        val intent = Intent(this, Ks_fetchingActivity2::class.java)
        startActivity(intent)
    }


}