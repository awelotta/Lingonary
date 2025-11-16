package com.example.lingonary

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.lingonary.adapters.WordAdapter
import com.example.lingonary.models.Word
import java.io.ByteArrayOutputStream
import java.io.InputStream


class TranscriptPlayerActivity: AppCompatActivity() {
    lateinit var mediaPlayer: MediaPlayer
    lateinit var playButton: AppCompatImageButton
    lateinit var transcript: TextView
    var newWords: ArrayList<Word> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.transcript_player)

        mediaPlayer = MediaPlayer.create(this, R.raw.spanish_speech)
        playButton = findViewById(R.id.playButton)
        playButton.setOnClickListener { v ->
            if (mediaPlayer.isPlaying) {
                mediaPlayer.stop()
            } else {
                mediaPlayer.start()
            }
            // should make some visual indication of isPlaying
            // Speech Attribution: Muntatge Carmen Amaya i Antonia Vilas.wav by barcelonetasonora -- https://freesound.org/s/609638/ -- License: Attribution NonCommercial 4.0
        }
        findViewById<ImageButton>(R.id.wordLibraryButton).setOnClickListener {
            // todo launch wordlibrary
        }
        findViewById<ImageButton>(R.id.backButton).setOnClickListener {
            var result = Intent()
            result.putParcelableArrayListExtra("newWords", newWords)
            Log.i("newwordsreturn", "newwords is $result")
            setResult(RESULT_OK, result)
            finish()
        }

        val inputStream = resources.openRawResource(R.raw.transcript)
        val transcriptText = readAllBytes(inputStream)
        val spaceIndices = getSpaceIndices(transcriptText)
        val spannable = SpannableString(transcriptText)
        for (i in spaceIndices.indices) {
            if (i == spaceIndices.size - 1) { break }
            val start = spaceIndices[i]+1
            val end = spaceIndices[i+1]
            val word = transcriptText.substring(start, end)
            val translation = translate(word)
            val wordClickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    inflateDefinitionPopup(widget, word, translation)
//                    TODO("location is bad")
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
        transcript = findViewById(R.id.transcript)
        transcript.text = spannable
        transcript.movementMethod = LinkMovementMethod()
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
    private fun inflateDefinitionPopup(view: View, word: String, translation: String) {
//        Toast.makeText(this, newWords.size.toString(), Toast.LENGTH_SHORT).show()//todo announce newwords contents just for testing
        // Source - https://stackoverflow.com/a/50188704
        // Posted by Suragch, modified by community. See post 'Timeline' for change history
        // Retrieved 2025-11-14, License - CC BY-SA 4.0
        // I modified it a bunch because originally java and etc

        // inflate the layout of the popup window
        var inflater: LayoutInflater = getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var popupView = inflater.inflate(R.layout.definition_popup, null)
        var targetWordView: TextView = popupView.findViewById(R.id.targetWord)
        var translationView: TextView = popupView.findViewById(R.id.translation)
        targetWordView.text = word
        translationView.text = translation
        var addToWordLibrary: ImageButton = popupView.findViewById(R.id.addToWordLibrary)
        addToWordLibrary.setOnClickListener {
            newWords.add(Word(word, translation))
            Toast.makeText(this, "WORD ADDED!", Toast.LENGTH_SHORT).show()
        }
        // create the popup window
        var width: Int = LinearLayout.LayoutParams.WRAP_CONTENT
        var height: Int = LinearLayout.LayoutParams.WRAP_CONTENT
        var focusable = true; // lets taps outside the popup also dismiss it // will it have other undesired/incorrect behavior?
        val popupWindow = PopupWindow(popupView, width, height, focusable);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window token
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
        // this feels so jank... why are we dynamically creating so much stuff? instead of just summoning one layout and changing a few offset values?
        // I should do relative layout for this kind of thing, right? except, text isn't components so difficult
        // dismiss the popup window when touched
        popupView.setOnTouchListener { v, event ->
            popupWindow.dismiss()
            true
        }
    }
    private fun translate(word: String): String {
        //todo
        return "je ne se quoi"
    }
}