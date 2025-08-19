package com.example.quickloan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.quickloan.models.CalHistory
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Bu_addCalHistory : AppCompatActivity() {
    private lateinit var etUserFName: EditText
    private lateinit var etUserLName: EditText
    private lateinit var calLoanAmount: EditText
    private lateinit var calLoanTime: EditText
    private lateinit var calLoanRate: EditText
    private lateinit var calLoanMonthlypayment: EditText
    private lateinit var btnSubmitData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bu_add_cal_history)

        etUserFName = findViewById(R.id.first_namecal)
        etUserLName = findViewById(R.id.last_name)
        calLoanAmount = findViewById(R.id.loan_amount)
        calLoanTime = findViewById(R.id.loan_time_edit)
        calLoanRate = findViewById(R.id.interest_rate_cal)
        calLoanMonthlypayment = findViewById(R.id.edit_monthly_pay)
        btnSubmitData = findViewById(R.id.cal_submit)

        dbRef = FirebaseDatabase.getInstance().getReference("Calculator_History")

        btnSubmitData.setOnClickListener {
            if (validateInputs()) {
                saveUserData()
            }
        }
    }

    private fun saveUserData() {

        //getting values
        val etFName = etUserFName.text.toString()
        val etLName = etUserLName.text.toString()
        val calAmount = calLoanAmount.text.toString()
        val calTime = calLoanTime.text.toString()
        val calRate = calLoanRate.text.toString()
        val calMonthlypayment = calLoanMonthlypayment.text.toString()



        if (etFName.isEmpty()) {
            etUserFName.error = "Please enter  first name"
        }
        if (etLName.isEmpty()) {
            etUserLName.error = "Please enter last name"
        }
        if (calAmount.isEmpty()) {
            calLoanAmount.error = "Please enter amount"
        }
        if (calTime.isEmpty()) {
            calLoanTime.error = "Please enter time"
        }
        if (calRate.isEmpty()) {
            calLoanRate.error = "Please enter interest rate"
        }
        if (calMonthlypayment.isEmpty()) {
            calLoanMonthlypayment.error = "Please enter payment"
        }

        val userId = dbRef.push().key!!

        val user = CalHistory( userId, etFName, etLName, calAmount,calTime,calRate,calMonthlypayment)

        dbRef.child(userId).setValue(user)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etUserFName.text.clear()
                etUserLName.text.clear()
                calLoanAmount.text.clear()
                calLoanTime.text.clear()
                calLoanRate.text.clear()
                calLoanMonthlypayment.text.clear()



            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val etFName = etUserFName.text.toString().trim()
        val etLName = etUserLName.text.toString().trim()
        val calAmount = calLoanAmount.text.toString().trim()
        val calTime = calLoanTime.text.toString().trim()
        val calRate = calLoanRate.text.toString().trim()
        val calMonthlypayment = calLoanMonthlypayment.text.toString().trim()

        if (etFName.isEmpty()) {
            etUserFName.error = "Please enter first name"
            isValid = false
        }
        if (etLName.isEmpty()) {
            etUserLName.error = "Please enter last name"
            isValid = false
        }
        if (calAmount.isEmpty()) {
            calLoanAmount.error = "Please enter amount"
            isValid = false
        }
        if (calTime.isEmpty()) {
            calLoanTime.error = "Please enter time"
            isValid = false
        }
        if (calRate.isEmpty()) {
            calLoanRate.error = "Please enter interest rate"
            isValid = false
        }
        if (calMonthlypayment.isEmpty()) {
            calLoanMonthlypayment.error = "Please enter payment"
            isValid = false
        }

        return isValid


    }


}