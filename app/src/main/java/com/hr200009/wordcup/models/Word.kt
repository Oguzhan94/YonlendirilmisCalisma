package com.hr200009.wordcup.models

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class Word(
    var source: String? = null,
    var translation: String? = null,
    var trueCounter: Int? = null,
    var falseCounter: Int? = null,
    var passCounter: Int? = null,
    val id: String? = null,
    var viewCounter: Int? = null,
    var isItLearned: Boolean? = null
) {
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "source" to source,
            "translation" to translation,
            "trueCounter" to trueCounter,
            "falseCounter" to falseCounter,
            "passCounter" to passCounter,
            "isItLearned" to isItLearned,
            "viewCounter" to viewCounter
        )
    }
}
