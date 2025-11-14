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

import java.util.ArrayList;

// Main class, hold fragments for main menu and word library so they share activity.
public class MainActivity extends AppCompatActivity implements HomeFragment.OnHomeFragmentListener {

    private Fragment homeFragment = new HomeFragment();
    private WordLibraryFragment wordLibraryFragment = new WordLibraryFragment();
    private Fragment active = homeFragment;

    private ActivityResultLauncher<Intent> podcastLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Section in charge of navigation between home and word library
        BottomNavigationView bottomNav = findViewById(R.id.bottomNav);
        FragmentManager fm = getSupportFragmentManager();

        fm.beginTransaction()
                .add(R.id.fragment_container, wordLibraryFragment, "2")
                .hide(wordLibraryFragment)
                .commit();

        fm.beginTransaction()
                .add(R.id.fragment_container, homeFragment, "1")
                .commit();

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

        // Transition between the main activiy and the podcast, used to get the added words from there back to word library.
        podcastLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        ArrayList<Word> newWords = null;
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
                });
    }

    @Override
    public void onOpenPodcast() {
        Intent intent = new Intent(this, PodcastActivity.class);
        podcastLauncher.launch(intent);
    }
}
