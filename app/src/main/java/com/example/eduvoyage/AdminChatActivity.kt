package com.example.eduvoyage

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
//import com.example.eduvoyage.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class AdminChatActivity : AppCompatActivity() {

    private lateinit var messageListView: ListView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button

    private lateinit var messages: MutableList<ChatMessage>
    private lateinit var adapter: MessageAdapter
    private lateinit var databaseRef: DatabaseReference

    private lateinit var receiverId: String
    private lateinit var senderId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_chat)

        receiverId = intent.getStringExtra("userId") ?: ""
        senderId = FirebaseAuth.getInstance().uid ?: ""

        messageListView = findViewById(R.id.messageListView)
        messageInput = findViewById(R.id.etMessage)
        sendButton = findViewById(R.id.btnSend)

        messages = mutableListOf()
        adapter = MessageAdapter(this, messages)
        messageListView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().getReference("messages")

        sendButton.setOnClickListener {
            val messageText = messageInput.text.toString().trim()
            if (messageText.isNotEmpty()) {
                val message = ChatMessage(senderId, receiverId, messageText)
                val messageKey = databaseRef.push().key ?: return@setOnClickListener
                databaseRef.child(messageKey).setValue(message)
                messageInput.text.clear()
            }
        }

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                messages.clear()
                for (msgSnap in snapshot.children) {
                    val msg = msgSnap.getValue(ChatMessage::class.java)
                    if ((msg?.senderId == senderId && msg.receiverId == receiverId) ||
                        (msg?.senderId == receiverId && msg.receiverId == senderId)) {
                        messages.add(msg)
                    }
                }
                adapter.notifyDataSetChanged()
                messageListView.setSelection(messages.size - 1)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@AdminChatActivity, "Failed to load messages", Toast.LENGTH_SHORT).show()
            }
        })
    }
}