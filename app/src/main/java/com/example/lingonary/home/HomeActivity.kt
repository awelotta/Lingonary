package com.example.lingonary.home

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import com.example.lingonary.R
import com.example.lingonary.onboarding.OnboardingKeys

class HomeActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val userName =
            intent.getStringExtra(OnboardingKeys.EXTRA_USER_NAME)
        val targetLanguage =
            intent.getStringExtra(OnboardingKeys.EXTRA_TARGET_LANGUAGE)

        val tvGreeting = findViewById<TextView>(R.id.tvGreetingHome)
        val btnCurrentLanguage = findViewById<Button>(R.id.btnCurrentLanguage)

        // Greeting
        tvGreeting.text = when {
            !userName.isNullOrBlank() -> "Hello $userName!"
            else -> "Hello!"
        }

        // “Learning:” chip text
        btnCurrentLanguage.text = targetLanguage ?: "Language"
    }
}
