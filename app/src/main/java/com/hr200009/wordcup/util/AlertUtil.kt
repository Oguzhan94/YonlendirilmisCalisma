package com.hr200009.wordcup.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hr200009.wordcup.R


class AlertUtil {


    fun showAlert(alertMessage: Int, context: Activity, selectedButton: (Boolean) -> Unit) {
        val alert: AlertDialog.Builder = AlertDialog.Builder(context)
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
}

