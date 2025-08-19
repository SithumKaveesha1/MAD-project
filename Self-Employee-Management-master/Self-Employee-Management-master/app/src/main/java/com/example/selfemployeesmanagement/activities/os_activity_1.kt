package com.example.selfemployeesmanagement.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.selfemployeesmanagement.R
import com.example.selfemployeesmanagement.models.goalsmodel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class os_activity_1 : AppCompatActivity() {

    private lateinit var etgoalname: EditText
    private lateinit var etexpectedprice: EditText
    private lateinit var etaddtime: EditText
    private lateinit var btnbuttongoal: Button
    private lateinit var btnviewgoal: Button


    private lateinit var dbRef : DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.os_activity_1)

        etgoalname = findViewById(R.id.goalname)
        etexpectedprice = findViewById(R.id.expectedprice)
        etaddtime = findViewById(R.id.addtime)
        btnbuttongoal = findViewById(R.id.buttongoal)
        btnviewgoal = findViewById(R.id.commongoal)

        btnviewgoal.setOnClickListener{
            val intent = Intent(this, os_activity2::class.java)
            startActivity(intent)

        }

        dbRef = FirebaseDatabase.getInstance().getReference("Goals")

        btnbuttongoal.setOnClickListener{
            saveGoalData()
        }

    }

    private fun saveGoalData(){

        //getting values
        val goalName = etgoalname.text.toString()
        val price = etexpectedprice.text.toString()
        val time = etaddtime.text.toString()

        //validations
        if (goalName.isEmpty()){
            etgoalname.error = "Please add the goal name"
        }

        if (price.isEmpty()){
            etgoalname.error = "Please add expected price"
        }

        if (time.isEmpty()){
            etgoalname.error = "Please add expected time"
        }

        val goalID = dbRef.push().key!!

        val goal = goalsmodel(goalID, goalName, price, time)

        dbRef.child(goalID).setValue(goal)
            .addOnCompleteListener {
                Toast.makeText(this, "Goal Inserted Successfully", Toast.LENGTH_LONG).show()

                etgoalname.text.clear()
                etaddtime.text.clear()
                etexpectedprice.text.clear()
            }
            .addOnFailureListener{ err ->
                Toast.makeText(this, "Goal Insertion Failed. Error : ${err.message}", Toast.LENGTH_LONG).show()

            }
    }
}