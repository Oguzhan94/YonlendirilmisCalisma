package com.hr200009.wordcup.activities

import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.FirebaseUtil

class PasswordResetActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()
    private lateinit var sendButton: Button
    private lateinit var emailEditText: EditText

    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_password_reset)

        sendButton = findViewById(R.id.button4)
        emailEditText = findViewById(R.id.editTextTextEmailAddress)

        sendPasswordResetEmail()
    }


    private fun sendPasswordResetEmail() {
        if (emailEditText.text.toString().isEmpty()) {
            Toast.makeText(this, R.string.fillInTheMailField, Toast.LENGTH_SHORT).show()
            return
        } else {
            sendButton.setOnClickListener() {
                dataBase.auth.sendPasswordResetEmail(emailEditText.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, R.string.sendingMailSuccess, Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, R.string.sendingMailFailed, Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }
}