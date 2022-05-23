package com.hr200009.wordcup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.WorkSource
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.models.Word
import com.hr200009.wordcup.util.FirebaseUtil

class ManualWordAddActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var textWordSource: EditText
    private lateinit var textWordTarget: EditText

    private lateinit var auth: FirebaseAuth

    private lateinit var wordSource: String
    private lateinit var wordTarget: String

    private var trueCounter: Int = 0
    private var falseCounter: Int = 0
    private var passCounter: Int = 0
    private var isItLearned: Boolean = false
    private var viewCounter: Int = 0
    private var wordId: String? = null

    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_word_add)

        saveButton = findViewById(R.id.buttonSaveWordsManualAdded)
        textWordSource = findViewById(R.id.textWordSourceManuelAdded)
        textWordTarget = findViewById(R.id.textWordTargetManuelAdded)


        auth = Firebase.auth

        run()
    }

    private fun run() {
        saveButton.setOnClickListener {
            wordAddManual()
        }
    }

    private fun wordAddManual() {
        wordSource = textWordSource.text.toString()
        wordTarget = textWordTarget.text.toString()
        wordId= db.collection("words").document(auth.uid.toString()).collection("allWords").document().toString()

        if (wordSource.isNotEmpty() && wordTarget.isNotEmpty()) {
            //val word = Word(wordSource, wordTarget, trueCounter, falseCounter, passCounter, isItLearned,)
            val wordData = hashMapOf(
                "source" to wordSource.lowercase(),
                "translation" to wordTarget.lowercase(),
                "trueCounter" to trueCounter,
                "falseCounter" to falseCounter,
                "passCounter" to passCounter,
                "isItLearned" to isItLearned,
                "id" to wordId,
                "viewCounter" to viewCounter
            )

            db.collection("words").document(auth.uid.toString()).collection("allWords")
                .document(wordId.toString())
                .set(wordData)
                .addOnSuccessListener {
                    Toast.makeText(this, R.string.word_added, Toast.LENGTH_SHORT).show()
                }.addOnFailureListener {
                    Toast.makeText(this, R.string.word_add_failed, Toast.LENGTH_SHORT).show()
                }
        }
        else {
            Toast.makeText(this, R.string.word_add_empty_fields, Toast.LENGTH_SHORT).show()
        }
    }
}

