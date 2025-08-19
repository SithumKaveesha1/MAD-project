package com.example.selfemployeesmanagement.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AlphaAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.TaskModel
import com.google.firebase.database.FirebaseDatabase

class TaskDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpTitle: TextView
    private lateinit var tvEmpDescription: TextView
    private lateinit var tvEmpDate: TextView
    private lateinit var tvEmpEvent: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_details)

        // Add fade-in animation to textView
        val textView1 = findViewById<TextView>(R.id.btnUpdate)
        val textView2 = findViewById<TextView>(R.id.btnDelete)
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 1000
        textView1.startAnimation(anim)
        textView2.startAnimation(anim)


        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener{
            openUpdateDialog(

                intent.getStringExtra("empId").toString(),
                intent.getStringExtra("empTitle").toString(),
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("empId").toString()
            )
        }

    }

    private fun deleteRecord(id: String) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Tasks").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Task data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingTaskActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()

    }

    }

    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpTitle = findViewById(R.id.tvEmpTitle)
        tvEmpDescription = findViewById(R.id.tvEmpDescription)
        tvEmpDate = findViewById(R.id.tvEmpDate)
        tvEmpEvent = findViewById(R.id.tvEmpEvent)
        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }
    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("empId")
        tvEmpTitle.text = intent.getStringExtra("empTitle")
        tvEmpDescription.text = intent.getStringExtra("empDescription")
        tvEmpDate.text = intent.getStringExtra("empDate")
        tvEmpEvent.text = intent.getStringExtra("empEvent")
    }
    private fun openUpdateDialog(
        empId:String,
        empTitle:String
    ){
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_task_dialog, null)

        mDialog.setView(mDialogView)

        val etEmpTitle = mDialogView.findViewById<EditText>(R.id.etEmpTitle)
        val etEmpDescription = mDialogView.findViewById<EditText>(R.id.etEmpDescription)
        val etEmpDate = mDialogView.findViewById<EditText>(R.id.etEmpDate)
        val etEmpEvent = mDialogView.findViewById<EditText>(R.id.etEmpEvent)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etEmpTitle.setText(  intent.getStringExtra("empTitle").toString())
        etEmpDescription.setText(  intent.getStringExtra("empDescription").toString())
        etEmpDate.setText(  intent.getStringExtra("empDate").toString())
        etEmpEvent.setText(  intent.getStringExtra("empEvent").toString())

        mDialog.setTitle("Updating $empTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()
        btnUpdateData.setOnClickListener {
            updateTaskData(
                empId,
                etEmpTitle.text.toString(),
                etEmpDescription.text.toString(),
                etEmpDate.text.toString(),
                etEmpEvent.text.toString()
            )

            Toast.makeText(applicationContext, "Employee Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpTitle.text = etEmpTitle.text.toString()
            tvEmpDescription.text = etEmpDescription.text.toString()
            tvEmpDate.text = etEmpDate.text.toString()
            tvEmpEvent.text = etEmpEvent.text.toString()

            alertDialog.dismiss()
        }

    }
    private fun updateTaskData(
        id: String,
        titlen: String,
        descriptionn: String,
        daten: String,
        eventn: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("Tasks").child(id)
        val taskInfo = TaskModel(id, titlen, descriptionn, daten,eventn)
        dbRef.setValue(taskInfo)
    }
}