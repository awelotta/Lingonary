package com.example.lingonary

import android.media.MediaPlayer
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import java.io.ByteArrayOutputStream
import java.io.InputStream


class TranscriptPlayerActivity: AppCompatActivity(), View.OnClickListener {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playButton: AppCompatImageButton
    lateinit var transcript: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.transcript_player)
        mediaPlayer = MediaPlayer.create(this, R.raw.spanish_speech)
        playButton = findViewById(R.id.playButton)
        playButton.setOnClickListener(this)

        val inputStream = resources.openRawResource(R.raw.transcript)
        val transcriptText = readAllBytes(inputStream)
        val spaceIndices = getSpaceIndices(transcriptText)
        val spannable = SpannableString(transcriptText)
        for (i in spaceIndices.indices) {
            if (i == spaceIndices.size - 1) { break }
            val start = spaceIndices[i]+1
            val end = spaceIndices[i+1]
            val word = transcriptText.substring(start, end)
            val wordClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    Toast.makeText(
                        widget.context,
                        word + ": NOUN 什么",
                        Toast.LENGTH_SHORT
                    ).show()
//                    TODO("Not yet implemented")
                }
                override fun updateDrawState(ds: TextPaint) {
                    super.updateDrawState(ds)
                    ds.isUnderlineText = false
//                    ds.color = R.color.teal_700 // how do color???
                }
            }

            spannable.setSpan(
                wordClickableSpan, start, end,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        // TODO show popup definition... first try to get bundle through
        transcript = findViewById(R.id.transcript)
        transcript.text = spannable
        transcript.movementMethod = LinkMovementMethod()
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
    private fun readAllBytes(inputStream: InputStream): String {
        // Source - https://stackoverflow.com/a/35446009
        // Posted by Slava Vedenin, modified by community. See post 'Timeline' for change history
        // Retrieved 2025-11-14, License - CC BY-SA 4.0
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(1024)

        var length: Int
        while ((inputStream.read(buffer).also { length = it }) != -1) {
            result.write(buffer, 0, length)
        }
        // StandardCharsets.UTF_8.name() > JDK 7
        return result.toString("UTF-8")
    }
    private fun getSpaceIndices(s: String): ArrayList<Int> {
        val result = arrayListOf<Int>(-1)
        for (i in s.indices) {
            if (s[i] == ' ') { result.add(i) }
        }
        result.add(s.length)
        return result
    }
}
//
//class WordClickableSpan : ClickableSpan() {
//    override fun onClick(widget: View) {
//
//        Toast.makeText(
//            widget.context,
//            "you clicked on a word!",
//            Toast.LENGTH_SHORT
//        ).show()
//        TODO("Not yet implemented")
//    }
//}