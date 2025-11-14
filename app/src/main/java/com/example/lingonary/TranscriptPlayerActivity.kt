package com.example.lingonary

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton

class TranscriptPlayerActivity: AppCompatActivity(), View.OnClickListener {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playButton: AppCompatImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.transcript_player)
        mediaPlayer = MediaPlayer.create(this, R.raw.spanish_speech)
        playButton = findViewById(R.id.playButton)
        playButton.setOnClickListener(this)
    }
    override fun onStart() {
        super.onStart()
    }
    override fun onClick(v: View) {
        if (v.id == R.id.transcript) {
            if (mediaPlayer.isPlaying) {
                mediaPlayer.pause()
            } else {
                mediaPlayer.start()
            }
        }
        // should make some visual indication of isPlaying
        // Speech Attribution: Muntatge Carmen Amaya i Antonia Vilas.wav by barcelonetasonora -- https://freesound.org/s/609638/ -- License: Attribution NonCommercial 4.0
    }
}