package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginChoiceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_choice)

        findViewById<Button>(R.id.btnStudentLogin).setOnClickListener {
            startActivity(Intent(this, StudentLoginActivity::class.java))
        }

        findViewById<Button>(R.id.btnAdminLogin).setOnClickListener {
            startActivity(Intent(this, AdminLoginActivity::class.java))
        }
    }
}
