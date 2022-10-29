package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var SearchText : EditText
    lateinit var ButtonSearch : Button

    private val list = mutableListOf<String>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)

        list.add("текст 1")
        list.add("текст 1")
        list.add("текст 1")
        list.add("текст 1")
        list.add("текст 1")
        list.add("текст 1")

        adapter = RecyclerAdapter(list) {
            val intent = Intent(this, InfoContactActivity::class.java)
            startActivity(intent)
        }

        val recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }
}