package com.example.quickloan.ksactivities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.quickloan.ksmodel.KsLoneModel
import com.example.quickloan.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ks_ApplyLoan : AppCompatActivity() {

    //Create Variables
    private lateinit var fullName: EditText
    private lateinit var nicNo: EditText
    private lateinit var mobileNo: EditText
    private lateinit var email: EditText
    private lateinit var uAddress: EditText
    private lateinit var howMuch: Spinner
    private lateinit var howLong: Spinner
    private lateinit var btnsubmit: Button



    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ks_apply_loan)



        fullName = findViewById(R.id.full_Name_2)
        nicNo = findViewById(R.id.nic_No_2)
        mobileNo = findViewById(R.id.mobile_No_2)
        email = findViewById(R.id.email_2)
        uAddress = findViewById(R.id.your_Address_2)
        howMuch = findViewById(R.id.how_much_2)
        howLong = findViewById(R.id.how_long_2)
        btnsubmit = findViewById(R.id.submit_a)

        val backButton: ImageButton = findViewById(R.id.btnBack1)




        dbRef = FirebaseDatabase.getInstance().getReference("Loans")


        btnsubmit.setOnClickListener {
           saveLoanData()

        }
        backButton.setOnClickListener {
            val intent = Intent(this, Ks_ButtonActivity::class.java)
            startActivity(intent)
            finish()
        }


    }
    //getting values
    private fun saveLoanData(){
        val fName = fullName.text.toString().trim()
        val nNo = nicNo.text.toString().trim()
        val mNo = mobileNo.text.toString().trim()
        val emails = email.text.toString().trim()
        val address = uAddress.text.toString().trim()
        val hMuch = howMuch.selectedItem.toString().trim()
        val hLong = howLong.selectedItem.toString().trim()


        // check if all fields are filled
        var isValid = true


        if (fName.isEmpty()){
            fullName.error = "Please enter full name"
            fullName.requestFocus()
            isValid = false
        }
        if (nNo.isEmpty()) {
            nicNo.error = "Please enter NIC"
            nicNo.requestFocus()
            isValid = false
        } else {
            //  NIC format validation
            val regex = Regex("^\\d{9}[vVxX]$") // Assuming the NIC should be 10 characters long with a single character at the end
            if (!nNo.matches(regex)) {
                nicNo.error = "Invalid NIC format"
                nicNo.requestFocus()
                isValid = false
            }
        }

        if (mNo.isEmpty()){
            mobileNo.error = "Please enter mobile no"
            mobileNo.requestFocus()
            isValid = false
        }else if (mNo.length != 10) {
            mobileNo.error = "Mobile no should be 10 digits"
            mobileNo.requestFocus()
            isValid = false
        }

        if (emails.isEmpty()){
            email.error = "Please enter email"
            email.requestFocus()
            isValid = false
        }else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {
            email.error = "Please enter a valid email address"
            email.requestFocus()
            isValid = false
        }

        if (address.isEmpty()){
            uAddress.error = "Please enter address"
            uAddress.requestFocus()
            isValid = false
        }
        // If any field is not filled, return from the function
        if (!isValid) {
            return
        }



        //Generate a new unique key and returns a DatabaseReference
        val loneId = dbRef.push().key!!


        //pass the data constructor
        val Lone = KsLoneModel(loneId, fName, nNo, mNo, emails, address, hMuch, hLong)

        dbRef.child(loneId).setValue(Lone)
            .addOnCompleteListener{
                Toast.makeText(this, "Lone details submit successfully", Toast.LENGTH_LONG).show()


                //if Data successfully insertsd after clear all fields default
                fullName.text.clear()
                nicNo.text.clear()
                mobileNo.text.clear()
                email.text.clear()
                uAddress.text.clear()
                howMuch.setSelection(0)
                howLong.setSelection(0)

            }.addOnFailureListener{ err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }
}