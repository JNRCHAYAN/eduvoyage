package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

        val addCountryBtn = findViewById<Button>(R.id.btnAddCountry)
        val chatUserBtn = findViewById<Button>(R.id.btnChatWithUser)

        addCountryBtn.setOnClickListener {
            startActivity(Intent(this, AddCountryDetailsActivity::class.java))
        }


        chatUserBtn.setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
