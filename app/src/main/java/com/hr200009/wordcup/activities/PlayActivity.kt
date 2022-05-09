package com.hr200009.wordcup.activities

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database

import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R

import com.hr200009.wordcup.models.Word


class PlayActivity : AppCompatActivity() {
    private lateinit var database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private var arrayList: ArrayList<Word> = ArrayList()

    private lateinit var textSource: TextView
    private lateinit var textTarget: EditText
    private lateinit var textControll: String
    private lateinit var goButton: Button

    private var trueCounter: Int = 0
    private var falseCounter: Int = 0
    private var passCounter: Int = 0
    private var wordId: String? = null

    var control: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)

        auth = Firebase.auth
        // arrayList = arrayListOf<Word>()
        database = Firebase.database.reference
        textSource = findViewById(R.id.textViewRandomMain)
        textTarget = findViewById(R.id.textView2)
        goButton = findViewById(R.id.button2)
        getWord()

    }

    private fun getWord() {
        // getir word nesnesi oluştur id ile map ile pushla.

        database = FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())

        database.get().addOnSuccessListener {
            for (word in it.children) {
                val word = word.getValue(Word::class.java)
                arrayList.add(word!!)
            }
            randomWord()
        }


        //arrayList.clear()
/*
        database.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (snapshot in dataSnapshot.children) {

                 val word = snapshot.getValue(Word::class.java)

                    arrayList.add(word!!)

                }
                //randomWord()
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })*/

    }

    private fun randomWord() {

        //var random = (0 until arrayList.size).random()
        // var temp = random
        // random olmuyor buna bak!!!
        arrayList.random().let {Word ->
            textSource.text = Word.source
            textControll = textTarget.text.toString()
            trueCounter = Word.trueCounter!!
            goButton.setOnClickListener(View.OnClickListener {
                if (Word.translation == textTarget.text.toString()) {
                    trueCounter++
                    updateWordStatus(Word.id!!, trueCounter)
                    Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show()
                }
            })


/*
        textSource.text = arrayList[random].source
        trueCounter = arrayList[random]!!.trueCounter!!.toInt()

        goButton.setOnClickListener(View.OnClickListener { view ->

            textControll = textTarget.text.toString()
            if (textControll == arrayList[random].translation) {
                Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show()

                trueCounter++
                // arrayList.removeAt(random)

                updateWordStatus(arrayList[random].id.toString(), trueCounter)
                random = (0 until arrayList.size).random()
                if (random != temp) {
                    textSource.text = arrayList[random].source
                } else {
                    random = (0 until arrayList.size).random()
                    textSource.text = arrayList[random].source
                }

            } else {
                Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show()
                Toast.makeText(
                    this,
                    "Correct answer is: ${arrayList[random].translation}",
                    Toast.LENGTH_SHORT
                ).show()
                Toast.makeText(this, "Your answer is: ${textControll}", Toast.LENGTH_SHORT).show()
            }
        })
*/
        }
    }

    private fun updateWordStatus(wordId: String, trueCounter: Int) {


        // database = FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())
        // .child(wordId)
        //database.child(word.id.toString()).setValue(word)
        database.child(wordId).child("trueCounter").setValue(trueCounter.toInt())
    }

    private fun falseCounter() {
        database =
            FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())
        database.child("falseCounter").setValue(falseCounter.toInt())
    }

    private fun passCounter() {
        database =
            FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())
        database.child("passCounter").setValue(passCounter.toInt())
    }
}
