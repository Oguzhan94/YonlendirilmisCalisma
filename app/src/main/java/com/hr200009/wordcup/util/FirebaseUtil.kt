package com.hr200009.wordcup.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


 class FirebaseUtil {
//buradan yürükelecek
     var auth: FirebaseAuth = Firebase.auth
    var currentUser = auth.currentUser
    var currentUserId = currentUser?.uid

    var db = Firebase.firestore

    var USER_INFO = db.collection("userInfo")
    var ALL_WORDS_REF =  db.collection("words").document(currentUserId.toString()).collection("allWords")
    var LEARNED_WORDS_REF = db.collection("words").document(currentUserId.toString()).collection("learnedWords")




    companion object {

        var AUTH: FirebaseAuth = Firebase.auth
       // var CURRENT_USER:FirebaseUser? = AUTH.currentUser
        var CURRENT_USER = AUTH.currentUser
        var CURRENT_USER_ID: String = CURRENT_USER?.uid.toString()

        var dbb = Firebase.firestore



        var USER_INFO = dbb.collection("userInfo")
        var ALL_WORDS_REF =  dbb.collection("words").document(CURRENT_USER?.uid.toString()).collection("allWords")
        var LEARNED_WORDS_REF = dbb.collection("words").document(CURRENT_USER?.uid.toString()).collection("learnedWords")

        var cr:FirebaseUser? = AUTH.currentUser
        fun reset(){
            AUTH  = Firebase.auth
            // var CURRENT_USER:FirebaseUser? = AUTH.currentUser
             CURRENT_USER = AUTH.currentUser
             CURRENT_USER_ID = CURRENT_USER?.uid.toString()

             dbb = Firebase.firestore



        }
    }




}