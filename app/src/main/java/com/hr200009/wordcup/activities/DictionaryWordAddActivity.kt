package com.hr200009.wordcup.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import android.view.View
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast

import com.google.mlkit.common.model.DownloadConditions
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions
import com.hr200009.wordcup.R
import com.hr200009.wordcup.databinding.ActivityDictionaryWordAddBinding

import com.hr200009.wordcup.util.FirebaseUtil

class DictionaryWordAddActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDictionaryWordAddBinding

    private var dataBase = FirebaseUtil()


    private lateinit var textTSource: String
    private lateinit var textTarget: String
    private var trueCounter: Int = 0
    private var falseCounter: Int = 0
    private var passCounter: Int = 0
    private var isItLearned: Boolean = false
    private var viewCounter: Int = 0

    var wordId: String? = null




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDictionaryWordAddBinding.inflate(layoutInflater)
        setContentView(binding.root)


        addWordToDatabase()
    }

    private fun translate() {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.TURKISH)
            .setTargetLanguage(TranslateLanguage.ENGLISH)
            .build()
        val turkishToEnglishTranslator = Translation.getClient(options)

        var conditions = DownloadConditions.Builder()
            .requireWifi()
            .build()
        turkishToEnglishTranslator.downloadModelIfNeeded(conditions)
            .addOnCompleteListener {
                // Model downloaded successfully. Okay to start translating.
                // (Set a flag, unhide the translation UI, etc.)
                Toast.makeText(this, R.string.translate_model_download_success, Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                // Model couldn’t be downloaded or other internal error.
                // ...
                Toast.makeText(this, R.string.translate_model_download_failed, Toast.LENGTH_SHORT).show()
            }

        binding.searchViewDictionaryWordAdd.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                turkishToEnglishTranslator.translate(newText!!)
                    .addOnSuccessListener { translatedText ->
                        // Translation successful.
                        textTarget = translatedText
                        textTSource = newText
                        binding.textViewTarget.text = textTarget
                        binding.textViewSource.text = textTSource
                    }
                    .addOnFailureListener {
                        // Error.
                        // ...
                        Toast.makeText(this@DictionaryWordAddActivity, "Error", Toast.LENGTH_SHORT).show()
                    }
                return false
            }
        })
    }

    private fun addWordToDatabase(){
        translate()
        binding.searchViewDictionaryWordAdd.requestFocus()
        binding.buttonAddWordDictionary.setOnClickListener(View.OnClickListener {
          if (textTSource.isNotEmpty() && textTarget.isNotEmpty()) {

              //wordId=database.push().key.toString()

              wordId= dataBase.allWords.document().toString()


              val wordData = hashMapOf(
                  "source" to textTSource.lowercase(),
                  "translation" to textTarget.lowercase(),
                  "trueCounter" to trueCounter,
                  "falseCounter" to falseCounter,
                  "passCounter" to passCounter,
                  "isItLearned" to isItLearned,
                  "id" to wordId,
                  "viewCounter" to viewCounter
              )

              dataBase.allWords.document(wordId.toString())
                  .set(wordData)
                  .addOnSuccessListener {
                      Toast.makeText(this, R.string.word_added, Toast.LENGTH_SHORT).show()
                  }
              //database.child("words").child(auth.uid.toString()).child("allWords").child(wordId.toString()).setValue(wordData)
              Toast.makeText(this, R.string.word_added, Toast.LENGTH_SHORT).show()
          } else {
              Toast.makeText(this, R.string.word_add_failed, Toast.LENGTH_SHORT).show()
          }
      })

    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}