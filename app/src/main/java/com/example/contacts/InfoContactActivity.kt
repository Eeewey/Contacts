package com.example.contacts

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView


class InfoContactActivity : AppCompatActivity() {

    var id = 0L
    lateinit var NameText : TextView
    lateinit var LastText : TextView
    lateinit var DateText : TextView
    lateinit var NumberText : TextView

    lateinit var RefactorButton : Button
    lateinit var DeleteButton : Button


    private val dao by lazy {
        ContDb.getDatabase(this).todoDao()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.info_contact)

        NameText = findViewById(R.id.InfoName)
        LastText = findViewById(R.id.InfoSurname)
        DateText = findViewById(R.id.InfoDateBorn)
        NumberText = findViewById(R.id.InfoNumber)

        RefactorButton = findViewById(R.id.RedactButton)
        DeleteButton = findViewById(R.id.DeleteButton)

        id = intent.getLongExtra(MainActivity.ITEM_ID_KEY, 0)
        val entity = dao.getById(id)

        NameText.text = entity.name
        LastText.text = entity.lastName
        NumberText.text = entity.number
        DateText.text = entity.dateOfBirth

        DeleteButton.setOnClickListener{
            var returnIntent = Intent()
            returnIntent.putExtra(MainActivity.ITEM_ID_KEY, id)
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