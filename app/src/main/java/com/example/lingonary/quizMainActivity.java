package com.example.lingonary;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quizactivity_main);

       
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
                .inflate(R.layout.dialog_result, null, false);

        TextView tvDetail = dialogView.findViewById(R.id.tvResultDetail);
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

   
        btnNo.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
    }
}
