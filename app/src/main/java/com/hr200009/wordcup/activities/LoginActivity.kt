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
    private val currentUser = Firebase.auth.currentUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        emailEditText = findViewById(R.id.textLoginEmailAddress)
        passwordEditText = findViewById(R.id.textLoginPassword)
        loginButton = findViewById(R.id.buttonLogin)
        registerButton = findViewById(R.id.buttonToRegister)
        forgotPassword = findViewById(R.id.buttonForgotPassword)

        auth = Firebase.auth

        run()
    }

    private fun run() {
        if (currentUser != null){
            openMainActivity()
        }else{
            login()
            openRegisterActivity()
        }
    }

    private fun login() {
        loginButton.setOnClickListener(View.OnClickListener {
            email = emailEditText.text.toString()
            password = passwordEditText.text.toString()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(applicationContext, R.string.empty_email_password, Toast.LENGTH_SHORT)
                    .show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(baseContext, R.string.login_success, Toast.LENGTH_SHORT)
                                .show()
                            openMainActivity()
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(baseContext, R.string.login_failed, Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            }
        })
    }

    private fun openRegisterActivity() {
        registerButton.setOnClickListener(View.OnClickListener {
            val intent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(intent)
        })
    }

    private fun openMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }

    fun openForgotPassword() {
        //
    }


}