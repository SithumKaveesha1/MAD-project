package com.example.selfemployeesmanagement

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class UpdateBudgetActivity : AppCompatActivity() {

    //Create Variables
    private lateinit var customername: EditText
    private lateinit var period: Spinner
    private lateinit var amount: EditText
    private lateinit var currencyType: Spinner
    private lateinit var accountType: Spinner
    private lateinit var foods: EditText
    private lateinit var shopping: EditText
    private lateinit var transpotation: EditText
    private lateinit var housing: EditText
    private lateinit var communication: EditText
    private lateinit var finance: EditText
    private lateinit var life: EditText
    private lateinit var other: EditText
    private lateinit var btnSaveData:Button

    lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.update_budget_activity)
        supportActionBar?.hide()

        //Check Database
        dbRef = FirebaseDatabase.getInstance().getReference("budget")

        //retrieves a reference to a view object
        customername = findViewById(R.id.customername)
        period = findViewById(R.id.period)
        amount = findViewById(R.id.amount)
        currencyType = findViewById(R.id.currencyType)
        accountType = findViewById(R.id.accountType)
        foods = findViewById(R.id.foods)
        shopping = findViewById(R.id.shopping)
        transpotation = findViewById(R.id.transpotation)
        housing = findViewById(R.id.housing)
        communication = findViewById(R.id.communication)
        finance = findViewById(R.id.finance)
        life = findViewById(R.id.life)
        other = findViewById(R.id.other)
        btnSaveData = findViewById(R.id.btnSaveData)

        //Request ID
        val budgetId = intent.getStringExtra("budgetId")
        if (budgetId != null) {
            dbRef.child(budgetId).get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val data = task.result?.getValue(BudgetModel::class.java)
                    customername.setText(data?.name)
                    period.setSelection(getIndex(period, data?.period))
                    amount.setText(data?.amount.toString())
                    currencyType.setSelection(getIndex(currencyType, data?.currencyType))
                    accountType.setSelection(getIndex(accountType, data?.accountType))
                    foods.setText(data?.foods.toString())
                    shopping.setText(data?.shopping.toString())
                    transpotation.setText(data?.transpotaion.toString())
                    housing.setText(data?.housing.toString())
                    communication.setText(data?.communication.toString())
                    finance.setText(data?.finance.toString())
                    life.setText(data?.life.toString())
                    other.setText(data?.other.toString())
                } else {
                    Log.d(TAG, "get failed with ${task.exception}")
                }
            }
        }


        btnSaveData.setOnClickListener {

            // Check if any of the fields are empty
            if (customername.text.isEmpty() || period.selectedItem.toString().isEmpty() ||
                amount.text.isEmpty() || currencyType.selectedItem.toString().isEmpty() ||
                accountType.selectedItem.toString().isEmpty() || foods.text.isEmpty() ||
                shopping.text.isEmpty() || transpotation.text.isEmpty() || housing.text.isEmpty() ||
                communication.text.isEmpty() || finance.text.isEmpty() || life.text.isEmpty() ||
                other.text.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // All fields are filled, so update the budget information
            val budget = BudgetModel(
                budgetId!!,
                customername.text.toString(),
                period.selectedItem.toString(),
                amount.text.toString(),
                currencyType.selectedItem.toString(),
                accountType.selectedItem.toString(),
                foods.text.toString(),
                shopping.text.toString(),
                transpotation.text.toString(),
                housing.text.toString(),
                communication.text.toString(),
                finance.text.toString(),
                life.text.toString(),
                other.text.toString()
            )
            //check success or not
            dbRef.child(budgetId!!).setValue(budget).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Data updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Error updating data", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }

    //Set spinner values
    private fun getIndex(spinner: Spinner, item: String?): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(item, ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

}
