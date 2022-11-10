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

    private val ContList = mutableListOf<ContData>()
    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)

        adapter = RecyclerAdapter(ContList) {
            val intent = Intent(this, InfoContactActivity::class.java)
            //intent.putExtra(it)
            startActivity(intent)
        }

        AddToCont(0,"вася", "мавыгин", "4", "880055355")

        val recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

    }

    fun AddToCont(id : Int, name : String, lastName : String, dateBorn : String, number : String){
        val cont = ContData(id, name, lastName, dateBorn, number)
        ContList.add(cont)

        adapter.notifyItemInserted(id)
    }

    fun GetContData (id : Int) : ContData?{
        return ContList.find { it.id == id }
    }
}