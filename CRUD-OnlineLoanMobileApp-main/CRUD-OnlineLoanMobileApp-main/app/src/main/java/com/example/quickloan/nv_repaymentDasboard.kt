package com.example.quickloan

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView

class nv_repaymentDasboard : AppCompatActivity() {

        private lateinit var tvPayNow: ImageView
    private lateinit var tvViewAllPay:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_repayment_dasboard)

        tvPayNow=findViewById(R.id.paynowIMG)
        tvViewAllPay=findViewById(R.id.manageIMG)

        tvPayNow.setOnClickListener {
            val intent = Intent(this,nv_PaymentForm::class.java)
            startActivity(intent)
        }
        tvViewAllPay.setOnClickListener {
            val intent = Intent(this,nv_payments::class.java)
            startActivity(intent)
        }
    }
}