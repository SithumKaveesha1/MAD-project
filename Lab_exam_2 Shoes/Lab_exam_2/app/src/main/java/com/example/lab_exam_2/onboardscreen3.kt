package com.example.lab_exam_2

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class onboardscreen3 : AppCompatActivity() {

    private lateinit var btn: ImageButton
    private lateinit var btn2: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_onboardscreen3)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


            findViewById<View>(R.id.imageButton4).setOnClickListener {
                val intent = Intent(this, getstarted::class.java)
                startActivity(intent)
            }
            findViewById<View>(R.id.button4).setOnClickListener {
                val intent = Intent(this, onboardscreen2::class.java)
                startActivity(intent)
            }
        }
    }
