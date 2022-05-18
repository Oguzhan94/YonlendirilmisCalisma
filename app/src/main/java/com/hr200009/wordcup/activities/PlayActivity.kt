package com.hr200009.wordcup.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore

import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R

import com.hr200009.wordcup.models.Word


class PlayActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth

    private var arrayList = ArrayList<Word>()


    private lateinit var textSource: TextView
    private lateinit var textTarget: EditText

    private lateinit var tempLayout: ConstraintLayout
    private lateinit var tempText: EditText
    private lateinit var tempButton: Button

    private lateinit var goButton: Button
    private lateinit var passButton: Button

    private  var trueCounter: Int = 0
    private var falseCounter: Int = 0
    private var passCounter: Int = 0
    private var viewCounter: Int = 0
    private var isItLearned: Boolean = false

    private var bool: Boolean = false

    private val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        auth = Firebase.auth

        tempLayout = findViewById(R.id.tempLayout)
        tempText = findViewById(R.id.editTextTextMultiLine)
        tempButton = findViewById(R.id.button3)


        database = Firebase.database.reference
        textSource = findViewById(R.id.textViewRandomMain)
        textTarget = findViewById(R.id.textView2)
        goButton = findViewById(R.id.button2)
        passButton = findViewById(R.id.button)
        //arrayList = ArrayList()


        run()


    }

    private fun run() {
        getWord()
    }
    private fun getWord() {

        arrayList.clear()
        db.collection("words").document(auth.uid.toString()).collection("allWords")
            .get()
            .addOnSuccessListener {
                for (word in it) {
                    val word = word.toObject(Word::class.java)

                    arrayList.add(word!!)

                }
                randomWord(arrayList)
            }


    }

    private fun tempLayout(boolean: Boolean) {
        goButton.visibility = View.INVISIBLE
        passButton.visibility = View.INVISIBLE
        tempLayout.visibility = View.VISIBLE

        if (boolean) {

            tempText.setText("Doğru cevap verdiniz")

        } else {
            tempText.setText("Yanlış cevap verdiniz")
        }
        tempButton.setOnClickListener(View.OnClickListener {
            goButton.visibility = View.VISIBLE
            passButton.visibility = View.VISIBLE
            tempLayout.visibility = View.INVISIBLE
            textTarget.text.clear()
            randomWord(arrayList)
        })


    }

    private fun randomWord(arrayList: ArrayList<Word>) {

        db.collection("words").document(auth.uid.toString()).collection("allWords")

        arrayList.random().let { Word ->
            textSource.text = Word.source
            trueCounter = Word.trueCounter!!
            falseCounter = Word.falseCounter!!
            passCounter = Word.passCounter!!
            viewCounter = Word.viewCounter!!






            goButton.setOnClickListener(View.OnClickListener {


                viewCounter++
                Word.viewCounter = viewCounter
                viewCounter(Word.id.toString(), viewCounter, Word, isItLearned)

                if (textTarget.text.toString() == Word.translation) {

                    trueCounter++
                    Word.trueCounter = trueCounter

                    trueCounter(Word.id.toString(), trueCounter, Word, isItLearned)

                    if (trueCounter!! >= 15) {
                        isItLearned = true
                        Word.isItLearned = isItLearned
                        isLearned(Word.id.toString(), isItLearned, Word)
                        learnedWord(Word.id!!, Word)
                    }

                    bool = true
                    tempLayout(bool)

                    // Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show()
                } else {
                    falseCounter++
                    Word.falseCounter = falseCounter
                    falseCounter(Word.id.toString(), falseCounter, Word, isItLearned)
                    bool = false
                    tempLayout(bool)
                    // Toast.makeText(this, "Yanlış", Toast.LENGTH_SHORT).show()
                }

                //randomWord()
            })
            passButton.setOnClickListener(View.OnClickListener {


                viewCounter++
                Word.viewCounter = viewCounter
                viewCounter(Word.id.toString(), viewCounter, Word, isItLearned)


                passCounter++
                Word.passCounter = passCounter

                passCounter(Word.id.toString(), passCounter, Word, isItLearned)
                textTarget.text.clear()
                Toast.makeText(this, "PAS GEÇTİNİZ", Toast.LENGTH_SHORT).show()
                randomWord(arrayList)
            })
        }


    }


    private fun learnedWord(id: String, word: Word) {
        var dbRef = db.collection("words").document(auth.uid.toString())
        dbRef.collection("learnedWords").document(id).set(word)


    }

    private fun viewCounter(wordId: String, viewCounter: Int, word: Word, isItLearned: Boolean) {


        var dbRef = db.collection("words").document(auth.uid.toString()).collection("allWords")
        dbRef.document(wordId).update("viewCounter", viewCounter.toInt())


        if (isItLearned) {
            learnedWord(wordId, word)
        }

    }

    private fun isLearned(wordId: String, isLearned: Boolean, word: Word) {

        var dbRef = db.collection("words").document(auth.uid.toString()).collection("allWords")
        dbRef.document(wordId).update("isItLearned", isLearned)

        if (isItLearned) {
            learnedWord(wordId, word)
        }
    }

    private fun trueCounter(wordId: String, trueCounter: Int, word: Word, isItLearned: Boolean) {



        var dbRef = db.collection("words").document(auth.uid.toString()).collection("allWords")
        dbRef.document(wordId).update("trueCounter", trueCounter.toInt())

        if (isItLearned) {
            learnedWord(wordId, word)
        }
    }

    private fun falseCounter(wordId: String, falseCounter: Int, word: Word, isItLearned: Boolean) {



        var dbRef = db.collection("words").document(auth.uid.toString()).collection("allWords")
        dbRef.document(wordId).update("falseCounter", falseCounter.toInt())
        if (isItLearned) {
            learnedWord(wordId, word)
        }
    }

    private fun passCounter(wordId: String, passCounter: Int, word: Word, isItLearned: Boolean) {


        var dbRef = db.collection("words").document(auth.uid.toString()).collection("allWords")
        dbRef.document(wordId).update("passCounter", passCounter.toInt())

        if (isItLearned) {
            learnedWord(wordId, word)
        }

    }

    override fun onBackPressed() {

        super.onBackPressed()
        finish()
    }
}
