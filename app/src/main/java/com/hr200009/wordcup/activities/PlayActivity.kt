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
    private lateinit var passButton: Button

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
        passButton = findViewById(R.id.button)
        getWord()

    }

    private fun getWord() {
        // getir word nesnesi oluştur id ile map ile pushla.

        database = FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (word in snapshot.children) {
                    val word = word.getValue(Word::class.java)
                    arrayList.add(word!!)
                }
                randomWord()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        )


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
    private fun run(){
        goButton.setOnClickListener( View.OnClickListener {
            randomWord()
        })
        passButton.setOnClickListener( View.OnClickListener {

        })
    }

    private fun randomWord() {

        //var random = (0 until arrayList.size).random()
        // var temp = random
        // random olmuyor buna bak!!!

    var random = (0 until arrayList.size).random()

        arrayList[random].let {
            textSource.text = it.source
            textControll = textTarget.text.toString()
            trueCounter = it.trueCounter!!
            falseCounter = it.falseCounter!!
            passCounter = it.passCounter!!
        }

        goButton.setOnClickListener( View.OnClickListener {
          if (textTarget != null) {
              if (textTarget.text.toString() == arrayList[random].translation) {
                  trueCounter++
                  arrayList[random].trueCounter = trueCounter
                  trueCounter(arrayList[random].id.toString(), trueCounter)
              }
              else {
                  falseCounter++
                  arrayList[random].falseCounter = falseCounter
                  falseCounter(arrayList[random].id.toString(), falseCounter)
              }

          }
        })
        passButton.setOnClickListener( View.OnClickListener {
            passCounter++
            arrayList[random].passCounter = passCounter
            passCounter(arrayList[random].id.toString(), passCounter)
        })
        }



    private fun trueCounter(wordId: String, trueCounter: Int) {


        // database = FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())
        // .child(wordId)
        //database.child(word.id.toString()).setValue(word)
        database.child(wordId).child("trueCounter").setValue(trueCounter.toInt())
    }

    private fun falseCounter(wordId: String, falseCounter: Int) {
        database.child(wordId).child("falseCounter").setValue(falseCounter.toInt())
    }

    private fun passCounter(wordId: String, passCounter: Int) {
        database.child(wordId).child("passCounter").setValue(passCounter.toInt())
    }
}

