package com.example.contacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var SearchText : EditText
    lateinit var ButtonSearch : Button
    lateinit var ButtonCreate : Button

    private var ContList = mutableListOf<TodoEntity>()
    private var hideList = mutableListOf<TodoEntity>()

    private lateinit var adapter: RecyclerAdapter
    lateinit var recyclerView : RecyclerView

    private val dao by lazy {
        TodoDatabase.getDatabase(this).todoDao()
    }

    companion object {
        const val REQUEST_CODE_INFO = 1
        const val REQUEST_CODE_CREATE = 2
        const val ITEM_ID_KEY = "ITEM_ID_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SearchText = findViewById(R.id.editTextTextPersonName)
        ButtonSearch = findViewById(R.id.SearchButton)
        ButtonCreate = findViewById(R.id.Add)
        recyclerView = findViewById<RecyclerView>(R.id.ViewOfContacts)

        changeList()

        ButtonCreate.setOnClickListener{
            var add_id = AddToCont("None","None", "None", "None")
            var intent = Intent(this, CreateActivity::class.java)

            intent.putExtra(ITEM_ID_KEY, add_id)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }

        SearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                filter(SearchText.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    fun filter(text: String){
        if(text == ""){
            changeList()
        }else {
            hideList.clear()
            hideList.addAll(dao.all)

            ContList.clear()

            for (cont in hideList) {
                if (text in cont.name.toLowerCase()) {
                    if(!ContList.contains(cont)) {
                        ContList.add(cont)
                    }
                }
            }
            changeListFiltered(ContList)
        }
    }

    fun changeListFiltered(filters: MutableList<TodoEntity>){
        adapter = RecyclerAdapter(filters) {
            if(it != -1) {
                val intent = Intent(this, InfoContactActivity::class.java)
                intent.putExtra(ITEM_ID_KEY, ContList[it].id)
                startActivityForResult(intent, REQUEST_CODE_INFO)
            }

        }
        adapter.notifyItemInserted(filters.lastIndex)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    fun changeList(){
        ContList.clear()
        ContList.addAll(dao.all)

        adapter = RecyclerAdapter(ContList) {
            if(it != -1) {
                val intent = Intent(this, InfoContactActivity::class.java)
                intent.putExtra(ITEM_ID_KEY, ContList[it].id)
                startActivityForResult(intent, REQUEST_CODE_INFO)
            }

        }
        adapter.notifyItemInserted(ContList.lastIndex)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
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

        if (resultCode == Activity.RESULT_OK) {

            val id = data!!.getIntExtra(ITEM_ID_KEY, 0)

            when (requestCode){
                REQUEST_CODE_INFO -> {
                    val isDelete = data!!.getBooleanExtra(InfoContactActivity.DELETE, false)

                    if (isDelete) {
                        dao.delete(ContList[id])
                        ContList.removeAt(id)
                        adapter.notifyItemRemoved(id)
                    }
                }
                REQUEST_CODE_CREATE -> {
                    val isCancel = data!!.getBooleanExtra(CreateActivity.CANCEL, false)

                    if(isCancel){
                        dao.delete(ContList[id])
                        ContList.removeAt(id)
                        adapter.notifyItemRemoved(id)
                    }
                }
            }
        }
    }
}