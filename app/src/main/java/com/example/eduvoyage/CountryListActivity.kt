package com.example.eduvoyage

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
//import com.example.eduvoyage.model.Country
import com.google.firebase.database.*

class CountryListActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var searchInput: EditText
    private lateinit var adapter: CountryAdapter
    private lateinit var countryList: MutableList<Country>
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country_list)

        searchInput = findViewById(R.id.etSearch)
        recyclerView = findViewById(R.id.recyclerViewCountries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        countryList = mutableListOf()

        adapter = CountryAdapter(countryList) { country ->
            val intent = Intent(this, CountryDetailsActivity::class.java)
            intent.putExtra("countryName", country.name)
            startActivity(intent)
        }

        recyclerView.adapter = adapter

        databaseRef = FirebaseDatabase.getInstance().getReference("countries")

        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                countryList.clear()
                for (child in snapshot.children) {
                    val name = child.key ?: continue
                    val map = child.value as Map<String, String>
                    countryList.add(Country(name, map))
                }
                adapter.setFilteredList(countryList)
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(query: CharSequence?, start: Int, before: Int, count: Int) {
                val filtered = countryList.filter {
                    it.name.contains(query.toString(), ignoreCase = true)
                }
                adapter.setFilteredList(filtered)
            }
        })
    }
}