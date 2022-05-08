package com.hr200009.wordcup.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.hr200009.wordcup.R


class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var forgotPassword: Button
    private lateinit var email: String
    private lateinit var password: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.textLoginEmailAddress)
        passwordEditText = findViewById(R.id.textLoginPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonToRegister)
        forgotPassword = findViewById(R.id.buttonForgotPassword)

        auth = Firebase.auth

    }
    override fun onStart() {
        super.onStart()
        run()
    }

    private fun run() {
        loginButton.setOnClickListener(View.OnClickListener {
            login()
        })
        registerButton.setOnClickListener(View.OnClickListener {
            openSignupActivity()
        })
    }


    private fun login() {
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, R.string.empty_email_password, Toast.LENGTH_SHORT).show()
        } else {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, R.string.login_success, Toast.LENGTH_SHORT).show()
                        openMainActivity()
                    } else {
                        Toast.makeText(this, R.string.login_failed, Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    private fun openMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
    private fun openSignupActivity() {
        val intent = Intent(this@LoginActivity, SignupActivity::class.java)
        startActivity(intent)
    }
/*
    private fun openForgotPasswordActivity() {
        forgotPassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        })
    }*/




}