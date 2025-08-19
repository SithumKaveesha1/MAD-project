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

class homepage : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var btn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_homepage)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<View>(R.id.cappuccino3).setOnClickListener {
            val intent = Intent(this, details::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageButton14).setOnClickListener {
            val intent = Intent(this, profile::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView60).setOnClickListener {
            val intent = Intent(this, order::class.java)
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
    }
}