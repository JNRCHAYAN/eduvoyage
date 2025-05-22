package com.example.eduvoyage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AdminSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_signup)

        // Initialize Firebase Auth and reference to "admin" node
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("admin")

        // Get all input views
        val name = findViewById<EditText>(R.id.etName)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val signupBtn = findViewById<Button>(R.id.btnSignup)

        // Handle Sign Up click
        signupBtn.setOnClickListener {
            val nameStr = name.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString().trim()

            // Check for empty fields
            if (nameStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create account with Firebase Auth
            auth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        if (uid != null) {
                            val adminData = mapOf(
                                "name" to nameStr,
                                "email" to emailStr
                            )
                            databaseRef.child(uid).setValue(adminData)
                                .addOnSuccessListener {
                                    Toast.makeText(this, "Admin Signup Successful", Toast.LENGTH_SHORT).show()
                                    finish() // You can redirect to AdminLoginActivity here
                                }
                                .addOnFailureListener {
                                    Toast.makeText(this, "Failed to save admin data", Toast.LENGTH_SHORT).show()
                                }
                        } else {
                            Toast.makeText(this, "User ID is null", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Signup Failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
