package com.example.lingonary.onboarding

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.lingonary.R
import com.example.lingonary.home.HomeActivity

class LearningLanguageActivity : ComponentActivity() {
    private var selectedTargetLanguage: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding_learning_language)

        val userName = intent.getStringExtra(OnboardingKeys.EXTRA_USER_NAME)
        val nativeLanguage = intent.getStringExtra(OnboardingKeys.EXTRA_NATIVE_LANGUAGE)

        val btnEnglish = findViewById<Button>(R.id.btnEnglishLearning)
        val btnChinese = findViewById<Button>(R.id.btnChineseLearning)
        val btnSpanish = findViewById<Button>(R.id.btnSpanishLearning)
        val btnBack = findViewById<Button>(R.id.btnBack3)
        val btnContinue = findViewById<Button>(R.id.btnContinue3)

        fun updateSelection(lang: String) {
            selectedTargetLanguage = lang

            btnEnglish.setBackgroundResource(
                if (lang == getString(R.string.lang_english))
                    R.drawable.bg_language_button_selected
                else
                    R.drawable.bg_language_button
            )

            btnChinese.setBackgroundResource(
                if (lang == getString(R.string.lang_chinese))
                    R.drawable.bg_language_button_selected
                else
                    R.drawable.bg_language_button
            )

            btnSpanish.setBackgroundResource(
                if (lang == getString(R.string.lang_spanish))
                    R.drawable.bg_language_button_selected
                else
                    R.drawable.bg_language_button
            )
        }

        btnEnglish.setOnClickListener { updateSelection(getString(R.string.lang_english)) }
        btnChinese.setOnClickListener { updateSelection(getString(R.string.lang_chinese)) }
        btnSpanish.setOnClickListener { updateSelection(getString(R.string.lang_spanish)) }

        btnBack.setOnClickListener { finish() }

        btnContinue.setOnClickListener {
            if (selectedTargetLanguage == null) {
                Toast.makeText(this, R.string.error_language_empty, Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra(OnboardingKeys.EXTRA_USER_NAME, userName)
                intent.putExtra(OnboardingKeys.EXTRA_NATIVE_LANGUAGE, nativeLanguage)
                intent.putExtra(OnboardingKeys.EXTRA_TARGET_LANGUAGE, selectedTargetLanguage)
                startActivity(intent)
            }
        }
    }
}
