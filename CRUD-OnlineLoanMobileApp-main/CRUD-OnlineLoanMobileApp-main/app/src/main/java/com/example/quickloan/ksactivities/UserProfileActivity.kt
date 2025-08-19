package com.example.quickloan.ksactivities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import com.example.quickloan.R
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.quickloan.ksmodel.User
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import de.hdodenhof.circleimageview.CircleImageView


class UserProfileActivity : AppCompatActivity() {

    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var mobileNo: EditText
    private lateinit var saveDataButton: Button
    private lateinit var profileImage: CircleImageView

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_profile)

        fullName = findViewById(R.id.usFull_Name2)
        email = findViewById(R.id.usEmail)
        mobileNo = findViewById(R.id.usMobile_No)
        saveDataButton = findViewById(R.id.btnSaveData)
        profileImage = findViewById(R.id.ksProfile_image)

        dbRef = FirebaseDatabase.getInstance().getReference("Users")

        saveDataButton.setOnClickListener {
            saveUserData()
        }

        profileImage.setOnClickListener {
            showToast("Profile image clicked")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun saveUserData() {
        val name = fullName.text.toString().trim()
        val emails = email.text.toString().trim()
        val mobile = mobileNo.text.toString().trim()

        // Check if the input fields are not empty
        if (name.isNotEmpty() && emails.isNotEmpty() && mobile.isNotEmpty()) {

        }
            // Check if the entered email address is valid
            if (Patterns.EMAIL_ADDRESS.matcher(emails).matches()) {

            // Create a User object with the input data
            val user = User(name, emails, mobile)

            // Generate a unique key for the user in the database
            val userId = dbRef.push().key

            // Check if a valid key was generated
            if (userId != null) {
                // Save the user data to the database using the generated key
                dbRef.child(userId).setValue(user)

                // Clear the input fields
                fullName.text.clear()
                email.text.clear()
                mobileNo.text.clear()

                // Show a success message
                showToast("User data saved successfully")
            }
        } else {
            // Show an error message if any input fields are empty
            showToast("Please fill in all the fields")
        }
    }
}




