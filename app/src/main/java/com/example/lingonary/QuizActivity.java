package com.example.lingonary;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.lingonary.models.Word;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class QuizActivity extends AppCompatActivity {

    private List<Word> wordList;
    private List<Word> quizList;

    private Word currentWord;

    private TextView titleVamos;
    private Button btnLetsGo;
    private Button btnHappy;
    private Button btnVictory;
    private Button btnPotato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizactivity_main);

        wordList = getIntent().getParcelableArrayListExtra("wordList");
        quizList = wordList.stream().filter(w -> w.getTimesCorrect() < 2).collect(Collectors.toList());

        titleVamos = findViewById(R.id.titleVamos);
        btnLetsGo = findViewById(R.id.btnLetsGo);
        btnHappy = findViewById(R.id.btnHappy);
        btnVictory = findViewById(R.id.btnVictory);
        btnPotato = findViewById(R.id.btnPotato);

        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finishQuiz());

        if (quizList != null && !quizList.isEmpty()) {
            loadNewQuestion();
        } else {
            finishQuiz();
        }
    }

    private void loadNewQuestion() {
        if (quizList.isEmpty()) {
            finishQuiz();
            return;
        }

        Collections.shuffle(quizList);
        currentWord = quizList.get(0);

        titleVamos.setText(currentWord.getLearning());

        List<Word> options = new ArrayList<>(wordList);
        options.remove(currentWord);
        Collections.shuffle(options);

        List<Button> buttons = new ArrayList<>();
        buttons.add(btnLetsGo);
        buttons.add(btnHappy);
        buttons.add(btnVictory);
        buttons.add(btnPotato);
        Collections.shuffle(buttons);

        buttons.get(0).setText(currentWord.getNativeLang());
        buttons.get(0).setOnClickListener(v -> {
            currentWord.incrementTimesCorrect();
            showResultDialog(true);
        });

        for (int i = 1; i < buttons.size(); i++) {
            buttons.get(i).setText(options.get(i - 1).getNativeLang());
            buttons.get(i).setOnClickListener(v -> showResultDialog(false));
        }
    }

    private void showResultDialog(boolean isCorrect) {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.quizpopup_result, null, false);

        TextView tvDetail = dialogView.findViewById(R.id.resultDetail);
        Button btnYes = dialogView.findViewById(R.id.btnYes);
        Button btnNo = dialogView.findViewById(R.id.btnNo);

        tvDetail.setText(isCorrect ? "Correct" : "Incorrect");

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setView(dialogView)
                .setCancelable(false)
                .create();

        btnYes.setText("Next");
        btnYes.setOnClickListener(v -> {
            quizList.remove(currentWord);
            loadNewQuestion();
            dialog.dismiss();
        });

        btnNo.setText("Exit");
        btnNo.setOnClickListener(v -> finishQuiz());

        dialog.show();
    }

    private void finishQuiz() {
        Intent resultIntent = new Intent();
        resultIntent.putParcelableArrayListExtra("wordList", (ArrayList<Word>) wordList);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
