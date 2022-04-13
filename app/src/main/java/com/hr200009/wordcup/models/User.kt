package com.hr200009.wordcup.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    val userName: String? = null,
    var difficulty: Int?= null,
    var notificationFrequency: Int? = null,
    var toBeLearned: Int? = null) {

    @Exclude
    fun toUserInfoMap(): Map<String, Any?>{
        return mapOf(
            "userName" to userName,
            "difficulty" to difficulty,
            "notificationFrequency" to notificationFrequency,
            "toBeLearned" to toBeLearned
        )
    }
}
