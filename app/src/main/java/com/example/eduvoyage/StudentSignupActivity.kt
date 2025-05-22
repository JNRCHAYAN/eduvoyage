package com.example.eduvoyage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudentSignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_signup)

        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference("students")

        val name = findViewById<EditText>(R.id.etName)
        val country = findViewById<EditText>(R.id.etCountry)
        val email = findViewById<EditText>(R.id.etEmail)
        val password = findViewById<EditText>(R.id.etPassword)
        val signupBtn = findViewById<Button>(R.id.btnSignup)

        signupBtn.setOnClickListener {
            val nameStr = name.text.toString().trim()
            val countryStr = country.text.toString().trim()
            val emailStr = email.text.toString().trim()
            val passwordStr = password.text.toString().trim()

            if (nameStr.isEmpty() || countryStr.isEmpty() || emailStr.isEmpty() || passwordStr.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(emailStr, passwordStr)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        val uid = auth.currentUser?.uid
                        val studentData = mapOf(
                            "name" to nameStr,
                            "country" to countryStr,
                            "email" to emailStr
                        )
                        databaseRef.child(uid!!).setValue(studentData)
                        Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
                        finish() // go back to login screen
                    } else {
                        Toast.makeText(this, "Signup failed: ${it.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
