package com.hr200009.wordcup.activities


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.FirebaseUtil

class SettingsActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()
    private lateinit var buttonToPasswordActivity: Button
    private lateinit var buttonToUserInfoActivity: Button
    private lateinit var buttonToLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        buttonToPasswordActivity = findViewById(R.id.buttonSettingsPassword)
        buttonToUserInfoActivity = findViewById(R.id.buttonSettingsUserInfo)
        buttonToLogout = findViewById(R.id.buttonSettingsLogout)

        run()
    }

    private fun run(){
        buttonToPasswordActivity.setOnClickListener(View.OnClickListener {
            openToPasswordUpdateActivity()
        })
        buttonToUserInfoActivity.setOnClickListener(View.OnClickListener {
            openToUserInfoActivity()
        })
        buttonToLogout.setOnClickListener(View.OnClickListener {
            logOut()
        })

    }

    private fun openToPasswordUpdateActivity(){
        val intent = Intent(this@SettingsActivity, UpdatePasswordActivity::class.java)
        startActivity(intent)
    }
    private fun openToUserInfoActivity(){
        val intent = Intent(this@SettingsActivity, UserInfoActivity::class.java)
        startActivity(intent)
    }
    private fun logOut(){
        dataBase.auth.signOut()
        val intent = Intent(this@SettingsActivity, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

}