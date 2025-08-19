package com.example.selfemployeesmanagement

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.app.AlertDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.provider.MediaStore
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import java.util.*
import com.google.firebase.database.*

class EmployeeSelfProfile : AppCompatActivity() {

    private lateinit var etFirstName: EditText
    private lateinit var etLastName: EditText
    private lateinit var etEmail: EditText
    private lateinit var etPassword: EditText
    private lateinit var etContactNo: EditText
    private lateinit var etTalentDescription: EditText

    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var databaseReference: DatabaseReference

    private lateinit var employeeImage: ImageView
    private lateinit var firebaseStorage: FirebaseStorage
    private val PICK_IMAGE_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.employeeselfprofile)
        supportActionBar?.hide()

        etFirstName = findViewById(R.id.selfEtFirstname)
        etLastName = findViewById(R.id.selfEtLastname)
        etEmail = findViewById(R.id.selfEtEmail)
        etPassword = findViewById(R.id.selfEtPassword)
        etContactNo = findViewById(R.id.selfEtPhoneNo)
        etTalentDescription = findViewById(R.id.selfEtTalentDescription)

        employeeImage = findViewById(R.id.img_emp_account)
        firebaseStorage = FirebaseStorage.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase.reference.child("Employees").child(FirebaseAuth.getInstance().currentUser!!.uid)

        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val firstName = dataSnapshot.child("fname").getValue(String::class.java)
                    val lastName = dataSnapshot.child("lname").getValue(String::class.java)
                    val email = dataSnapshot.child("uemail").getValue(String::class.java)
                    val password = dataSnapshot.child("upassword").getValue(String::class.java)
                    val phone = dataSnapshot.child("uphoneNo").getValue(String::class.java)
                    val talent = dataSnapshot.child("udescription").getValue(String::class.java)

                    val databaseRef = FirebaseDatabase.getInstance().reference.child("Employees").child(FirebaseAuth.getInstance().currentUser!!.uid)
                    databaseRef.child("imageUri").addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            val imageUrl = dataSnapshot.getValue(String::class.java)
                            if (!imageUrl.isNullOrEmpty()) {
                                Picasso.get().load(imageUrl).into(employeeImage)
                            } else {
                                // Set a default image if the URL is empty or null
                                employeeImage.setImageResource(R.drawable.img21)
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            // Handle the error in retrieving the image URL
                            Toast.makeText(this@EmployeeSelfProfile, "Failed to retrieve image URL: ${databaseError.message}", Toast.LENGTH_SHORT).show()
                        }
                    })

                    etFirstName.setText(firstName)
                    etLastName.setText(lastName)
                    etEmail.setText(email)
                    etPassword.setText(password)
                    etContactNo.setText(phone)
                    etTalentDescription.setText(talent)
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                //err
            }
        })

        employeeImage.setOnClickListener {
            selectImage()
        }


        val updateButton = findViewById<Button>(R.id.selfBtnEditProfile)
        updateButton.setOnClickListener {
            val updatedFirstName = etFirstName.text.toString().trim()
            val updatedLastName = etLastName.text.toString().trim()
            val updatedEmail = etEmail.text.toString().trim()
            val updatedPassword = etPassword.text.toString().trim()
            val updatedContactNo = etContactNo.text.toString().trim()
            val updatedTalentDescription = etTalentDescription.text.toString().trim()


            val userUpdates = hashMapOf<String, Any>(
                "fname" to updatedFirstName,
                "lname" to updatedLastName,
                "uemail" to updatedEmail,
                "upassword" to updatedPassword,
                "uphoneNo" to updatedContactNo,
                "udescription" to updatedTalentDescription
            )

            databaseReference.updateChildren(userUpdates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
        }

        val deleteButton = findViewById<Button>(R.id.selfBtnDeleteProfile)
        deleteButton.setOnClickListener {
            FirebaseAuth.getInstance().currentUser?.delete()
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        databaseReference.removeValue()
                        Toast.makeText(this, "Profile deleted successfully", Toast.LENGTH_SHORT).show()

                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                    }
                }
        }
    }


    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePicture, 0)
                }
                options[item] == "Choose from Gallery" -> {
                    val pickPhoto =
                        Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    startActivityForResult(pickPhoto, PICK_IMAGE_REQUEST)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                0 -> {
                    val selectedImage = data?.extras?.get("data") as Bitmap
                    employeeImage.setImageBitmap(selectedImage)
                    uploadImageToFirebaseStorage()
                }
                PICK_IMAGE_REQUEST -> {
                    val selectedImageUri = data?.data
                    val selectedImageBitmap =
                        MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImageUri)
                    employeeImage.setImageBitmap(selectedImageBitmap)
                    uploadImageToFirebaseStorage()
                }
            }
        }
    }

    private fun uploadImageToFirebaseStorage() {
        val image = (employeeImage.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        val filename = "${FirebaseAuth.getInstance().currentUser?.uid}.jpg"
        val storageRef = firebaseStorage.reference.child("Images/$filename")
        val uploadTask = storageRef.putBytes(data)

        uploadTask.addOnSuccessListener { uploadTaskSnapshot ->
            storageRef.downloadUrl.addOnSuccessListener { uri ->
                // Save the image URI in the "Users" node of the Realtime Database
                val currentUserRef = firebaseDatabase.reference.child("Employees").child(FirebaseAuth.getInstance().currentUser!!.uid)
                currentUserRef.child("imageUri").setValue(uri.toString())

                Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener { exception ->
                Toast.makeText(this, "Failed to get image URL: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
        }.addOnFailureListener { exception ->
            Toast.makeText(this, "Failed to upload image: ${exception.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
