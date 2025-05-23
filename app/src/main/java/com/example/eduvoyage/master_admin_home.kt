package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class master_admin_home : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_master_admin_home)

        val addCountryBtn = findViewById<Button>(R.id.btnAddCountry)

        addCountryBtn.setOnClickListener {
            startActivity(Intent(this, AddCountryDetailsActivity::class.java))
        }

    }
}
