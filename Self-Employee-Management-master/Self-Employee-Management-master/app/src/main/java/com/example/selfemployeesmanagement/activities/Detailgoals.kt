package com.example.selfemployeesmanagement.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.goalsmodel
import com.google.firebase.database.FirebaseDatabase

class detailgoals : AppCompatActivity() {

    private lateinit var tvgoalId : TextView
    private lateinit var tvgoalname : TextView
    private lateinit var tvprice : TextView
    private lateinit var tvtime : TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete : Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detailgoals)

        initView()
        setValuestoViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(
                intent.getStringExtra("goalid").toString(),
                intent.getStringExtra("goalname").toString()

            )
        }

        btnDelete.setOnClickListener{
            deleteRecord(
                intent.getStringExtra("goalid").toString()
            )
        }
    }

    private fun deleteRecord(
        id:String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Goals").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Goal Details Deleted", Toast.LENGTH_LONG).show()
            val intent = Intent(this, os_activity2::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error->
            Toast.makeText(this, "Deleting Failed. Error: ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun initView(){
        tvgoalId = findViewById(R.id.goalidtype)
        tvgoalname = findViewById(R.id.golanametype)
        tvprice = findViewById(R.id.goalpricetype)
        tvtime = findViewById(R.id.goaltimetype)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuestoViews(){

        tvgoalId.text = intent.getStringExtra("goalid")
        tvgoalname.text = intent.getStringExtra("goalname")
        tvprice.text = intent.getStringExtra("goalprice")
        tvtime.text = intent.getStringExtra("goaltime")

    }

    private fun openUpdateDialog(
        goalid : String,
        goalname : String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_goals,null)

        mDialog.setView(mDialogView)

        val etGoalName = mDialogView.findViewById<EditText>(R.id.etGoalName)
        val etGoalPrice = mDialogView.findViewById<EditText>(R.id.etGoalPrice)
        val etGoalTime = mDialogView.findViewById<EditText>(R.id.etGoalTime)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etGoalName.setText(intent.getStringExtra("goalname").toString())
        etGoalPrice.setText(intent.getStringExtra("goalprice").toString())
        etGoalTime.setText(intent.getStringExtra("goaltime").toString())


        mDialog.setTitle("Updating $goalname Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener{
            updateGoalData(
                goalid,
                etGoalName.text.toString(),
                etGoalPrice.text.toString(),
                etGoalTime.text.toString()
            )

            Toast.makeText(applicationContext, "Goal Details Updated" , Toast.LENGTH_LONG).show()

            //Set updated data to text views
            tvgoalname.text = etGoalName.text.toString()
            tvprice.text = etGoalPrice.text.toString()
            tvtime.text = etGoalTime.text.toString()

            alertDialog.dismiss()
        }

    }

    private fun updateGoalData(
        id: String,
        name:String,
        price: String,
        time : String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("Goals").child(id)
        val goalInfo = goalsmodel(id, name,price,time)
        dbRef.setValue(goalInfo)
    }

}