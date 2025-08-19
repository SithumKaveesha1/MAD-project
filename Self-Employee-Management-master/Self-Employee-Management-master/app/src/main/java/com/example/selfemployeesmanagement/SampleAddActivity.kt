package com.example.selfemployeesmanagement
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SampleAddActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sampleadd )
        supportActionBar?.hide()
    }
}