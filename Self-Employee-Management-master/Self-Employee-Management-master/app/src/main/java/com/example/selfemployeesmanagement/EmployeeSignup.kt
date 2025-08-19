package com.example.selfemployeesmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import com.example.selfemployeesmanagement.databinding.ActivityMainBinding
import com.example.selfemployeesmanagement.databinding.UserregisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class EmployeeSignup : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth
    private lateinit var database : FirebaseDatabase

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employeeregister)
        supportActionBar?.hide()

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        val eFirstName : EditText = findViewById(R.id.empFirstname)
        val eLastName : EditText = findViewById(R.id.empLastname)
        val eEmail : EditText = findViewById(R.id.empEmail)
        val ePhoneNo : EditText = findViewById(R.id.empPhoneNo)
        val ePassword : EditText = findViewById(R.id.empPassword)
        val eRePassword : EditText = findViewById(R.id.empRePassword)
        val eJob : Spinner = findViewById(R.id.empSpinner)
        val bDescription : EditText = findViewById(R.id.talentDescription)
        val eRegisterBtn : Button = findViewById(R.id.empRegisterButton)

        val singInText : TextView = findViewById(R.id.empFirstname)
        val signUpButton: Button = findViewById(R.id.empLogButton)


        singInText.setOnClickListener{
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        eRegisterBtn.setOnClickListener{
            val fName = eFirstName.text.toString()
            val lName = eLastName.text.toString()
            val uEmail = eEmail.text.toString()
            val uPhoneNo = ePhoneNo.text.toString()
            val Upassword = ePassword.text.toString()
            val UrePassword = eRePassword.text.toString()
            val uJob = eJob.selectedItem.toString()
            val ubDescription = bDescription.text.toString()


            if (fName.isEmpty() || lName.isEmpty() || uEmail.isEmpty() || uPhoneNo.isEmpty() || Upassword.isEmpty() || UrePassword.isEmpty() || uJob.isEmpty() || ubDescription.isEmpty()){
                if (fName.isEmpty()){
                    eFirstName.error = "Enter Your first name"
                }
                if (lName.isEmpty()){
                    eLastName.error = "Enter Your last name"
                }
                if (uEmail.isEmpty()){
                    eEmail.error = "Enter Your email"
                }
                if (uPhoneNo.isEmpty()){
                    ePhoneNo.error = "Enter Your email"
                }
                if (Upassword.isEmpty()){
                    ePassword.error = "Enter Your password"
                }
                if (UrePassword.isEmpty()){
                    eRePassword.error = "Re-Type your password"
                }
                if (ubDescription.isEmpty()){
                    bDescription.error = "Re-Type your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }else if (!uEmail.matches(emailPattern.toRegex())){
                eEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()
            }else if (uPhoneNo.length < 10){
                ePhoneNo.error = "Enter valid phone number"
                Toast.makeText(this, "Enter a phone number more than 10 digits", Toast.LENGTH_SHORT).show()
            } else if (Upassword.length < 6){
                ePassword.error = "Enter valid password"
                Toast.makeText(this, "Enter a password more than 6 characters", Toast.LENGTH_SHORT).show()
            }else if (Upassword != UrePassword){
                eRePassword.error = "Password not matched"
                Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT)
            } else{
                auth.createUserWithEmailAndPassword(uEmail,Upassword).addOnCompleteListener{
                    if (it.isSuccessful){
                        val databaseRef = database.reference.child("Employees").child(auth.currentUser!!.uid)
                        val employees : Employees = Employees(fName,lName,uEmail,uPhoneNo,Upassword,uJob,ubDescription, auth.currentUser!!.uid)

                        databaseRef.setValue(employees).addOnCompleteListener {
                            if (it.isSuccessful){
                                val intent = Intent(this, SigninActivity::class.java)
                                startActivity(intent)
                            }else{
                                Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }else{
                        Toast.makeText(this, "Something went wrong try again", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}