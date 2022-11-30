package com.example.contacts

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText


class EditActivity : AppCompatActivity() {

    var id = 0
    lateinit var NameText: EditText
    lateinit var LastText: EditText
    lateinit var DateText: EditText
    lateinit var NumberText: EditText

    lateinit var CancelButton: Button
    lateinit var SaveButton: Button

    companion object {
        const val EDIT = "EDIT"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)

        NameText = findViewById(R.id.NameEdit)
        LastText = findViewById(R.id.LastNameEdit)
        /*DateText = findViewById(R.id.DateEdit)
        NumberText = findViewById(R.id.NumberEdit)*/

        CancelButton = findViewById(R.id.Cancelbutton)
        SaveButton = findViewById(R.id.Savebutton)

        id = intent.getIntExtra(MainActivity.ITEM_ID_KEY, 0)

        //NameText.setText(TodoDatabase.getDatabase(this).todoDao().getById(id.toLong()).name)
        //LastText.setText(TodoDatabase.getDatabase(this).todoDao().getById(id.toLong()).lastName)
        /*DateText.setText(itemTextDate)
        NumberText.setText(itemTextNumber)*/


        CancelButton.setOnClickListener {
            finish()
        }

        SaveButton.setOnClickListener {
            var intent = Intent(this, MainActivity::class.java)

            finish()
        }
    }
}