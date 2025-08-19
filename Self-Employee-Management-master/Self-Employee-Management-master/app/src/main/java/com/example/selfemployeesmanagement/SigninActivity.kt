package com.example.selfemployeesmanagement

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.selfemployeesmanagement.databinding.LoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class SigninActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login )
        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()

        val signInEmail : EditText = findViewById(R.id.userEmail)
        val signInPassword : EditText = findViewById(R.id.userpassword)
        val signInButton : Button = findViewById(R.id.loginButton)


        signInButton.setOnClickListener{
            val email = signInEmail.text.toString()
            val password = signInPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()){
                if (email.isEmpty()){
                    signInEmail.error = "Enter the email address"
                }

                if (password.isEmpty()){
                    signInPassword.error = "Enter the password"
                }
                Toast.makeText(this, "Enter valid details", Toast.LENGTH_SHORT).show()
            }else if (!email.matches(emailPattern.toRegex())){
                signInEmail.error = "Enter valid email address"
                Toast.makeText(this, "Enter valid email address", Toast.LENGTH_SHORT).show()
             }else if (password.length < 6){
                signInPassword.error = "Enter valid password"
                Toast.makeText(this, "Enter a password more than 6 characters", Toast.LENGTH_SHORT).show()
            }else{
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, DashboardActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, "Something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}