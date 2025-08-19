package com.example.selfemployeesmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.selfemployeesmanagement.databinding.ActivityMainBinding
import com.example.selfemployeesmanagement.databinding.UserregisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SignupActivity : AppCompatActivity() {

private lateinit var auth : FirebaseAuth
private lateinit var database : FirebaseDatabase

private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.userregister)
        supportActionBar?.hide()

        auth=FirebaseAuth.getInstance()
        database=FirebaseDatabase.getInstance()

        val firstName : EditText = findViewById(R.id.firstname)
        val lastName : EditText = findViewById(R.id.lastname)
        val email : EditText = findViewById(R.id.email)
        val password : EditText = findViewById(R.id.password)
        val rePassword : EditText = findViewById(R.id.repassword)
        val registerBtn : Button = findViewById(R.id.registerButton)

        val singInText : TextView = findViewById(R.id.firstname)
        val signUpButton: Button = findViewById(R.id.logButton)

        singInText.setOnClickListener{
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        signUpButton.setOnClickListener {
            val intent = Intent(this, SigninActivity::class.java)
            startActivity(intent)
        }

        registerBtn.setOnClickListener{
            val fName = firstName.text.toString()
            val lName = lastName.text.toString()
            val uEmail = email.text.toString()
            val Upassword = password.text.toString()
            val UrePassword = rePassword.text.toString()


            if (fName.isEmpty() || lName.isEmpty() || uEmail.isEmpty() || Upassword.isEmpty() || UrePassword.isEmpty()){
                if (fName.isEmpty()){
                    firstName.error = "Enter Your first name"
                }
                if (lName.isEmpty()){
                    lastName.error = "Enter Your last name"
                }
                if (uEmail.isEmpty()){
                    email.error = "Enter Your email"
                }
                if (Upassword.isEmpty()){
                    password.error = "Enter Your password"
                }
                if (UrePassword.isEmpty()){
                    rePassword.error = "Re-Type your password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()

            }else if (!uEmail.matches(emailPattern.toRegex())){
                email.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()
            }else if (Upassword.length < 6){
                password.error = "Enter valid password"
                Toast.makeText(this, "Enter a password more than 6 characters", Toast.LENGTH_SHORT).show()
            }else if (Upassword != UrePassword){
                rePassword.error = "Password not matched"
                Toast.makeText(this, "Password not matched", Toast.LENGTH_SHORT)
            } else{
                auth.createUserWithEmailAndPassword(uEmail,Upassword).addOnCompleteListener{
                    if (it.isSuccessful){
                        val databaseRef = database.reference.child("Users").child(auth.currentUser!!.uid)
                        val users : Users = Users(fName,lName,uEmail,Upassword, auth.currentUser!!.uid)

                        databaseRef.setValue(users).addOnCompleteListener {
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