package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.FirebaseUtil

class UpdatePasswordActivity : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var newPassword: String
    private lateinit var newPasswordCheck: String
    private lateinit var currentPassword: String
    private lateinit var currentPasswordEditText: EditText
    private lateinit var newPasswordEditText: EditText
    private lateinit var newPasswordCheckEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_password)

        currentPasswordEditText = findViewById(R.id.textSettingsCurrentPassword)
        newPasswordEditText = findViewById(R.id.TextSettingsNewPassword)
        newPasswordCheckEditText = findViewById(R.id.TextSettingsNewPasswordCheck)
        saveButton = findViewById(R.id.buttonUpdatePassword)



        run()
    }

    private fun run() {
        passwordUpdate()
    }

    private fun passwordUpdate() {
        saveButton.setOnClickListener(View.OnClickListener {
            currentPassword = currentPasswordEditText.text.toString()
            newPassword = newPasswordEditText.text.toString()
            newPasswordCheck = newPasswordCheckEditText.text.toString()

            if (newPassword == newPasswordCheck) {
                // doğrulama
                val credential = EmailAuthProvider
                    .getCredential(FirebaseUtil.CURRENT_USER!!.email!!, currentPassword)
                // tekrar giriş yapma
                FirebaseUtil.CURRENT_USER!!.reauthenticate(credential)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this, R.string.re_aut_success, Toast.LENGTH_SHORT)
                                .show()
                            // şifre güncelleme
                            FirebaseUtil.CURRENT_USER!!.updatePassword(newPassword)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(this, R.string.success_update_password, Toast.LENGTH_SHORT)
                                            .show()
                                        FirebaseUtil.AUTH.signOut()
                                        openLoginActivity()
                                    } else {
                                        Toast.makeText(this, R.string.failed_update_password, Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }
                        } else {
                            // mail veya şifre yanlış de
                            Toast.makeText(this, R.string.current_password_not_match, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                Toast.makeText(this, R.string.password_not_match, Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    private fun openLoginActivity() {
        val intent = Intent(this@UpdatePasswordActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

}