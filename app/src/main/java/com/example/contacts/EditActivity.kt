package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText


class EditActivity : AppCompatActivity() {

    var id = 0L
    lateinit var NameText: EditText
    lateinit var LastText: EditText
    lateinit var DateText: EditText
    lateinit var NumberText: EditText

    lateinit var CancelButton: Button
    lateinit var SaveButton: Button

    companion object {
        const val EDIT = "EDIT"
    }

    private val dao by lazy {
        TodoDatabase.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        NameText = findViewById(R.id.NameEdit)
        LastText = findViewById(R.id.LastNameEdit)
//        DateText = findViewById(R.id.InfoDateBorn)
//        NumberText = findViewById(R.id.)

        CancelButton = findViewById(R.id.Cancelbutton)
        SaveButton = findViewById(R.id.Savebutton)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0L)
        val entity = dao.getById(id)

        NameText.setText(entity.name)
        LastText.setText(entity.lastName)
//        DateText.setText(itemTextDate)
//        NumberText.setText(itemTextNumber)


        CancelButton.setOnClickListener {
            finish()
        }

        SaveButton.setOnClickListener {
            println(entity)
            entity.name = NameText.text.toString()
            entity.lastName = LastText.text.toString()
            println(entity)
            dao.update(entity)
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }
    }
}