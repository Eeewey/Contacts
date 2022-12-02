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

    private val dao by lazy {
        ContDb.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        NameText = findViewById(R.id.NameEdit)
        LastText = findViewById(R.id.LastNameEdit)
        DateText = findViewById(R.id.editTextDate)
        NumberText = findViewById(R.id.editNumber)

        CancelButton = findViewById(R.id.Cancelbutton)
        SaveButton = findViewById(R.id.Savebutton)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0L)
        val entity = dao.getById(id)

        NameText.setText(entity.name)
        LastText.setText(entity.lastName)
        DateText.setText(entity.dateOfBirth)
        NumberText.setText(entity.number)

        CancelButton.setOnClickListener {
            finish()
        }

        SaveButton.setOnClickListener {
            entity.name = NameText.text.toString()
            entity.lastName = LastText.text.toString()
            entity.number = NumberText.text.toString()
            entity.dateOfBirth = DateText.text.toString()

            dao.update(entity)
            var intent = Intent(this, MainActivity::class.java)
            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }
    }
}