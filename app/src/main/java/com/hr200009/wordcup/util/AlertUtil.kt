package com.hr200009.wordcup.util

import android.app.Activity
import android.content.Intent
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import com.hr200009.wordcup.R


class AlertUtil {

    companion object {

        fun internetAlert(activity: Activity) {
            AlertDialog.Builder(activity)
                //.setTitle(R.string.alert_dialog_title)
                .setMessage(R.string.alert_dialog_message)
                .setPositiveButton(R.string.alert_dialog_positive_button) { _, _ ->
                    val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
                    activity.startActivity(intent)
                }
                .setNegativeButton(R.string.alert_dialog_negative_button) { _, _ ->
                    activity.finish()
                }
                .show()
        }
    }

}