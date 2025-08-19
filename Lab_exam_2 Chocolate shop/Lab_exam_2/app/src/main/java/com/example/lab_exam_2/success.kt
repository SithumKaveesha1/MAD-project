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

class success : AppCompatActivity() {
    private lateinit var btn: Button
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_success)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        findViewById<View>(R.id.imageView63).setOnClickListener {
            val intent = Intent(this, setting::class.java)
            startActivity(intent)
        }
        findViewById<View>(R.id.imageView61).setOnClickListener {
            val intent = Intent(this, homepage::class.java)
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
        findViewById<View>(R.id.imageView25).setOnClickListener {
            val intent = Intent(this, order::class.java)
            startActivity(intent)

        }
        findViewById<View>(R.id.imageView58).setOnClickListener {
            val intent = Intent(this, homepage::class.java)
            startActivity(intent)

        }
    }
}