package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.AlertUtil
import com.hr200009.wordcup.util.FirebaseUtil
import com.hr200009.wordcup.util.NetworkUtil

class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        // Offline için data izini veriyoruz
        FirebaseUtil.FIRE_BASE_DİSK_PERSISTENCE
        // Initialize Firebase Auth
        auth = Firebase.auth

        startSplashActivity()
    }

    private fun startSplashActivity() {
        checkInternetConnection()
    }

    private fun checkInternetConnection() {
        if (NetworkUtil.isInternetConnected(applicationContext)) {
            //Start countDownTimer
            loadingTime()
        } else {
            // Show InternetAlert
            val currentUser = auth.currentUser
            if(currentUser != null){
                Toast.makeText(applicationContext, R.string.no_internet_connection, Toast.LENGTH_LONG).show()
                loadingTime()
            }else{
               AlertUtil.internetAlert(this)
            }

        }
    }

    private fun loadingTime() {
        val countDownTimer: CountDownTimer = object : CountDownTimer(3000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                //start nextActivity
                openNextActivity()
            }
        }
        countDownTimer.start()
    }

    private fun openNextActivity() {
        val intent = Intent(this@SplashActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}