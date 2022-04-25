package com.hr200009.wordcup.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.adaptor.WordAdapter
import com.hr200009.wordcup.models.Word

class AttachedWordsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Word>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attached_words)

        database = Firebase.database.reference
        database.keepSynced(true)
        auth = Firebase.auth

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
        val user = auth.currentUser
        val userId = user?.uid
        database = FirebaseDatabase.getInstance().getReference("words").child(userId.toString())

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayList.clear()
                for (snapshot in dataSnapshot.children) {
                    val word = snapshot.getValue(Word::class.java)
                    arrayList.add(word!!)
                }
                var size = arrayList.size.toString()
                Toast.makeText(this@AttachedWordsActivity, "Size: $size", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = WordAdapter(arrayList)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}