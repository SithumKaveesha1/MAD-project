package com.example.exam02

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class Profile : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile)

        var btnNext = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.back1)
        btnNext.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

        var btnNextETC1 = findViewById<ImageView>(R.id.imageView922)
        btnNextETC1.setOnClickListener {
            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }

        var btnNextETC2 = findViewById<TextView>(R.id.textView44)
        btnNextETC2.setOnClickListener {
            val intent = Intent(this, Notification::class.java)
            startActivity(intent)
        }

        var btnNextETC4 = findViewById<ImageView>(R.id.imageView13)
        btnNextETC4.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var btnNextETC3 = findViewById<TextView>(R.id.textView8)
        btnNextETC3.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var btnNextETC6 = findViewById<ImageView>(R.id.imageView10)
        btnNextETC6.setOnClickListener {
            val intent = Intent(this, News::class.java)
            startActivity(intent)
        }

        var btnNextETC5 = findViewById<TextView>(R.id.textView6)
        btnNextETC5.setOnClickListener {
            val intent = Intent(this, News::class.java)
            startActivity(intent)
        }
    }
}
//profile