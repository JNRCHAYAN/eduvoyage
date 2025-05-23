package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class AdminHomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_home)

     
        val chatUserBtn = findViewById<Button>(R.id.btnChatWithUser)


        chatUserBtn.setOnClickListener {
            startActivity(Intent(this, ChatUserListActivity::class.java))
        }

    }
}
