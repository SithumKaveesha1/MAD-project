package com.example.selfemployeesmanagement.activities

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.TaskModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.Calendar

class InsertionTaskActivity : AppCompatActivity() {

    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var etDate: EditText
    private lateinit var etEvent: EditText

    private lateinit var btnSave: Button

    private lateinit var dbRef:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_insertion)

        etTitle= findViewById(R.id.etTitle)
        etDescription= findViewById(R.id.etDescription)
        etDate= findViewById(R.id.etDate)
        etEvent= findViewById(R.id.etEvent)

        btnSave= findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("Tasks")

        btnSave.setOnClickListener{
            saveTaskData()
        }
// Add an OnClickListener to the etDate EditText field
        etDate.setOnClickListener {
            showDatePickerDialog()
        }
    }

    private fun showDatePickerDialog() {
        // Get the current date
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // Create a DatePickerDialog and set the initial date to the current date
        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Set the text of the etDate EditText to the selected date
                etDate.setText("$dayOfMonth/${monthOfYear + 1}/$year")
            }, year, month, day)

        // Show the DatePickerDialog
        datePickerDialog.show()
    }

    private fun saveTaskData(){
        //geting values
        val empTitle = etTitle.text.toString()
        val empDescription = etDescription.text.toString()
        val empDate = etDate.text.toString()
        val empEvent = etEvent.text.toString()

        if(empTitle.isEmpty()){
            etTitle.error="Please enter Title"
        }
        if(empDescription.isEmpty()){
            etDescription.error="Please enter Description"
        }
        if(empDate.isEmpty()){
            etDate.error="Please enter Date"
        }
        if(empEvent.isEmpty()){
            etEvent.error="Please enter Event"
        }


        val empId = dbRef.push().key!!

        val task= TaskModel(empId,empTitle,empDescription,empDate,empEvent)

        dbRef.child(empId).setValue(task)
            .addOnCompleteListener{
                Toast.makeText(this,"Data insert successfully",Toast.LENGTH_LONG).show()
                etTitle.text.clear()
                etDescription.text.clear()
                etDate.text.clear()
                etEvent.text.clear()



            }.addOnFailureListener { err->
                Toast.makeText(this,"Error ${err.message}",Toast.LENGTH_LONG).show()
            }
    }
}