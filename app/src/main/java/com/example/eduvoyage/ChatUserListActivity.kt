package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.eduvoyage.model.ChatUser
import com.google.firebase.database.*

class ChatUserListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserAdapter
    private lateinit var userList: MutableList<ChatUser>
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_user_list)

        recyclerView = findViewById(R.id.recyclerViewUsers)
        recyclerView.layoutManager = LinearLayoutManager(this)
        userList = mutableListOf()
        adapter = UserAdapter(userList) { user ->
            if (user.uid.isNotBlank()) {
                val intent = Intent(this, AdminChatActivity::class.java)
                intent.putExtra("userId", user.uid)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid user data", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().getReference("students")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                userList.clear()
                for (child in snapshot.children) {
                    val uid = child.key ?: continue
                    val name = child.child("name").getValue(String::class.java) ?: "Unknown"
                    userList.add(ChatUser(uid, name))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatUserListActivity, "Error loading users", Toast.LENGTH_SHORT).show()
            }
        })
    }
}