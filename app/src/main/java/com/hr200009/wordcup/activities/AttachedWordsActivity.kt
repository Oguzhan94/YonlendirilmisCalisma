package com.hr200009.wordcup.activities

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.adaptor.WordAdapter
import com.hr200009.wordcup.models.Word

class AttachedWordsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    private lateinit var recyclerView: RecyclerView
    private lateinit var arrayList: ArrayList<Word>


    private lateinit var textSource: EditText
    private lateinit var textTarget: EditText
    private var textTrueCount: TextView? = null
    private var textFalseCount: TextView? = null
    private var textPassCount: TextView? = null
    private var textViewCount: TextView? = null
    private var textIsItLearned: TextView? = null
    private lateinit var editButton: Button
    private lateinit var writeButton: Button


    private lateinit var tempLayout2: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attached_words)

        database = Firebase.database.reference
        database.keepSynced(true)
        auth = Firebase.auth


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

        textSource?.setText(text.source)
        textTarget?.setText(text.translation)
        textTrueCount?.setText(text.trueCounter.toString())
        textFalseCount?.setText(text.falseCounter.toString())
        textPassCount?.setText(text.passCounter.toString())
        textViewCount?.setText(text.viewCounter.toString())
        textIsItLearned?.setText(text.viewCounter.toString())


        textSource.isFocusableInTouchMode = false
        textTarget.isFocusableInTouchMode = false

        editButton.setOnClickListener() {
            textSource.isFocusableInTouchMode = true
            textTarget.isFocusableInTouchMode = true

        }
        writeButton.setOnClickListener() {
            database = FirebaseDatabase.getInstance().getReference("words").child(auth.uid.toString())
                .child("allWords")
            database.child(text.id.toString()).child("source").setValue(textSource.text.toString())
            database.child(text.id.toString()).child("translation").setValue(textTarget.text.toString())
        }
    }
    private fun firstLayout() {
        setContentView(R.layout.activity_attached_words)
    }

    private fun run() {
        getWords()
    }

    private fun getWords() {
        val user = auth.currentUser
        val userId = user?.uid
        database = FirebaseDatabase.getInstance().getReference("words").child(userId.toString())
            .child("allWords")

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayList.clear()
                for (snapshot in dataSnapshot.children) {
                    val word = snapshot.getValue(Word::class.java)
                    arrayList.add(word!!)
                }
                var size = arrayList.size.toString()

                Toast.makeText(this@AttachedWordsActivity, "Size: $size", Toast.LENGTH_SHORT).show()
                recyclerView.adapter = WordAdapter(arrayList) {
                    arrayList[it].let { word ->

                        secondaryLayout(word)


                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

    }
}