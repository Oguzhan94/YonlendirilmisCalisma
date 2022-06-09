package com.hr200009.wordcup.util

import android.app.Activity
import androidx.appcompat.app.AlertDialog


fun Activity.showAlert(alertMessage: Int, selectedButton: (Boolean) -> Unit) {
    val alert: AlertDialog.Builder = AlertDialog.Builder(this)
    alert.setMessage(alertMessage)
    alert.setCancelable(true)
    alert.setPositiveButton("Evet") { dialog, id ->
        selectedButton(true)
        dialog.cancel()
    }
    alert.setNegativeButton("Hayır") { dialog, id ->
        selectedButton(false)
        dialog.cancel()
    }
    alert.create().show()
}