package com.hr200009.wordcup.util

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FirebaseUtil {
    companion object {
        const val FIREBASE_BASE_URL = "https://wordcup-f8f0f.firebaseio.com/"
        val FIRE_BASE_DİSK_PERSISTENCE = Firebase.database.setPersistenceEnabled(true)
        val FIRE_BASE_KEEPING_DATA_FRESH = Firebase.database.getReference("words").keepSynced(true)
        val FIRE_BASE_CURRENT_USER = FirebaseAuth.getInstance().currentUser?.uid
        val WORDS_USER_REF = Firebase.database.reference.child("words").child(FIRE_BASE_CURRENT_USER!!)
        val WORD_ADD_REF = Firebase.database.reference.child("words").child(FIRE_BASE_CURRENT_USER!!).child(Firebase.database.reference.push().key!!)

    }
}