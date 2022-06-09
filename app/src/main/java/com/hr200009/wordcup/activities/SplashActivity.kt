package com.hr200009.wordcup.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.widget.Toast
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.AlertUtil
import com.hr200009.wordcup.util.FirebaseUtil
import com.hr200009.wordcup.util.NetworkUtil
import android.provider.Settings
import com.hr200009.wordcup.util.showAlert

class SplashActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        loadingTime()
    }

    override fun onResume() {
        super.onResume()
        openNextActivity()
    }

    private fun loadingTime() {
        val countDownTimer: CountDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                openNextActivity()
            }
        }
        countDownTimer.start()
    }

    private fun checkInternet(): Boolean {
        return NetworkUtil.isInternetConnected(applicationContext)
    }

    private fun checkUserLogin(): Boolean {
        return dataBase.currentUser != null
    }

    private fun openNextActivity() {
        if (checkUserLogin() && checkInternet()) {
            openMainActivity()
        } else if (checkUserLogin() && !checkInternet()) {
            Toast.makeText(this, R.string.no_internet_connection, Toast.LENGTH_SHORT).show()
            openMainActivity()
        } else if (!checkUserLogin() && checkInternet()) {
            openLoginActivity()
        } else {
            showAlert()
        }
    }

    private fun showAlert(){
        showAlert(R.string.internet_connection_alert_dialog_message) {
            if (it) {
                openWifiSettings()
            }
            else{
                finishAndRemoveTask()
            }
        }
    }

    private fun openLoginActivity() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun openMainActivity() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    private fun openWifiSettings(){
        val intent = Intent(Settings.ACTION_WIFI_SETTINGS)
        startActivity(intent)
    }

}