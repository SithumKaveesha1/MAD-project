package com.example.quickloan

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.quickloan.models.CalHistory
import com.google.firebase.database.FirebaseDatabase

class Bu_calDetails : AppCompatActivity() {
    private lateinit var tUserId: TextView
    private lateinit var tvUserFName: TextView
    private lateinit var tvUserLName: TextView
    private lateinit var tvUserLAmount: TextView
    private lateinit var tvUserLTime: TextView
    private lateinit var tvUserLRate: TextView
    private lateinit var tvUserMonthLPay: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bu_cal_details)

        initView()
        setValuesToViews()



        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("userId").toString(),
                intent.getStringExtra("etFName").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("userId").toString()
            )
        }

    }

    private fun initView() {
        tUserId = findViewById(R.id.tvUserId)
        tvUserFName = findViewById(R.id.tvUserFname)
        tvUserLName = findViewById(R.id.tvUserLName)
        tvUserLAmount = findViewById(R.id.tvUserAmount)
        tvUserLTime = findViewById(R.id.tvUserLTime)
        tvUserLRate = findViewById(R.id.tvUserLRate)
        tvUserMonthLPay = findViewById(R.id.tvUserMonthPay)


        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tUserId.text = intent.getStringExtra("userId")
        tvUserFName.text = intent.getStringExtra("etFName")
        tvUserLName.text = intent.getStringExtra("etLName")
        tvUserLAmount.text = intent.getStringExtra("calAmount")
        tvUserLTime.text = intent.getStringExtra("calTime")
        tvUserLRate.text = intent.getStringExtra("calRate")
        tvUserMonthLPay.text = intent.getStringExtra("calMonthlypayment")

    }

private fun deleteRecord(
    id: String
){
    val dbRef = FirebaseDatabase.getInstance().getReference("Calculator_History").child(id)
    val mTask = dbRef.removeValue()

    mTask.addOnSuccessListener {
        Toast.makeText(this, "User data deleted", Toast.LENGTH_LONG).show()

        val intent = Intent(this, Bu_fetchCalHistory::class.java)
        finish()
        startActivity(intent)
    }.addOnFailureListener{ error ->
        Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
    }
}

@SuppressLint("MissingInflatedId")
private fun openUpdateDialog(
    userId: String,
    etFName: String
) {
    val mDialog = AlertDialog.Builder(this)
    val inflater = layoutInflater
    val mDialogView = inflater.inflate(R.layout.bu_updatecalhostory, null)

    mDialog.setView(mDialogView)

    val etFName = mDialogView.findViewById<EditText>(R.id.etuserFname)
    val etLName = mDialogView.findViewById<EditText>(R.id.etuserLname)
    val calAmount = mDialogView.findViewById<EditText>(R.id.etuserAmount)
    val calTime = mDialogView.findViewById<EditText>(R.id.etuserTime)
    val calRate = mDialogView.findViewById<EditText>(R.id.etuserRate)
    val calMonthlypayment = mDialogView.findViewById<EditText>(R.id.etuserMonthlyp)

    val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

    etFName.setText(intent.getStringExtra("etFName").toString())
    etLName.setText(intent.getStringExtra("etLName").toString())
    calAmount.setText(intent.getStringExtra("calAmount").toString())
    calTime.setText(intent.getStringExtra("calTime").toString())
    calRate.setText(intent.getStringExtra("calRate").toString())
    calMonthlypayment.setText(intent.getStringExtra("calMonthlypayment").toString())

    mDialog.setTitle("Updating $etFName Record")

    val alertDialog = mDialog.create()
    alertDialog.show()

    btnUpdateData.setOnClickListener {
        updateUserData(
            userId,
            etFName.text.toString(),
            etLName.text.toString(),
            calAmount.text.toString(),
            calTime.text.toString(),
            calRate.text.toString(),
            calMonthlypayment.text.toString()

        )

        Toast.makeText(applicationContext, "User Data Updated", Toast.LENGTH_LONG).show()

        //we are setting updated data to our textviews
        tvUserFName.text = etFName.text.toString()
        tvUserLName.text = etLName.text.toString()
        tvUserLAmount.text = calAmount.text.toString()
        tvUserLTime.text = calTime.text.toString()
        tvUserLRate.text = calRate.text.toString()
        tvUserMonthLPay.text = calMonthlypayment.text.toString()

        alertDialog.dismiss()
    }
}

private fun updateUserData(
    userId: String,
    etFName: String,
    etLName: String,
    calAmount: String,
    calTime: String,
    calRate: String,
    calMonthlypayment: String

) {
    val dbRef = FirebaseDatabase.getInstance().getReference("Calculator_History").child(userId)
    val userInfo = CalHistory(userId, etFName, etLName, calAmount,calTime,calRate,calMonthlypayment)
    dbRef.setValue(userInfo)
}
}
