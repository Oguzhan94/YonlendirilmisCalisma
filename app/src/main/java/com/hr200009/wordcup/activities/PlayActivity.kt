package com.hr200009.wordcup.activities

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.firebase.database.*
import com.hr200009.wordcup.R
import com.hr200009.wordcup.models.Word
import com.hr200009.wordcup.util.FirebaseUtil


class PlayActivity : AppCompatActivity() {
    private var dataBase = FirebaseUtil()


     var arrayList = ArrayList<Word>()


    private lateinit var textSource: TextView
    private lateinit var textTarget: EditText

    private lateinit var tempLayout: ConstraintLayout
    private lateinit var tempText: EditText
    private lateinit var tempButton: Button

    private lateinit var goButton: Button
    private lateinit var passButton: Button

    private var trueCounter: Int = 0
    private var falseCounter: Int = 0
    private var passCounter: Int = 0
    private var viewCounter: Int = 0
    private var isItLearned: Boolean = false

    private var bool: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)


        tempLayout = findViewById(R.id.tempLayout)
        tempText = findViewById(R.id.editTextTextMultiLine)
        tempButton = findViewById(R.id.button3)


        textSource = findViewById(R.id.textViewRandomMain)
        textTarget = findViewById(R.id.textView2)
        goButton = findViewById(R.id.button2)
        passButton = findViewById(R.id.button)


        run()


    }

    private fun run() {
        getWord()

    }

    private fun getWord() {

        arrayList.clear()
        dataBase.allWords
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


        arrayList.random().let { Word ->
            textSource.text = Word.source
            trueCounter = Word.trueCounter!!
            falseCounter = Word.falseCounter!!
            passCounter = Word.passCounter!!
            viewCounter = Word.viewCounter!!






            goButton.setOnClickListener(View.OnClickListener {

                viewCounter++
                Word.viewCounter = viewCounter
                //viewCounter(Word.id.toString(), viewCounter, Word, isItLearned)
                asd(Word.id.toString(), isItLearned, Word, viewCounter)

                if (textTarget.text.toString() == Word.translation) {

                    trueCounter++
                    Word.trueCounter = trueCounter


                    asd(Word.id.toString(), isItLearned, Word, trueCounter)



                    bool = true
                    tempLayout(bool)

                    // Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show()
                } else {
                    falseCounter++
                    Word.falseCounter = falseCounter
                    asd(Word.id.toString(), isItLearned, Word, falseCounter)
                    bool = false
                    tempLayout(bool)
                    // Toast.makeText(this, "Yanlış", Toast.LENGTH_SHORT).show()
                }

                //randomWord()
            })
            passButton.setOnClickListener(View.OnClickListener {


                viewCounter++
                Word.viewCounter = viewCounter
               // viewCounter(Word.id.toString(), viewCounter, Word, isItLearned)
                asd(Word.id.toString(), isItLearned, Word, viewCounter)


                passCounter++
                Word.passCounter = passCounter


               // asd(Word.id.toString(), isItLearned, Word, passCounter)
                asd(Word.id.toString(), isItLearned, Word, passCounter)
                textTarget.text.clear()
                Toast.makeText(this, "PAS GEÇTİNİZ", Toast.LENGTH_SHORT).show()
                randomWord(arrayList)
            })
        }


    }


    private fun learnedWord(id: String, word: Word) {
        dataBase.learnedWords.document(id).set(word)
    }





    private fun asd(wordId: String, isLearned: Boolean, word: Word, args: Any) {
        when (args) {
            passCounter -> dataBase.allWords.document(wordId)
                .update("passCounter", passCounter.toInt())
            falseCounter -> dataBase.allWords.document(wordId)
                .update("falseCounter", falseCounter.toInt())
            trueCounter -> dataBase.allWords.document(wordId)
                .update("trueCounter", trueCounter.toInt())
            viewCounter -> dataBase.allWords.document(wordId)
                .update("viewCounter", viewCounter.toInt())
            isItLearned -> dataBase.allWords.document(wordId).update("isItLearned", isLearned)
        }

        if (trueCounter!! >= 15) {
            word.isItLearned = true
            dataBase.learnedWords.document(wordId).set(word)
        }
        if (isItLearned) {
            when (args) {
                passCounter -> dataBase.allWords.document(wordId)
                    .update("passCounter", passCounter.toInt())
                falseCounter -> dataBase.allWords.document(wordId)
                    .update("falseCounter", falseCounter.toInt())
                trueCounter -> dataBase.allWords.document(wordId)
                    .update("trueCounter", trueCounter.toInt())
                viewCounter -> dataBase.allWords.document(wordId)
                    .update("viewCounter", viewCounter.toInt())
                isItLearned -> dataBase.allWords.document(wordId).update("isItLearned", isLearned)
            }
        }

    }


    override fun onBackPressed() {

        super.onBackPressed()
        finish()
    }
}
