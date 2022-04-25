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
    private var arrayList2: ArrayList<String> = ArrayList()
    private lateinit var textSource: TextView
    private lateinit var textTarget: EditText
    private lateinit var textControll: String
    private lateinit var goButton: Button

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
        val user = auth.currentUser
        val userId = user?.uid
        database = FirebaseDatabase.getInstance().getReference("words").child(userId.toString())

        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                arrayList.clear()
                arrayList2.clear()
                for (snapshot in dataSnapshot.children) {
                    arrayList2.add(snapshot.key.toString())
                    snapshot.key?.let {
                        Toast.makeText(this@PlayActivity, snapshot.key, Toast.LENGTH_SHORT).show()
                    }
                    val word = snapshot.getValue(Word::class.java)
                    arrayList.add(word!!)
                }
                randomWord()
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    private fun randomWord() {
        var random = (0..arrayList.size).random()
        var temp = random
        textSource.text = arrayList[random].source

        goButton.setOnClickListener(View.OnClickListener { view ->
            textControll = textTarget.text.toString()
            if (textControll == arrayList[random].translation) {
                Toast.makeText(this, "You win", Toast.LENGTH_SHORT).show()
               // arrayList.removeAt(random)
                updateWordStatus(random)
                random = (0..arrayList.size).random()
                if(random != temp) {
                    textSource.text = arrayList[random].source
                }
                else {
                    random = (0..arrayList.size).random()
                    textSource.text = arrayList[random].source
                }

            }
            else{
                Toast.makeText(this, "You lose", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Correct answer is: ${arrayList[random].translation}", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Your answer is: ${textControll}", Toast.LENGTH_SHORT).show()
            }
        })

    }
    private fun updateWordStatus(sayi: Int) {
        val user = auth.currentUser
        val userId = user?.uid
        database = FirebaseDatabase.getInstance().getReference("words").child(userId.toString()).child(arrayList2[sayi])
        //database.child(word.id.toString()).setValue(word)
        database.child("trueCounter").setValue("1")
    }
}