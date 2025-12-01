package com.example.lingonary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.lingonary.models.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Word> wordList;
    private List<Word> quizList;
    private Word currentWord;
    private int currentQuestionIndex = 0;

    private TextView titleVamos;
    private Button btnOption1, btnOption2, btnOption3, btnOption4, btnContinue;
    private ProgressBar progressBar;
    private List<Button> optionButtons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizactivity_main);

        SharedPreferences sharedPreferences = getSharedPreferences("lingonary_prefs", Context.MODE_PRIVATE);
        int quizLength = sharedPreferences.getInt("quiz_length", 10);
        int masteryThreshold = sharedPreferences.getInt("mastery_threshold", 3);
        boolean includeMastered = sharedPreferences.getBoolean("include_mastered", false);

        wordList = getIntent().getParcelableArrayListExtra("wordList");
        if (wordList != null) {
            List<Word> availableWords;
            if (includeMastered) {
                availableWords = new ArrayList<>(wordList);
            } else {
                availableWords = wordList.stream().filter(w -> w.getTimesCorrect() < masteryThreshold).collect(Collectors.toList());
            }
            Collections.shuffle(availableWords);
            quizList = availableWords.stream().limit(quizLength).collect(Collectors.toList());
        }

        titleVamos = findViewById(R.id.titleVamos);
        btnOption1 = findViewById(R.id.btnOption1);
        btnOption2 = findViewById(R.id.btnOption2);
        btnOption3 = findViewById(R.id.btnOption3);
        btnOption4 = findViewById(R.id.btnOption4);
        btnContinue = findViewById(R.id.btnContinue);
        progressBar = findViewById(R.id.progressBar);

        optionButtons = new ArrayList<>();
        optionButtons.add(btnOption1);
        optionButtons.add(btnOption2);
        optionButtons.add(btnOption3);
        optionButtons.add(btnOption4);

        for (Button button : optionButtons) {
            button.setOnClickListener(this);
        }

        btnContinue.setOnClickListener(v -> {
            currentQuestionIndex++;
            loadNewQuestion();
        });

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finishQuiz());

        if (quizList != null && !quizList.isEmpty()) {
            progressBar.setMax(quizList.size());
            loadNewQuestion();
        } else {
            finishQuiz();
        }
    }

    private void loadNewQuestion() {
        if (currentQuestionIndex >= quizList.size()) {
            finishQuiz();
            return;
        }

        resetButtonStyles();
        currentWord = quizList.get(currentQuestionIndex);
        currentWord.setHasBeenInQuiz(true);
        titleVamos.setText(currentWord.getLearning());
        progressBar.setProgress(currentQuestionIndex + 1);

        List<Word> options = new ArrayList<>(wordList);
        options.remove(currentWord);
        Collections.shuffle(options);

        Collections.shuffle(optionButtons);

        optionButtons.get(0).setText(currentWord.getNativeLang());
        for (int i = 1; i < optionButtons.size(); i++) {
            optionButtons.get(i).setText(options.get(i - 1).getNativeLang());
        }
    }

    @Override
    public void onClick(View v) {
        Button selectedButton = (Button) v;
        String selectedAnswer = selectedButton.getText().toString();

        if (selectedAnswer.equals(currentWord.getNativeLang())) {
            selectedButton.setBackground(ContextCompat.getDrawable(this, R.drawable.quiz_button_correct));
            currentWord.incrementTimesCorrect();
        } else {
            selectedButton.setBackground(ContextCompat.getDrawable(this, R.drawable.quiz_button_incorrect));
            // Highlight the correct answer
            for (Button button : optionButtons) {
                if (button.getText().toString().equals(currentWord.getNativeLang())) {
                    button.setBackground(ContextCompat.getDrawable(this, R.drawable.quiz_button_correct));
                    break;
                }
            }
        }

        for (Button button : optionButtons) {
            button.setEnabled(false);
        }

        btnContinue.setVisibility(View.VISIBLE);
    }

    private void resetButtonStyles() {
        for (Button button : optionButtons) {
            button.setBackground(ContextCompat.getDrawable(this, R.drawable.quiz_button_default));
            button.setEnabled(true);
        }
        btnContinue.setVisibility(View.INVISIBLE);
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("wordList", (ArrayList<Word>) wordList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
