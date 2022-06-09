package com.hr200009.wordcup.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hr200009.wordcup.R
import com.hr200009.wordcup.databinding.ActivityAccountSettingsBinding
import com.hr200009.wordcup.util.FirebaseUtil
import com.hr200009.wordcup.util.showAlert

class AccountSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAccountSettingsBinding
    private var dataBase = FirebaseUtil()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAccountSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        run()
    }

    private fun run() {
        getUserNickName()
        binding.button6.setOnClickListener {
            updateUserNickName()
        }
        binding.button7.setOnClickListener {
            showAlert(R.string.account_delete_alert_message) {
                if (it) {
                    deleteThisAccount()
                }
            }
        }
    }

    private fun getUserNickName() {
        dataBase.userInfo.get().addOnSuccessListener {
            binding.editTextTextPersonName.setText(it.get("userName").toString())
        }
    }

    private fun updateUserNickName() {
        dataBase.userInfo.update("userName", binding.editTextTextPersonName.text.toString())
        Toast.makeText(this, R.string.nickname_update_success, Toast.LENGTH_SHORT).show()
    }

    private fun deleteThisAccount() {
        dataBase.currentUser!!.delete()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, R.string.delete_user, Toast.LENGTH_SHORT).show()
                    openToLoginScreen()
                }
            }
    }

    private fun openToLoginScreen() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finishAffinity()
    }
}