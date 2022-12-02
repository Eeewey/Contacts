package com.example.contacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    lateinit var SearchText : EditText
    lateinit var ButtonCreate : Button

    private var ContList = mutableListOf<ContEntity>()
    private var buffVievList = mutableListOf<ContEntity>()

    private lateinit var adapter: RecyclerAdapter
    lateinit var recyclerView : RecyclerView

    private val dao by lazy {
        ContDb.getDatabase(this).todoDao()
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
        ButtonCreate = findViewById(R.id.Add)

        recyclerView = findViewById(R.id.ViewOfContacts)

        UpdateAdapter()

        ButtonCreate.setOnClickListener{
            var add_id = AddToCont("None","None", "None", "None")
            var intent = Intent(this, CreateActivity::class.java)

            intent.putExtra(ITEM_ID_KEY, add_id)
            startActivityForResult(intent, REQUEST_CODE_CREATE)
        }

        SearchText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                SortList(SearchText.text.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun SortList(text: String){
        if(text == ""){
            UpdateAdapter()
        }else {
            buffVievList.clear()
            buffVievList.addAll(dao.all)

            ContList.clear()

            for (cont in buffVievList) {
                if (text.toLowerCase() in (cont.name.toLowerCase() + " " +cont.lastName.toLowerCase())) {
                    if(!ContList.contains(cont)) {
                        ContList.add(cont)
                    }
                }
            }

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
    }

    private fun UpdateAdapter(){
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
        val contEntity = ContEntity()

        contEntity.name = name
        contEntity.lastName = lastName
        contEntity.dateOfBirth = dateBorn
        contEntity.number = number

        contEntity.id = dao.insert(contEntity)

        ContList.add(contEntity)
        adapter.notifyItemInserted(ContList.lastIndex)

        return contEntity.id!!
    }

    override fun onActivityResult(requestCode: Int, resultCode : Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val id = data!!.getLongExtra(ITEM_ID_KEY, 0L)

            val cont = dao.getById(id)
            var it = 0

            for(cont in ContList){
                ++it
                if(cont.id == id){
                    break
                }
            }

            when (requestCode){
                REQUEST_CODE_INFO -> {
                    dao.delete(cont)
                    ContList.remove(cont)
                    adapter.notifyItemRemoved(it)

                    UpdateAdapter()
                }

                REQUEST_CODE_CREATE -> {
                    dao.delete(cont)
                    ContList.remove(cont)
                    adapter.notifyItemRemoved(it)

                    UpdateAdapter()
                }
            }
        }
    }
}