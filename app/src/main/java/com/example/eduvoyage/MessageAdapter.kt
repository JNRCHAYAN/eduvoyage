
package com.example.eduvoyage

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.TextView
//import com.example.eduvoyage.model.ChatMessage
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context, private val messages: List<ChatMessage>) : BaseAdapter() {

    private val currentUserId = FirebaseAuth.getInstance().uid

    override fun getCount(): Int = messages.size

    override fun getItem(position: Int): Any = messages[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val message = messages[position]
        val view = convertView ?: LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        val textView = view.findViewById<TextView>(android.R.id.text1)

        textView.text = message.message
        textView.textSize = 16f
        textView.setPadding(20, 10, 20, 10)

        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        if (message.senderId == currentUserId) {
            layoutParams.gravity = Gravity.END
            textView.setBackgroundResource(android.R.color.holo_blue_light)
        } else {
            layoutParams.gravity = Gravity.START
            textView.setBackgroundResource(android.R.color.darker_gray)
        }

        textView.layoutParams = layoutParams
        return view
    }
}
