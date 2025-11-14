package com.example.lingonary;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import com.example.lingonary.models.Word;

public class PodcastActivity extends AppCompatActivity {

    private final ArrayList<Word> newWords = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.podcast_view);
        // Settig the button functions here.
        View addButton = findViewById(R.id.btnAddWordFromPodcast);
        View btnExit = findViewById(R.id.podcastExit);

        addButton.setOnClickListener(v -> showAddWordDialog());
        btnExit.setOnClickListener(v -> sendResultAndFinish());
    }

    // Add word function,
    private void showAddWordDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_word, null);
        EditText editLearning = dialogView.findViewById(R.id.editLearning);
        EditText editNative = dialogView.findViewById(R.id.editNative);

        new AlertDialog.Builder(this)
                .setTitle("Add New Word")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String learning = editLearning.getText().toString().trim();
                    String nativeWord = editNative.getText().toString().trim();

                    if (!learning.isEmpty() && !nativeWord.isEmpty()) {
                        newWords.add(new Word(learning, nativeWord));
                        Toast.makeText(this, "Word added!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Both fields required", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    //ensuring that the words are sent back.
    private void sendResultAndFinish() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("newWords", newWords);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
