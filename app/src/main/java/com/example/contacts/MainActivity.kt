package com.example.contacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var SearchText : EditText
    lateinit var ButtonSearch : Button

    private val ContList = mutableListOf<TodoEntity>()
    private lateinit var adapter: RecyclerAdapter

    private val dao by lazy {
        TodoDatabase.getDatabase(applicationContext).todoDao()
    }

    companion object {
        const val REQUEST_CODE = 1
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)

        ContList.addAll(dao.all)

        adapter = RecyclerAdapter(ContList) {
            val intent = Intent(this, InfoContactActivity::class.java)
            intent.putExtra(ITEM_ID_KEY, it)
            startActivityForResult(intent, REQUEST_CODE)
        }

        AddToCont("Вася","Мавыгин", "0124241", "9124124111")

        val recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    public fun AddToCont(name : String, lastName : String, dateBorn : String, number : String){
        val todoEntity = TodoEntity()

        todoEntity.name = name
        todoEntity.lastName = lastName
        todoEntity.dateOfBirth = dateBorn
        todoEntity.number = number

        todoEntity.isDone = true
        todoEntity.id = dao.insert(todoEntity)

        ContList.add(todoEntity)
        adapter.notifyItemInserted(ContList.lastIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            val id = data!!.getIntExtra(ITEM_ID_KEY, 0)
            val isDelete = data!!.getBooleanExtra(InfoContactActivity.DELETE, false)

            if(isDelete){
                dao.delete(ContList[id])
                ContList.removeAt(id)
                adapter.notifyItemRemoved(id)
            }
        }
    }
}