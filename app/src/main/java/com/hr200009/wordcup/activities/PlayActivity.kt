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
import com.hr200009.wordcup.R
import com.hr200009.wordcup.models.Word
import com.hr200009.wordcup.util.FirebaseUtil


class PlayActivity : AppCompatActivity() {
    private var dataBase = FirebaseUtil()


    private var arrayList = ArrayList<Word>()


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
                    arrayList.add(word)
                    println("****************************           ${word.isItLearned.toString()}             **************************** ")
                }
                randomWord(arrayList)
            }


    }


    private fun tempLayout(boolean: Boolean) {
        goButton.visibility = View.INVISIBLE
        passButton.visibility = View.INVISIBLE
        tempLayout.visibility = View.VISIBLE

        if (boolean) {

            tempText.setText(R.string.answerIsCorrect)

        } else {
            tempText.setText(R.string.answerIsWrong)
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

        val numberOfElements = 2



        arrayList.asSequence().shuffled().take(numberOfElements).toList().let {
            for (word in it){
                arrayList.add(word)
            }
        }

        arrayList.random().let { Word ->
            textSource.text = Word.source
            trueCounter = Word.trueCounter!!
            falseCounter = Word.falseCounter!!
            passCounter = Word.passCounter!!
            viewCounter = Word.viewCounter!!
            isItLearned = Word.isItLearned!!



            goButton.setOnClickListener(View.OnClickListener {



                if (textTarget.text.toString() == Word.translation) {

                    trueCounter++
                    Word.trueCounter = trueCounter
                    if (Word.trueCounter!! >= 15 && Word.isItLearned == false) {

                        isItLearned = true
                        Word.isItLearned = isItLearned
                        updateWordData(Word.id.toString(), isItLearned, isItLearned)
                        learnedWord(Word.id.toString(), Word)
                        getWord()
                    }

                    updateWordData(Word.id.toString(), isItLearned, trueCounter)


                    bool = true
                    tempLayout(bool)

                    // Toast.makeText(this, "Doğru", Toast.LENGTH_SHORT).show()
                } else {
                    falseCounter++
                    Word.falseCounter = falseCounter
                    updateWordData(Word.id.toString(), isItLearned, falseCounter)
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
                updateWordData(Word.id.toString(), isItLearned, viewCounter)


                passCounter++
                Word.passCounter = passCounter


                // asd(Word.id.toString(), isItLearned, Word, passCounter)
                updateWordData(Word.id.toString(), isItLearned, passCounter)
                textTarget.text.clear()
                Toast.makeText(this, R.string.answerIsPassed, Toast.LENGTH_SHORT).show()
                randomWord(arrayList)
            })
            viewCounter++
            Word.viewCounter = viewCounter
            updateWordData(Word.id.toString(), isItLearned, viewCounter)
        }


    }


    private fun learnedWord(id: String, word: Word) {
        dataBase.learnedWords.document(id).set(word)
            .addOnSuccessListener {
                Toast.makeText(this, R.string.word_added, Toast.LENGTH_SHORT).show()
            }

    }


    private fun updateWordData(wordId: String, isLearned: Boolean, args: Any) {
        when (args) {
            passCounter -> dataBase.allWords.document(wordId)
                .update("passCounter", passCounter)
            falseCounter -> dataBase.allWords.document(wordId)
                .update("falseCounter", falseCounter)
            trueCounter -> dataBase.allWords.document(wordId)
                .update("trueCounter", trueCounter)
            viewCounter -> dataBase.allWords.document(wordId)
                .update("viewCounter", viewCounter)
            isItLearned -> dataBase.allWords.document(wordId).
            update("isItLearned", isLearned)
        }

        if (isItLearned) {
            when (args) {
                passCounter -> dataBase.learnedWords.document(wordId)
                    .update("passCounter", passCounter.toInt())
                falseCounter -> dataBase.learnedWords.document(wordId)
                    .update("falseCounter", falseCounter.toInt())
                trueCounter -> dataBase.learnedWords.document(wordId)
                    .update("trueCounter", trueCounter.toInt())
                viewCounter -> dataBase.learnedWords.document(wordId)
                    .update("viewCounter", viewCounter.toInt())
            }
        }

    }

    private fun getPopularWords() {
        val intent = intent
        val activity = intent.getStringExtra("activity")
        if (activity == "popular") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun getWeeklyWords() {
        val intent = intent
        val activity = intent.getStringExtra("activity")
        if (activity == "weekly") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    override fun onBackPressed() {

        super.onBackPressed()
        finishAffinity()
    }
}
