package com.example.lingonary;


import android.content.Intent;
import android.os.Bundle;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.lingonary.models.Word;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.example.lingonary.onboarding.OnboardingKeys;
import java.util.ArrayList;

// Main class, hold fragments for main menu and word library so they share activity.
public class MainActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentListener {

    private HomeFragment homeFragment;
    private WordLibraryFragment wordLibraryFragment = new WordLibraryFragment();
    private Fragment active;

    private ActivityResultLauncher<Intent> podcastLauncher;

    private String userName;
    private String targetLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        FragmentManager fm = getSupportFragmentManager();

        // Get onboarding data from intent
        userName = getIntent().getStringExtra(OnboardingKeys.EXTRA_USER_NAME);
        targetLanguage = getIntent().getStringExtra(OnboardingKeys.EXTRA_TARGET_LANGUAGE);

        if (userName == null) userName = "";
        if (targetLanguage == null) targetLanguage = "Language";

        // --- PASS DATA TO HOME FRAGMENT ---
        Bundle args = new Bundle();
        args.putString("username", userName);
        args.putString("targetLanguage", targetLanguage);

        homeFragment = new HomeFragment();
        homeFragment.setArguments(args);
        active = homeFragment;

        // Add fragments
        fm.beginTransaction()
                .add(R.id.fragment_container, wordLibraryFragment, "2")
                .hide(wordLibraryFragment)
                .commit();

        fm.beginTransaction()
                .add(R.id.fragment_container, homeFragment, "1")
                .commit();

        // Navigation
        bottomNav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                fm.beginTransaction().hide(active).show(homeFragment).commit();
                active = homeFragment;
            } else if (id == R.id.nav_word_library) {
                fm.beginTransaction().hide(active).show(wordLibraryFragment).commit();
                active = wordLibraryFragment;
            }
            return true;
        });

        // Podcast result handling
        podcastLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {

                        ArrayList<Word> newWords;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
                            newWords = result.getData().getParcelableArrayListExtra("newWords", Word.class);
                        } else {
                            newWords = result.getData().getParcelableArrayListExtra("newWords");
                        }

                        if (newWords != null) {
                            for (Word word : newWords) {
                                wordLibraryFragment.addWord(word.getLearning(), word.getNativeLang());
                            }
                        }
                    }
                }
        );
    }

    @Override
    public void onOpenPodcast() {
        Intent intent = new Intent(this, PodcastActivity.class);
        podcastLauncher.launch(intent);
    }

    public String getUserName() {
        return userName;
    }

    public String getTargetLanguage() {
        return targetLanguage;
    }
}
