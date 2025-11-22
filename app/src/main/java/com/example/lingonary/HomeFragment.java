package com.example.lingonary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

// All of the code for the home screen, basically only going to the podcast app.

public class HomeFragment extends Fragment {

    private OnHomeFragmentListener listener;

    public interface OnHomeFragmentListener {
        void onOpenPodcast();
        void onOpenTranscript();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnHomeFragmentListener) {
            listener = (OnHomeFragmentListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        TextView tvGreeting = view.findViewById(R.id.tvGreetingHome);
        Button btnLanguage = view.findViewById(R.id.btnCurrentLanguage);

        Bundle args = getArguments();
        if (args != null) {
            String username = args.getString("username");
            String targetLang = args.getString("targetLanguage");

            if (username != null && !username.isEmpty()) {
                tvGreeting.setText("Hello " + username + "!");
            } else {
                tvGreeting.setText("Hello!");
            }

            if (targetLang != null && !targetLang.isEmpty()) {
                btnLanguage.setText(targetLang);
            } else {
                btnLanguage.setText("Language");
            }
        }
        //Calling a function to move to the podcast activity, implemented on the main activity.
        View btnPodcast1 = view.findViewById(R.id.podcastBtn1);
        View btnPodcast2 = view.findViewById(R.id.podcastBtn2);

        btnPodcast1.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOpenPodcast();
            }
        });
        btnPodcast2.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOpenTranscript();
            }
        });

        return view;
    }
}
