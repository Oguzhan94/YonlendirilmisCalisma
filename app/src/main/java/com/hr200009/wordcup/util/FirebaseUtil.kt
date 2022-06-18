package com.hr200009.wordcup.util

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.activities.MainActivity
import com.hr200009.wordcup.activities.PlayActivity
import com.hr200009.wordcup.adaptor.WordAdapter
import com.hr200009.wordcup.models.Word

class FirebaseUtil {
    var auth: FirebaseAuth = Firebase.auth
    var currentUser = auth.currentUser
    var currentUserUid = currentUser?.uid



    val db = Firebase.firestore
    var userInfo = db.collection("userInfo").document(currentUserUid.toString())
    var wordId =
        db.collection("userWords").document(auth.uid.toString()).collection("allWords").document()
            .toString()
    var allWords = db.collection("userWords").document(currentUserUid.toString()).collection("allWords")
    var learnedWords = db.collection("userWords").document(currentUserUid.toString()).collection("learnedWords")

    var userNickname = db.collection("userInfo").document(auth.uid.toString()).collection("userName")
    var dailyWords = db.collection("adminWords").document("tr").collection("dailyWords")
    var weeklyWords = db.collection("adminWords").document("tr").collection("weeklyWords")

    var userWords = db.collection("userWords").document(currentUserUid.toString())
    }






