package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.eduvoyage.AdminUser
import com.google.firebase.database.*

class AdminListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AdminAdapter
    private lateinit var adminList: MutableList<AdminUser>
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_list)

        recyclerView = findViewById(R.id.recyclerViewAdmins)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adminList = mutableListOf()
        adapter = AdminAdapter(adminList) { admin ->
            if (admin.uid.isNotBlank()) {
                val intent = Intent(this, UserChatActivity::class.java)
                intent.putExtra("adminId", admin.uid)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Invalid admin data", Toast.LENGTH_SHORT).show()
            }
        }
        recyclerView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().getReference("admin")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                adminList.clear()
                for (child in snapshot.children) {
                    val uid = child.key ?: continue
                    val name = child.child("name").getValue(String::class.java) ?: "Unknown"
                    adminList.add(AdminUser(uid, name))
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminListActivity, "Error loading admin list", Toast.LENGTH_SHORT).show()
            }
        })
    }
}