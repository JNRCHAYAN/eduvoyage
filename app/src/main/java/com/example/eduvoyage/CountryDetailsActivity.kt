package com.example.eduvoyage

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class CountryDetailsActivity : AppCompatActivity() {

    private lateinit var databaseRef: DatabaseReference
    private lateinit var tvDetails: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_details)

        tvDetails = findViewById(R.id.tvDetails)
        val countryName = intent.getStringExtra("countryName")

        if (countryName == null) {
            Toast.makeText(this, "No country selected", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        databaseRef = FirebaseDatabase.getInstance().getReference("countries").child(countryName)

        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val details = StringBuilder("Country: $countryName\n\n")
                for (child in snapshot.children) {
                    details.append("${child.key?.capitalize()}: ${child.value}\n\n")
                }
                tvDetails.text = details.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CountryDetailsActivity, "Failed to load details", Toast.LENGTH_SHORT).show()
            }
        })
    }
}