package com.example.lingonary;

import android.app.Activity;
import android.content.Intent;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.WordAdapter;
import com.example.lingonary.models.Word;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class WordLibraryFragment extends Fragment {

    private RecyclerView recyclerHaventStarted;
    private RecyclerView recyclerLearning;
    private RecyclerView recyclerMastered;
    private WordAdapter haventStartedAdapter;
    private WordAdapter learningAdapter;
    private WordAdapter masteredAdapter;
    private List<Word> wordList;
    private Button btnQuiz;

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
                            wordList = data.getParcelableArrayListExtra("wordList");
                            updateWordLists();
                        }
                    }
                });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_library, container, false);

        recyclerHaventStarted = view.findViewById(R.id.recyclerHaventStarted);
        recyclerLearning = view.findViewById(R.id.recyclerLearning);
        recyclerMastered = view.findViewById(R.id.recyclerMastered);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        recyclerHaventStarted.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerLearning.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerMastered.setLayoutManager(new LinearLayoutManager(getContext()));

        if (wordList == null) {
            wordList = new ArrayList<>();
            wordList.add(new Word("Amor", "love"));
            wordList.add(new Word("Tranquilo", "quiet"));
            wordList.add(new Word("Seguro", "safe"));
            wordList.add(new Word("Ocho", "eight"));
            wordList.add(new Word("Vamos", "let's go"));
        }

        updateWordLists();

        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putParcelableArrayListExtra("wordList", (ArrayList<Word>) wordList);
            quizLauncher.launch(intent);
        });

        return view;
    }

    private void updateWordLists() {
        if (wordList == null) return;
        List<Word> haventStartedList = wordList.stream().filter(w -> !w.hasBeenInQuiz()).collect(Collectors.toList());
        List<Word> learningList = wordList.stream().filter(w -> w.hasBeenInQuiz() && w.getTimesCorrect() < 3).collect(Collectors.toList());
        List<Word> masteredList = wordList.stream().filter(w -> w.getTimesCorrect() >= 3).collect(Collectors.toList());

        haventStartedAdapter = new WordAdapter(haventStartedList);
        learningAdapter = new WordAdapter(learningList);
        masteredAdapter = new WordAdapter(masteredList);

        recyclerHaventStarted.setAdapter(haventStartedAdapter);
        recyclerLearning.setAdapter(learningAdapter);
        recyclerMastered.setAdapter(masteredAdapter);
    }

    public void addWord(String learning, String nativeWord) {
        Word newWord = new Word(learning, nativeWord);
        wordList.add(newWord);
        updateWordLists();
    }
}
