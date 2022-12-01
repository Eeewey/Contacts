package com.example.contacts

import android.annotation.SuppressLint
import android.app.Activity
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
    lateinit var ButtonCreate : Button

    private val ContList = mutableListOf<TodoEntity>()
    private lateinit var adapter: RecyclerAdapter

    private val dao by lazy {
        TodoDatabase.getDatabase(this).todoDao()
    }

    companion object {
        const val REQUEST_CODE = 1
        const val REQUEST_CODE_CREATE = 2
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)
        ButtonCreate = findViewById(R.id.Add)

        ContList.addAll(dao.all)

        adapter = RecyclerAdapter(ContList) {
            val intent = Intent(this, InfoContactActivity::class.java)
            intent.putExtra(ITEM_ID_KEY, ContList[it].id)
            startActivityForResult(intent, REQUEST_CODE)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        ButtonCreate.setOnClickListener{
            var add_id = AddToCont("None","None", "None", "None")
            var intent = Intent(this, CreateActivity::class.java)

            intent.putExtra(ITEM_ID_KEY, add_id)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }

    }

    fun AddToCont(name : String, lastName : String, dateBorn : String, number : String) : Long{
        val todoEntity = TodoEntity()

        todoEntity.name = name
        todoEntity.lastName = lastName
        todoEntity.dateOfBirth = dateBorn
        todoEntity.number = number

        todoEntity.id = dao.insert(todoEntity)

        ContList.add(todoEntity)
        adapter.notifyItemInserted(ContList.lastIndex)

        return todoEntity.id!!
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
        if (requestCode == REQUEST_CODE_CREATE && resultCode == Activity.RESULT_OK) {

            val id = data!!.getIntExtra(ITEM_ID_KEY, 0)
            val isDelete = data!!.getBooleanExtra(CreateActivity.CANCEL, false)

            if(isDelete){
                dao.delete(ContList[id])
                ContList.removeAt(id)
                adapter.notifyItemRemoved(id)
            }
        }
    }
}