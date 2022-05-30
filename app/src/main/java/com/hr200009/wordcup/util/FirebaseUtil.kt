package com.hr200009.wordcup.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
     var auth: FirebaseAuth = Firebase.auth
     var currentUser = auth.currentUser
     var currentUserUid = currentUser?.uid

    val db = Firebase.firestore
    var userInfo = db.collection("userInfo").document(auth.uid.toString())
    var wordId = db.collection("words").document(auth.uid.toString()).collection("allWords").document().toString()
    var allWords = db.collection("words").document(auth.uid.toString()).collection("allWords")
    var learnedWords = db.collection("words").document(currentUserUid.toString()).collection("learnedWords")

}