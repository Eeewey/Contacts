package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class MainActivity : AppCompatActivity() {

    lateinit var SearchText : EditText
    lateinit var ButtonSearch : Button

    private val ContList = mutableListOf<TodoEntity>()
    private lateinit var adapter: RecyclerAdapter

    lateinit var db: TodoDatabase
    lateinit var todoDao: TodoDao

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_KEY_NAME = "ITEM_KEY_NAME"
        const val ITEM_KEY_LASTNAME = "ITEM_KEY_LASTNAME"
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)

        db = Room.databaseBuilder(
            applicationContext,
            TodoDatabase::class.java, "todo"
        )
            .allowMainThreadQueries() // выполняемся в основном потоке
            .build()
        todoDao = db.todoDao()

        ContList.addAll(todoDao.all)

        adapter = RecyclerAdapter(ContList) {
            val intent = Intent(this, InfoContactActivity::class.java)
            intent.putExtra(ITEM_KEY_NAME, ContList.get(it).name)
            intent.putExtra(ITEM_KEY_LASTNAME, ContList.get(it).lastName)
            intent.putExtra(ITEM_ID_KEY, it)
            startActivityForResult(intent, REQUEST_CODE)
        }

        AddToCont("Вася","ааа", "0124241", "9124124111")

        val recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun AddToCont(name : String, lastName : String, dateBorn : String, number : String){
        val todoEntity = TodoEntity()
        todoEntity.name = name
        todoEntity.lastName = lastName
        todoEntity.dateOfBirth = dateBorn
        todoEntity.number = number

        todoEntity.id = todoDao.insert(todoEntity)

        ContList.add(todoEntity)
        adapter.notifyItemInserted(ContList.lastIndex)
    }
}