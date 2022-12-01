package com.example.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class CreateActivity : AppCompatActivity() {

    var id = 0L
    lateinit var EditName : EditText
    lateinit var EditSurname : EditText
    lateinit var EditNumber : EditText
    lateinit var EditDateBorn : EditText
    lateinit var ButtonCreate : Button
    lateinit var ButtonCancel : Button

    companion object {
        const val CANCEL = "CANCEL"
    }

    private val dao by lazy {
        TodoDatabase.getDatabase(this).todoDao()
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create)

        EditName = findViewById(R.id.InfoName)
        EditSurname = findViewById(R.id.EditSurnameCreate)
        EditNumber = findViewById(R.id.InfoNumber)
        EditDateBorn = findViewById(R.id.EditDateBornCreate)
        ButtonCreate = findViewById(R.id.Create_contact)
        ButtonCancel = findViewById(R.id.Cancel_create)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0L)
        val entity = dao.getById(id)

        var intent = Intent(this, MainActivity::class.java)

        ButtonCreate.setOnClickListener {
            entity.name = EditName.text.toString()
            entity.lastName = EditSurname.text.toString()
            entity.number = EditNumber.text.toString()
            entity.dateOfBirth = EditDateBorn.text.toString()
            dao.update(entity)

            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }

        ButtonCancel.setOnClickListener{
            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            intent.putExtra(CANCEL, true)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }

}