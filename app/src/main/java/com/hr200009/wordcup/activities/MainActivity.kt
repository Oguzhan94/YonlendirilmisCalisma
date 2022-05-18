package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R


class MainActivity : AppCompatActivity() {

    private lateinit var database: DatabaseReference
    private lateinit var nickNameTextView: TextView
    private lateinit var currentUserId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var settingsButton: ImageButton
    private lateinit var openAddWordCategory: Button
    private lateinit var openAttachedWordActivity: Button
    private lateinit var openPlayCategoryActivity: Button
    private lateinit var openLearnedWordsActivity: Button

    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Firebase.database.reference
        database.keepSynced(true)
        auth = Firebase.auth

        nickNameTextView = findViewById(R.id.textNickname)
        settingsButton = findViewById(R.id.buttonOpenSettings)
        openAddWordCategory = findViewById(R.id.buttonAddWordCategory)
        openAttachedWordActivity = findViewById(R.id.buttonToAddedWords)
        openPlayCategoryActivity = findViewById(R.id.buttonOpenPlayCategory)
        openLearnedWordsActivity = findViewById(R.id.buttonLearnedWords)

        currentUserId = auth.uid.toString()
        currentUserId
        readData(currentUserId)

        run()
    }

    private fun run() {
        settingsButton.setOnClickListener(View.OnClickListener {
            openSettingsActivity()
        })
        openAddWordCategory.setOnClickListener(View.OnClickListener {
            openAddWordCategory()
        })
        openAttachedWordActivity.setOnClickListener(View.OnClickListener {
            openAttachedWordActivity()
        })
        openPlayCategoryActivity.setOnClickListener(View.OnClickListener {
            openPlayCategoryActivity()
        })
        openLearnedWordsActivity.setOnClickListener(View.OnClickListener {
            openLearnedWordsActivity()
        })

    }

    private fun readData(userId: String) {

        val dbRef = db.collection("userInfo").document(userId)

        dbRef.get()
            .addOnSuccessListener {
                if (it != null){
                    val nickName: String = getString(R.string.welcome_message, it.get("userName").toString())
                    nickNameTextView.text = nickName
                }else{
                    Toast.makeText(this, "Data bulunamadı", Toast.LENGTH_SHORT)
                }
            }


    }

    private fun openSettingsActivity() {
        val intent = Intent(this@MainActivity, SettingsActivity::class.java)
        startActivity(intent)
    }

    private fun openAddWordCategory() {
        val intent = Intent(this@MainActivity, WordAddCategoryActivity::class.java)
        startActivity(intent)
    }

    private fun openAttachedWordActivity() {
        val intent = Intent(this@MainActivity, AttachedWordsActivity::class.java)
        startActivity(intent)
    }
    private fun openPlayCategoryActivity() {
        val intent = Intent(this@MainActivity, CategoryActivity::class.java)
        startActivity(intent)
    }
    private fun openLearnedWordsActivity() {
        val intent = Intent(this@MainActivity, LearnedWordsActivity::class.java)
        startActivity(intent)
    }
}