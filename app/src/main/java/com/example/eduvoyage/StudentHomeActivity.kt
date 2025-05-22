package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class StudentHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_home)

        val btnCountryList = findViewById<Button>(R.id.btnCountryList)
        val btnChat = findViewById<Button>(R.id.btnChat)

        btnCountryList.setOnClickListener {
            startActivity(Intent(this, CountryListActivity::class.java)) // Replace with your actual country list activity
        }

        btnChat.setOnClickListener {
            startActivity(Intent(this, AdminListActivity::class.java)) // Start chat with admin
        }
    }
}
