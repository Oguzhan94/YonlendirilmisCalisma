package com.hr200009.wordcup.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R
import com.hr200009.wordcup.util.FirebaseUtil

class PasswordResetActivity : AppCompatActivity() {

    private var dataBase = FirebaseUtil()

    private lateinit var sendButton: Button
    private lateinit var emailEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password_reset)

        sendButton = findViewById(R.id.button4)
        emailEditText = findViewById(R.id.editTextTextEmailAddress)

        sendPasswordResetEmail()

    }


    private fun sendPasswordResetEmail() {

        sendButton.setOnClickListener() {
            dataBase.auth.sendPasswordResetEmail(emailEditText.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Email sent", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, "Failed to send email", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}