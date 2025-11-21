package com.example.lingonary;



import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.WordAdapter;
import com.example.lingonary.models.Word;

import java.util.ArrayList;
import java.util.List;
// Class implementing the word library fragment inside Main function
public class WordLibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private ArrayList<Word> wordList;   // MUST be ArrayList
    private Button btnQuiz;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_library, container, false);

        recyclerView = view.findViewById(R.id.recyclerWords);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wordList = new ArrayList<>();

        // Example words
        wordList.add(new Word("Amor", "love"));
        wordList.add(new Word("Tranquilo", "quiet"));
        wordList.add(new Word("Seguro", "safe"));
        wordList.add(new Word("Ocho", "eight"));
        wordList.add(new Word("Vamos", "let's go"));

        adapter = new WordAdapter(wordList);
        recyclerView.setAdapter(adapter);

        // ---------------------------
        // FIXED QUIZ BUTTON HANDLER
        // ---------------------------
        btnQuiz.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), QuizActivity.class);
            intent.putParcelableArrayListExtra("wordLibrary", wordList);
            startActivity(intent);
        });

        return view;
    }

    // Add words from PodcastActivity
    public void addWord(String learning, String nativeWord) {
        Word newWord = new Word(learning, nativeWord);
        wordList.add(newWord);
        adapter.notifyItemInserted(wordList.size() - 1);
    }

    // Return the full list for MainActivity
    public ArrayList<Word> getWords() {
        return wordList;
    }
}