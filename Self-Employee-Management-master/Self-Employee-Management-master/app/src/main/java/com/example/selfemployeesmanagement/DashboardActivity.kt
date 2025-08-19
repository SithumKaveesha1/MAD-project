package com.example.selfemployeesmanagement

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.example.selfemployeesmanagement.activities.InsertionTaskActivity
import com.example.selfemployeesmanagement.activities.os_activity_1

class DashboardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard )
        supportActionBar?.hide()

        val searchCardView : CardView = findViewById(R.id.cvSearch)
        val exitCardView : CardView = findViewById(R.id.cvExit)
        val profileCardView : CardView = findViewById(R.id.cvProfile)
        val budgetView: CardView = findViewById(R.id.cvBudget)
        val goalView: CardView = findViewById(R.id.cvGoals)
        val taskView: CardView  = findViewById(R.id.cvTasks)


        searchCardView.setOnClickListener {
            val intent = Intent(this, EmployeeListActivity::class.java)
            startActivity(intent)
        }

        exitCardView.setOnClickListener {
            finishAffinity()
        }

        profileCardView.setOnClickListener {
            val intent = Intent(this, EmployeeSelfProfile::class.java)
            startActivity(intent)
        }
        budgetView.setOnClickListener{
            val intent = Intent(this, ViewBudgetActivity::class.java)
            startActivity(intent)
        }
        goalView.setOnClickListener{
            val intent = Intent(this, os_activity_1::class.java)
            startActivity(intent)
        }
        taskView.setOnClickListener{
            val intent = Intent(this, InsertionTaskActivity::class.java)
            startActivity(intent)
        }
}
}