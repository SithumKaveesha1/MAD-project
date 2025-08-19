package com.example.quickloan


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.quickloan.ksactivities.Ks_ButtonActivity
import com.example.quickloan.ksactivities.Ks_fetchingActivity2
import com.example.quickloan.ksactivities.UserProfileActivity
import com.example.quickloan.ksactivities.ks_ApplyLoan


class Bu_dashboard : AppCompatActivity() {

     private lateinit var btndashapply: ImageView
     private lateinit var btndashcal: ImageView
    private lateinit var btndashstatus: ImageView
    private lateinit var btndashrepay: ImageView
    private lateinit var btndashprofile: ImageView
    private lateinit var btndashmessage: ImageView
    private lateinit var btndashlogin: ImageView
    private lateinit var btndashcalhistory: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bu_dashboard)

        btndashapply = findViewById(R.id.dash_btnApply)
        btndashcal = findViewById(R.id.dash_btncal)
        btndashstatus = findViewById(R.id.dash_btntrack)
        btndashrepay = findViewById(R.id.dash_btnrepay)
        btndashprofile  = findViewById(R.id.dash_btncalprofile)
        btndashmessage  = findViewById(R.id.dash_btnmessage)
        btndashlogin  = findViewById(R.id.dash_btnlogin)
        btndashcalhistory  = findViewById(R.id.dash_btncalhistory)

        btndashapply.setOnClickListener {
            navigateToNextapplyActivity()

        }

        btndashcal.setOnClickListener {
            navigateToNextcalActivity()
        }
        btndashstatus.setOnClickListener {
            navigateToNextstatusActivity()
        }
        btndashrepay.setOnClickListener {
            navigateToNextrepayActivity()
        }
        btndashprofile.setOnClickListener {
            navigateToNextprofileActivity()
        }
        btndashmessage.setOnClickListener {
            navigateToNextmsgActivity()
        }
        btndashlogin.setOnClickListener {
            navigateToNextloginActivity()
        }
        btndashcalhistory .setOnClickListener {
            navigateToNexthistoryActivity()
        }
    }

    private fun navigateToNextapplyActivity() {
        val intent = Intent(this, Ks_ButtonActivity::class.java)
        startActivity(intent)
    }

    private fun  navigateToNextcalActivity() {
        val intent = Intent(this, Bu_Calculator::class.java)
        startActivity(intent)
    }
    private fun  navigateToNextstatusActivity() {
        val intent = Intent(this, Bu_Calculator::class.java)
        startActivity(intent)
    }
    private fun  navigateToNextrepayActivity() {
        val intent = Intent(this, nv_repaymentDasboard::class.java)
        startActivity(intent)
    }
    private fun  navigateToNextprofileActivity() {
        val intent = Intent(this, UserProfileActivity::class.java)
        startActivity(intent)
    }
    private fun  navigateToNextmsgActivity() {
        val intent = Intent(this, Bu_Calculator::class.java)
        startActivity(intent)
    }
    private fun  navigateToNextloginActivity() {
        val intent = Intent(this, activity_nv_signin::class.java)
        startActivity(intent)
    }
    private fun  navigateToNexthistoryActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

//
    }

