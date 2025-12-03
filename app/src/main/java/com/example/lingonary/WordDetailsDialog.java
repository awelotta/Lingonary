package com.example.lingonary;

import android.app.Dialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.lingonary.models.Word;

public class WordDetailsDialog extends DialogFragment {

    private static final String ARG_WORD = "arg_word";
    private Word word;
    private WordDetailsListener listener;

    public interface WordDetailsListener {
        void onDeleteWord(Word word);
    }

    public static WordDetailsDialog newInstance(Word word, WordDetailsListener listener) {
        WordDetailsDialog dialog = new WordDetailsDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_WORD, word);
        dialog.setArguments(args);
        dialog.listener = listener;
        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        word = getArguments().getParcelable(ARG_WORD);

        return new AlertDialog.Builder(requireContext())
                .setTitle(word.getLearning())
                .setMessage(
                        "Translation: " + word.getNativeLang() +
                                "\n\nDefinition:\n(You can later fetch dictionary API here)"
                )
                .setPositiveButton("Delete", (dialog, which) -> {
                    if (listener != null) listener.onDeleteWord(word);
                })
                .setNegativeButton("Close", null)
                .create();
    }
}
