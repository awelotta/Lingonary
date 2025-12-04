package com.example.lingonary;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.lingonary.adapters.PodcastAdapter;
import com.example.lingonary.models.Podcast;

import java.util.ArrayList;
import java.util.List;

public class PodcastListDialogFragment extends DialogFragment {

    public static PodcastListDialogFragment newInstance() {
        return new PodcastListDialogFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_podcast_list, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Set the width of the dialog to 90% of the screen width
        Window window = getDialog().getWindow();
        Point size = new Point();
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        window.setLayout((int) (size.x * 0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvPodcasts = view.findViewById(R.id.rvPodcasts);
        rvPodcasts.setLayoutManager(new LinearLayoutManager(getContext()));

        String description = "This is a podcast app built by Team Spaghetti during the fall semester of 2025.";
        List<Podcast> podcastList = new ArrayList<>();
        podcastList.add(new Podcast("Maxi Ferraro: “Lo ideal sería que el Gobierno no se radicalice”", description));
        podcastList.add(new Podcast("Podcast 2", description));
        podcastList.add(new Podcast("Podcast 3", description));
        podcastList.add(new Podcast("Podcast 4", description));
        podcastList.add(new Podcast("Podcast 5", description));
        podcastList.add(new Podcast("Podcast 6", description));

        PodcastAdapter adapter = new PodcastAdapter(podcastList, podcast -> {
            PodcastDetailDialogFragment.newInstance(podcast).show(getParentFragmentManager(), "podcast_detail_dialog");
            dismiss(); // Close the list dialog
        });
        rvPodcasts.setAdapter(adapter);
    }
}
