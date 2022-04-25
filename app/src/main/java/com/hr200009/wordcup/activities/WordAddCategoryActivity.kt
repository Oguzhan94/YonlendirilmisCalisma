package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.hr200009.wordcup.R

class WordAddCategoryActivity : AppCompatActivity() {

    private lateinit var addWordFromDictionary: Button
    private lateinit var addWordManuel: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_word_add_category)

        addWordFromDictionary = findViewById(R.id.buttonAddWordFromDictionary)
        addWordManuel = findViewById(R.id.buttonAddWordFromManuel)

        run()
    }

    private fun run() {
        addWordFromDictionary.setOnClickListener() {
            Intent(this, DictionaryWordAddActivity::class.java).also {
                startActivity(it)
            }
        }
        addWordManuel.setOnClickListener() {
            Intent(this, ManualWordAddActivity::class.java).also {
                startActivity(it)
            }
        }
    }

}