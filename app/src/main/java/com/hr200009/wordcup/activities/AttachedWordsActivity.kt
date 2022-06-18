package com.hr200009.wordcup.activities

import android.content.Intent
import android.os.Bundle
import android.view.View

import android.widget.Button
import android.widget.EditText

import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.adaptor.WordAdapter
import com.hr200009.wordcup.databinding.ActivityAttachedWordsBinding
import com.hr200009.wordcup.models.Word
import com.hr200009.wordcup.util.FirebaseUtil

class AttachedWordsActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Word>

    private lateinit var textSource: EditText
    private lateinit var textTarget: EditText
    private lateinit var textTrueCount: TextView
    private lateinit var textFalseCount: TextView
    private lateinit var textPassCount: TextView
    private lateinit var textViewCount: TextView
    private lateinit var textIsItLearned: TextView
    private lateinit var editButton: Button
    private lateinit var writeButton: Button

private lateinit var binding: ActivityAttachedWordsBinding

    private lateinit var tempLayout2: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attached_words)



        tempLayout2 = findViewById(R.id.cons)



        recyclerView = findViewById(R.id.recyclerViewAttachedWords)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        arrayList = arrayListOf<Word>()
        run()
    }

    private fun secondaryLayout(text: Word) {
        setContentView(R.layout.word_detail)

        textSource = findViewById(R.id.textSource)
        textTarget = findViewById(R.id.textTarget)
        textTrueCount = findViewById(R.id.textTrueCount)
        textFalseCount = findViewById(R.id.textFalseCount)
        textPassCount = findViewById(R.id.textPassCount)
        textViewCount = findViewById(R.id.textViewCount)
        textIsItLearned = findViewById(R.id.textIsItLearned)
        editButton = findViewById(R.id.editButton)
        writeButton = findViewById(R.id.writeButton)

        text.let {
            textSource.setText(it.source)
            textTarget.setText(it.translation)
            textTrueCount.text = it.trueCounter.toString()
            textFalseCount.text = it.falseCounter.toString()
            textPassCount.text = it.passCounter.toString()
            textViewCount.text = it.viewCounter.toString()
            textIsItLearned.text = it.isItLearned.toString()}


        textSource.isFocusableInTouchMode = false
        textTarget.isFocusableInTouchMode = false

        editButton.setOnClickListener() {
            textSource.isFocusableInTouchMode = true
            textTarget.isFocusableInTouchMode = true

            textSource.requestFocus()

        }
        writeButton.setOnClickListener() {
            dataBase.allWords.document(text.id.toString())
                .update(mapOf(
                    "source" to textSource.text.toString(),
                    "translation" to textTarget.text.toString()
                ))
                .addOnSuccessListener {
                    Toast.makeText(this,R.string.success_word_update,Toast.LENGTH_SHORT)
                }
            textSource.isFocusableInTouchMode = false
            textTarget.isFocusableInTouchMode = false
        }

    }


    private fun run() {
        getWords()


    }

    private fun getWords() {


        dataBase.allWords.get().addOnSuccessListener { it ->
            arrayList.clear()
            if (it != null) {
                for (snapshot in it) {
                    val word = snapshot.toObject(Word::class.java)
                    arrayList.add(word)
                }
                var size = arrayList.size.toString()

                Toast.makeText(this@AttachedWordsActivity, "Size: $size", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = WordAdapter(arrayList) {
                    secondaryLayout(arrayList[it])
                }
            }

        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
       finish()
    }
}