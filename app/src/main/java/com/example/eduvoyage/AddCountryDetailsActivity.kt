package com.example.eduvoyage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddCountryDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_country_details)

        val auth = FirebaseAuth.getInstance()
        val database = FirebaseDatabase.getInstance().reference

        val etCountryName = findViewById<EditText>(R.id.etCountryName)
        val etAbout = findViewById<EditText>(R.id.etAbout)
        val etCulture = findViewById<EditText>(R.id.etCulture)
        val etCities = findViewById<EditText>(R.id.etCities)
        val etRegions = findViewById<EditText>(R.id.etRegions)
        val etBudget = findViewById<EditText>(R.id.etBudget)
        val etVisaCost = findViewById<EditText>(R.id.etVisaCost)
        val etFlightCost = findViewById<EditText>(R.id.etFlightCost)
        val etMonthlyExpense = findViewById<EditText>(R.id.etMonthlyExpense)
        val etTuitionFees = findViewById<EditText>(R.id.etTuitionFees)
        val etUniversities = findViewById<EditText>(R.id.etUniversities)
        val etProcess = findViewById<EditText>(R.id.etProcess)
        val etDocuments = findViewById<EditText>(R.id.etDocuments)
        val etApplicationTime = findViewById<EditText>(R.id.etApplicationTime)
        val etScholarships = findViewById<EditText>(R.id.etScholarships)
        val etIELTS = findViewById<EditText>(R.id.btnIELTS)
        val etSOP = findViewById<EditText>(R.id.btnSOP)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)

        btnSubmit.setOnClickListener {
            val countryName = etCountryName.text.toString().trim()
            val adminId = auth.currentUser?.uid

            if (countryName.isEmpty()) {
                Toast.makeText(this, "Please enter a country name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (adminId == null) {
                Toast.makeText(this, "Admin not authenticated", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val countryData = mapOf(
                "about" to etAbout.text.toString(),
                "culture" to etCulture.text.toString(),
                "cities" to etCities.text.toString(),
                "regions" to etRegions.text.toString(),
                "budget" to etBudget.text.toString(),
                "visaCost" to etVisaCost.text.toString(),
                "flightCost" to etFlightCost.text.toString(),
                "monthlyExpense" to etMonthlyExpense.text.toString(),
                "tuitionFees" to etTuitionFees.text.toString(),
                "universities" to etUniversities.text.toString(),
                "process" to etProcess.text.toString(),
                "documents" to etDocuments.text.toString(),
                "applicationTime" to etApplicationTime.text.toString(),
                "scholarships" to etScholarships.text.toString(),
                "ielts" to etIELTS.text.toString(),
                "sop" to etSOP.text.toString()
            )

            val updates = mapOf(
                "/countries/$countryName" to countryData,
                "/admin/$adminId/countries/$countryName" to countryData
            )

            database.updateChildren(updates)
                .addOnSuccessListener {
                    Toast.makeText(this, "Country details saved", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to save details", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
