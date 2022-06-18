package com.hr200009.wordcup.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import com.hr200009.wordcup.R

class CategoryActivity : AppCompatActivity() {

    private lateinit var mixUpWordsButton: Button
    private lateinit var popularWords: Button
    private lateinit var weeklyWords: Button



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        mixUpWordsButton = findViewById(R.id.buttonRandomWordPlay)
        popularWords = findViewById(R.id.buttonPopularWords)
        weeklyWords = findViewById(R.id.buttonWeeklyWords)

        run()
    }

    private fun run() {
        mixUpWordsButton.setOnClickListener {
            openPlayActivityToMixUp()
        }
        popularWords.setOnClickListener {
            openPlayActivityToPopular()
        }
        weeklyWords.setOnClickListener {
            openPlayActivityToWeekly()
        }
    }

    private fun openPlayActivityToMixUp() {
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("activity", "mixUp")
        startActivity(intent)
    }

    private fun openPlayActivityToPopular() {
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("activity", "popular")
        startActivity(intent)
    }

    private fun openPlayActivityToWeekly() {
        val intent = Intent(this, PlayActivity::class.java)
        intent.putExtra("activity", "weekly")
        startActivity(intent)
    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}