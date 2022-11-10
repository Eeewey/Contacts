package com.example.contacts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView

class InfoContactActivity : AppCompatActivity() {

    lateinit var NameText : TextView
    lateinit var LastText : TextView
    lateinit var DateText : TextView
    lateinit var NumberText : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_contact)

        NameText = findViewById(R.id.Name)
        LastText = findViewById(R.id.LastName)
        DateText = findViewById(R.id.DateOfBirth)
        NumberText = findViewById(R.id.Number)

        //val id = intent
    }

    public fun SetText(name : String, lastName : String, dateBorn : String, number : String){
        NameText.text = name
        LastText.text = lastName
        DateText.text = dateBorn
        NumberText.text = number
    }
}