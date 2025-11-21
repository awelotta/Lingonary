package com.example.lingonary;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.lingonary.models.Word;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizactivity_main);
        ArrayList<Word> wordLibrary;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            wordLibrary = getIntent().getParcelableArrayListExtra("wordLibrary", Word.class);
        } else {
            wordLibrary = getIntent().getParcelableArrayListExtra("wordLibrary");
        }

        if (wordLibrary == null) {
            wordLibrary = new ArrayList<>();
        }

        // Example: display the first word (just for testing)
        Log.d("QuizActivity", "Received words: " + wordLibrary.size());
       
        ImageView backArrow = findViewById(R.id.backArrow);
        backArrow.setOnClickListener(v -> finish());

     
        Button btnLetsGo = findViewById(R.id.btnLetsGo);
        Button btnHappy = findViewById(R.id.btnHappy);
        Button btnVictory = findViewById(R.id.btnVictory);
        Button btnPotato = findViewById(R.id.btnPotato);


        btnLetsGo.setOnClickListener(v -> showResultDialog(true));

       
        View.OnClickListener incorrectListener = v -> showResultDialog(false);
        btnHappy.setOnClickListener(incorrectListener);
        btnVictory.setOnClickListener(incorrectListener);
        btnPotato.setOnClickListener(incorrectListener);
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

     
        btnYes.setOnClickListener(v -> {
            // TODO: implement next question / screen
            dialog.dismiss();
        });

   
        btnNo.setOnClickListener(v -> finish());

        dialog.show();
    }
}
