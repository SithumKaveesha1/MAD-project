package com.example.quickloan

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.quickloan.ksactivities.ks_ApplyLoan
import com.google.firebase.auth.FirebaseAuth

class activity_nv_signin : AppCompatActivity() {

    private lateinit var etPass: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvSignupText: TextView
    private lateinit var tvForgotpwd: TextView
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nv_signin)

        etEmail = findViewById(R.id.login_email)
        etPass = findViewById(R.id.login_password)
        btnLogin = findViewById(R.id.login_button)
        tvSignupText = findViewById(R.id.signupRedirectText)
        tvForgotpwd = findViewById(R.id.forgot_password)
        firebaseAuth = FirebaseAuth.getInstance()


        btnLogin.setOnClickListener {
            navigateTonextActivity()


        }


        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPass.text.toString().trim()
            if (email.isEmpty()) {
                etEmail.error = "Email is required"
                etEmail.requestFocus()
                return@setOnClickListener
            }



            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                etEmail.error = "Please enter a valid email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                etPass.error = "Password is required"
                etPass.requestFocus()
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, Bu_dashboard::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            baseContext, "Authentication failed.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }

        tvForgotpwd.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val view = layoutInflater.inflate(R.layout.nv_dialog_forgot, null)
            val userEmail = view.findViewById<EditText>(R.id.editBox)
            builder.setView(view)
            val dialog = builder.create()
            view.findViewById<Button>(R.id.btnReset).setOnClickListener {
                compareEmail(userEmail)
                dialog.dismiss()
            }
            view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null) {
                dialog.window!!.setBackgroundDrawable(android.graphics.drawable.ColorDrawable(0))
            }
            dialog.show()
        }

        tvSignupText.setOnClickListener {
            val signupIntent = Intent(this, activity_nv_signup::class.java)
            startActivity(signupIntent)
        }
    }

    fun navigateTonextActivity() {
        val intent = Intent(this, Bu_dashboard::class.java)
        startActivity(intent)
    }

    // Handle forgot password
    private fun compareEmail(email: EditText) {
        if (email.text.toString().isEmpty()) {
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email.text.toString()).matches()) {
            return
        }
        firebaseAuth.sendPasswordResetEmail(email.text.toString())
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
