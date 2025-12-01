package com.example.lingonary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsFragment extends Fragment {

    private EditText etQuizLength;
    private EditText etMasteryThreshold;
    private SwitchMaterial switchIncludeMastered;
    private RadioGroup rgSortBy;
    private Button btnSendFeedback;
    private Button btnSave;

    private SharedPreferences sharedPreferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        sharedPreferences = requireActivity().getSharedPreferences("lingonary_prefs", Context.MODE_PRIVATE);

        etQuizLength = view.findViewById(R.id.etQuizLength);
        etMasteryThreshold = view.findViewById(R.id.etMasteryThreshold);
        switchIncludeMastered = view.findViewById(R.id.switchIncludeMastered);
        rgSortBy = view.findViewById(R.id.rgSortBy);
        btnSendFeedback = view.findViewById(R.id.btnSendFeedback);
        btnSave = view.findViewById(R.id.btnSave);

        loadSettings();

        btnSendFeedback.setOnClickListener(v -> {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "lingonary@illinois.edu", null));
            startActivity(Intent.createChooser(emailIntent, "Send feedback"));
        });

        btnSave.setOnClickListener(v -> {
            saveSettings();
            Toast.makeText(getContext(), "Settings Saved!", Toast.LENGTH_SHORT).show();
        });

        return view;
    }

    private void loadSettings() {
        etQuizLength.setText(String.valueOf(sharedPreferences.getInt("quiz_length", 10)));
        etMasteryThreshold.setText(String.valueOf(sharedPreferences.getInt("mastery_threshold", 3)));
        switchIncludeMastered.setChecked(sharedPreferences.getBoolean("include_mastered", false));

        int sortBy = sharedPreferences.getInt("sort_by", R.id.rbSortAlphabetical);
        rgSortBy.check(sortBy);
    }

    private void saveSettings() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("quiz_length", Integer.parseInt(etQuizLength.getText().toString()));
        editor.putInt("mastery_threshold", Integer.parseInt(etMasteryThreshold.getText().toString()));
        editor.putBoolean("include_mastered", switchIncludeMastered.isChecked());
        editor.putInt("sort_by", rgSortBy.getCheckedRadioButtonId());
        editor.apply();
    }
}
