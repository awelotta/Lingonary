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
import edu.illinois.cs465.wordlibrarytest.adapters.WordAdapter;
import edu.illinois.cs465.wordlibrarytest.models.Word;
import java.util.ArrayList;
import java.util.List;
// Class implementing the word library fragment inside Main function
public class WordLibraryFragment extends Fragment {

    private RecyclerView recyclerView;
    private WordAdapter adapter;
    private List<Word> wordList;
    private Button btnQuiz;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_word_library, container, false);
        // Part that manages the recycler view.
        recyclerView = view.findViewById(R.id.recyclerWords);
        btnQuiz = view.findViewById(R.id.btnQuiz);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        wordList = new ArrayList<>();

        // Example words
        wordList.add(new Word("Amor", "love"));
        wordList.add(new Word("Tranquilo", "quiet"));
        wordList.add(new Word("Seguro", "safe"));
        wordList.add(new Word("Ocho", "eight"));

        adapter = new WordAdapter(wordList);
        recyclerView.setAdapter(adapter);

        //Quiz button that moves to the Quiz Activity.
        btnQuiz.setOnClickListener(v ->
                { Intent intent = new Intent(getActivity(), QuizActivity.class);
                    startActivity(intent); }
        );

        return view;
    }
    // Function used to add words to the list from Main function.
    public void addWord(String learning, String nativeWord) {
        Word newWord = new Word(learning, nativeWord);
        wordList.add(newWord);
        adapter.notifyItemInserted(wordList.size() - 1);
    }

}
