package com.hr200009.wordcup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
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
import com.hr200009.wordcup.models.Word
import com.hr200009.wordcup.util.FirebaseUtil

class LearnedWordsActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Word>


    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_learned_words)

        recyclerView = findViewById(R.id.recyclerViewAttachedWords)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)

        arrayList = arrayListOf<Word>()
        run()
    }
    private fun run() {
        getWords()
    }

    private fun getWords() {

        val dbRef = dataBase.learnedWords
        dbRef.addSnapshotListener { dataSnapshot, _ ->

            arrayList.clear()
            if (dataSnapshot != null) {
                for (snapshot in dataSnapshot) {
                    val word = snapshot.toObject(Word::class.java)
                    arrayList.add(word)
                }
                var size = arrayList.size.toString()

                Toast.makeText(this@LearnedWordsActivity, "You have $size words", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = WordAdapter(arrayList) {

                }
            }

        }


    }
}