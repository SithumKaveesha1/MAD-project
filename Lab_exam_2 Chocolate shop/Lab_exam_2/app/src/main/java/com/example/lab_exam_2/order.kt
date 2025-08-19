package com.example.lab_exam_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class order : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var btn: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_order)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets

        }
        findViewById<View>(R.id.imageView24).setOnClickListener {
            val intent = Intent(this, details::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView61).setOnClickListener {
            val intent = Intent(this, homepage::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView62).setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView63).setOnClickListener {
            val intent = Intent(this, setting::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView58).setOnClickListener {
            val intent = Intent(this, homepage::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.button17).setOnClickListener {
            val intent = Intent(this, success::class.java)
            startActivity(intent)
        }

    }
}