package com.example.quickloan

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class nv_paymentsDetailsActivity : AppCompatActivity(){
    private lateinit var tvloanid: TextView
    private lateinit var tvdate: TextView
    private lateinit var tvname: TextView
    private lateinit var tvamount: TextView
    private lateinit var tvpostalcode: TextView
    private lateinit var tvmobileno: TextView
    private lateinit var btnupdate: Button
    private lateinit var btndelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_update_delete)

        initView()
        setValuesToViews()

        btnupdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("loanId").toString(),
                intent.getStringExtra("name").toString()
            )

        }
        btndelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("loanId").toString()
            )
        }
        }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("PaymentHistory").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this,"Payment record deleted",Toast.LENGTH_LONG).show()

            val intent = Intent(this,nv_PaymentForm::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{error ->
            Toast.makeText(this,"deleting Err ${error.message}",Toast.LENGTH_LONG).show()

        }
    }
    private fun initView(){
        tvloanid = findViewById(R.id.loanid)
        tvdate = findViewById(R.id.date)
        tvname = findViewById(R.id.name)
        tvamount = findViewById(R.id.amount)
        tvpostalcode = findViewById(R.id.postalcode)
        tvmobileno = findViewById(R.id.mobileNo)

        btnupdate = findViewById(R.id.btnUpdate)
        btndelete = findViewById(R.id.btnDelete)
    }


    private fun setValuesToViews(){
        tvloanid.text=intent.getStringExtra("loanId")
        tvdate.text=intent.getStringExtra("date")
        tvname.text=intent.getStringExtra("name")
        tvamount.text=intent.getStringExtra("amount")
        tvpostalcode.text=intent.getStringExtra("postalCode")
        tvmobileno.text=intent.getStringExtra("mobileNo")
    }
private fun openUpdateDialog(
    loanId: String,
    name: String
){
    val mDialog=AlertDialog.Builder(this)
    val inflater=layoutInflater
    val mDialogView=inflater.inflate(R.layout.activity_nv_update_dialog,null)

    mDialog.setView(mDialogView)

val etName = mDialogView.findViewById<EditText>(R.id.etCardHolderName)
    val etpostalcode = mDialogView.findViewById<EditText>(R.id.etpostalcode)
    val etmobile = mDialogView.findViewById<EditText>(R.id.etmobile)
    val btnupdate = mDialogView.findViewById<Button>(R.id.btnUpdateData)

    etName.setText(intent.getStringExtra("name").toString())
    etpostalcode.setText(intent.getStringExtra("postalCode").toString())
    etmobile.setText(intent.getStringExtra("mobileNo").toString())

    mDialog.setTitle("updating $name Record")
    val alertDialog = mDialog.create()
    alertDialog.show()

    btnupdate.setOnClickListener{
        updatePayData(
            loanId,
            etName.text.toString(),
            etpostalcode.text.toString(),
            etmobile.text.toString()
        )

        Toast.makeText(applicationContext,"Payment Data updated",Toast.LENGTH_LONG).show()

        //we are setting updated data to textviews
        tvname.text= etName.text.toString()
        tvpostalcode.text= etpostalcode.text.toString()
        tvmobileno.text=etmobile.text.toString()

        alertDialog.dismiss()
    }

}
    private fun updatePayData(

        id: String,
        name: String,
        postalcode: String,
        mobile: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("PaymentHistory").child(id)
        val payInfo = PaymentModel(id, name, postalcode, mobile)
        dbRef.setValue(payInfo)
    }
}