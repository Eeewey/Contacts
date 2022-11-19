package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class InfoContactActivity : AppCompatActivity() {

    var id = 0
    lateinit var NameText : TextView
    lateinit var LastText : TextView
    lateinit var DateText : TextView
    lateinit var NumberText : TextView

    lateinit var RefactorButton : Button
    lateinit var DelteButton : Button

    companion object {
        const val RESULT_KEY = "RESULT_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_contact)

        NameText = findViewById(R.id.Name)
        LastText = findViewById(R.id.LastName)
        DateText = findViewById(R.id.DateOfBirth)
        NumberText = findViewById(R.id.Number)

        RefactorButton = findViewById(R.id.RedactButton)
        DelteButton = findViewById(R.id.DeleteButton)

        val itemTextName = intent.getStringExtra(MainActivity.ITEM_KEY_NAME)
        val itemTextLastName = intent.getStringExtra(MainActivity.ITEM_KEY_LASTNAME)

        id = intent.getIntExtra(MainActivity.ITEM_ID_KEY, 0)

        NameText.text = itemTextName
        LastText.text = itemTextLastName
    }
}