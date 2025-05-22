package com.example.eduvoyage


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
//import com.example.eduvoyage.model.AdminUser

class AdminAdapter(
    private val admins: List<AdminUser>,
    private val onClick: (AdminUser) -> Unit
) : RecyclerView.Adapter<AdminAdapter.AdminViewHolder>() {

    inner class AdminViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvAdminName: TextView = view.findViewById(R.id.tvAdminName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdminViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_admin, parent, false)
        return AdminViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdminViewHolder, position: Int) {
        val admin = admins[position]
        holder.tvAdminName.text = admin.name
        holder.itemView.setOnClickListener { onClick(admin) }
    }

    override fun getItemCount(): Int = admins.size
}
