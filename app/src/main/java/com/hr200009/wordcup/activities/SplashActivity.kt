package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.CountDownTimer
import android.widget.Toast
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.AlertUtil
import com.hr200009.wordcup.util.FirebaseUtil
import com.hr200009.wordcup.util.NetworkUtil

class SplashActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_splash)
        loadingTime()
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
            AlertUtil.internetAlert(this)
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

}