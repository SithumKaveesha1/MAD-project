package com.example.selfemployeesmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionBudgetActivity : AppCompatActivity() {

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
        setContentView(R.layout.insert_budget_activity)
        supportActionBar?.hide()

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

        dbRef = FirebaseDatabase.getInstance().getReference("budget")

        btnSaveData.setOnClickListener{
            saveBudgetData()
        }
    }

    private fun saveBudgetData(){

        //getting values
        val cusName = customername.text.toString()
        val selectPeroid = period.selectedItem.toString()
        val FullAmount = amount.text.toString()
        val currencyType = currencyType.selectedItem.toString()
        val accountType = accountType.selectedItem.toString()
        val ExFoods = foods.text.toString()
        val ExShopping = shopping.text.toString()
        val ExTranspotation = transpotation.text.toString()
        val ExHousing = housing.text.toString()
        val ExCommunication = communication.text.toString()
        val ExFinance = finance.text.toString()
        val Exlife = life.text.toString()
        val ExOther = other.text.toString()

        //Check all fields fill or not
        if (cusName.isEmpty()){
            customername.error = "Please Enter Name"
        }
        if (selectPeroid.isEmpty()){
            amount.error = "Please Enter Period"
        }
        if (FullAmount.isEmpty()){
            amount.error = "Please Enter Amount"
        }
        if (currencyType.isEmpty()){
            amount.error = "Please Enter Currency type"
        }
        if (accountType.isEmpty()){
            amount.error = "Please Enter Account Type"
        }
        if (ExFoods.isEmpty()){
            foods.error = "Please Enter Foods "
        }
        if (ExShopping.isEmpty()){
            shopping.error = "Please Enter Shopping"
        }
        if (ExTranspotation.isEmpty()){
            transpotation.error = "Please Enter Transpotation"
        }
        if (ExHousing.isEmpty()){
            housing.error = "Please Enter Housing"
        }
        if (ExCommunication.isEmpty()){
            communication.error = "Please Enter Communication"
        }
        if (ExFinance.isEmpty()){
            finance.error = "Please Enter Finance"
        }
        if (Exlife.isEmpty()){
            life.error = "Please Enter Life"
        }
        if (ExOther.isEmpty()){
            other.error = "Please Enter Other"
        }


        // Check if any of the fields are empty
        if (cusName.isEmpty() || selectPeroid.isEmpty() || FullAmount.isEmpty() || currencyType.isEmpty() || accountType.isEmpty() || ExFoods.isEmpty() || ExShopping.isEmpty() ||
            ExTranspotation.isEmpty() || ExHousing.isEmpty() || ExCommunication.isEmpty() ||
            ExFinance.isEmpty() || Exlife.isEmpty() || ExOther.isEmpty()) {
            Toast.makeText(this, "Please fill all data", Toast.LENGTH_LONG).show()
            return
        }

        //Generates a new unique key and returns a DatabaseReference
        val budgetId = dbRef.push().key!!

        //Pass the data constructor
        val budget = BudgetModel(budgetId, cusName, selectPeroid,FullAmount,currencyType, accountType, ExFoods, ExShopping, ExTranspotation, ExHousing, ExCommunication , ExFinance, Exlife, ExOther)

        dbRef.child(budgetId).setValue(budget)
            .addOnCompleteListener{
                Toast.makeText(this, "Data Inserted Successfully", Toast.LENGTH_LONG).show()

                //If Data successfully Inserted after clear all fields default
                customername.text.clear()
                amount.text.clear()
                foods.text.clear()
                shopping.text.clear()
                transpotation.text.clear()
                housing.text.clear()
                communication.text.clear()
                finance.text.clear()
                life.text.clear()
                other.text.clear()
            }.addOnFailureListener{ err ->
                Toast.makeText(this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }
    }
}