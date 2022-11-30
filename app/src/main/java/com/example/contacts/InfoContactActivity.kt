package com.example.contacts

import android.app.Activity
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
    lateinit var DeleteButton : Button

    companion object {
        const val DELETE = "DELETE"
    }

    private val dao by lazy {
        TodoDatabase.getDatabase(applicationContext).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_contact)

        NameText = findViewById(R.id.Name)
        LastText = findViewById(R.id.LastName)
        DateText = findViewById(R.id.DateOfBirth)
        NumberText = findViewById(R.id.Number)

        RefactorButton = findViewById(R.id.RedactButton)
        DeleteButton = findViewById(R.id.DeleteButton)

        id = intent.getIntExtra(MainActivity.ITEM_ID_KEY, 0)
        val entity = dao.getById(id.toLong())

        NameText.text = entity.name
        LastText.text = entity.lastName
        NumberText.text = entity.number
        DateText.text = entity.dateOfBirth

        DeleteButton.setOnClickListener{
            var returnIntent = Intent()
            returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id)
            returnIntent.putExtra(DELETE, true)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }

        RefactorButton.setOnClickListener{
            var intent = Intent(this, EditActivity::class.java)

            intent.putExtra(MainActivity.ITEM_ID_KEY, id)
            startActivity(intent)
        }
    }
}