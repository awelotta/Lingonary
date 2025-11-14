package com.example.lingonary;

import android.os.Bundle;
import android.widget.Button;
// Placeholder for the quiz, just a button to exit
import androidx.appcompat.app.AppCompatActivity;
public class QuizActivity extends AppCompatActivity{
    private Button btnExit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz_view);
        btnExit = findViewById(R.id.quizExit);

        btnExit.setOnClickListener(v -> finish());
    }

}
