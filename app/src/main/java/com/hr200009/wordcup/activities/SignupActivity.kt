package com.hr200009.wordcup.activities


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hr200009.wordcup.R
import com.hr200009.wordcup.models.User
import com.hr200009.wordcup.util.FirebaseUtil

class SignupActivity : AppCompatActivity() {



    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var nickNameEditText: EditText
    private lateinit var registerButton: Button
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var nickName: String
    private var difficulty: Int = 0
    private var notificationFrequency: Int = 0
    private var toBeLearned: Int = 0
private  var au: FirebaseUtil = FirebaseUtil()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        emailEditText = findViewById(R.id.textRegisterEmail)
        passwordEditText = findViewById(R.id.textRegisterPassword)
        nickNameEditText = findViewById(R.id.textRegisterNickname)
        registerButton = findViewById(R.id.buttonRegister)



    }

   override fun onStart() {
        super.onStart()
        run()
    }

    private fun run() {
        registerButton.setOnClickListener(View.OnClickListener {
            registerNewUser()
        })
    }

    private fun registerNewUser() {
        email = emailEditText.text.toString()
        password = passwordEditText.text.toString()
        nickName = nickNameEditText.text.toString()
        if (email.isEmpty() || password.isEmpty() || nickName.isEmpty()) {
            Toast.makeText(applicationContext, R.string.fill_in_all_fields, Toast.LENGTH_SHORT)
                .show()
        } else {
            FirebaseUtil.AUTH.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        // Register success, update UI with the signed-in user's information
                        Toast.makeText(baseContext, R.string.signup_success, Toast.LENGTH_SHORT)
                            .show()
                        writeNewUser(nickName, difficulty, notificationFrequency, toBeLearned)
                        continueToSettings()
                    } else {
                        // If register in fails, display a message to the user.
                        Toast.makeText(baseContext, R.string.signup_failed, Toast.LENGTH_SHORT)
                            .show()
                    }

                }
        }
    }


    private fun continueToSettings() {
        val intent = Intent(this@SignupActivity, UserInfoActivity::class.java)
        intent.putExtra("activity", "signup")
        startActivity(intent)
        finishAffinity()
    }

    private fun writeNewUser(
        name: String,
        difficulty: Int,
        notificationFrequency: Int,
        toBeLearned: Int
    ) {
        val user = User(name, difficulty, notificationFrequency, toBeLearned)
       // database.child("userInfo").child(auth.uid.toString()).setValue(user)
        FirebaseUtil.dbb.collection("userInfo")
            .document(au.auth.currentUser?.uid.toString())
            .set(user)
    }


}