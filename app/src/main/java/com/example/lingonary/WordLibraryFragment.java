package com.example.lingonary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.WordAdapter;
import com.example.lingonary.database.WordDatabase;
import com.example.lingonary.models.Word;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class WordLibraryFragment extends Fragment {

    private RecyclerView recyclerHaventStarted, recyclerLearning, recyclerMastered;
    private WordAdapter haventStartedAdapter, learningAdapter, masteredAdapter;
    private List<Word> wordList = new ArrayList<>();
    private Button btnQuiz;
    private WordDatabase db;
    private ActivityResultLauncher<Intent> quizLauncher;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        quizLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();

                        if (data != null) {
                            ArrayList<Word> updatedWords =
                                    data.getParcelableArrayListExtra("wordList");

                            if (updatedWords != null) {
                                new Thread(() -> {
                                    for (Word w : updatedWords) {
                                        db.wordDao().updateWord(w);
                                    }
                                }).start();
                            }
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_word_library, container, false);

        recyclerHaventStarted = view.findViewById(R.id.recyclerHaventStarted);
        recyclerLearning = view.findViewById(R.id.recyclerLearning);
        recyclerMastered = view.findViewById(R.id.recyclerMastered);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        recyclerHaventStarted.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLearning.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMastered.setLayoutManager(new LinearLayoutManager(getContext()));

        db = WordDatabase.getInstance(requireContext());

        observeWordList();

        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putParcelableArrayListExtra("wordList", new ArrayList<>(wordList));
            quizLauncher.launch(intent);
        });

        return view;
    }

    private void observeWordList() {
        db.wordDao().getAllWords().observe(getViewLifecycleOwner(), words -> {
            if (words == null) return;

            // Initial seed if DB is empty
            if (words.isEmpty()) {
                seedSampleWords();
                return;
            }

            wordList = words;
            updateWordLists();
        });
    }

    private void seedSampleWords() {
        new Thread(() -> {
            db.wordDao().insertWord(new Word("Amor", "love"));
            db.wordDao().insertWord(new Word("Tranquilo", "quiet"));
            db.wordDao().insertWord(new Word("Seguro", "safe"));
            db.wordDao().insertWord(new Word("Ocho", "eight"));
            db.wordDao().insertWord(new Word("Vamos", "let's go"));
        }).start();
    }

    private void updateWordLists() {
        SharedPreferences prefs = requireActivity()
                .getSharedPreferences("lingonary_prefs", Context.MODE_PRIVATE);

        int masteryThreshold = prefs.getInt("mastery_threshold", 3);
        int sortBy = prefs.getInt("sort_by", R.id.rbSortAlphabetical);

        List<Word> haventStarted = wordList.stream()
                .filter(w -> !w.hasBeenInQuiz())
                .collect(Collectors.toList());

        List<Word> learning = wordList.stream()
                .filter(w -> w.hasBeenInQuiz() && w.getTimesCorrect() < masteryThreshold)
                .collect(Collectors.toList());

        List<Word> mastered = wordList.stream()
                .filter(w -> w.getTimesCorrect() >= masteryThreshold)
                .collect(Collectors.toList());

        Comparator<Word> alphabet = Comparator.comparing(Word::getLearning);
        Comparator<Word> byDate = Comparator.comparingLong(Word::getDateAdded).reversed();

        if (sortBy == R.id.rbSortByDate) {
            haventStarted.sort(byDate);
            learning.sort(byDate);
            mastered.sort(byDate);
        } else {
            haventStarted.sort(alphabet);
            learning.sort(alphabet);
            mastered.sort(alphabet);
        }

        haventStartedAdapter = new WordAdapter(haventStarted);
        learningAdapter = new WordAdapter(learning);
        masteredAdapter = new WordAdapter(mastered);

        haventStartedAdapter.setOnWordClickListener(this::openWordDetailsPopup);
        learningAdapter.setOnWordClickListener(this::openWordDetailsPopup);
        masteredAdapter.setOnWordClickListener(this::openWordDetailsPopup);

        recyclerHaventStarted.setAdapter(haventStartedAdapter);
        recyclerLearning.setAdapter(learningAdapter);
        recyclerMastered.setAdapter(masteredAdapter);
    }

    private void openWordDetailsPopup(Word word) {
        WordDetailsDialog dialog = WordDetailsDialog.newInstance(
                word,
                deletedWord -> new Thread(() -> {
                    db.wordDao().deleteWord(deletedWord);
                }).start()
        );

        dialog.show(getParentFragmentManager(), "wordDetailsPopup");
    }

    public void addWord(String learning, String nativeWord) {
        new Thread(() -> db.wordDao().insertWord(new Word(learning, nativeWord))).start();
    }
}
