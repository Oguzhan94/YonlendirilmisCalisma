package com.hr200009.wordcup.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.auth.User
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R


class UserInfoActivity : AppCompatActivity() {

    private lateinit var radioGroupToBeLearned: RadioGroup
    private lateinit var radioGroupDifficulty: RadioGroup
    private lateinit var radioGroupNotification: RadioGroup
    private lateinit var toBeLearnedFirst: RadioButton
    private lateinit var toBeLearnedSecond: RadioButton
    private lateinit var difficultyEasy: RadioButton
    private lateinit var difficultyMedium: RadioButton
    private lateinit var difficultyHard: RadioButton
    private lateinit var notificationHourly: RadioButton
    private lateinit var notificationDaily: RadioButton
    private lateinit var notificationMonthly: RadioButton
    private lateinit var buttonSaveUserInfo: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var currentUserId: String

    private var toBeLearned = ""
    private var difficulty = ""
    private var notification = ""


    val db = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        radioGroupToBeLearned = findViewById(R.id.toBeLearnedRadioGroup)
        radioGroupDifficulty = findViewById(R.id.diffucultyRadioGroup)
        radioGroupNotification = findViewById(R.id.notificationRadioGroup)
        toBeLearnedFirst = findViewById(R.id.toBeLearnedFirst)
        toBeLearnedSecond = findViewById(R.id.toBeLearnedSecond)
        difficultyEasy = findViewById(R.id.diffucultyEasy)
        difficultyMedium = findViewById(R.id.diffucultyMedium)
        difficultyHard = findViewById(R.id.diffucultyHard)
        notificationHourly = findViewById(R.id.notificationHourly)
        notificationDaily = findViewById(R.id.notificationWeekly)
        notificationMonthly = findViewById(R.id.notificationMontly)
        buttonSaveUserInfo = findViewById(R.id.buttonSaveUserInfo)


        auth = Firebase.auth
        currentUserId = auth.uid.toString()




        run()
    }

    private fun getData(userId: String) {

       val dbRef = db.collection("userInfo").document(userId)
        dbRef.get()
            .addOnSuccessListener {

                toBeLearned = it.get("toBeLearned").toString()
                difficulty = it.get("difficulty").toString()
                notification = it.get("notificationFrequency").toString()


                if (toBeLearned == "0") {
                    radioGroupToBeLearned.check(R.id.toBeLearnedFirst)
                } else {
                    radioGroupToBeLearned.check(R.id.toBeLearnedSecond)
                }

                when (difficulty) {
                    "0" -> {
                        radioGroupDifficulty.check(R.id.diffucultyEasy)
                    }
                    "1" -> {
                        radioGroupDifficulty.check(R.id.diffucultyMedium)
                    }
                    else -> {
                        radioGroupDifficulty.check(R.id.diffucultyHard)
                    }
                }

                when (notification) {
                    "0" -> {
                        radioGroupNotification.check(R.id.notificationHourly)
                    }
                    "1" -> {
                        radioGroupNotification.check(R.id.notificationWeekly)
                    }
                    else -> {
                        radioGroupNotification.check(R.id.notificationMontly)
                    }
                }

            }
/*
        database = FirebaseDatabase.getInstance().getReference("userInfo")
        database.child(userId).get().addOnSuccessListener {
            toBeLearned = it.child("toBeLearned").value.toString()
            difficulty = it.child("difficulty").value.toString()
            notification = it.child("notificationFrequency").value.toString()

            if (toBeLearned == "0") {
                radioGroupToBeLearned.check(R.id.toBeLearnedFirst)
            } else {
                radioGroupToBeLearned.check(R.id.toBeLearnedSecond)
            }

            when (difficulty) {
                "0" -> {
                    radioGroupDifficulty.check(R.id.diffucultyEasy)
                }
                "1" -> {
                    radioGroupDifficulty.check(R.id.diffucultyMedium)
                }
                else -> {
                    radioGroupDifficulty.check(R.id.diffucultyHard)
                }
            }

            when (notification) {
                "0" -> {
                    radioGroupNotification.check(R.id.notificationHourly)
                }
                "1" -> {
                    radioGroupNotification.check(R.id.notificationWeekly)
                }
                else -> {
                    radioGroupNotification.check(R.id.notificationMontly)
                }
            }*/
        }


    private fun updateData(userId: String) {

        val dbRef = db.collection("userInfo").document(userId)


        radioGroupToBeLearned.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.toBeLearnedFirst -> toBeLearned = "0"
                R.id.toBeLearnedSecond -> toBeLearned = "1"
            }

        }
        radioGroupDifficulty.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.diffucultyEasy -> difficulty = "0"
                R.id.diffucultyMedium -> difficulty = "1"
                R.id.diffucultyHard -> difficulty = "2"
            }

        }
        radioGroupNotification.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.notificationHourly -> notification = "0"
                R.id.notificationWeekly -> notification = "1"
                R.id.notificationMontly -> notification = "2"
            }

        }
        buttonSaveUserInfo.setOnClickListener(View.OnClickListener {
           dbRef.update(mapOf(
               "toBeLearned" to toBeLearned,
               "difficulty" to difficulty,
               "notificationFrequency" to notification))
            getData(userId)
            Toast.makeText(this, R.string.user_information_update_successful, Toast.LENGTH_SHORT)
                .show()
            comeToSignupActivity()
        })
        /*
        buttonSaveUserInfo.setOnClickListener(View.OnClickListener {
            database.child(userId).updateChildren(mapOf(
                "toBeLearned" to toBeLearned,
                "difficulty" to difficulty,
                "notificationFrequency" to notification))
            getData(userId)
            Toast.makeText(this, R.string.user_information_update_successful, Toast.LENGTH_SHORT)
                .show()
            comeToSignupActivity()
        })*/
    }

    private fun comeToSignupActivity() {
        val intent = intent
        val activity = intent.getStringExtra("activity")
        if (activity == "signup") {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }


    private fun run() {
        getData(currentUserId)
        updateData(currentUserId)
    }


}


